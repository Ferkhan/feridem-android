package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class HotelDAC extends GestorBaseDatos {

    public HotelDAC(@Nullable Context contexto) {
        super(contexto);
    }

    public Cursor leerRegistrosActivos() {
        String consultaSQL =  " SELECT IdHotel, Nombre, Ciudad, Direccion, Latitud, Longitud, Estado, FechaIngreso, FechaModificacion "
                + " FROM " + TABLA_HOTEL
                + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    public Cursor leerPorId(int IdHotel) {
        String consultaSQL =  " SELECT IdHotel, Nombre, Ciudad, Direccion, Latitud, Longitud, Estado, FechaIngreso, FechaModificacion "
                + " FROM " + TABLA_HOTEL
                + " WHERE Estado = 1 "
                + " AND IdHotel = ?";
        String[] valores = {String.valueOf(IdHotel)};
        return obtenerConsulta(consultaSQL, valores);
    }
}
