package com.example.bitirme_projesi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Egitim_Sonuclari extends AppCompatActivity {

    TextView scoreCorrect1, scoreWrong1;
    TextView scoreCorrect2, scoreWrong2;
    TextView scoreCorrect3, scoreWrong3;
    TextView scoreCorrect4, scoreWrong4;

    TextView egitim_Bir, egitim_Iki, egitim_Uc, egitim_Dort;
    Button exit;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("scores").child(user.getUid());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitim_sonuclari);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scoreCorrect1 = findViewById(R.id.textViewAnswerCorrect1);
        scoreWrong1 = findViewById(R.id.textViewAnswerWrong1);
        scoreCorrect2 = findViewById(R.id.textViewAnswerCorrect2);
        scoreWrong2 = findViewById(R.id.textViewAnswerWrong2);
        scoreCorrect3 = findViewById(R.id.textViewAnswerCorrect3);
        scoreWrong3 = findViewById(R.id.textViewAnswerWrong3);
        scoreCorrect4 = findViewById(R.id.textViewAnswerCorrect4);
        scoreWrong4 = findViewById(R.id.textViewAnswerWrong4);
        exit = findViewById(R.id.buttonExit);
        egitim_Bir = findViewById(R.id.textViewegitimBir);
        egitim_Iki = findViewById(R.id.textViewegitim_iki);
        egitim_Uc = findViewById(R.id.textViewegitiUc);
        egitim_Dort = findViewById(R.id.textViewegitimDort);

        loadEducationNames(); // Eğitim adlarını yükle

        // Eğitim bir verilerini çek
        DatabaseReference egitimBirRef = databaseReference.child("egitim_bir");
        egitimBirRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long correct = dataSnapshot.child("correct").getValue(Long.class);
                    Long wrong = dataSnapshot.child("wrong").getValue(Long.class);
                    scoreCorrect1.setText(String.valueOf(correct));
                    scoreWrong1.setText(String.valueOf(wrong));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // İsteğin iptal edilmesi durumunda yapılacak işlemler
            }
        });

        // Eğitim iki verilerini çek
        DatabaseReference egitimIkiRef = databaseReference.child("egitim_iki");
        egitimIkiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long correct = dataSnapshot.child("correct").getValue(Long.class);
                    Long wrong = dataSnapshot.child("wrong").getValue(Long.class);
                    scoreCorrect2.setText(String.valueOf(correct));
                    scoreWrong2.setText(String.valueOf(wrong));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // İsteğin iptal edilmesi durumunda yapılacak işlemler
            }
        });

        // Eğitim üç verilerini çek
        DatabaseReference egitimUcRef = databaseReference.child("egitim_uc");
        egitimUcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long correct = dataSnapshot.child("correct").getValue(Long.class);
                    Long wrong = dataSnapshot.child("wrong").getValue(Long.class);
                    scoreCorrect3.setText(String.valueOf(correct));
                    scoreWrong3.setText(String.valueOf(wrong));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // İsteğin iptal edilmesi durumunda yapılacak işlemler
            }
        });

        // Eğitim dört verilerini çek
        DatabaseReference egitimDortRef = databaseReference.child("egitim_dort");
        egitimDortRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long correct = dataSnapshot.child("correct").getValue(Long.class);
                    Long wrong = dataSnapshot.child("wrong").getValue(Long.class);
                    scoreCorrect4.setText(String.valueOf(correct));
                    scoreWrong4.setText(String.valueOf(wrong));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // İsteğin iptal edilmesi durumunda yapılacak işlemler
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Eğitim adlarını çek ve ilgili TextView'lere yerleştir
    private void loadEducationNames() {
        DatabaseReference educationRef = FirebaseDatabase.getInstance().getReference().child("education");

        educationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String educationNumber = snapshot.getKey();
                        String educationName = snapshot.child("name").getValue(String.class);

                        // Her eğitimin adını ilgili TextView'e yerleştir
                        switch (educationNumber) {
                            case "1":
                                egitim_Bir.setText(educationName);
                                break;
                            case "2":
                                egitim_Iki.setText(educationName);
                                break;
                            case "3":
                                egitim_Uc.setText(educationName);
                                break;
                            case "4":
                                egitim_Dort.setText(educationName);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Veritabanı erişiminde hata oluştuğunda yapılacak işlemler
            }
        });
    }
}
