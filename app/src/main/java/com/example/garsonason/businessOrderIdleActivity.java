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
import android.widget.ListAdapter;
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

public class businessOrderIdleActivity extends AppCompatActivity {

    private ListView idleListView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayListData;
    private FirebaseAuth mAuth;
    private DatabaseReference database_Ref;
    private ArrayList<String> arrayList3;
    static businessOrderIdleActivity activityA;
    private ArrayList<String> arrayList6;
    private ArrayList<String> arrayList5;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_order_idle);
        activityA = this;


        final String musteriId = getIntent().getExtras().getString("musId");
        final String isletmeId = getIntent().getExtras().getString("isId");

        idleListView = findViewById(R.id.gelenSiparisListview);
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList3 = new ArrayList<>();
        arrayList6 = new ArrayList<>();
        arrayList5 = new ArrayList<>();
        arrayListData = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList6);
        mAuth = FirebaseAuth.getInstance();
        final TextView ordersDone2 = (TextView) findViewById(R.id.ordersDone2);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");


        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String a = ds.getKey();
                    final keepData model = ds.getValue(keepData.class);

                    if (model.getKullaniciTuru().equals("musteri")){

                        final DatabaseReference myRef = database.getReference().child("Isletme_Siparisler").child(isletmeId).child(a).child("sepet");

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String b = ds.getKey();
                                    final String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                                    arrayList.add(b);
                                    arrayList6.add("Siparişi Veren: "+model.getKullaniciAdi());
                                    arrayList5.add(a);
                                    idleListView.setAdapter( arrayAdapter);

                                    arrayAdapter.notifyDataSetChanged();


                                }

                                if (arrayAdapter.isEmpty()){
                                    ordersDone2.setVisibility(View.VISIBLE);
                                }
                                else if(!arrayAdapter.isEmpty()){
                                    ordersDone2.setVisibility(View.GONE);
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


        idleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
             Intent intent= new Intent(businessOrderIdleActivity.this,busnismusnisActivity.class);
             intent.putExtra("pus",arrayList.get(position));
             intent.putExtra("mId",arrayList5.get(position));
             startActivity(intent);
             finish();
            }
        });

    }

}
