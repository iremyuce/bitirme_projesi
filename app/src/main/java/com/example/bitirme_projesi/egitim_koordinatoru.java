package com.example.bitirme_projesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitirme_projesi.databinding.ActivityMainBinding;
import com.example.bitirme_projesi.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class egitim_koordinatoru extends AppCompatActivity {
    EditText mail;
    EditText password;
    Button signIn;
    ProgressBar progressBar;
    TextView forgotPassword;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("koordinator_profile");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitim_koordinatoru);
        mail=findViewById(R.id.editTextEmailAddress);
        password=findViewById(R.id.editTextPassword);
        signIn=findViewById(R.id.buttonSignIn);
        forgotPassword=findViewById(R.id.textViewForgotPassword);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail= mail.getText().toString();
                String userPassword= password.getText().toString();
                signIpWithFirebase(userEmail,userPassword);

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(egitim_koordinatoru.this, forgotPassword.class);
                startActivity(i);


            }
        });
    }
    public void signIpWithFirebase(String userEmail, String userPassword) {
        progressBar.setVisibility(View.VISIBLE);
        signIn.setClickable(false);

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null) {
                                // Kullanıcı id sini alıp o id adı altında adımızı ve soyadımızı kaydedelim
                                DatabaseReference currentUserDb = databaseReference.child(currentUser.getUid());
                                String userName = extractUsernameFromEmail(userEmail);
                                currentUserDb.child("adisoyadi").setValue(userName);
                                Toast.makeText(egitim_koordinatoru.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                            }
                            Intent i= new Intent(egitim_koordinatoru.this, egitim_or_soru.class);
                            startActivity(i);
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(egitim_koordinatoru.this,"Sign In Is Successful ",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(egitim_koordinatoru.this,"Sign In Is Not Successful ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // E-posta adresinden kullanıcı adını çıkaran fonksiyon
    public String extractUsernameFromEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "";
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return "";
        }

        return email.substring(0, atIndex);
    }
}
