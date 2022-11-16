package com.example.ayudatec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContrasenaCrearQr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_crear_qr);

        Button btnIngresar = findViewById(R.id.btnIngresar); //Declaracion del boton


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText user = findViewById(R.id.txtUsuario);   //Declaracion de argumentos
                EditText contra =findViewById(R.id.txtContra);
                String User= user.getText().toString();
                String Contra= contra.getText().toString();

                if (User.equals("Admin") && Contra.equals("SGD 04051125")){ //Validacion de contraseña
                        Intent c = new Intent(ContrasenaCrearQr.this, Formulario.class);
                        startActivity(c);
                }
                else {  //Respuesta a contraseña incorrecta
                    user.setText("");
                    user.setHint("Usuario equivocado");
                    contra.setText("");
                    contra.setHint("Contraseña equivocada");
                }
            }
        });
    }
}