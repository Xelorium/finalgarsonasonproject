package com.example.garsonason;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class customerMainActivity extends AppCompatActivity {

    SurfaceView barcodeOku;
    private EditText idBusiness;
    private Button codeSubmit_Button;
    private Button customerLogoutButton;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    BarcodeDetector barcodeDetector;
    TextView txtResult;
    String gelenDeger;
    private ProgressDialog progressDialog1;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(barcodeOku.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        final String musteriId = getIntent().getExtras().getString("id2");
        final String isletmeId = getIntent().getExtras().getString("isId");
        progressDialog1 = new ProgressDialog(this);


        barcodeOku = (SurfaceView)findViewById(R.id.barcodeOku);
        txtResult = (TextView) findViewById(R.id.txtResult);



        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this,barcodeDetector)
                .setRequestedPreviewSize(640,480)
                .build();

        barcodeOku.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(customerMainActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);

                    return;
                }
                try {
                    cameraSource.start (barcodeOku.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() !=0){

                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Create vibrate
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtResult.setText(qrCodes.valueAt(0).displayValue);
                            gelenDeger = txtResult.getText().toString();

                            final String kod = idBusiness.getText().toString();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
                            myRef2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        dataSnapshot.getKey();
                                        keepData model = ds.getValue(keepData.class);


                                        if (model.getIsletmeKodu().equals(gelenDeger)) {

                                            txtResult.setText("Başarılı! Kamerayı QR Kodundan Çekebilirsiniz.");

                                            Intent intent = new Intent(customerMainActivity.this, customerPanelActivity.class);
                                            intent.putExtra("isId", model.getIsletmeKodu());
                                            intent.putExtra("musId", musteriId);

                                            finish();
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
        });


        System.out.println(musteriId);
        idBusiness = findViewById(R.id.idBusiness);
        codeSubmit_Button = findViewById(R.id.codeSubmit_Button);

        Button reportBug = (Button) findViewById(R.id.reportBug_Button);

        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customerMainActivity.this, reportBugActivity.class);
                startActivity(intent);
            }
        });

        codeSubmit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String kod = idBusiness.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef2 = database.getReference().child("tbl_kullanicilar");
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            dataSnapshot.getKey();
                            keepData model = ds.getValue(keepData.class);


                            if (model.getIsletmeKodu().equals(kod)) {

                                Intent intent = new Intent(customerMainActivity.this, customerPanelActivity.class);
                                intent.putExtra("isId", model.getIsletmeKodu());
                                intent.putExtra("musId", musteriId);
                                finish();
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

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(customerMainActivity.this);

        builder.setTitle("ÇIKIŞ YAP");
        builder.setMessage("Hesabınızdan gerçekten çıkış yapmak istiyor musunuz?");
        builder.setCancelable(true);
        builder.setPositiveButton("ÇIKIŞ YAP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(customerMainActivity.this, loginActivity.class);
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
}
