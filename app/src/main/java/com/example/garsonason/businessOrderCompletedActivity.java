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

public class businessOrderCompletedActivity extends AppCompatActivity {

    private ListView completedListView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayListData;
    private FirebaseAuth mAuth;
    private DatabaseReference database_Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_order_completed);

        completedListView = findViewById(R.id.gelenSiparis);
        final String isletmeId = getIntent().getExtras().getString("isId");

        arrayList = new ArrayList<>();
        arrayListData = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = ds.getKey();
                    final keepData model = ds.getValue(keepData.class);

                    if (model.getKullaniciTuru().equals("musteri")){

                        final DatabaseReference myRef = database.getReference().child("Isletme_Siparisler").child(isletmeId).child(a).child("sepet");
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
                                                if (model.getDurum().equals("Onaylandı")||model.getDurum().equals("İptal Edildi") ){
                                                    arrayList.add("Ürün Adı: " +model.getUrunAdi()+"  |  Ürün Miktarı: "+model.getMiktar()
                                                            +"\nToplam Fiyat: "+model.getFiyat()
                                                            +"  |  Sipariş Durumu: "+ model.getDurum());
                                                    completedListView.setAdapter(arrayAdapter);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
