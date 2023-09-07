package com.feridem.android.business_logic.entidades;

import java.sql.Date;

public class UsuarioRol {
    private int id;
    private int idRolPadre;
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

    public int getIdRolPadre() {
        return idRolPadre;
    }

    public void setIdRolPadre(int idRolPadre) {
        this.idRolPadre = idRolPadre;
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
