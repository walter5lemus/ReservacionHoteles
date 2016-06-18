package com.example.truenoblanco.proyecto2;

/**
 * Created by TruenoBlanco on 3/6/2016.
 */
public class Transaccion {

   int id, idpromocion, personas;
    String  fechaInicio, fechaFinal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpromocion() {
        return idpromocion;
    }

    public void setIdpromocion(int idpromocion) {
        this.idpromocion = idpromocion;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
