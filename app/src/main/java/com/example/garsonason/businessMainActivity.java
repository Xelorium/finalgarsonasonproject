package com.example.garsonason;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class businessMainActivity extends AppCompatActivity {


    private EditText businessAddProduct_Name_Edittext;
    private EditText businessAddProduct_Type_Edittext;
    private EditText businessAddProduct_Cost_Edittext;
    private Button businessAddProduct_Add_Button;
    private Button businessLogoutButton;
    private Button listeleButton;
    private FirebaseAuth mAuth;
    private DatabaseReference database_Ref;
    private Button gelenSiparis;
    private Button tamamSiparis;
    private TextView userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_main);


        businessAddProduct_Add_Button = findViewById(R.id.businessAddProduct_Add_Button);
        listeleButton = findViewById(R.id.listele_Button);
        gelenSiparis = findViewById(R.id.gelenSiparisButton);
        tamamSiparis = findViewById(R.id.tamamSiparisButton);
        businessLogoutButton = findViewById(R.id.businessLogoutButton);
        userId = findViewById(R.id.businessUserId);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final String isletmeId = getIntent().getExtras().getString("id");

        userId.setText("Kullanıcı adı: "+ isletmeId);

        final DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = ds.getKey();
                    keepData model = ds.getValue(keepData.class);
                    if (isletmeId.equals(a)){
                        userId.setText("Kullanıcı Adı: "+ model.getKullaniciAdi());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        businessLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(businessMainActivity.this);

                builder.setTitle("ÇIKIŞ YAP");
                builder.setMessage("Hesabınızdan gerçekten çıkış yapmak istiyor musunuz?");
                builder.setCancelable(true);
                builder.setPositiveButton("ÇIKIŞ YAP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(businessMainActivity.this, loginActivity.class);
                        dialog.dismiss();
                        startActivity(intent);
                        finish();


                    }
                });
                builder.setNegativeButton("VAZGEÇ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        gelenSiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(businessMainActivity.this, businessOrderIdleActivity.class);
                intent.putExtra("isId", isletmeId);
                startActivity(intent);


            }
        });

        tamamSiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(businessMainActivity.this, businessOrderCompletedActivity.class);
                intent.putExtra("isId", isletmeId);
                startActivity(intent);

            }
        });

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

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(businessMainActivity.this);

        builder.setTitle("ÇIKIŞ YAP");
        builder.setMessage("Hesabınızdan gerçekten çıkış yapmak istiyor musunuz?");
        builder.setCancelable(true);
        builder.setPositiveButton("ÇIKIŞ YAP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(businessMainActivity.this, loginActivity.class);
                dialog.dismiss();
                startActivity(intent);
                finish();


            }
        });
        builder.setNegativeButton("VAZGEÇ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }




}
