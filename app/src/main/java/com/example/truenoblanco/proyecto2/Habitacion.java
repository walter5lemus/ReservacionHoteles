package com.example.truenoblanco.proyecto2;

/**
 * Created by TruenoBlanco on 17/6/2016.
 */
public class Habitacion {

    float precio;
    int disponible;
    String codHabitacion,tipoHabitacion,descripcion;

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public String getCodHabitacion() {
        return codHabitacion;
    }

    public void setCodHabitacion(String codHabitacion) {
        this.codHabitacion = codHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
