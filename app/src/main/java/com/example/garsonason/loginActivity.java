package com.example.garsonason;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private EditText userId_Edittext;
    private EditText userPassword_Edittext;
    private Button userLogin_Button;
    private FirebaseAuth mAuth;
    private TextView forgotPassword_TextView;
    private ProgressDialog progressDialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId_Edittext = findViewById(R.id.userId_Edittext);
        userPassword_Edittext = findViewById(R.id.userPassword_Edittext);
        forgotPassword_TextView = findViewById(R.id.forgotPassword_Text);
        userLogin_Button = findViewById(R.id.userLogin_Button);
        progressDialog1 = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        userLogin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kullaniciAdi = userId_Edittext.getText().toString();
                String kullaniciSifre = userPassword_Edittext.getText().toString();
                if (!TextUtils.isEmpty(kullaniciAdi) && !TextUtils.isEmpty(kullaniciSifre)) {

                    login_user(kullaniciAdi, kullaniciSifre);

                } else {
                    Toast.makeText(getApplicationContext(), "Eksik veya yanlış girdi. ", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgotPassword_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, forgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login_user(String kullaniciAdi, String kullaniciSifre) {

        mAuth.signInWithEmailAndPassword(kullaniciAdi, kullaniciSifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String id = mAuth.getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar").child(id);
                    progressDialog1.setTitle("Giriş Yapılıyor");
                    progressDialog1.setMessage("Lütfen bekleyin...");
                    progressDialog1.setCanceledOnTouchOutside(false);
                    progressDialog1.show();
                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            keepData model = new keepData();
                            model = dataSnapshot.getValue(model.getClass());

                            if (model.getKullaniciTuru().equals("isletme")) {


                                Intent intent = new Intent(loginActivity.this, businessMainActivity.class);
                                intent.putExtra("id", id);
                                progressDialog1.dismiss();
                                startActivity(intent);

                            } else if (model.getKullaniciTuru().equals("musteri")) {

                                Intent intent2 = new Intent(loginActivity.this, customerMainActivity.class);
                                progressDialog1.dismiss();
                                intent2.putExtra("id2", id);
                                startActivity(intent2);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Eksik veya yanlış girdi. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
