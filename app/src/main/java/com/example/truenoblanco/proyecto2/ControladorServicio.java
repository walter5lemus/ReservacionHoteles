package com.example.truenoblanco.proyecto2;

/**
 * Created by TruenoBlanco on 9/6/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.Contact;
import com.example.truenoblanco.proyecto2.login.SignUp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ControladorServicio {

    public static String obtenerRespuestaPeticion(String url, Context ctx) {

        String respuesta = " ";

        // Estableciendo tiempo de espera del servicio
        HttpParams parametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parametros, 3000);
        HttpConnectionParams.setSoTimeout(parametros, 5000);

        // Creando objetos de conexion
        HttpClient cliente = new DefaultHttpClient(parametros);
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpRespuesta = cliente.execute(httpGet);
            StatusLine estado = httpRespuesta.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                HttpEntity entidad = httpRespuesta.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG).show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }

    public static String obtenerRespuestaPost(String url, JSONObject obj,
                                              Context ctx) {
        String respuesta = " ";
        try {
            HttpParams parametros = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(parametros, 3000);
            HttpConnectionParams.setSoTimeout(parametros, 5000);

            HttpClient cliente = new DefaultHttpClient(parametros);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("content-type", "application/json");

            StringEntity nuevaEntidad = new StringEntity(obj.toString());
            httpPost.setEntity(nuevaEntidad);
            Log.v("Peticion",url);
            Log.v("POST", httpPost.toString());
            HttpResponse httpRespuesta = cliente.execute(httpPost);
            StatusLine estado = httpRespuesta.getStatusLine();

            int codigoEstado = estado.getStatusCode();

            if (codigoEstado == 200) {
                respuesta = Integer.toString(codigoEstado);
                Log.v("respuesta",respuesta);
            }
            else{
                Log.v("respuesta",Integer.toString(codigoEstado));
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
                    .show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }

        return respuesta;
    }

    public static int respuesta(String peticion, Context ctx) {

        String json = obtenerRespuestaPeticion(peticion, ctx);

        int i=1;
        try {
            JSONObject resultado  = new JSONObject(json);
            int respuesta = resultado.getInt("resultado");


            if (respuesta == 1){
                //Toast.makeText(ctx, "Registro ingresado", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
            e.getMessage();
            return 0;
        }

        return i;
    }

    public static List obtenerpass(String json, Context ctx) {

        List<Contact> listaClientes = new ArrayList<Contact>();

        try {
            JSONArray clientesJSON = new JSONArray(json);
            for (int i = 0; i < clientesJSON.length(); i++) {

                JSONObject obj = clientesJSON.getJSONObject(i);

                Contact cliente = new Contact();

                cliente.setUname(obj.getString("nick"));
                cliente.setPass(obj.getString("pass"));
                listaClientes.add(cliente);
            }
            return listaClientes;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }

    }

    public static List<Habitacion> obtenerHabitacionesExternas(String json, Context ctx) {

        List<Habitacion> listaDocentes = new ArrayList<Habitacion>();

        try {
            JSONArray docentesJSON = new JSONArray(json);
            for (int i = 0; i < docentesJSON.length(); i++) {

                JSONObject obj = docentesJSON.getJSONObject(i);

                Habitacion docente = new Habitacion();

                docente.setCodHabitacion(obj.getString("CODHABITACION"));
                docente.setTipoHabitacion(obj.getString("TIPOHABITACION"));
                docente.setDescripcion(obj.getString("DESCRIPCION"));
                docente.setPrecio(Float.parseFloat(obj.getString("PRECIO")));
                docente.setDisponible(obj.getInt("DISPONIBLE"));
                docente.setMaxPersonas(obj.getInt("MAXPERSONAS"));
                listaDocentes.add(docente);
            }
            return listaDocentes;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }

    }

    public static List<Transaccion> obtenerTransacciones(String json, Context ctx) {

        List<Transaccion> listaDocentes = new ArrayList<Transaccion>();
        try {
            JSONArray docentesJSON = new JSONArray(json);
            for (int i = 0; i < docentesJSON.length(); i++) {

                JSONObject obj = docentesJSON.getJSONObject(i);

                Transaccion docente = new Transaccion();
                Calendar calendario = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                docente.setId(Integer.parseInt(obj.getString("ID")));
                docente.setCodHabitacion(obj.getString("CODHABITACION"));
                docente.setFechaInicio(Date.valueOf(obj.getString("FECHAINICIO")));
                docente.setFechaFinal(Date.valueOf(obj.getString("FECHAFINAL")));
                docente.setPersonas(obj.getInt("PERSONAS"));
                listaDocentes.add(docente);
            }
            return listaDocentes;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON Transaccion", Toast.LENGTH_LONG)
                    .show();
            return null;
        }

    }

    public static List<Transaccion> obtenertransaccionesUserExterno(String json, Context ctx) {

        List<Transaccion> listaTransaccion = new ArrayList<Transaccion>();

        try {
            JSONArray docentesJSON = new JSONArray(json);
            for (int i = 0; i < docentesJSON.length(); i++) {
                JSONObject obj = docentesJSON.getJSONObject(i);
                Transaccion transaccion = new Transaccion();
                transaccion.setCodHabitacion(obj.getString("codhabitacion"));
                transaccion.setFecha1(obj.getString("fechainicio"));
                transaccion.setFecha2(obj.getString("fechafinal"));
                transaccion.setPersonas(obj.getInt("personas"));
                listaTransaccion.add(transaccion);
            }
            return listaTransaccion;
        } catch (Exception e) {
            e.getStackTrace();
            Toast.makeText(ctx, "Error en parseo de JSON1", Toast.LENGTH_LONG)
                    .show();
            return null;
        }

    }
    public static List<Habitacion> obtenertransaccionesUserExterno1(String json, Context ctx) {

        List<Habitacion> listaHabitacion = new ArrayList<Habitacion>();

        try {
            JSONArray docentesJSON = new JSONArray(json);
            for (int i = 0; i < docentesJSON.length(); i++) {

                JSONObject obj = docentesJSON.getJSONObject(i);
                Habitacion habitacion = new Habitacion();
                habitacion.setTipoHabitacion(obj.getString("tipohabitacion"));
                listaHabitacion.add(habitacion);
            }
            return listaHabitacion;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON2", Toast.LENGTH_LONG)
                    .show();
            return null;
        }

    }
}





