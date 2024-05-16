package com.example.bitirme_projesi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class yapmam_gerekenler extends MainActivity {

    ImageView belgeler;
    ImageView egitim;
    TextView yapmamGereken;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yapmam_gerekenler);

        belgeler=findViewById(R.id.imageViewyuklenecekBelgeler);
        egitim=findViewById(R.id.imageViewEgitimler);
        yapmamGereken=findViewById(R.id.textViewYapmamGerekenler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        belgeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(yapmam_gerekenler.this, yuklenecek_belgeler.class);
                startActivity(i);
            }
        });

        egitim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(yapmam_gerekenler.this, egitimler.class);
                startActivity(i);
            }
        });
    }
}