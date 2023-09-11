package com.feridem.android.data_access;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

/**
 * Este es el DAC para habitacion
 */
public class HabitacionDAC extends GestorBaseDatos {

    public HabitacionDAC(@Nullable Context contexto) {
        super(contexto);
    }

    /**
     * leerRegistros: Se encarga de leer los registros en la tabla habitacion.
     * @return cursorConsulta: Es la tabla de datos obtenida a partir de la consulta del query.
     * @throws AppException
     */
    @Override
    public Cursor leerRegistros() throws AppException {
        String consultaSQL =  " SELECT IdHabitacion, IdHotel, Nombre, Descripcion, PrecioNoche, ImagenPrincipal, Estado, FechaIngreso, FechaModificacion "
                            + " FROM " + TABLA_HABITACION
                            + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerPorId: Leer los registros de la tabla Habitacion, pero tomando como parametro el Id del registro.
     * @param idRegistro
     * @return cursorConsulta Es la tabla de datos obtenida a partir de la consulta del query.
     * @throws AppException
     */
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
