package com.feridem.android.interfazdatos.modeloentidad;

import java.sql.Date;

public class HabitacionImagen {
    private int id;
    private int idHabitacion;
    private int nombre;
    private int estado;
    private Date fechaIngreso;
    private Date fechaModifiacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaModifiacion() {
        return fechaModifiacion;
    }

    public void setFechaModifiacion(Date fechaModifiacion) {
        this.fechaModifiacion = fechaModifiacion;
    }
}
