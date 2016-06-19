package com.example.truenoblanco.proyecto2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ConsultarTransacciones extends AppCompatActivity {

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
        System.out.println(user);
        String materiasExternas ="";
        materiasExternas = ControladorServicio.obtenerRespuestaPeticion(url,this);
        System.out.println(materiasExternas);
        try {
            listaTransaccion.addAll(ControladorServicio.obtenertransaccionesUserExterno(materiasExternas, this));
            listaHabitacion.addAll(ControladorServicio.obtenertransaccionesUserExterno1(materiasExternas, this));
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void actualizarListView() {
        String dato = "";
        nombreDocentes.clear();
        for (int i = 0; i < listaTransaccion.size(); i++) {
            dato = "Transaccion N°="+i+"\nHabitacion: "+
                    listaTransaccion.get(i).getCodHabitacion() + "\nFecha Inicio: "
                    + listaTransaccion.get(i).getFecha1() + "\nFecha Final: "
                    +listaTransaccion.get(i).getFecha2()  +"\nPersonas: "
                    +listaTransaccion.get(i).getPersonas() +"\n";
                    System.out.println(listaTransaccion.get(i));
            nombreDocentes.add(dato);
        }
        /*for (int i = 0; i < listaHabitacion.size(); i++) {
            dato = "Tipo de Habitacion: "+
                    listaHabitacion.get(i).getTipoHabitacion();
            nombreDocentes.add(dato);
        }*/
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreDocentes);
        listViewDocentes.setAdapter(adaptador);
    }
}
