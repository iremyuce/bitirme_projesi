package com.example.bitirme_projesi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.documentfile.provider.DocumentFile;

import com.example.bitirme_projesi.databinding.ActivityPdfUploadBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class pdf_upload extends MainActivity {

    private @NonNull ActivityPdfUploadBinding binding;
    private Uri pdfFileUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ActivityResultLauncher<String> launcher;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPdfUploadBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storageReference = FirebaseStorage.getInstance().getReference().child("pdfs");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("pdfs");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                pdfFileUri = uri;
                DocumentFile documentFile = DocumentFile.fromSingleUri(pdf_upload.this, uri);
                String fileName = documentFile.getName();
                binding.fileName.setText(fileName);
            }
        });

        binding.selectPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });
        binding.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPdfFileToFirebase();
            }
        });

        binding.showAllPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show All PDF buttonuna tıklandığında belirli bir sınıfa yönlendirme kodunu buraya ekleyin
                Intent intent = new Intent(pdf_upload.this, ShowAllPdfActivity.class); // Yönlendirilecek sınıfın adını ShowAllPdfActivity olarak varsayalım
                startActivity(intent);
            }
        });

    }

    private void selectPdf() {
        launcher.launch("application/pdf");
    }

    private void uploadPdfFileToFirebase() {
        if (pdfFileUri != null) {
            String fileName = binding.fileName.getText().toString();
            String uid = user.getUid();
            String storagePath = uid + "/" + fileName;
            StorageReference mStorageRef = storageReference.child(storagePath);

            if (pdfFileUri != null) {
                mStorageRef.putFile(pdfFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                PdfFile pdfFile = new PdfFile(fileName, downloadUri.toString());
                                databaseReference.child(uid).child(fileName).setValue(pdfFile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(pdf_upload.this, "PDF successfully uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(pdf_upload.this, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        int uploadingPercent = (int) (snapshot.getBytesTransferred() * 100 / snapshot.getTotalByteCount());
                        binding.progressBar.setProgress(uploadingPercent);

                        if (!binding.progressBar.isShown()) {
                            binding.progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pdf_upload.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        if (binding.progressBar.isShown()) {
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

            // PDF'i yükleme işlemi burada devam eder...
        } else {
            Toast.makeText(this, "Please select pdf first", Toast.LENGTH_SHORT).show();
        }
    }
}