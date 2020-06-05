package com.example.garsonason;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class customerMenuActivity extends AppCompatActivity {

    private static final String TAG = "customerMenuActivity";

    private ListView urunleriListele_Musteri_ListView;
    private ListView sepettekileriListele_MusteriListView;
    private FirebaseAuth mAuth;
    private DatabaseReference database_Ref;
    private DatabaseReference database_Ref2;
    public ArrayAdapter<String> arrayAdapter;
    //private ArrayAdapter<String> arrayAdapter2;
    private ArrayList<String> arrayList3;
    private ArrayList<String> arrayList4;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList5;
    private ArrayList<String> arrayList6;
    private ArrayList<urunModel> sepet;
    private Button siparisVer;
    sepetAdapter arrayAdapter2;

    @Override
    protected void onResume() {
        super.onResume();
        if (sepet.size()<=0){
            siparisVer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
        Log.d(TAG, "onCreate: Started");
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList4 = new ArrayList<>();
        arrayList5 = new ArrayList<>();
        arrayList6 = new ArrayList<>();


        siparisVer=findViewById(R.id.siparisVer);
        sepet = new ArrayList<urunModel>();
        arrayList3 = new ArrayList<>();



        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        arrayAdapter2= new sepetAdapter(getApplicationContext(),R.layout.custom_listview_1, sepet);

        final String musteriId = getIntent().getExtras().getString("musId");
        urunleriListele_Musteri_ListView = findViewById(R.id.urunleriListele_Musteri_ListView);
        sepettekileriListele_MusteriListView = findViewById(R.id.sepettekileriListele_MusteriListView);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String isletmeId = getIntent().getExtras().getString("isId");
        System.out.println(isletmeId);
        System.out.println(musteriId);




        final DatabaseReference myRef = database.getReference().child("Isletme_Urunler_Bilgi").child(isletmeId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String a = ds.getKey();
                    customerProductAdapter model = ds.getValue(customerProductAdapter.class);

                    arrayList.add("Ürün Adı: " + model.geturunAdi() + "\n" + "Ürün Türü: " + model.geturunTipi() + "\n" + "Ürün Fiyatı: " + model.geturunFiyat() + "TL");
                    arrayList2.add(a);
                    arrayList3.add(model.geturunFiyat());
                    arrayList4.add(model.geturunAdi());
                    arrayList6.add(model.getUrunDurumu());
                    urunleriListele_Musteri_ListView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        urunleriListele_Musteri_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String date = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date());
                HashMap<String, String> siparisListesi = new HashMap<>();
                siparisListesi.put("siparis", arrayList2.get(position));
                siparisListesi.put("durum", "beklemede");
                siparisListesi.put("tarih", date);


                //database_Ref.setValue(siparisListesi);

                /*String deneme=arrayList.get(position);
                Toast.makeText(getApplicationContext(), deneme, Toast.LENGTH_SHORT).show();*/
                final String urunAdi=arrayList4.get(position);
                String urunDurum = "Beklemede";
                String fiyat = arrayList3.get(position);
                urunModel urunmodel = new urunModel();
                urunmodel.urunAdi=urunAdi;
                urunmodel.durum = urunDurum;

                String olan=null;
                if (sepet.size()>0){
                    for(int i=0;i<=sepet.size()-1;i++){
                        if(sepet.get(i).urunAdi.equals(urunAdi)){
                            olan=String.valueOf(i);
                        }
                    }
                    if (olan!=null){
                        sepet.get(Integer.parseInt(olan)).miktar+=1;

                    }else{
                        urunmodel.miktar=1;
                        sepet.add(urunmodel);

                    }
                }
                else{
                    urunmodel.miktar=1;
                    sepet.add(urunmodel);
                }
                urunmodel.fiyat=Integer.valueOf(fiyat);
                ArrayList<urunModel> urunListesi = new ArrayList<>();
//                urunModel deneme = new urunModel(urunmodel.urunAdi,urunmodel.miktar,urunmodel.fiyat);



//                sepet.add(deneme);
                sepettekileriListele_MusteriListView.setAdapter(arrayAdapter2);
                if (sepet.size()>0){
                    siparisVer.setVisibility(View.VISIBLE);
                }
                {
                };



            }

        });

        sepettekileriListele_MusteriListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sepet.get(position).miktar == 1) {
                    sepet.remove(position);
                } else {
                    sepet.get(position).miktar--;
                }

                arrayAdapter2.notifyDataSetChanged();


            }
        });




       siparisVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(customerMenuActivity.this);

                builder.setTitle("SİPARİŞİ ONAYLA");
                builder.setMessage("Siparişi gerçekten onaylamak istiyor musunuz?");
                builder.setCancelable(true);
                builder.setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0;i<sepet.size();i++){
                            sepet.get(i).fiyat*=sepet.get(i).miktar;
                        }


                        Intent intent = new Intent(customerMenuActivity.this, customerChkActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("sepet", sepet);
                        intent.putExtra("isId", isletmeId);
                        intent.putExtra("musId", musteriId);
                        intent.putExtras(bundle);



                        arrayAdapter2.notifyDataSetChanged();
                        if (sepet.size()<=0){
                            siparisVer.setVisibility(View.GONE);
                        }
                        dialog.dismiss();
                        startActivity(intent);
                        sepet.clear();
                        finish();


                    }
                });

                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
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
