package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuCliente extends AppCompatActivity {

    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("Username");


    }

    public void reservar(View d){
        final ProgressDialog progressDialog = new ProgressDialog(this);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Espere...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Intent inicioReservar=new Intent(this,ReservasCliente.class);
        inicioReservar.putExtra("Username", user);

        startActivity(inicioReservar);
        progressDialog.dismiss();
    }
    public void promociones(View d){
        Intent iniPromociones=new Intent(this,Promociones.class);
        iniPromociones.putExtra("Username", user);
        startActivity(iniPromociones);
    }

   public void ConsultarTransacciones(View v){
        Intent intent = new Intent(this, ConsultarTransacciones.class);
       intent.putExtra("Username",user);
        startActivity(intent);
    }

    public void TipoHabitaciones(View v){
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    /*public void salirMenuCliente(View d){
        Intent salir=new Intent(this,MainActivity.class);
        startActivity(salir);
    }*/
}
