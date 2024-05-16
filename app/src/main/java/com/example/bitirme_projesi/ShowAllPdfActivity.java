package com.example.bitirme_projesi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowAllPdfActivity extends MainActivity {

    private ListView pdfListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> pdfNames;
    private StorageReference storageReference;
    private FirebaseUser user;
    private int selectedPosition = -1; // Seçilen öğenin indeksini tutmak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_pdf);

        pdfListView = findViewById(R.id.pdfListView);
        pdfNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pdfNames);
        pdfListView.setAdapter(adapter);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Kullanıcının UID'si ile PDF'lerin bulunduğu klasörü referans alın
        storageReference = FirebaseStorage.getInstance().getReference().child("pdfs/" + user.getUid());

        // Cloud Storage'dan PDF dosyalarını çekin
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                // Dosya adını alın
                String pdfName = item.getName();
                // PDF adını listeye ekleyin
                pdfNames.add(pdfName);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Başarısızlık durumunda hata mesajı gösterin
            Toast.makeText(ShowAllPdfActivity.this, "Failed to read PDFs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        // ListView'daki öğeler için tıklama işleyicisi ekleyin
        pdfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Seçilen öğenin indeksini kaydedin
                selectedPosition = position;
            }
        });

        // Silme butonunu bul ve tıklama işleyicisi ekleyin
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Seçili bir öğe yoksa kullanıcıya mesaj gösterin
                if (selectedPosition == -1) {
                    Toast.makeText(ShowAllPdfActivity.this, "Select a PDF to delete", Toast.LENGTH_SHORT).show();
                } else {
                    // Seçilen öğenin adını alın
                    String selectedPdfName = pdfNames.get(selectedPosition);
                    // Firebase veritabanından seçilen PDF'yi kaldırın
                    storageReference.child(selectedPdfName).delete().addOnSuccessListener(aVoid -> {
                        // Kullanıcıya geribildirim gösterin
                        Toast.makeText(ShowAllPdfActivity.this, "PDF deleted: " + selectedPdfName, Toast.LENGTH_SHORT).show();
                        // Listeden öğeyi kaldırın ve ListView'i güncelleyin
                        pdfNames.remove(selectedPosition);
                        adapter.notifyDataSetChanged();
                        // Seçili pozisyonu sıfırlayın
                        selectedPosition = -1;
                    }).addOnFailureListener(e -> {
                        // Başarısızlık durumunda hata mesajı gösterin
                        Toast.makeText(ShowAllPdfActivity.this, "Failed to delete PDF: " + selectedPdfName, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}
