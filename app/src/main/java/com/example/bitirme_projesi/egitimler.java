package com.example.bitirme_projesi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class egitimler extends MainActivity {

    CheckBox egitimbir;
    CheckBox egitimiki;
    CheckBox egitimuc;
    CheckBox egitimdort;
    TextView almanGerekenler;
    TextView bilgilendirme;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitimler);

        egitimbir = findViewById(R.id.checkBoxegitimbir);
        egitimiki = findViewById(R.id.checkBoxegitimiki);
        egitimuc = findViewById(R.id.checkBoxegitimuc);
        egitimdort = findViewById(R.id.checkBoxegitimdort);
        almanGerekenler = findViewById(R.id.textViewAlmanGerekenEgitimler);
        bilgilendirme = findViewById(R.id.textViewBilgi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // CheckBox'lar için ilgili düğümleri almak için metodu çağır
        getEducationDataForCheckBox("1", egitimbir, egitim_bir.class);
        getEducationDataForCheckBox("2", egitimiki, egitim_iki.class);
        getEducationDataForCheckBox("3", egitimuc, egitim_uc.class);
        getEducationDataForCheckBox("4", egitimdort, egitim_dort.class);

        bilgilendirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(egitimler.this, Egitim_Sonuclari.class);
                startActivity(i);
            }
        });
    }

    // Her bir CheckBox için ilgili düğümü alacak metodu tanımla
    private void getEducationDataForCheckBox(String nodeNumber, CheckBox checkBox, Class destinationClass) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("education").child(nodeNumber);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String egitimAdi = dataSnapshot.child("name").getValue(String.class);
                    checkBox.setText(egitimAdi);

                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(egitimler.this, destinationClass);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    Toast.makeText(egitimler.this, "Eğitim verisi bulunamadı!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(egitimler.this, "Üzgünüz, bir problem var!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
