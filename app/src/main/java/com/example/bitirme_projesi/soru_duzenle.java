package com.example.bitirme_projesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class soru_duzenle extends egitim_or_soru {
    EditText editTextEducationName, editTextQuestion, editTextOptionA, editTextOptionB, editTextOptionC, editTextOptionD, editTextAnswer;
    Button buttonSaveQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_duzenle);

        editTextEducationName = findViewById(R.id.editTextEducationName);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextOptionA = findViewById(R.id.editTextOptionA);
        editTextOptionB = findViewById(R.id.editTextOptionB);
        editTextOptionC = findViewById(R.id.editTextOptionC);
        editTextOptionD = findViewById(R.id.editTextOptionD);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        buttonSaveQuestion = findViewById(R.id.buttonSaveQuestion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestionToFirebase();
            }
        });
    }

    private void saveQuestionToFirebase() {
        String educationName = editTextEducationName.getText().toString().trim();
        String question = editTextQuestion.getText().toString().trim();
        String optionA = editTextOptionA.getText().toString().trim();
        String optionB = editTextOptionB.getText().toString().trim();
        String optionC = editTextOptionC.getText().toString().trim();
        String optionD = editTextOptionD.getText().toString().trim();
        String answer = editTextAnswer.getText().toString().trim();

        DatabaseReference questionsRef = FirebaseDatabase.getInstance().getReference().child("questions").child(educationName);

        // Belirli bir düğüm altındaki tüm çocuk düğümlerini dinleyerek sayılarını al
        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long questionCount = dataSnapshot.getChildrenCount();
                // Soru numarasını oluştur
                String questionId = "soru" + (questionCount + 1);
                // Soruyu Firebase veritabanına kaydetme
                questionsRef.child(questionId).child("question").setValue(question);
                questionsRef.child(questionId).child("optionA").setValue(optionA);
                questionsRef.child(questionId).child("optionB").setValue(optionB);
                questionsRef.child(questionId).child("optionC").setValue(optionC);
                questionsRef.child(questionId).child("optionD").setValue(optionD);
                questionsRef.child(questionId).child("answer").setValue(answer);

                Toast.makeText(soru_duzenle.this, "Soru başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Hata durumunda yapılacaklar
                Toast.makeText(soru_duzenle.this, "Soru kaydedilirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
