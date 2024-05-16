package com.example.bitirme_projesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
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

public class MainActivity extends AppCompatActivity  {

    ImageView bilmemGerekenler;
    ImageView yapmamGerekenler;
    ImageView logo;
    TextView Hosgeldin;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=database.getReference().child("profile");
    String hosgeldin;
    int number=1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_search:
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                Intent g = new Intent(MainActivity.this, search.class);
                startActivity(g);
                break;
            case R.id.menu_item_anaSayfa:
                Toast.makeText(getApplicationContext(), "Ana Sayfa", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.menu_item_bilmemGerekenler:
                Toast.makeText(getApplicationContext(), "Bilmem Gerekenler", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(MainActivity.this, bilmem_gerekenler.class);
                startActivity(a);
                break;
            case R.id.menu_item_yapmamGerekenler:
                Toast.makeText(getApplicationContext(), "Yapmam Gerekenler", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(MainActivity.this, yapmam_gerekenler.class);
                startActivity(b);
                break;
            case R.id.menu_item_yuklenecekBelgeler:
                Toast.makeText(getApplicationContext(), "Yüklenecek Belgeler", Toast.LENGTH_SHORT).show();
                Intent c = new Intent(MainActivity.this, yuklenecek_belgeler.class);
                startActivity(c);
                break;
            case R.id.menu_item_egitimler:
                Toast.makeText(getApplicationContext(), "Eğitimler", Toast.LENGTH_SHORT).show();
                Intent d = new Intent(MainActivity.this, egitimler.class);
                startActivity(d);
                break;
            case R.id.menu_item_cikis:
                Toast.makeText(getApplicationContext(), "Çıkış Yapıldı", Toast.LENGTH_SHORT).show();
                Intent f = new Intent(MainActivity.this, sign_up.class);
                startActivity(f);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bilmemGerekenler=findViewById(R.id.imageViewBilmemGerekenler);
        yapmamGerekenler=findViewById(R.id.imageViewYapmamGerekenler);
        logo=findViewById(R.id.imageViewLogo);
        Hosgeldin=findViewById(R.id.textViewHosgeldin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            merhaba(uid);
        }
        bilmemGerekenler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, bilmem_gerekenler.class);
                startActivity(i);

            }
        });

        yapmamGerekenler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, yapmam_gerekenler.class);
                startActivity(i);
            }
        });

    }
    public void merhaba(String uid){
        // Read from the database
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("adisoyadi").getValue(String.class);
                    userName = capitalizeFirstLetter(userName);
                    Hosgeldin.setText("Merhaba " + userName);
                } else {
                    // Kullanıcı verisi bulunamazsa bir hata mesajı göster
                    Toast.makeText(MainActivity.this, "Kullanıcı bulunamadı!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Sorry, there is a problem!", Toast.LENGTH_LONG).show();

            }
        });
    }
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}