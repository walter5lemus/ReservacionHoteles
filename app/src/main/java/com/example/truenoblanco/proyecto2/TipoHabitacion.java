package com.example.truenoblanco.proyecto2;

/**
 * Created by TruenoBlanco on 17/6/2016.
 */
public class TipoHabitacion {

    int id, maxpersonas;
    String tipoHabitacion,Descripcion;
    boolean disponible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxpersonas() {
        return maxpersonas;
    }

    public void setMaxpersonas(int maxpersonas) {
        this.maxpersonas = maxpersonas;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
