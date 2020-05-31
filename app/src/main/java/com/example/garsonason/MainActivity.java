package com.example.garsonason;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public Button girisYapButonu;
    public Button kayitOlButonu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        kayitOlButonu = findViewById(R.id.register_Button);
        kayitOlButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitOlYonlendir();
            }
        });


        girisYapButonu = findViewById(R.id.login_Button);
        girisYapButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girisYapYonlendir();
            }
        });
    }

    public void girisYapYonlendir() {
        Intent girisEkraniYonlendir = new Intent(this, loginActivity.class);
        startActivity(girisEkraniYonlendir);
    }

    public void kayitOlYonlendir() {
        Intent kayitEkraniYonlendir = new Intent(this, registerButtonsActivity.class);
        startActivity(kayitEkraniYonlendir);
    }
}
