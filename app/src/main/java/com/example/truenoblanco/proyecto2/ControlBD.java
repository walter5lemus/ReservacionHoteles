package com.example.truenoblanco.proyecto2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ControlBD {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControlBD(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }



    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "proyecto2.s3db";
        private static final int VERSION = 1;
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("CREATE TABLE transaccion(id integer not null primary key autoincrement, " +
                        "fechaInicio VARCHAR(30) NOT NULL,fechaFinal  Varchar(30) NOT NULL,personas integer NOT NULL," +
                        "promociones VARCHAR(30),tipohabitacion varchar(30) NOT NULL);");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        }
    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        DBHelper.close();
    }

    /*public String insertar(Transaccion transaccion) {

        String regInsertados="Registro Insertado Nº= ";
        long contador=0;

            ContentValues trans = new ContentValues();
            trans.put("fecha", transaccion.getFecha());
            trans.put("dias", transaccion.getDias());
            trans.put("personas", transaccion.getPersonas());
            trans.put("habitaciones", transaccion.getHabitaciones());
            trans.put("promociones", transaccion.getPromociones());
            trans.put("tipohabitacion", transaccion.getTipohabitacion());
            contador = db.insert("transaccion", null, trans);
            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            } else {
                regInsertados = regInsertados + contador;
            }




        return regInsertados;

    }*/


}
