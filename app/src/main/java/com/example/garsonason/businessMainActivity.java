package com.example.garsonason;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class businessMainActivity extends AppCompatActivity {


    private EditText businessAddProduct_Name_Edittext;
    private EditText businessAddProduct_Type_Edittext;
    private EditText businessAddProduct_Cost_Edittext;
    private Button businessAddProduct_Add_Button;
    private Button listeleButton;
    private FirebaseAuth mAuth;
    private DatabaseReference database_Ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_main);

        businessAddProduct_Add_Button = findViewById(R.id.businessAddProduct_Add_Button);
        listeleButton = findViewById(R.id.intent_Button);

        mAuth = FirebaseAuth.getInstance();

        businessAddProduct_Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText businessAddProduct_Name_Edittext = findViewById(R.id.businessAddProduct_Name_Edittext);
                EditText businessAddProduct_Type_Edittext = findViewById(R.id.businessAddProduct_Type_Edittext);
                EditText businessAddProduct_Cost_Edittext = findViewById(R.id.businessAddProduct_Cost_Edittext);
                String urunAdi = businessAddProduct_Name_Edittext.getText().toString();
                String urunTipi = businessAddProduct_Type_Edittext.getText().toString();
                String urunFiyati = businessAddProduct_Cost_Edittext.getText().toString();
                String date = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date());


                if (!TextUtils.isEmpty(urunAdi) && !TextUtils.isEmpty(urunTipi) && !TextUtils.isEmpty(urunFiyati)) {
                    urunEkle(urunAdi, urunTipi, urunFiyati, date);

                } else {
                    Toast.makeText(getApplicationContext(), "Tüm alanları doldurmanız gerekiyor.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listeleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isletmeId = getIntent().getExtras().getString("id");
                Intent intent = new Intent(businessMainActivity.this, listeCek.class);
                intent.putExtra("id2", isletmeId);
                startActivity(intent);
            }
        });


    }

    private void urunEkle(String urunAdi, String urunTipi, String urunFiyati, String date) {

        String isletmeId = getIntent().getExtras().getString("id");
        database_Ref = FirebaseDatabase.getInstance().getReference().child("Isletme_Urunler_Bilgi").child(isletmeId).push();
        HashMap<String, String> isletmeUrunKayit = new HashMap<>();
        isletmeUrunKayit.put("urunAdi", urunAdi);
        isletmeUrunKayit.put("urunTipi", urunTipi);
        isletmeUrunKayit.put("urunFiyat", urunFiyati);
        isletmeUrunKayit.put("kayitTarihi", date);

        database_Ref.setValue(isletmeUrunKayit).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Ürün başarıyla kaydedilmiştir. ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bir sorun oluştu! Lütfen sonra tekrar deneyin.", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}
