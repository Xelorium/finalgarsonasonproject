package com.example.garsonason;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class businessOrderIdleActivity extends AppCompatActivity {

    private ListView idleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_order_idle);

        idleListView = findViewById(R.id.gelenSiparis);

        String isletmeId = getIntent().getExtras().getString("isId");
    }
}
