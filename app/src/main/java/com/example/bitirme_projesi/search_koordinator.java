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

public class search_koordinator extends MainActivity implements SearchView.OnQueryTextListener {

    private SearchView maramaKutusu;
    private ListView mliste;

    String [] AramaListesi ={"Ana Sayfa","Eğitim Adı Düzenle","Soru Düzenle"};

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
                if (selectedItem.equals("Ana Sayfa")) {
                    Intent intent = new Intent(search_koordinator.this, egitim_or_soru.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Eğitim Adı Düzenle")) {
                    Intent intent = new Intent(search_koordinator.this, Egitim_adi_duzenle.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Soru Düzenle")) {
                    Intent intent = new Intent(search_koordinator.this, soru_duzenle.class);
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
