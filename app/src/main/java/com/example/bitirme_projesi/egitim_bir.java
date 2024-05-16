package com.example.bitirme_projesi;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bitirme_projesi.MainActivity;
import com.example.bitirme_projesi.R;
import com.example.bitirme_projesi.egitimler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class egitim_bir extends AppCompatActivity {

    TextView time,correct,wrong;
    TextView question,a,b,c,d;
    Button next, finish;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference= database.getReference().child("questions").child("Deneme");

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference databaseReferenceSecond = database.getReference();

    String quizQuestions;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String quizCorrectAnswer;
    int questionCount;
    int questionNumber=1;
    String userAnswer;
    int userCorrect=0;
    int userWrong=0;
    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 30000;
    Boolean timerContinue;
    long timeLeft = TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitim_bir);

        time=findViewById(R.id.textViewTime);
        correct=findViewById(R.id.TextViewCorrect);
        wrong=findViewById(R.id.textViewWrong);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        question=findViewById(R.id.textViewQuestions);

        a=findViewById(R.id.textViewA);
        b=findViewById(R.id.textViewB);
        c=findViewById(R.id.textViewC);
        d=findViewById(R.id.textViewD);

        next=findViewById(R.id.buttonNext);
        finish=findViewById(R.id.buttonFinish);

        game();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                game();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendScore();
                Intent i = new Intent(egitim_bir.this, egitimler.class);
                startActivity(i);
                finish();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="a";
                checkAnswer(userAnswer);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="b";
                checkAnswer(userAnswer);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="c";
                checkAnswer(userAnswer);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="d";
                checkAnswer(userAnswer);
            }
        });
    }

    public void game(){
        startTimer();
        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionCount = (int) dataSnapshot.getChildrenCount();

                DataSnapshot currentQuestionSnapshot = dataSnapshot.child("soru"+questionNumber);
                if (currentQuestionSnapshot.exists()) {
                    quizCorrectAnswer = currentQuestionSnapshot.child("answer").getValue(String.class);
                    quizAnswerA = currentQuestionSnapshot.child("optionA").getValue(String.class);
                    quizAnswerB = currentQuestionSnapshot.child("optionB").getValue(String.class);
                    quizAnswerC = currentQuestionSnapshot.child("optionC").getValue(String.class);
                    quizAnswerD = currentQuestionSnapshot.child("optionD").getValue(String.class);
                    quizQuestions = currentQuestionSnapshot.child("question").getValue(String.class);

                    // Diğer işlemler...
                } else {
                    Log.e("Firebase", "Current question does not exist");
                    // Hata mesajı veya diğer işlemler...
                }



                question.setText(quizQuestions);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);

                if(questionNumber < questionCount) {
                    questionNumber++;
                } else {
                    Toast.makeText(egitim_bir.this, "You answered all questions", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "onCancelled called", error.toException());
                Toast.makeText(egitim_bir.this, "Failed to read value. Check log for details.", Toast.LENGTH_LONG).show();
                Toast.makeText(egitim_bir.this, "Sorry, there is a problem!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkAnswer(String userAnswer) {
        if(quizCorrectAnswer.equals(userAnswer)) {
            setColorAndScore(Color.GREEN, 1);
        } else {
            setColorAndScore(Color.RED, 0);
            findAnswer();
        }
    }

    public void setColorAndScore(int color, int increment) {
        switch(userAnswer) {
            case "a":
                a.setBackgroundColor(color);
                break;
            case "b":
                b.setBackgroundColor(color);
                break;
            case "c":
                c.setBackgroundColor(color);
                break;
            case "d":
                d.setBackgroundColor(color);
                break;
        }
        userCorrect += increment;
        correct.setText(String.valueOf(userCorrect));
    }

    public void findAnswer() {
        switch(quizCorrectAnswer) {
            case "a":
                a.setBackgroundColor(Color.GREEN);
                break;
            case "b":
                b.setBackgroundColor(Color.GREEN);
                break;
            case "c":
                c.setBackgroundColor(Color.GREEN);
                break;
            case "d":
                d.setBackgroundColor(Color.GREEN);
                break;
        }
        userWrong++;
        wrong.setText(String.valueOf(userWrong));
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerContinue = false;
                pauseTimer();
                question.setText("Sorry, time is up");
            }
        }.start();
        timerContinue = true;
    }

    public void resetTimer() {
        timeLeft = TOTAL_TIME;
        updateCountDownText();
    }

    public void updateCountDownText() {
        int second = (int)(timeLeft / 1000) % 60;
        time.setText("" + second);
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        timerContinue = false;
    }

    public void sendScore() {
        String userUID = user.getUid();
        DatabaseReference userRef = databaseReferenceSecond.child("scores").child(userUID);

        // Eğitim bir için doğru ve yanlış cevapları kaydet
        DatabaseReference egitimBirRef = userRef.child("egitim_bir");
        egitimBirRef.child("correct").setValue(userCorrect);
        egitimBirRef.child("wrong").setValue(userWrong);
    }

}




