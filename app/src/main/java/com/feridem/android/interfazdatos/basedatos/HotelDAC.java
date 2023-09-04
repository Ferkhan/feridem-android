package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

public class HotelDAC extends GestorBaseDatos {

    public HotelDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistrosExito() throws AppException {
        String consultaSQL =  " SELECT IdHotel, Nombre, Ciudad, Direccion, Latitud, Longitud, Estado, FechaIngreso, FechaModificacion "
                + " FROM " + TABLA_HOTEL
                + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        String consultaSQL =  " SELECT IdHotel, Nombre, Ciudad, Direccion, Latitud, Longitud, Estado, FechaIngreso, FechaModificacion "
                + " FROM " + TABLA_HOTEL
                + " WHERE Estado = 1 "
                + " AND IdHotel = ?";
        String[] valores = {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }
}
