package com.feridem.android.business_logic.entidades;


public class RegistroSesion {
    private int id;
    private int idUsuario;
    private String resultadoIngreso;
    private int estadoSesion;
    private String fechaIngreso;
    private String fechaCierre;

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

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
