package com.example.truenoblanco.proyecto2;

/**
 * Created by TruenoBlanco on 17/6/2016.
 */
public class Conexion {

    public String URLLocal = "http://192.168.1.6";
    public String URLServerLocal = "http://192.168.1.6";
    //public String URLServerPublico = "";
    public Conexion(){}
    public String getURLServerLocal(){
        return URLServerLocal;
    }
    /*public String getURLServerPublico(){
        return URLServerPublico;
    }*/
    public String getURLLocal(){
        return URLLocal;
    }
}
