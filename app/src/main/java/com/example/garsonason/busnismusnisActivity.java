package com.example.garsonason;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class busnismusnisActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busnismusnis);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String isletmeId = mAuth.getCurrentUser().getUid();
        final String pushId = getIntent().getExtras().getString("pus");
        final String musteriId = getIntent().getExtras().getString("mId");
        final TextView customerNameTextView = (TextView) findViewById(R.id.customerNameTextView);
        final TextView ordersDone = (TextView) findViewById(R.id.ordersDone);

        final ListView idleListView2 = (ListView) findViewById(R.id.idleListView2);
        Button businessGetBackButton = (Button) findViewById(R.id.businessGetBackButton);
        final ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<String> arrayList2 = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef;

        final DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
        final String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());



        businessGetBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(busnismusnisActivity.this, businessOrderIdleActivity.class);
                intent.putExtra("isId", isletmeId);
                startActivity(intent);
                finish();
            }
        });

        final DatabaseReference myref4 = database.getReference().child("tbl_kullanicilar");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = ds.getKey();
                    keepData model = ds.getValue(keepData.class);
                    if (isletmeId.equals(a)){
                        customerNameTextView.setText(model.getKullaniciAdi()+" Kullanıcısından Gelen Siparişler");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = ds.getKey();
                    final keepData model = ds.getValue(keepData.class);

                    if (model.getKullaniciTuru().equals("musteri")) {

                        final DatabaseReference myRef = database.getReference().child("Isletme_Siparisler").child(isletmeId).child(a).child("sepet").child(pushId).child(date);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String a = ds.getKey();
                                    keepData_3 model = ds.getValue(keepData_3.class);
                                    if (model.getDurum().equals("Beklemede")) {
                                        arrayList.add("Ürün Adı: " + model.getUrunAdi() + "  |  Ürün Miktarı: " + model.getMiktar()
                                                + "\nToplam Fiyat: " + model.getFiyat()
                                                + "TL  |  Sipariş Durumu: " + model.getDurum());
                                        arrayList2.add(a);
                                        idleListView2.setAdapter(arrayAdapter);

                                    }


                                }
                                if (arrayAdapter.isEmpty()){
                                    ordersDone.setVisibility(View.VISIBLE);
                                }
                                else if(!arrayAdapter.isEmpty()){
                                    ordersDone.setVisibility(View.GONE);
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

        idleListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(busnismusnisActivity.this);

                builder.setTitle("İŞLEM SEÇİNİZ");
                builder.setMessage("Lütfen seçilen siparişe uygulamak istediğiniz işlemi seçin.\nSiparişi:");
                builder.setCancelable(true);
                builder.setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        final DatabaseReference myRef = database.getReference().child("Isletme_Siparisler").child(isletmeId).child(musteriId).child("sepet").child(pushId).child(date).child(arrayList2.get(position)).child("durum");
                        myRef.setValue("Onaylandı");

                        Intent intent = new Intent(busnismusnisActivity.this, busnismusnisActivity.class);
                        intent.putExtra("isId", isletmeId);
                        Toast.makeText(getApplicationContext(), "İşleminiz başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());


                        dialog.dismiss();
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("İPTAL ET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String a = ds.getKey();
                                    final keepData model = ds.getValue(keepData.class);

                                    if (model.getKullaniciTuru().equals("musteri")) {

                                        final DatabaseReference myRef = database.getReference().child("Isletme_Siparisler").child(isletmeId).child(a).child("sepet");
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                arrayList.clear();
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                    String b = ds.getKey();
                                                    final String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                                                    myRef.child(b)

                                                            .child(date).child(arrayList2.get(position)).child("durum").setValue("İptal Edildi");


                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                                Intent intent = new Intent(busnismusnisActivity.this, busnismusnisActivity.class);
                                intent.putExtra("isId", isletmeId);
                                Toast.makeText(getApplicationContext(), "İşleminiz başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });


                        dialog.dismiss();
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNeutralButton("VAZGEÇ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });



    }
}
