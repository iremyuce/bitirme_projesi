package com.example.bitirme_projesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class egitim_or_soru extends AppCompatActivity {

    Button egitimAd;
    Button Soru;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_koordinator,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_koordinator_search:
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                Intent g = new Intent(egitim_or_soru.this, search_koordinator.class);
                startActivity(g);
                break;
            case R.id.menu_item_koordinator_anaSayfa:
                Toast.makeText(getApplicationContext(), "Ana Sayfa", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(egitim_or_soru.this, egitim_or_soru.class);
                startActivity(i);
                break;
            case R.id.menu_item_koordinator_egitim_adi_duzenle:
                Toast.makeText(getApplicationContext(), "Eğitim Adı Düzenle", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(egitim_or_soru.this, Egitim_adi_duzenle.class);
                startActivity(a);
                break;
            case R.id.menu_item_koordinator_soru_duzenle:
                Toast.makeText(getApplicationContext(), "Soru Düzenle", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(egitim_or_soru.this, soru_duzenle.class);
                startActivity(b);
                break;
            case R.id.menu_item_koordinator_cikis:
                Toast.makeText(getApplicationContext(), "Çıkış Yapıldı", Toast.LENGTH_SHORT).show();
                Intent f = new Intent(egitim_or_soru.this,egitim_koordinatoru.class);
                startActivity(f);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitim_or_soru);

        egitimAd=findViewById(R.id.buttonEgitimAdDuzenle);
        Soru=findViewById(R.id.buttonSoruDuzenle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        egitimAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(egitim_or_soru.this, Egitim_adi_duzenle.class);
                startActivity(i);
            }
        });

        Soru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(egitim_or_soru.this, soru_duzenle.class);
                startActivity(i);
            }
        });
    }
}