package com.example.garsonason;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class reportBugActivity extends AppCompatActivity {

    private ProgressDialog progressDialog1;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        progressDialog1 = new ProgressDialog(this);

        final EditText reportBugCompEdittext = (EditText) findViewById(R.id.reportBugCompEdittext);
        final EditText reportBugEdittext = (EditText) findViewById(R.id.reportBugEdittext);
        Button reportBug_SubmitButton = (Button) findViewById(R.id.reportBug_SubmitButton);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        final DatabaseReference myRef2 = database.getReference().child("tbl_report").child(date).push();


        reportBug_SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportBaslik = reportBugCompEdittext.getText().toString();
                String reportAciklama = reportBugEdittext.getText().toString();

                if (!reportBaslik.isEmpty() && !reportAciklama.isEmpty()) {

                    HashMap<String, String> reportGonder = new HashMap<>();
                    reportGonder.put("baslik", reportBaslik);
                    reportGonder.put("aciklama", reportAciklama);

                    progressDialog1.setTitle("GÖNDERİLİYOR");
                    progressDialog1.setMessage("Lütfen bekleyin...");
                    progressDialog1.setCanceledOnTouchOutside(false);
                    progressDialog1.show();

                    myRef2.setValue(reportGonder).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog1.dismiss();
                                Toast.makeText(getApplicationContext(), "Hata bildiriminiz başarıyla iletildi. Teşekkürler!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen ilgili alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}
