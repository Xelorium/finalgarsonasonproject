package com.example.garsonason;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class businessAddProductActivity extends Fragment {


    private FirebaseAuth mAuth;
    private EditText businessAddProduct_Cost_Edittext;
    private EditText businessAddProduct_Type_Edittext;
    private EditText businessAddProduct_Name_Edittext;
    private DatabaseReference database_Ref;
    private Toast toast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_business_add_product, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button businessAddProduct_Add_Button = getView().findViewById(R.id.businessAddProduct_Add_Button);


        businessAddProduct_Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void urunEkle(String urunAdi, String urunTipi, String urunFiyati, String date) {

        final String isletmeId = getArguments().getString("id");
        database_Ref = FirebaseDatabase.getInstance().getReference().child("Isletme_Urunler_Bilgi").child(isletmeId).push();
        HashMap<String, String> isletmeUrunKayit = new HashMap<>();
        isletmeUrunKayit.put("urunAdi", urunAdi);
        isletmeUrunKayit.put("urunTipi", urunTipi);
        isletmeUrunKayit.put("urunFiyat", urunFiyati);
        isletmeUrunKayit.put("kayitTarihi", date);

        database_Ref.setValue(isletmeUrunKayit).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Ürün başarıyla kaydedilmiştir. ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Bir sorun oluştu! Lütfen sonra tekrar deneyin.", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}
