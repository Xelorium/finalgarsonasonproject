package com.example.garsonason;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        final String musteriId = getIntent().getExtras().getString("id2");
        System.out.println(musteriId);
        idBusiness = findViewById(R.id.idBusiness);
        codeSubmit_Button = findViewById(R.id.codeSubmit_Button);

        codeSubmit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String kod = idBusiness.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        System.out.println(kod);
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            dataSnapshot.getKey();
                            keepData model = ds.getValue(keepData.class);
                            System.out.println(model.getAdres());
                            if (model.getIsletmeKodu().equals(kod)) {

                                Intent intent = new Intent(customerMainActivity.this, customerMenuActivity.class);
                                intent.putExtra("isId", model.getIsletmeKodu());
                                intent.putExtra("musId", musteriId);
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
}
