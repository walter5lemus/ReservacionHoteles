package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);


    }

    public void reservar(View d){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        Intent inicioReservar=new Intent(this,ReservasCliente.class);
        progressDialog.dismiss();
        startActivity(inicioReservar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Espere...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    public void promociones(View d){
        Intent iniPromociones=new Intent(this,Promociones.class);
        startActivity(iniPromociones);
    }

   public void estadoCuenta(View d){
        Intent iniEstadoCuenta=new Intent(this,EstadoDeCuentaCliente.class);
        startActivity(iniEstadoCuenta);
    }

   /* public void renovarReservacion(View v){
        Intent intent = new Intent(this, AmpliarReserva.class);
        startActivity(intent);
    }

    public void salirMenuCliente(View d){
        Intent salir=new Intent(this,MainActivity.class);
        startActivity(salir);
    }*/
}
