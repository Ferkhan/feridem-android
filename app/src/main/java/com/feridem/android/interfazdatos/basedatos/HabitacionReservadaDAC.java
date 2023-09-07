package com.feridem.android.interfazdatos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.feridem.android.framework.AppException;

import java.util.Date;

/**
 * Este es el DAC para la habitacion reservada.
 */
public class HabitacionReservadaDAC extends GestorBaseDatos {

    public HabitacionReservadaDAC(@Nullable Context contexto) {
        super(contexto);
    }

    /**
     * leerRegistros: Se encarga de leer lo registros de la tabla Habitación Reservada.
     * @return cursorConsulta: Es la tabla de registros con estado "Activo",  obtenida a partir de la consulta del query.
     * @throws AppException
     */
    @Override
    public Cursor leerRegistros() throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalida, TotalNoches, PrecioNoche, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_HABITACION_RESERVADA
                + " WHERE  Estado = 1 ";
        return obtenerConsulta(consultaSQL, null);
    }

    /**
     * leerPorId: Se encarga de leer lo registros de la tabla Habitación Reservada, pero tomando como parámetro el Id del registro.
     * @param idRegistro
     * @return cursorConsulta: Es la tabla de registros con estado "Activo" y  Id de Reservacion específico, obtenida a partir de la consulta del query
     * @throws AppException
     */
    @Override
    public Cursor leerPorId(int idRegistro) throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalida, TotalNoches, PrecioNoche, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_HABITACION_RESERVADA
                + " WHERE  Estado = 1 "
                + " AND    IdReservacion = ? ";
        String[] valores = new String[] {String.valueOf(idRegistro)};
        return obtenerConsulta(consultaSQL, valores);
    }

    /**
     * insertarRegistro: Se encarga de insertar un registro en la tabla Habitación Reservada,tomando como parámetros el idHabitacion,
     *                   idUsuario,fechaEntrada,fechaSalida,totalNoches,precioNoche,precioTotal,codigoQR
     * @param idHabitacion: Define el Id de la habitación reservada
     * @param idUsuario: Representa le Id del usuario que reservo la habitación
     * @param fechaEntrada: Reprsenta la fecha de inicio de la reserva
     * @param fechaSalida: Representa la fecha final para la reserva
     * @param totalNoches: Representa la cantidad de noches de reserva que existen entre la fecha salida y la fecha entrada.
     * @param precioNoche: Representa el precio de la reserva, por cada noche.
     * @param precioTotal: Representa el precio total por la cantidad de noches reservadas.
     * @param codigoQR: Representa el código único generado por la reserva.
     * @return long: Retorna un valor "long", que representa el identificador del nuevo registro que se ha insertado en la tabla.
     */
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

    /**
     * LeerRegistroUsuario: Se encarga de leer los registros de la tabla Habitación reservada, pero tomando como parámetro el Id del usuario.
     * @param idUsuario:  Representa le Id del usuario que reservo la habitación
     * @return cursorConsulta: Es la tabla de registros con estado  "Activo" y  Id de usuario específico,  obtenida a partir de la consulta del query.
     * @throws AppException
     */
    public Cursor leerRegistrosUsuario(int idUsuario) throws AppException {
        consultaSQL = " SELECT IdReservacion, IdHabitacion, IdUsuario, FechaEntrada, FechaSalida, TotalNoches, PrecioNoche, PrecioTotal, CodigoQR, Estado, FechaRegistro, FechaModificacion "
                + " FROM " + TABLA_HABITACION_RESERVADA
                + " WHERE  Estado = 1 "
                + " AND    IdUsuario = ? ";
        String[] valores = new String[] {String.valueOf(idUsuario)};
        return obtenerConsulta(consultaSQL, valores);
    }
}