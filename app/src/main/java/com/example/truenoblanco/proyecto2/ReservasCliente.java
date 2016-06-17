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
import android.widget.Toast;

import java.util.Calendar;

public class ReservasCliente extends AppCompatActivity {

    ControlBD helper;
    int año,mes,dia,año2,mes2,dia2;
    String pers, hab, Dia, promoSTR, habSTR;
    Spinner spinnerPro, spinnerHab;
    EditText personas, habitaciones, dias,campoFecha,campoFecha2;
    Button BotonFecha,BotonFecha2;
    Calendar calendario,calendario2;

    private DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    private DatePickerDialog.OnDateSetListener oyenteSelectorFecha2;
    private static final  int TIPO_DIALOGO = 0;
    private static final  int TIPO_DIALOGO2 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_cliente);
        helper = new ControlBD(this);

        campoFecha = (EditText) findViewById(R.id.campoFecha);
        BotonFecha = (Button) findViewById(R.id.botonFecha);
        calendario = Calendar.getInstance();
        año = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mostrarFecha();
        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view,int year, int MonthofYear, int dayOfMonth){
                año = year;
                mes = MonthofYear+1;
                dia = dayOfMonth;
                mostrarFecha();
            }
        };

        campoFecha2 = (EditText) findViewById(R.id.campoFecha2);
        BotonFecha2 = (Button) findViewById(R.id.botonFecha2);
        calendario2 = Calendar.getInstance();
        año2 = calendario2.get(Calendar.YEAR);
        mes2 = calendario2.get(Calendar.MONTH);
        dia2 = calendario2.get(Calendar.DAY_OF_MONTH)+1;
        mostrarFecha2();
        oyenteSelectorFecha2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view,int year, int MonthofYear, int dayOfMonth){
                año2 = year;
                mes2 = MonthofYear+1;
                dia2 = dayOfMonth;
                mostrarFecha2();
            }
        };

        personas = (EditText) findViewById(R.id.edtPersonaReservacion);
        spinnerPro = (Spinner) findViewById(R.id.spPromociones);
        ArrayAdapter adapPromo = ArrayAdapter.createFromResource(this, R.array.promo, android.R.layout.simple_spinner_dropdown_item);
        adapPromo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPro.setAdapter(adapPromo);

        if(calendario.before(calendario2)){
            final int si=1;
        }



    }




    @Override
    public Dialog onCreateDialog(int id){
        switch (id){
            case 0:
                return new DatePickerDialog(this,oyenteSelectorFecha,año,mes,dia);
            case 1:
                return new DatePickerDialog(this,oyenteSelectorFecha2,año2,mes2,dia2);

        }
        return null;
    }

    public void mostrarCalendario(View control){
        showDialog(TIPO_DIALOGO);
    }
    public void mostrarCalendario2(View control){
        showDialog(TIPO_DIALOGO2);
    }



    public void mostrarFecha(){
        campoFecha.setText(dia + "/" + mes + "/" + año);
    }
    public void mostrarFecha2(){
        campoFecha2.setText(dia2 + "/" + mes2 + "/" + año2);
    }


    public void avanzar(View v) {


        if(calendario.after(calendario2)){
            Intent intent=new Intent(this, ConfirmacionReservacionCliente.class);

            startActivity(intent);

        }
        else
            Toast.makeText(ReservasCliente.this, "La Fecha Final no puede ser antes que la Fecha de Inicio", Toast.LENGTH_SHORT).show();




    }



}
