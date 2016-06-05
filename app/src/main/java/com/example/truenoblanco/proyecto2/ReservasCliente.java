package com.example.truenoblanco.proyecto2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.Transaccion;

import java.util.Calendar;

public class ReservasCliente extends AppCompatActivity {

    ControlBD helper;
    int año,mes,dia;
    float precio=0;
    String[] menuPromo;
    String[] TippoHabitacion;
    String pers, hab, Dia, promoSTR, habSTR;
    Spinner spinnerPro, spinnerHab;
    EditText personas, habitaciones, dias, promociones, tipoHab,campoFecha;
    Button BotonFecha,imagebutton;
    private static final  int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;

    Spinner spPersonas, spHabitaciones, spTipos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_cliente);
        helper = new ControlBD(this);

        campoFecha = (EditText) findViewById(R.id.campoFecha);
        BotonFecha = (Button) findViewById(R.id.botonFecha);
        Calendar calendario = Calendar.getInstance();
        año = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mostrarFecha();
        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view,int year, int MonthofYear, int dayOfMonth){
                año = year;
                mes = MonthofYear+1;
                dia = dayOfMonth;
                mostrarFecha();
            }
        };

        personas = (EditText) findViewById(R.id.edtPersonaReservacion);
        habitaciones = (EditText) findViewById(R.id.edtHabitacionReservacion);
        dias = (EditText) findViewById(R.id.edtDiasReservaReservacion);
        spinnerPro = (Spinner) findViewById(R.id.spPromociones);

        ArrayAdapter adapPromo = ArrayAdapter.createFromResource(this, R.array.promo, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapHabitacion = ArrayAdapter.createFromResource(this, R.array.tipoHabitacion, android.R.layout.simple_spinner_dropdown_item);

        adapPromo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapHabitacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPro.setAdapter(adapPromo);
        spinnerHab.setAdapter(adapHabitacion);
        spinnerHab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub
                int  pos=spinnerHab.getSelectedItemPosition()-1;
                //precio = ;
                Toast.makeText(getApplicationContext(), "selected "+ pos +" con precio "+precio, Toast.LENGTH_SHORT).show();

                /**** do your code*****/
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });



    }



    @Override
    public Dialog onCreateDialog(int id){
        switch (id){
            case 0:
                return new DatePickerDialog(this,oyenteSelectorFecha,año,mes,dia);

        }
        return null;
    }

    public void mostrarCalendario(View control){
        showDialog(TIPO_DIALOGO);
    }

    public void mostrarFecha(){
        campoFecha.setText(dia + "/" + mes + "/" + año);
    }

    public void avanzar(View v) {

        pers = personas.getText().toString();
        hab = habitaciones.getText().toString();
        Dia = dias.getText().toString();
        promoSTR = spinnerPro.getSelectedItem().toString();
        habSTR = spinnerHab.getSelectedItem().toString();

        Intent intent=new Intent(this, ConfirmacionReservacionCliente.class);
        intent.putExtra("personas",pers);
        intent.putExtra("habitacion",hab);
        intent.putExtra("dias", Dia);
        intent.putExtra("promo", promoSTR);
        intent.putExtra("fecha",campoFecha.getText().toString());
        intent.putExtra("precio", String.valueOf(precio));
        startActivity(intent);
        insertarTransaccion(v);

    }

    public void insertarTransaccion(View v) {

        Transaccion transaccion=new Transaccion();
        transaccion.setFecha(campoFecha.getText().toString());
        transaccion.setDias(Integer.parseInt(Dia));
        transaccion.setPersonas(Integer.parseInt(pers));
        transaccion.setHabitaciones(Integer.parseInt(hab));
        transaccion.setPromociones(promoSTR);
        helper.abrir();
        String regInsertados=helper.insertar(transaccion);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }




}
