package com.example.truenoblanco.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EstadoDeCuentaCliente extends AppCompatActivity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_de_cuenta_cliente);
    }

    public void salirEstadoCliente(View d){
        Intent salir=new Intent(this,MenuCliente.class);
        startActivity(salir);
    }
}
