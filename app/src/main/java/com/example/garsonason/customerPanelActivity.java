package com.example.garsonason;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customerPanelActivity extends AppCompatActivity {

    private TextView customerPostUserId;
    private Button customerPostLogoutButton;
    private Button customerOrdersButton;
    private Button customerMenuButton;
    private Button customerChangeisId;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel);

        customerPostUserId = findViewById(R.id.customerPostUserId);
        customerPostLogoutButton = findViewById(R.id.customerPostLogoutButton);
        customerOrdersButton = findViewById(R.id.customerOrdersButton);
        customerMenuButton = findViewById(R.id.customerMenuButton);
        customerChangeisId = findViewById(R.id.customerChangeisId);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String musteriId= getIntent().getExtras().getString("musId");
        final String isletmeId= getIntent().getExtras().getString("isId");

        final DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = ds.getKey();
                    keepData model = ds.getValue(keepData.class);
                    if (musteriId.equals(a)){
                        customerPostUserId.setText("Kullanıcı Adı: "+ model.getKullaniciAdi());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        customerChangeisId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(customerPanelActivity.this);

                builder.setTitle("İŞLETME DEĞİŞTİR");
                builder.setMessage("İşletmenizi gerçekten değiştirmek istiyor musunuz?");
                builder.setCancelable(true);
                builder.setPositiveButton("DEĞİŞTİR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(customerPanelActivity.this, customerMainActivity.class);
                        intent.putExtra("id2", musteriId);
                        intent.putExtra("isId", isletmeId);
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

        customerPostLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(customerPanelActivity.this);

                builder.setTitle("ÇIKIŞ YAP");
                builder.setMessage("Hesabınızdan gerçekten çıkış yapmak istiyor musunuz?");
                builder.setCancelable(true);
                builder.setPositiveButton("ÇIKIŞ YAP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(customerPanelActivity.this, loginActivity.class);
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

        customerMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customerPanelActivity.this, customerMenuActivity.class);
                intent.putExtra("isId", isletmeId);
                intent.putExtra("musId", musteriId);
                startActivity(intent);
            }
        });


        customerOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customerPanelActivity.this, customerListOrdersActivity.class);
                intent.putExtra("isId", isletmeId);
                intent.putExtra("musId", musteriId);
                startActivity(intent);

            }
        });





    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(customerPanelActivity.this);

        builder.setTitle("ÇIKIŞ YAP");
        builder.setMessage("Hesabınızdan gerçekten çıkış yapmak istiyor musunuz?");
        builder.setCancelable(true);
        builder.setPositiveButton("ÇIKIŞ YAP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(customerPanelActivity.this, loginActivity.class);
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
