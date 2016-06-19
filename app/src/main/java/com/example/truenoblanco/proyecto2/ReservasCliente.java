package com.example.truenoblanco.proyecto2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class ReservasCliente extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ControlBD helper;
    ProgressDialog progressDialog;
    int año,mes,dia,año2,mes2,dia2;
    Spinner spinnerPro;
    EditText personas, campoFecha,campoFecha2;
    Button BotonFecha,BotonFecha2;
    Calendar calendario,calendario2;
    static List<Habitacion> listaHabitaciones;
    ListView listViewHabitaciones;
    Conexion conn;
    static List<String> nombreDocentes;

    private DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    private DatePickerDialog.OnDateSetListener oyenteSelectorFecha2;
    private static final  int TIPO_DIALOGO = 0;
    private static final  int TIPO_DIALOGO2 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_cliente);
        helper = new ControlBD(this);
        nombreDocentes = new ArrayList<String>();
        listaHabitaciones = new ArrayList<Habitacion>();
        conn=new Conexion();
        progressDialog = new ProgressDialog(this);

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

        listViewHabitaciones = (ListView) findViewById(R.id.listView);
        int disponible = 1;
        String url="";
        url=conn.getURLLocal()+"/etapa2/ws_db_consultarHabitacion.php?disponible="+disponible;

        String materiasExternas ="";
        materiasExternas = ControladorServicio.obtenerRespuestaPeticion(url,this);
        System.out.println(materiasExternas);
        try {
            listaHabitaciones.addAll(ControladorServicio.obtenerHabitacionesExternas(materiasExternas, this));
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listViewHabitaciones.setOnItemClickListener(this);
        

    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try{
            if(calendario.before(calendario2)){
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Espere...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                Class<?> clase=Class.forName("com.example.truenoblanco.proyecto2.ConfirmacionReservacionCliente");
                Intent inte = new Intent(this,clase);
                Habitacion group = listaHabitaciones.get(position);

                //inte.putExtra("grupo",group);
                this.startActivity(inte);
            }


        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        //progressDialog.dismiss();
    }



    private void actualizarListView() {
        String dato = "";
        nombreDocentes.clear();
        for (int i = 0; i < listaHabitaciones.size(); i++) {
            dato = "Tipo Habitación:"+listaHabitaciones.get(i).getTipoHabitacion() + "\nDescripcion: "
                    +listaHabitaciones.get(i).getDescripcion() + "\nPrecio: "
                    +listaHabitaciones.get(i).getPrecio();

            nombreDocentes.add(dato);
        }
        //eliminarElementosDuplicados();
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreDocentes);
        listViewHabitaciones.setAdapter(adaptador);
    }
    private void eliminarElementosDuplicados() {
        HashSet<Habitacion> conjuntoMateria = new HashSet<Habitacion>();
        conjuntoMateria.addAll(listaHabitaciones);
        listaHabitaciones.clear();
        listaHabitaciones.addAll(conjuntoMateria);
        HashSet<String> conjuntoNombre = new HashSet<String>();
        conjuntoNombre.addAll(nombreDocentes);
        nombreDocentes.clear();
        nombreDocentes.addAll(conjuntoNombre);
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
