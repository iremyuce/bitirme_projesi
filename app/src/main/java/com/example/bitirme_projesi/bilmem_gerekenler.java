package com.example.bitirme_projesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bilmem_gerekenler extends MainActivity {

    TextView yazi;
    ImageView servis;
    TextView plaka;
    ImageView yemek;
    TextView saat;
    ImageView calismaAlani;
    TextView Kuzey;
    ImageView buddy;
    TextView Mete;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilmem_gerekenler);

        yazi = findViewById(R.id.textViewOnyazi);
        servis = findViewById(R.id.imageViewServis);
        plaka = findViewById(R.id.textViewplaka);
        yemek = findViewById(R.id.imageViewYemek);
        saat = findViewById(R.id.textViewSaat);
        calismaAlani = findViewById(R.id.imageViewBuddy);
        Kuzey = findViewById(R.id.textViewKuzey);
        buddy = findViewById(R.id.imageViewCalismaAlanim);
        Mete = findViewById(R.id.textViewMete);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bilmemGerekenler();
    }

    public void bilmemGerekenler() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            // Read from the database
            databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String yaziBilgi = dataSnapshot.child("yazi").getValue(String.class);
                        String servisBilgi = dataSnapshot.child("servis").getValue(String.class);
                        String yemekBilgi = dataSnapshot.child("yemek").getValue(String.class);
                        String calismaAlaniBilgi = dataSnapshot.child("calisma_alani").getValue(String.class);
                        String buddyBilgi = dataSnapshot.child("buddy").getValue(String.class);

                        yazi.setText(yaziBilgi);
                        plaka.setText(servisBilgi);
                        saat.setText(yemekBilgi);
                        Kuzey.setText(calismaAlaniBilgi);
                        Mete.setText(buddyBilgi);
                    } else {
                        Toast.makeText(bilmem_gerekenler.this, "Kullanıcı verisi bulunamadı!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(bilmem_gerekenler.this, "Sorry, there is a problem!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(bilmem_gerekenler.this, "Kullanıcı bulunamadı!", Toast.LENGTH_LONG).show();
        }
    }
}
