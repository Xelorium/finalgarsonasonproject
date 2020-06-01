package com.example.garsonason;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class customerChkActivity extends AppCompatActivity {

    ArrayList<urunModel> ozet;
    private ArrayList<String> arrayList;
    private ListView siparisOzetiTextView;
    ArrayAdapter<String> arrayAdapter;
    private Double araTop;
    private TextView toplamTutar;
    private Button odemeYap;
    private Button odemeVazgec;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chk);

        arrayList = new ArrayList<>();

        ListView siparisOzetiTextView = findViewById(R.id.sepetListesi);
        toplamTutar = findViewById(R.id.toplamTutar);
        odemeYap = findViewById(R.id.odemeYap);
        odemeVazgec = findViewById(R.id.odemeVazgec);

        final Bundle bundle = getIntent().getExtras();
        ozet = (ArrayList<urunModel>) bundle.getSerializable("sepet");
        for (int i=0; i<ozet.size(); i++) {
            arrayList.add("Urun Adı: "+ ozet.get(i).urunAdi+" Miktar: "+ ozet.get(i).miktar+" Ürün Fiyatı: "+ ozet.get(i).fiyat);
            araTop+= ozet.get(i).fiyat;
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        toplamTutar.setText("Toplam Tutar: "+araTop+"TL");


        siparisOzetiTextView.setAdapter(arrayAdapter);


        odemeYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customerChkActivity.this, customerPayActivity.class);
                bundle.putSerializable("ozet",ozet);
                intent.putExtra("isId", getIntent().getStringExtra("isId"));
                intent.putExtra("musId", getIntent().getStringExtra("musId"));
                intent.putExtra("araTop",araTop);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        odemeVazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(customerChkActivity.this);

                builder.setTitle("SİPARİŞ İPTALİ");
                builder.setMessage("Siparişi gerçekten iptal etmek istiyor musunuz?");
                builder.setCancelable(true);
                builder.setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent(customerChkActivity.this, customerMenuActivity.class);

                dialog.dismiss();
                startActivity(intent2);

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







    }
}
