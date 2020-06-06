package com.example.garsonason;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customerMainActivity extends AppCompatActivity {

    private EditText idBusiness;
    private Button codeSubmit_Button;
    private Button customerLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        final String musteriId = getIntent().getExtras().getString("id2");
        final String isletmeId = getIntent().getExtras().getString("isId");

        System.out.println(musteriId);
        idBusiness = findViewById(R.id.idBusiness);
        codeSubmit_Button = findViewById(R.id.codeSubmit_Button);

        Button reportBug = (Button) findViewById(R.id.reportBug_Button);

        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customerMainActivity.this, reportBugActivity.class);
                startActivity(intent);
            }
        });

        codeSubmit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String kod = idBusiness.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            dataSnapshot.getKey();
                            keepData model = ds.getValue(keepData.class);


                            if (model.getIsletmeKodu().equals(kod)) {

                                Intent intent = new Intent(customerMainActivity.this, customerPanelActivity.class);
                                intent.putExtra("isId", model.getIsletmeKodu());
                                intent.putExtra("musId", musteriId);
                                finish();
                                startActivity(intent);

                            }


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(customerMainActivity.this);

        builder.setTitle("ÇIKIŞ YAP");
        builder.setMessage("Hesabınızdan gerçekten çıkış yapmak istiyor musunuz?");
        builder.setCancelable(true);
        builder.setPositiveButton("ÇIKIŞ YAP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(customerMainActivity.this, loginActivity.class);
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
