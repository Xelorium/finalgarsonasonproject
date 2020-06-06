package com.example.garsonason;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class registerButtonsActivity extends AppCompatActivity {

    public Button musteriKaydiButonu;
    public Button isletmeKaydiButonu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_buttons);

        Button reportBug = (Button) findViewById(R.id.reportBug_Button);

        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerButtonsActivity.this, reportBugActivity.class);
                startActivity(intent);
            }
        });

        musteriKaydiButonu = findViewById(R.id.customerRegisterEntry_Button);
        musteriKaydiButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musteriYonlendir();
            }
        });

        isletmeKaydiButonu = findViewById(R.id.businessRegisterEntry_Button);
        isletmeKaydiButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isletmeYonlendir();
            }
        });
    }

    public void musteriYonlendir() {
        Intent musteriKayitEkraniYonlendir = new Intent(this, customerRegisterActivity.class);
        startActivity(musteriKayitEkraniYonlendir);
    }

    public void isletmeYonlendir() {
        Intent isletmeKayitEkaniYonlendir = new Intent(this, businessRegisterActivity.class);
        startActivity(isletmeKayitEkaniYonlendir);
    }
}
