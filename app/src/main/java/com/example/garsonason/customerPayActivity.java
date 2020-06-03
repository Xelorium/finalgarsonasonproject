package com.example.garsonason;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class customerPayActivity extends AppCompatActivity {

    private TextView toplamOdeme;
    private Button chkoutOde;
    private Button menuButton;
    private MaskedEditText kartSahibiEdit;
    private MaskedEditText kartNoEdit;
    private MaskedEditText sktEdit;
    private MaskedEditText cvcEdit;
    private FirebaseAuth mAuth;
    private DatabaseReference database_Ref;
    ArrayList<urunModel> odemeOzet;
    private ProgressDialog progressDialog1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pay);

        TextView toplamOdeme = findViewById(R.id.toplamOdeme);
        Button chkoutOde = findViewById(R.id.chkoutOde);
        Button menuButton = findViewById(R.id.menuButton);
        final MaskedEditText kartSahibiEdit = findViewById(R.id.kartSahibiEdit);
        final MaskedEditText kartNoEdit = findViewById(R.id.kartNoEdit);
        final MaskedEditText sktEdit = findViewById(R.id.sktEdit);
        final MaskedEditText cvcEdit = findViewById(R.id.cvcEdit);

        progressDialog1 = new ProgressDialog(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();



        Bundle bundle3 = getIntent().getExtras();
        final String musteriId = getIntent().getExtras().getString("musId");
        final String isletmeId = getIntent().getExtras().getString("isId");
        String araTop = getIntent().getExtras().getString("araTop");
        odemeOzet = (ArrayList<urunModel>) bundle3.getSerializable("gonder");


        toplamOdeme.setText(araTop +"TL");

        chkoutOde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kartName = String.valueOf(kartSahibiEdit.getUnmaskedText());
                String kartNo = String.valueOf(kartNoEdit.getUnmaskedText());
                String kartSkt = String.valueOf(sktEdit.getUnmaskedText());
                String kartCvc = String.valueOf(cvcEdit.getUnmaskedText());

                if (!TextUtils.isEmpty(kartName)
                        && !TextUtils.isEmpty(kartNo)
                        && !TextUtils.isEmpty(kartSkt)
                        && !TextUtils.isEmpty(kartCvc)){

                    if (kartNo.length()>7
                            && kartSkt.length()>3
                            && kartCvc.length()>2){

                        progressDialog1.setTitle("SİPARİŞ TAMAMLANIYOR");
                        progressDialog1.setMessage("Lütfen bekleyin...");
                        progressDialog1.setCanceledOnTouchOutside(false);
                        progressDialog1.show();


                        String date = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(new Date());
                        database_Ref = FirebaseDatabase.getInstance().getReference().child("Isletme_Siparisler").child(isletmeId).child(musteriId).child("sepet").push().child(date);
                        database_Ref.setValue(odemeOzet).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(customerPayActivity.this, customerMenuActivity.class);
                                    intent.putExtra("isId", getIntent().getStringExtra("isId"));
                                    intent.putExtra("musId", getIntent().getStringExtra("musId"));
                                    progressDialog1.dismiss();
                                    Toast.makeText(getApplicationContext(), "Siparişiniz tamamlandı ve ödemeniz alındı. Tebrikler!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Hata. Lütfen alanları eksik veya boş geçmeyin ve tekrar deneyin.", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    Toast.makeText(getApplicationContext(), "Hata. Alanların boş olmadığından veya doğru girildiğinden emin olun ve tekrar deneyin.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(customerPayActivity.this);

                builder.setTitle("MENÜYE DÖN");
                builder.setMessage("Bu işlem siparişinizin iptal olmasına neden olacaktır. Yine de devam etmek istiyor musunuz?");
                builder.setCancelable(true);
                builder.setPositiveButton("DEVAM ET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent = new Intent(customerPayActivity.this, customerMenuActivity.class);
                        intent.putExtra("isId", getIntent().getStringExtra("isId"));
                        intent.putExtra("musId", getIntent().getStringExtra("musId"));
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
        });
    }
}
