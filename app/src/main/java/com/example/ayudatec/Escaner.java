package com.example.ayudatec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayudatec.interfaces.OnScanQr;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Escaner extends AppCompatActivity implements OnScanQr {

    Handler handler = new Handler();
    @SuppressWarnings("deprecation")
    ProgressDialog progressDialog;
    TextView txtNcontrol;
    TextView txtNombre;
    TextView txtApellidoP;
    TextView txtApellidoM;
    TextView txtCarrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_formulario);
        txtNcontrol = findViewById(R.id.textView5);
        txtNombre = findViewById(R.id.textView9);
        txtApellidoP = findViewById(R.id.textView7);
        txtApellidoM = findViewById(R.id.textView6);
        txtCarrera = findViewById(R.id.textView8);

        IntentIntegrator integrador = new IntentIntegrator(Escaner.this);
        integrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrador.setPrompt("Lector QR");
        integrador.setCameraId(0);
        integrador.setBeepEnabled(true);
        integrador.setBarcodeImageEnabled(true);
        integrador.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Presentacion del resultado
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if (result.getContents()==null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                new fetchData(result.getContents(), this).start();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(int ncontrol, String nombre, String apellidoP, String apellidoM, String foto, String carrera) {
        txtNcontrol.setText(ncontrol+"");
        txtNombre.setText(nombre);
        txtApellidoP.setText(apellidoP);
        txtApellidoM.setText(apellidoM);
        txtCarrera.setText(carrera);
    }

    class fetchData extends Thread{
        String str;
        String data = "";
        OnScanQr qrData;
        int ncontrol;
        String nombre;
        String apellidoP;
        String apellidoM;
        String foto = "";
        String carrera;

        public fetchData (String str, OnScanQr qrData){
            this.str = str;
            this.qrData = qrData;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void run(){
            handler.post(() -> {
                progressDialog = new ProgressDialog( Escaner.this );
                progressDialog.setMessage("Obteniendo datos...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            });
            try{
                URL url = new URL( str );
                HttpURLConnection httpURLConnection = ( HttpURLConnection ) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ( (line = bufferedReader.readLine()) != null ){
                    data = new StringBuilder().append(data).append(line).toString();
                }
                if (!data.isEmpty()){

                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject jsonA = jsonObject.getJSONObject("data");

                    ncontrol = jsonA.getInt("ncontrol");
                    nombre = jsonA.getString("nombre");
                    carrera = jsonA.getString("carrera");
                    //Detectar apellido paterno, y apellido materno (proceso manual) posibles errores, recomendable cambiar diseno db
                    ArrayList<String> nombreC = new ArrayList<>(5);
                    StringBuilder n = new StringBuilder();
                    int length = 0;
                    for (int i=0; i<nombre.length();i++){
                        if (nombre.charAt(i) == ' ' || i == nombre.length()-1){
                            if (i != nombre.length()-1){
                                length += 1;
                                nombreC.add(n.toString());
                                n = new StringBuilder();
                            }
                            else{
                                length += 1;
                                n.append(nombre.charAt(i));
                                nombreC.add(n.toString());
                                n = new StringBuilder();
                            }
                        }
                        else {
                            n.append(nombre.charAt(i));
                        }
                    }
                    if (length == 3) {
                        nombre = nombreC.get(0);
                    }
                    if (length == 4) {
                        nombre = nombreC.get(0) + " " + nombreC.get(1);
                    }
                    if (length == 5) {
                        nombre = nombreC.get(0) + " " + nombreC.get(1) + " " + nombreC.get(2);
                    }

                    apellidoP = nombreC.get(length - 2);
                    apellidoM = nombreC.get(length - 1);
                    try {
                        synchronized (this) {
                            wait(2000);
                            runOnUiThread(() -> qrData.onClick(ncontrol, nombre, apellidoP, apellidoM, foto, carrera));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                if (progressDialog.isShowing()) progressDialog.dismiss();

            });
        }
    }
}