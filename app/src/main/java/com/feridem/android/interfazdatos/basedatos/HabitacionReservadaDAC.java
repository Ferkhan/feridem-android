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
    public Cursor leerRegistros() throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalida, TotalNoches, PrecioNoche, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_HABITACION_RESERVADA
                    + " WHERE  Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalida, TotalNoches, PrecioNoche, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                    + " FROM " + TABLA_HABITACION_RESERVADA
                    + " WHERE  Estado = 1 "
                    + " AND    IdReservacion = ? ";
        String[] valores = new String[] {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    public long insertarRegistro(int idHabitacion, int idUsuario, String fechaEntrada, String fechaSalida, int totalNoches, double precioNoche, double precioTotal, String codigoQR) {
        valoresContenido = new ContentValues();
        valoresContenido.put("IdHabitacion", idHabitacion);
        valoresContenido.put("IdUsuario", idUsuario);
        valoresContenido.put("FechaEntrada", fechaEntrada);
        valoresContenido.put("FechaSalida", fechaSalida);
        valoresContenido.put("TotalNoches", totalNoches);
        valoresContenido.put("PrecioNoche", precioNoche);
        valoresContenido.put("PrecioTotal", precioTotal);
        valoresContenido.put("CodigoQR", codigoQR);
        return getWritableDatabase().insert(TABLA_HABITACION_RESERVADA, null, valoresContenido);
    }
}
