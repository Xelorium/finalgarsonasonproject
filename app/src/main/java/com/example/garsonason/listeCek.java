package com.example.garsonason;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listeCek extends AppCompatActivity {

    private ListView urunListele_ListView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        final TextView ordersDone5 = (TextView)findViewById(R.id.ordersDone5);
        arrayList = new ArrayList<>();
        urunListele_ListView = findViewById(R.id.urunleriListele_ListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String isletmeId = getIntent().getExtras().getString("id2");
        System.out.println(isletmeId);
        DatabaseReference myRef = database.getReference().child("Isletme_Urunler_Bilgi").child(isletmeId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    dataSnapshot.getKey();
                    customerProductAdapter model = ds.getValue(customerProductAdapter.class);
                    arrayList.add("Ürün Adı: " + model.geturunAdi() + "\n" + "Ürün Türü: " + model.geturunTipi() + "\n" + "Ürün Fiyatı: " + model.geturunFiyat() + "TL");

                    urunListele_ListView.setAdapter(arrayAdapter);
                }

                if (arrayAdapter.isEmpty()) {
                    ordersDone5.setVisibility(View.VISIBLE);
                }
                if (!arrayAdapter.isEmpty()) {
                    ordersDone5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
