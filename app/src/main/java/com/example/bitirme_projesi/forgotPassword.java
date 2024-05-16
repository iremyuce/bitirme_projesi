package com.example.bitirme_projesi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    EditText email;
    Button button;
    ProgressBar progressBar;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=findViewById(R.id.editTextPasswordEmail);
        button=findViewById(R.id.buttonPasswordContinue);
        progressBar=findViewById(R.id.progressBarPasswordForgot);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail=email.getText().toString();
                resetPassword(userMail);

            }
        });
    }

    public void resetPassword(String userMail){
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(userMail)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgotPassword.this, "We sent you an email to reset your password!",Toast.LENGTH_LONG).show();
                            finish();
                            button.setClickable(false);
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                        else{
                            Toast.makeText(forgotPassword.this, "Sorry, There is a problem, try again later... ",Toast.LENGTH_LONG).show();

                        }

                    }
                });


    }
}