package com.example.ayudatec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnEscanear;
    Button btnGenerar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnEscanear = (Button) findViewById(R.id.btnScanear);
        btnGenerar=(Button) findViewById(R.id.btnCrear);


        btnEscanear.setOnClickListener(view -> {
            Intent c = new Intent(MainActivity.this, Escaner.class);
            startActivity(c);
        });

        btnGenerar.setOnClickListener(view -> {
            Intent c = new Intent(MainActivity.this, CrearQr.class);
            startActivity(c);
        });

    }
}