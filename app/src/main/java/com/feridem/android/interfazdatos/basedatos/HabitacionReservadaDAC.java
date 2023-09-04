package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

import java.util.Date;

public class HabitacionReservadaDAC extends GestorBaseDatos {

    public HabitacionReservadaDAC(@Nullable Context contexto) {
        super(contexto);
    }

    @Override
    public Cursor leerRegistrosExito() throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalida, TotalNoches, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_HABITACION_RESERVADA
                    + " WHERE  Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalia, TotalNoches, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_HABITACION_RESERVADA
                    + " WHERE  Estado = 1 "
                    + " AND    IdReservacion = ? ";
        String[] valores = new String[] {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    public long insertarRegistro(String idHabitacion, String idUsuario, Date fechaEntrada, Date fechaSalida, int totalNoches, double precioNoche, double precioTotal, String codigoQR) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdHabitacion", idHabitacion);
        valoresContenido.put("IdUsuario", idUsuario);
        valoresContenido.put("FechaEntrada", String.valueOf(fechaEntrada));
        valoresContenido.put("FechaSalida", String.valueOf(fechaSalida));
        valoresContenido.put("TotalNoches", totalNoches);
        valoresContenido.put("PrecioNoche", precioNoche);
        valoresContenido.put("PrecioTotal", precioTotal);
        valoresContenido.put("CodigoQR", codigoQR);
        return getWritableDatabase().insert(TABLA_HABITACION_RESERVADA, null, valoresContenido);
    }
}
