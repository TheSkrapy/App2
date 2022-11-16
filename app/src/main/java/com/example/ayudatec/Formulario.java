package com.example.ayudatec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class Formulario extends AppCompatActivity {
    ImageButton btnGenerar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        btnGenerar = (ImageButton) findViewById(R.id.btnGenerar);

        EditText Nc = findViewById(R.id.txtNC);   //Declaracion de argumentos
        EditText nombre =findViewById(R.id.txtNombre);
        EditText Ap = findViewById(R.id.txtApellidoP);
        EditText Am = findViewById(R.id.txtApellidoM);
        EditText carrera = findViewById(R.id.txtCarrera);
        String NC=  Nc.getText().toString();
        String Nombre=  nombre.getText().toString();
        String AP=  Ap.getText().toString();
        String AM=  Am.getText().toString();
        String Carrera=  carrera.getText().toString();


    }
}