package com.example.garsonason;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity {

    private Button passwordResetButton;
    private EditText passwordResetEdittext;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        passwordResetButton = findViewById(R.id.passwordReset_Button);
        passwordResetEdittext = findViewById(R.id.passwordReset_email_Edittext);
        mAuth = FirebaseAuth.getInstance();

        Button reportBug = (Button) findViewById(R.id.reportBug_Button);

        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgotPasswordActivity.this, reportBugActivity.class);
                startActivity(intent);
            }
        });

        passwordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = passwordResetEdittext.getText().toString();

                if (!email.isEmpty()){

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Şifre sıfırlama talebiniz e-postanıza gönderilmiştir.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Geçersiz e-posta adresi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Alanları boş bırakmayın.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
