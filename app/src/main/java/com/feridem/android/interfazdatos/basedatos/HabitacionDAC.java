package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

public class HabitacionDAC extends GestorBaseDatos {

    public HabitacionDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistros() throws AppException {
        String consultaSQL =  " SELECT IdHabitacion, IdHotel, Nombre, Descripcion, PrecioNoche, ImagenPrincipal, Estado, FechaIngreso, FechaModificacion "
                            + " FROM " + TABLA_HABITACION
                            + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        String consultaSQL =  " SELECT IdHabitacion, IdHotel, Nombre, Descripcion, PrecioNoche, ImagenPrincipal, Estado, FechaIngreso, FechaModificacion "
                            + " FROM " + TABLA_HABITACION
                            + " WHERE Estado = 1 "
                            + " AND IdHabitacion = ?";
        String[] valores = {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }
}
