package com.example.bitirme_projesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Egitim_adi_duzenle extends egitim_or_soru {

    private EditText editTextEducationName;
    private Button buttonAddEducation;

    private DatabaseReference databaseReference;
    private int educationCount = 1; // Başlangıçta eğitim sayısı 1 olarak ayarlandı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitim_adi_duzenle);

        editTextEducationName = findViewById(R.id.editText_education_name);
        buttonAddEducation = findViewById(R.id.button_add_education);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("education");

        buttonAddEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEducation();
            }
        });
    }

    private void addEducation() {
        String educationName = editTextEducationName.getText().toString().trim();

        if (!educationName.isEmpty()) {
            // Eğitim adını sabit olarak "1", "2", "3" gibi numaralarla kaydet
            databaseReference.child(String.valueOf(educationCount)).child("name").setValue(educationName);
            educationCount++; // Bir sonraki eğitim için sayacı artır
            editTextEducationName.setText("");
        }
    }
}
