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

public class customerRegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button customerRegister_Button;
    private EditText customerRegister_id_Edittext;
    private EditText customerRegister_mail_Edittext;
    private EditText customerRegister_password_Edittext;
    private EditText customerRegister_passwordRepeat_Edittext;
    private EditText customerRegister_phoneNumber_Edittext;
    private DatabaseReference database_Ref;
    private ProgressDialog progressDialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        customerRegister_Button = findViewById(R.id.customerRegister_Button);
        customerRegister_id_Edittext = findViewById(R.id.customerRegister_id_Edittext);
        customerRegister_mail_Edittext = findViewById(R.id.customerRegister_mail_Edittext);
        customerRegister_password_Edittext = findViewById(R.id.customerRegister_password_Edittext);
        customerRegister_passwordRepeat_Edittext = findViewById(R.id.customerRegister_passwordRepeat_Edittext);
        customerRegister_phoneNumber_Edittext = findViewById(R.id.customerRegister_phoneNumber_Edittext);
        progressDialog1 = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        customerRegister_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String posta = customerRegister_mail_Edittext.getText().toString();
                String sifre = customerRegister_password_Edittext.getText().toString();
                String kullaniciAdi = customerRegister_id_Edittext.getText().toString();
                String sifreTekrar = customerRegister_passwordRepeat_Edittext.getText().toString();
                String telNo = customerRegister_phoneNumber_Edittext.getText().toString();
                String puan = "n/a";
                String adres = "n/a";

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
        });
    }

    private void kayitOl(final String posta, final String sifre, final String kullaniciAdi, final String telNo, final String puan, final String adres) {
        mAuth.createUserWithEmailAndPassword(posta, sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    String users_Id = mAuth.getCurrentUser().getUid();
                    database_Ref = FirebaseDatabase.getInstance().getReference().child("tbl_kullanicilar").child(users_Id);
                    HashMap<String, String> musteriKullaniciKayit = new HashMap<>();
                    musteriKullaniciKayit.put("kullaniciAdi", kullaniciAdi);
                    musteriKullaniciKayit.put("sifre", sifre);
                    musteriKullaniciKayit.put("telNo", telNo);
                    musteriKullaniciKayit.put("ePosta", posta);
                    musteriKullaniciKayit.put("adres", adres);
                    musteriKullaniciKayit.put("puan", puan);
                    musteriKullaniciKayit.put("kullaniciTuru", "musteri");
                    musteriKullaniciKayit.put("isletmeKodu", "n/a");

                    progressDialog1.setTitle("Kayıt İşlemi Tamamlanıyor");
                    progressDialog1.setMessage("Lütfen bekleyin...");
                    progressDialog1.setCanceledOnTouchOutside(false);
                    progressDialog1.show();


                    database_Ref.setValue(musteriKullaniciKayit).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent kayitTamamlandi = new Intent(customerRegisterActivity.this, loginActivity.class);
                                progressDialog1.dismiss();
                                Toast.makeText(getApplicationContext(), "Kayıt işlemi başarıyla tamamlandı!", Toast.LENGTH_SHORT).show();
                                startActivity(kayitTamamlandi);
                            }
                        }
                    });

                } else {

                    Toast.makeText(getApplicationContext(), "Hata var!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
