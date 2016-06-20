package com.example.truenoblanco.proyecto2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReservasCliente extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ControlBD helper;

    ProgressDialog progressDialog;
    int año,mes,dia,año2,mes2,dia2;
    Spinner spinnerPro;
    EditText personas, campoFecha,campoFecha2;
    Button BotonFecha,BotonFecha2;
    Calendar calendario,calendario2,actual;
    Date fechaActual;
    static List<Habitacion> listaHabitaciones;
    static List<Transaccion> listaTransaccion;
    String user;

    ListView listViewHabitaciones;
    Conexion conn;
    static List<String> nombreHabitaciones;

    private DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    private DatePickerDialog.OnDateSetListener oyenteSelectorFecha2;
    private static final  int TIPO_DIALOGO = 0;
    private static final  int TIPO_DIALOGO2 = 1;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_cliente);
        helper = new ControlBD(this);
        fechaActual=new Date();
        nombreHabitaciones = new ArrayList<String>();
        listaHabitaciones = new ArrayList<Habitacion>();
        listaTransaccion = new ArrayList<Transaccion>();
        conn=new Conexion();
        progressDialog = new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("Username");

        campoFecha = (EditText) findViewById(R.id.campoFecha);
        BotonFecha = (Button) findViewById(R.id.botonFecha);
        calendario = Calendar.getInstance();
        año = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH)+1;
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        calendario.set(año,mes,dia);
         actual = Calendar.getInstance();
         actual.set(año,mes,dia);
        mostrarFecha();
        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view,int year, int MonthofYear, int dayOfMonth){
                año = year;
                mes = MonthofYear;
                dia = dayOfMonth;
                calendario.set(año,mes,dia);
                mostrarFecha();
            }
        };

        campoFecha2 = (EditText) findViewById(R.id.campoFecha2);
        BotonFecha2 = (Button) findViewById(R.id.botonFecha2);
        calendario2 = Calendar.getInstance();
        año2 = calendario2.get(Calendar.YEAR);
        mes2 = calendario2.get(Calendar.MONTH)+1;
        dia2 = calendario2.get(Calendar.DAY_OF_MONTH)+1;
        mostrarFecha2();
        calendario2.set(año2,mes2,dia2);
        oyenteSelectorFecha2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view,int year2, int MonthofYear2, int dayOfMonth2){
                año2 = year2;
                mes2 = MonthofYear2;
                dia2 = dayOfMonth2;
                calendario2.set(año2,mes2,dia2);
                mostrarFecha2();
            }
        };


        personas = (EditText) findViewById(R.id.edtPersonaReservacion);
        ArrayAdapter adapPromo = ArrayAdapter.createFromResource(this, R.array.promo, android.R.layout.simple_spinner_dropdown_item);
        adapPromo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        listViewHabitaciones = (ListView) findViewById(R.id.listView);
        int disponible = 1;
        String url="";
        url=conn.getURLLocal()+"/etapa2/ws_db_consultarHabitacion.php?disponible="+disponible;

        String materiasExternas ="";
        updateDisponible();

        materiasExternas = ControladorServicio.obtenerRespuestaPeticion(url,this);
        try {
            listaHabitaciones.addAll(ControladorServicio.obtenerHabitacionesExternas(materiasExternas, this));
            updateDisponible();
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listViewHabitaciones.setOnItemClickListener(this);

        String url1="";
        url1=conn.getURLLocal()+"/etapa2/ws_db_consultarTransaccion.php";
        String materiasExterna ="";
        materiasExterna = ControladorServicio.obtenerRespuestaPeticion(url1,this);

        try {
            listaTransaccion.addAll(ControladorServicio.obtenerTransacciones(materiasExterna, this));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateDisponible(){
        Date fechaActual = new Date();
        fechaActual.getTime();

        Date fechaFinal;
        for (int i = 0; i < listaTransaccion.size(); i++) {
            fechaFinal = listaTransaccion.get(i).getFechaFinal();

            System.out.println(actual);
            System.out.println(fechaFinal);
            if (fechaActual.after(fechaFinal)){
                String url2="";
                url2=conn.getURLLocal()+"/etapa2/ws_db_updateDisponible.php?codhabitacion="+listaTransaccion.get(i).getCodHabitacion();
                ControladorServicio.obtenerRespuestaPeticion(url2,this);
            }
        }
    }



    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Habitacion habitacion = listaHabitaciones.get(position);
        int numPersona;


        Float precio = habitacion.getPrecio();

        long end = calendario2.getTimeInMillis();
        long start = calendario.getTimeInMillis();
        long dias= TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));

            if(calendario.before(calendario2)){
                   try{
                        numPersona = Integer.parseInt(personas.getText().toString());
                        if(habitacion.getMaxPersonas()>=numPersona){
                    Class<?> clase= null;
                    try {
                        clase = Class.forName("com.example.truenoblanco.proyecto2.ConfirmacionReservacionCliente");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                            precio = precio*dias;
                            Intent inte = new Intent(this,clase);
                            inte.putExtra("fechainicio",campoFecha.getText().toString());
                            inte.putExtra("fechafinal",campoFecha2.getText().toString());
                            inte.putExtra("personas",personas.getText().toString());
                            inte.putExtra("tipohabitacion",habitacion.getTipoHabitacion());
                            inte.putExtra("codhabitacion",habitacion.getCodHabitacion());
                            inte.putExtra("precio",precio);
                            inte.putExtra("Username",user);
                           this.startActivity(inte);
                    }
                    else{
                    Toast.makeText(ReservasCliente.this, "El numero de personas excede al permitido", Toast.LENGTH_SHORT).show();
                    }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(ReservasCliente.this, "LLene el campo Personas", Toast.LENGTH_SHORT).show();
                    }

            }else
                Toast.makeText(ReservasCliente.this, "La fecha de inicio tiene que ser antes que la fecha final", Toast.LENGTH_SHORT).show();
    }

    private void actualizarListView() {
        String dato = "";
        nombreHabitaciones.clear();
        for (int i = 0; i < listaHabitaciones.size(); i++) {
            dato = "Codigo Habitacion: "+listaHabitaciones.get(i).getCodHabitacion()+"\nTipo Habitación:"+listaHabitaciones.get(i).getTipoHabitacion() + "\nDescripcion: "
                    +listaHabitaciones.get(i).getDescripcion() + "\nPrecio: $"
                    +listaHabitaciones.get(i).getPrecio();

            nombreHabitaciones.add(dato);
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreHabitaciones);
        listViewHabitaciones.setAdapter(adaptador);
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
        campoFecha.setText(año+"-"+ mes+"-"+dia);
    }
    public void mostrarFecha2(){
        campoFecha2.setText(año2+"-"+mes2+"-"+dia2);
    }

}
