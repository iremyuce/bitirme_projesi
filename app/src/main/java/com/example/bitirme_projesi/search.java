package com.example.bitirme_projesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class search extends MainActivity implements SearchView.OnQueryTextListener {

    private SearchView maramaKutusu;
    private ListView mliste;

    String [] AramaListesi ={"Bilmem Gerekenler","Yapman Gerekenler","Eğitimler","Ana Sayfa","Yüklenecek Belgeler","Buddy","Servis","Çalışma Alanın","Yemek"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        maramaKutusu=(SearchView) findViewById(R.id.aramaKutusu);
        mliste=(ListView) findViewById(R.id.liste);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,android.R.id.text1,AramaListesi);
        mliste.setAdapter(adapter);
        mliste.setTextFilterEnabled(true);
        setupArama();

        // ListView öğelerine tıklama olayını dinlemek için
        mliste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tıklanan öğenin adını alın
                String selectedItem = AramaListesi[position];

                // Tıklanan öğeye göre yönlendirme yapın
                if (selectedItem.equals("Bilmem Gerekenler")) {
                    Intent intent = new Intent(search.this, bilmem_gerekenler.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Yapmam Gerekenler")) {
                    Intent intent = new Intent(search.this, yapmam_gerekenler.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Eğitimler")) {
                    Intent intent = new Intent(search.this, egitimler.class);
                    startActivity(intent);
                }
                else if (selectedItem.equals("Ana Sayfa")) {
                    Intent intent = new Intent(search.this, MainActivity.class);
                    startActivity(intent);
                }else if (selectedItem.equals("Yüklenecek Belgeler")) {
                    Intent intent = new Intent(search.this, yuklenecek_belgeler.class);
                    startActivity(intent);
                }
                else if (selectedItem.equals("Buddy")) {
                    Intent intent = new Intent(search.this, bilmem_gerekenler.class);
                    startActivity(intent);
                }
                else if (selectedItem.equals("Çalışma Alanın")) {
                    Intent intent = new Intent(search.this, bilmem_gerekenler.class);
                    startActivity(intent);
                }else if (selectedItem.equals("Yemek")) {
                    Intent intent = new Intent(search.this, bilmem_gerekenler.class);
                    startActivity(intent);
                }
                else if (selectedItem.equals("Servis")) {
                    Intent intent = new Intent(search.this, bilmem_gerekenler.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void setupArama(){
        maramaKutusu.setIconifiedByDefault(false);
        maramaKutusu.setOnQueryTextListener(this);
        maramaKutusu.setSubmitButtonEnabled(true);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(TextUtils.isEmpty(newText)){
            mliste.clearTextFilter();
        }else {
            mliste.setFilterText(newText);
        }
        return true;
    }
}
