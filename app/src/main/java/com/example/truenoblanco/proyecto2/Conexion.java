package com.example.truenoblanco.proyecto2;

/**
 * Created by TruenoBlanco on 15/6/2016.
 */
public class Conexion {

    public String URLLocal = "http://169.254.80.80";

    public String URLServerLocal = "http://192.168.1.13";

    public Conexion(){}
    public String getURLServerLocal(){
        return URLServerLocal;
    }

    public String getURLLocal(){
        return URLLocal;
    }
}
