package com.example.bitirme_projesi;

import static com.example.bitirme_projesi.R.id.belgeListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class yuklenecek_belgeler extends MainActivity {

    private ListView belgeListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> belgeList;
    private ArrayList<String> belgeKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuklenecek_belgeler);

        belgeListView = findViewById(R.id.belgeListView);
        belgeList = new ArrayList<>();
        belgeKeys = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, belgeList);
        belgeListView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference belgelerRef = database.getReference("yuklenecekBelgeler").child("1");

        belgelerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                belgeList.clear();
                belgeKeys.clear();
                for (DataSnapshot belgeSnapshot : dataSnapshot.getChildren()) {
                    String belgeAdi = belgeSnapshot.getValue(String.class);
                    String belgeIndex = belgeSnapshot.getKey();
                    belgeList.add(belgeAdi);
                    belgeKeys.add(belgeIndex);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hata durumunda buraya düşer
                Toast.makeText(yuklenecek_belgeler.this, "Failed to read documents: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // ListView'daki öğeler için tıklama işleyicisi ekleyin
        belgeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String belgeIndex = belgeKeys.get(position);
                Intent intent = new Intent(yuklenecek_belgeler.this, pdf_upload.class);
                intent.putExtra("belgeIndex", belgeIndex);
                startActivity(intent);
            }
        });
    }
}
