package com.example.garsonason;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class businessRegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button businessRegister_Button;
    private EditText businessRegister_id_Edittext;
    private EditText businessRegister_mail_Edittext;
    private EditText businessRegister_password_Edittext;
    private EditText businessRegister_passwordRepeat_Edittext;
    private EditText businessRegister_phoneNumber_Edittext;
    private DatabaseReference database_Ref;
    private ProgressDialog progressDialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);
        businessRegister_id_Edittext = findViewById(R.id.businessRegister_id_Edittext);
        businessRegister_mail_Edittext = findViewById(R.id.businessRegister_mail_Edittext);
        businessRegister_password_Edittext = findViewById(R.id.businessRegister_password_Edittext);
        businessRegister_Button = findViewById(R.id.businessRegister_Button);
        businessRegister_passwordRepeat_Edittext = findViewById(R.id.businessRegister_passwordRepeat_Edittext);
        businessRegister_phoneNumber_Edittext = findViewById(R.id.businessRegister_phoneNumber_Edittext);
        progressDialog1 = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        businessRegister_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String posta = businessRegister_mail_Edittext.getText().toString();
                String sifre = businessRegister_password_Edittext.getText().toString();
                String kullaniciAdi = businessRegister_id_Edittext.getText().toString();
                String sifreTekrar = businessRegister_passwordRepeat_Edittext.getText().toString();
                String telNo = businessRegister_phoneNumber_Edittext.getText().toString();
                String puan = "0";
                String adres = "Not given";

                if (!TextUtils.isEmpty(posta) && !TextUtils.isEmpty(sifre) && !TextUtils.isEmpty(sifreTekrar) && !TextUtils.isEmpty(telNo)) {
                    if (TextUtils.equals(sifre, sifreTekrar)) {
                        kayitOl(posta, sifre, kullaniciAdi, telNo, puan, adres);

                    } else {
                        Toast.makeText(getApplicationContext(), "Şifre tekrarını doğru girdiğinizden emin olun.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Tüm alanları doldurmanız gerekiyor.", Toast.LENGTH_SHORT).show();
                }


            }

            private void kayitOl(final String posta, final String sifre, final String kullaniciAdi, final String telNo, final String puan, final String adres) {
                mAuth.createUserWithEmailAndPassword(posta, sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            String users_Id = mAuth.getCurrentUser().getUid();
                            database_Ref = FirebaseDatabase.getInstance().getReference().child("tbl_kullanicilar").child(users_Id);
                            HashMap<String, String> isletmeKullaniciKayit = new HashMap<>();
                            isletmeKullaniciKayit.put("kullaniciAdi", kullaniciAdi);
                            isletmeKullaniciKayit.put("sifre", sifre);
                            isletmeKullaniciKayit.put("telNo", telNo);
                            isletmeKullaniciKayit.put("ePosta", posta);
                            isletmeKullaniciKayit.put("adres", adres);
                            isletmeKullaniciKayit.put("puan", puan);
                            isletmeKullaniciKayit.put("kullaniciTuru", "isletme");
                            isletmeKullaniciKayit.put("isletmeKodu", users_Id);

                            progressDialog1.setTitle("Kayıt İşlemi Tamamlanıyor");
                            progressDialog1.setMessage("Lütfen bekleyin...");
                            progressDialog1.setCanceledOnTouchOutside(false);
                            progressDialog1.show();


                            database_Ref.setValue(isletmeKullaniciKayit).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent kayitTamamlandi = new Intent(businessRegisterActivity.this, loginActivity.class);
                                        progressDialog1.dismiss();
                                        Toast.makeText(getApplicationContext(), "Kayıt işlemi başarıyla tamamlandı!", Toast.LENGTH_SHORT).show();
                                        startActivity(kayitTamamlandi);

                                    }

                                }
                            });


                        } else {

                            Toast.makeText(getApplicationContext(), "hata" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        });

    }
}