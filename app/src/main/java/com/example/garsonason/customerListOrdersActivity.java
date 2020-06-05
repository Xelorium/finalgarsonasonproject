package com.example.garsonason;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class customerListOrdersActivity extends AppCompatActivity {

    private ListView customerOrdersListView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private DatabaseReference database_Ref;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList3;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list_orders);
        customerOrdersListView = findViewById(R.id.customerOrdersListView);

        final String isletmeId = getIntent().getExtras().getString("isId");
        final String musteriId = getIntent().getExtras().getString("musId");

        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList3 = new ArrayList<>();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");

        final DatabaseReference myRef = database.getReference().child("Isletme_Siparisler").child(isletmeId).child(musteriId).child("sepet");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String b = ds.getKey();
                    final String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    final DatabaseReference myRef3 = myRef.child(b).child(date);

                    myRef3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String a = ds.getKey();
                                keepData_3 model = ds.getValue(keepData_3.class);
                                if (model.getDurum().equals("Onaylandı")||model.getDurum().equals("İptal Edildi")|| model.getDurum().equals("Beklemede") ){
                                    arrayList.add("Ürün Adı: " +model.getUrunAdi()+"  |  Ürün Miktarı: "+model.getMiktar()
                                            +"\nToplam Fiyat: "+model.getFiyat()
                                            +"  |  Sipariş Durumu: "+ model.getDurum());
                                    customerOrdersListView.setAdapter(arrayAdapter);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
