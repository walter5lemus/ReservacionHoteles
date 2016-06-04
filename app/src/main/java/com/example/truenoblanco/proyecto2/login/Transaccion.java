package com.example.truenoblanco.proyecto2.login;

/**
 * Created by TruenoBlanco on 3/6/2016.
 */
public class Transaccion {

    String fecha,promociones,tipohabitacion;
    int dias,personas,habitaciones;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPromociones() {
        return promociones;
    }

    public void setPromociones(String promociones) {
        this.promociones = promociones;
    }

    public String getTipohabitacion() {
        return tipohabitacion;
    }

    public void setTipohabitacion(String tipohabitacion) {
        this.tipohabitacion = tipohabitacion;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }
}
