package com.feridem.android.interfazdatos.data_access;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

/**
 * Este es el DAC para hotel.
 */
public class HotelDAC extends GestorBaseDatos {

    public HotelDAC(@Nullable Context contexto) {
        super(contexto);
    }

    /**
     * leerRegistros: Se encarga de leer los registros de la tabla Holtel.
     * @return cursorConsulta: Es la tabla de registros con estado "Activo", obtenida a partir de la consulta del query.
     * @throws AppException
     */
    @Override
    public Cursor leerRegistros() throws AppException {
        String consultaSQL =  " SELECT IdHotel, Nombre, Ciudad, Direccion, Latitud, Longitud, Estado, FechaIngreso, FechaModificacion "
                + " FROM " + TABLA_HOTEL
                + " WHERE Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerPorId: Se encarga de leer los registrs de la tabla Hotel, pero tomando en cuenta el Id del "hotel".
     * @param idRegistro: Representa el "Id" del hotel.
     * @return cursorConsulta: Es la tabla de registros con Estado "Activo" y Id de hotel específico, obtenida a partir de la consulta del query.
     * @throws AppException
     */
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
