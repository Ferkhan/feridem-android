package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class HabitacionDAC extends GestorBaseDatos {

    public HabitacionDAC(@Nullable Context contexto) {
        super(contexto);
    }

    public Cursor leerRegistrosActivos() {
        String consultaSQL =  " SELECT IdHabitacion, IdHotel, Nombre, Descripcion, PrecioNoche, ImagenPrincipal, Estado, FechaIngreso, FechaModificacion "
                            + " FROM " + TABLA_HABITACION
                            + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    public Cursor leerPorId(int IdHabitacion) {
        String consultaSQL =  " SELECT IdHabitacion, IdHotel, Nombre, Descripcion, PrecioNoche, ImagenPrincipal, Estado, FechaIngreso, FechaModificacion "
                            + " FROM " + TABLA_HABITACION
                            + " WHERE Estado = 1 "
                            + " AND IdHabitacion = ?";
        String[] valores = {String.valueOf(IdHabitacion)};
        return obtenerConsulta(consultaSQL, valores);
    }
}
