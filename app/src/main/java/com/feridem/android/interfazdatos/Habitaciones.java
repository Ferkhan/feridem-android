package com.feridem.android.interfazdatos;

public class Habitaciones {
    private int id = 10;
    private String nombre;
    private String descripcion = "hola";
    private int precio;
    private String hotel;
    private String imagen;
    private String direccion;
    public Habitaciones(String nombre, int precio, String hotel, String direccion, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.hotel = hotel;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    public Habitaciones() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
