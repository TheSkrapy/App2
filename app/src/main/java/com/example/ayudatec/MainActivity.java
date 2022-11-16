package com.example.ayudatec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnEscanear;
    ImageButton btnGenerar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnEscanear = (ImageButton) findViewById(R.id.btnScanear); //Declaracion de botones
        btnGenerar=(ImageButton) findViewById(R.id.btnCrear);


        btnEscanear.setOnClickListener(view -> {  //Funcionamiento del boton escaner
            Intent c = new Intent(MainActivity.this, Escaner.class);
            startActivity(c);
        });

        btnGenerar.setOnClickListener(view -> {  //Funcionamiento del boton generar
            Intent c = new Intent(MainActivity.this, ContrasenaCrearQr.class);
            startActivity(c);
        });

    }
}