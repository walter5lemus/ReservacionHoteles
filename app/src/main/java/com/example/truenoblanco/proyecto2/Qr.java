package com.example.truenoblanco.proyecto2;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Qr extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    String promocion=null;
    int promo;
    String persona,user;
    String tipoHabitacion,codHabitacion;
    String fechaInicio, fechaFinal;
    Float precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Bundle bundle = getIntent().getExtras();
        persona = bundle.getString("personas");
        fechaInicio = bundle.getString("fechainicio");
        fechaFinal = bundle.getString("fechafinal");
        tipoHabitacion = bundle.getString("tipohabitacion");
        precio = bundle.getFloat("precio");
        codHabitacion = bundle.getString("codhabitacion");
        user = bundle.getString("Username");

    }

    public void QrScanner(View view){

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();   // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        promocion=rawResult.getText().toString();
        promo = Integer.parseInt(promocion);

        Class<?> clase= null;

        try {
            clase = Class.forName("com.example.truenoblanco.proyecto2.ConfirmacionReservacionCliente");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent inte = new Intent(this,clase);
        inte.putExtra("promocion",promocion);
        inte.putExtra("promo",promo);
        inte.putExtra("fechainicio",fechaInicio);
        inte.putExtra("fechafinal",fechaFinal);
        inte.putExtra("personas",persona);
        inte.putExtra("tipohabitacion",tipoHabitacion);
        inte.putExtra("codhabitacion",codHabitacion);
        inte.putExtra("precio",precio);
        inte.putExtra("Username",user);
        startActivity(inte);
        this.startActivity(inte);


        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    public void regresar(View v){
    Class<?> clase= null;

    try {
        clase = Class.forName("com.example.truenoblanco.proyecto2.ConfirmacionReservacionCliente");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    Intent inte = new Intent(this,clase);
        inte.putExtra("promocion",promocion);
        inte.putExtra("promo",promo);
        inte.putExtra("fechainicio",fechaInicio);
        inte.putExtra("fechafinal",fechaFinal);
        inte.putExtra("personas",persona);
        inte.putExtra("tipohabitacion",tipoHabitacion);
        inte.putExtra("codhabitacion",codHabitacion);
        inte.putExtra("precio",precio);
        inte.putExtra("Username",user);
        this.startActivity(inte);
    }
}
