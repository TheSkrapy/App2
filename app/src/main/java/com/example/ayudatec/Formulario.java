package com.example.ayudatec;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ayudatec.interfaces.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Formulario extends AppCompatActivity {
    ImageButton btnGenerar;
    ArrayAdapter<String> adapterItems;
    int NC;
    String Nombre;
    String AP;
    String AM;
    String Carrera="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        String[] carreras = {"ING. TICS", "ING. INDUSTRIAL", "ING. MECATRONICA", "LIC. ADMINISTRACION"};
        btnGenerar = (ImageButton) findViewById(R.id.btnGenerar);

        EditText Nc = findViewById(R.id.txtNC);   //Declaracion de argumentos
        EditText nombre =findViewById(R.id.txtNombre);
        EditText Ap = findViewById(R.id.txtApellidoP);
        EditText Am = findViewById(R.id.txtApellidoM);
        AutoCompleteTextView carrera = findViewById(R.id.txtCarrera);
        String[] item = new String[1];

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, carreras);
        carrera.setAdapter(adapterItems);

        carrera.setOnItemClickListener((parent, view, position, id) ->
                item[0] = parent.getItemAtPosition(position).toString());

        btnGenerar.setOnClickListener(view -> {
            NC=  Integer.parseInt(Nc.getText().toString());
            Nombre=  nombre.getText().toString();
            AP=  Ap.getText().toString();
            AM=  Am.getText().toString();
            Carrera = item[0];
            btnSendPostRequestClicked();
            Intent c = new Intent(Formulario.this, CrearQr.class);
            startActivity(c);
        });

    }

    private void btnSendPostRequestClicked() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Alumno alumno = new Alumno(NC, Nombre + " " + AP + " " + AM, null, Carrera);
        Call<Alumno> call = apiInterface.getAlumnoInformation(alumno);

        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(@NonNull Call<Alumno> call, @NonNull Response<Alumno> response) {
                Log.e(TAG, "onResponse:" + response.code());
               // Log.e(TAG, "onResponse: ncontrol" + response.body().getNcontrol());
            }

            @Override
            public void onFailure(@NonNull Call<Alumno> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure:" + t.getMessage());
            }
        });
    }
}