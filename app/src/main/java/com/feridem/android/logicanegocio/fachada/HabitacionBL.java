package com.feridem.android.logicanegocio.fachada;

import android.content.Context;

import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.basedatos.HabitacionDAC;
import com.feridem.android.logicanegocio.entidades.Habitacion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionBL extends GestorBL {
    private HabitacionDAC habitacionDAC;

    public HabitacionBL(Context contexto) {
        super(contexto);
        habitacionDAC = new HabitacionDAC(contexto);
    }

    public List<Habitacion> obtenerRegistrosActivos() throws AppException {
        Habitacion habitacion;
        List<Habitacion> listaHabitaciones = new ArrayList<>();
        cursorConsulta = habitacionDAC.leerRegistros();

        if (cursorConsulta.moveToFirst()) {
            do {
                habitacion = new Habitacion();
                habitacion.setId(cursorConsulta.getInt(0));
                habitacion.setIdHotel(cursorConsulta.getInt(1));
                habitacion.setNombre(cursorConsulta.getString(2));
                habitacion.setDescripcion(cursorConsulta.getString(3).replace("\\n", "\n"));
                habitacion.setPrecioNoche(cursorConsulta.getDouble(4));
                habitacion.setImagen(cursorConsulta.getString(5));
                habitacion.setEstado(cursorConsulta.getInt(6));
                try {
                    habitacion.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(7)));
                    habitacion.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(8)));
                } catch (ParseException error) {
                    throw new AppException(error, getClass(), "obtenerRegistrosActivos()");
                }
                listaHabitaciones.add(habitacion);
            } while (cursorConsulta.moveToNext());
        }

        cursorConsulta.close();
        return listaHabitaciones;
    }

    public Habitacion obtenerPorId(int idHabitacion) throws AppException {
        Habitacion habitacion = new Habitacion();
        cursorConsulta = habitacionDAC.leerPorId(idHabitacion);

        if (cursorConsulta.moveToFirst()) {
            habitacion = new Habitacion();
            habitacion.setId(cursorConsulta.getInt(0));
            habitacion.setIdHotel(cursorConsulta.getInt(1));
            habitacion.setNombre(cursorConsulta.getString(2));
            habitacion.setDescripcion(cursorConsulta.getString(3));
            habitacion.setPrecioNoche(cursorConsulta.getDouble(4));
            habitacion.setImagen(cursorConsulta.getString(5));
            habitacion.setEstado(cursorConsulta.getInt(6));
            try {
                habitacion.setFechaIngreso(formatoFechaHora.parse(cursorConsulta.getString(7)));
                habitacion.setFechaModificacion(formatoFechaHora.parse(cursorConsulta.getString(8)));
            } catch (ParseException error) {
                throw new AppException(error, getClass(), "obtenerPorId()");
            }
        }

        return habitacion;
    }



}
