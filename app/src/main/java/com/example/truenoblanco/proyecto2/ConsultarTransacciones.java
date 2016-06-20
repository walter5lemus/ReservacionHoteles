package com.example.truenoblanco.proyecto2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConsultarTransacciones extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    static List<Transaccion> listaTransaccion;
    static List<Habitacion> listaHabitacion;
    Conexion conn;
    ListView listViewDocentes;
    static List<String> nombreDocentes;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_transacciones);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("Username");

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn=new Conexion();
        listaTransaccion = new ArrayList<Transaccion>();
        listaHabitacion = new ArrayList<Habitacion>();
        nombreDocentes = new ArrayList<String>();
        listViewDocentes = (ListView) findViewById(R.id.listView2);

        String url="";
        url=conn.getURLLocal()+"/etapa2/ws_db_consultarTransaccionUser.php?nick="+user;
        String materiasExternas ="";
        materiasExternas = ControladorServicio.obtenerRespuestaPeticion(url,this);
        try {
            listaTransaccion.addAll(ControladorServicio.obtenertransaccionesUserExterno(materiasExternas, this));
            listaHabitacion.addAll(ControladorServicio.obtenertransaccionesUserExterno1(materiasExternas, this));
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualizarListView();


    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final int posicion = position;

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Eliminar Autor");
        dialogo.setMessage("¿Desea eliminar o cancelar la Reservacion?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                eliminar(posicion);
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo.show();
    }

    public void eliminar(int position){

        try{
            String url="";
            url=conn.getURLLocal()+"/etapa2/ws_db_eliminarTransaccion.php?nick="+user+"&codhabitacion="+listaTransaccion.get(position).getCodHabitacion()+"&fechainicio="+listaTransaccion.get(position).getFecha1()+
                    "&fechafinal="+listaTransaccion.get(position).getFecha2()+"&personas="+listaTransaccion.get(position).getPersonas();
            int i = ControladorServicio.respuesta(url, this);
            if (i==1){
                Toast.makeText(this, "Eliminado con Exito", Toast.LENGTH_SHORT).show();
                String url2="";
                url2=conn.getURLLocal()+"/etapa2/ws_db_updateDisponible.php?codhabitacion="+listaTransaccion.get(position).getCodHabitacion();
                ControladorServicio.obtenerRespuestaPeticion(url2,this);
                actualizarListView();
            }
            else
                Toast.makeText(this, "Error, No ha sido Eliminado", Toast.LENGTH_SHORT).show();
        }   catch(Exception e){
            e.getStackTrace();
        }


        Class<?> clase= null;
        try {
            clase = Class.forName("com.example.truenoblanco.proyecto2.MenuCliente");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent intee = new Intent(this,clase);
        intee.putExtra("Username",user);
        startActivity(intee);

    }

    public void cancelar() {
        Toast.makeText(this, "Registro no eliminado", Toast.LENGTH_SHORT).show();
    }


    private void actualizarListView() {
        String dato = "";
        ArrayList<String> listaHabitaciones = new ArrayList<String>();
        nombreDocentes.clear();
        for (int i = 0; i < listaHabitacion.size(); i++) {
            listaHabitaciones.add(listaHabitacion.get(i).getTipoHabitacion().toString());
        }
        for (int i = 0; i < listaTransaccion.size(); i++) {
            int a=i+1;
            dato = "Transaccion N°="+a+"\nHabitacion: "+
                    listaTransaccion.get(i).getCodHabitacion() +"\nTipo Habitacion: "
                    +listaHabitaciones.get(i)+"\nFecha Inicio: "
                    + listaTransaccion.get(i).getFecha1() + "\nFecha Final: "
                    +listaTransaccion.get(i).getFecha2()  +"\nPersonas: "
                    +listaTransaccion.get(i).getPersonas();
            nombreDocentes.add(dato);
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreDocentes);
        listViewDocentes.setAdapter(adaptador);
        listViewDocentes.setOnItemClickListener(this);
    }

}
