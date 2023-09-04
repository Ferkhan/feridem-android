package com.feridem.android.logicanegocio.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.HabitacionReservadaDAC;
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionReservadaBL extends GestorBL {
    private HabitacionReservadaDAC habitacionReservadaDAC;

    public HabitacionReservadaBL(Context contexto) {
        super(contexto);
        habitacionReservadaDAC = new HabitacionReservadaDAC(contexto);
    }

    public List<HabitacionReservada> obtenerRegistrosActivos() throws AppException {
        HabitacionReservada habitacionReservada;
        List<HabitacionReservada> listaHabitaciones = new ArrayList<>();
        cursorConsulta = habitacionReservadaDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                habitacionReservada = new HabitacionReservada();
                habitacionReservada.setId(cursorConsulta.getInt(0));
                habitacionReservada.setIdHabitacion(cursorConsulta.getInt(1));
                habitacionReservada.setIdUsuario(cursorConsulta.getInt(2));
                habitacionReservada.setFechaEntrada(cursorConsulta.getString(3));
                habitacionReservada.setFechaSalida(cursorConsulta.getString(4));
                habitacionReservada.setTotalNoches(cursorConsulta.getInt(5));
                habitacionReservada.setPrecioNoche(cursorConsulta.getDouble(6));
                habitacionReservada.setPrecioTotal(cursorConsulta.getDouble(7));
                habitacionReservada.setCodigoQR(cursorConsulta.getString(8));
                habitacionReservada.setEstado(cursorConsulta.getInt(9));
                try {
                    habitacionReservada.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(10)));
                    habitacionReservada.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(11)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaHabitaciones.add(habitacionReservada);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaHabitaciones;
    }


    public HabitacionReservada obtenerPorId(int idReservacion) throws AppException {
        HabitacionReservada habitacionReservada = new HabitacionReservada();
        cursorConsulta = habitacionReservadaDAC.leerPorId(idReservacion);

        if (cursorConsulta.moveToFirst()) {
            habitacionReservada.setId(cursorConsulta.getInt(0));
            habitacionReservada.setIdHabitacion(cursorConsulta.getInt(1));
            habitacionReservada.setIdUsuario(cursorConsulta.getInt(2));
            habitacionReservada.setFechaEntrada(cursorConsulta.getString(3));
            habitacionReservada.setFechaSalida(cursorConsulta.getString(4));
            habitacionReservada.setTotalNoches(cursorConsulta.getInt(5));
            habitacionReservada.setPrecioNoche(cursorConsulta.getDouble(6));
            habitacionReservada.setPrecioTotal(cursorConsulta.getDouble(7));
            habitacionReservada.setCodigoQR(cursorConsulta.getString(8));
            habitacionReservada.setEstado(cursorConsulta.getInt(9));
            try {
                habitacionReservada.setFechaRegistro(formatoFechaHora.parse(cursorConsulta.getString(10)));
                habitacionReservada.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(11)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
            }
        }

        cursorConsulta.close();
        return habitacionReservada;
    }
    public long ingresarRegistro(int idHabitacion, int idUsuario, String fechaEntrada, String fechaSalida, int totalNoches, double precioNoche, double precioTotal, String codigoQR) {
        return habitacionReservadaDAC.insertarRegistro(idHabitacion, idUsuario, fechaEntrada, fechaSalida, totalNoches, precioNoche, precioTotal, codigoQR);

    }

}
