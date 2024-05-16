package com.example.bitirme_projesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class kullanici extends AppCompatActivity {
    ImageButton koordinator;
    TextView calisan;
    ImageButton calisan_image;
    TextView egitim_koordinatoru;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici);

        calisan = findViewById(R.id.textViewcalisan);
        koordinator=findViewById(R.id.imageButtonegitim_koordiantoru);
        calisan_image=findViewById(R.id.imageButtoncalisan);
        egitim_koordinatoru = findViewById(R.id.textViewkoordinator);

        koordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(kullanici.this, egitim_koordinatoru.class);
                startActivity(i);

            }
        });

        calisan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(kullanici.this, sign_up.class);
                startActivity(i);

            }
        });


    }
}