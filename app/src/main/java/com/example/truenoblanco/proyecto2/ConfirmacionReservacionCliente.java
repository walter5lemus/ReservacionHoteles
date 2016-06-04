package com.example.truenoblanco.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.Transaccion;

public class ConfirmacionReservacionCliente extends AppCompatActivity {

    String persona, habitacion, dias, promo, tipoHabitacion,fecha,precio;
    TextView mensaje,mensaje2,mensaje3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_reservacion_cliente);



        mensaje = (TextView)findViewById(R.id.tvReservaConfor);
        mensaje2 = (TextView) findViewById(R.id.Precio);
        mensaje3=(TextView) findViewById(R.id.precio2);

        Bundle bundle = getIntent().getExtras();

        persona = bundle.getString("personas");
        habitacion = bundle.getString("habitacion");
        dias = bundle.getString("dias");
        promo = bundle.getString("promo");
        tipoHabitacion = bundle.getString("tipoHab");
        fecha = bundle.getString("fecha");
        precio = bundle.getString("precio");

        mensaje.setText("RESERVA POR "+dias+ " DIAS \n COMENZANDO EL DIA "+fecha+"\n HABITACION PARA "+persona +" PERSONAS \nHABITACION # "+tipoHabitacion+" \nCANTIDAD DE HABITACIONES " +habitacion+ " \nPROMOCION: "+promo);
        mensaje2.setText("CON UN TOTAL A PAGAR DE");
        mensaje3.setText("$ "+precio);


    }

    public void iniciar(View v){
        Intent intent = new Intent(this, MenuCliente.class);
        startActivity(intent);

    }



}
