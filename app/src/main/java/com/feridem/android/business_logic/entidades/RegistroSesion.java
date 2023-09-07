package com.feridem.android.business_logic.entidades;

import java.util.Date;

public class RegistroSesion {
    private int id;
    private int idUsuario;
    private String resultadoIngreso;
    private int estadoSesion;
    private Date fechaIngreso;
    private Date fechaCierre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getResultadoIngreso() {
        return resultadoIngreso;
    }

    public void setResultadoIngreso(String resultadoIngreso) {
        this.resultadoIngreso = resultadoIngreso;
    }

    public int getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(int estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
