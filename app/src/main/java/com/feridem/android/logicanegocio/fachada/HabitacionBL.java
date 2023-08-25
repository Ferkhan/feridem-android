package com.feridem.android.logicanegocio.fachada;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.basedatos.GestorBaseDatos;
import com.feridem.android.interfazdatos.basedatos.HabitacionDAC;
import com.feridem.android.logicanegocio.entidades.Habitacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HabitacionBL extends GestorBaseDatos {
    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public HabitacionBL(@Nullable Context contexto) {
        super(contexto);
    }

    public List<Habitacion> obtenerRegistrosActivos() {

        List<Habitacion> listaHabitaciones = new ArrayList<>();
        Habitacion habitacion;
        HabitacionDAC habitacionDAC = new HabitacionDAC(contexto);
        Cursor cursorHabitaciones = habitacionDAC.leerRegistrosActivos();

        if (cursorHabitaciones.moveToFirst()) {
            do {
                habitacion = new Habitacion();
                habitacion.setId(cursorHabitaciones.getInt(0));
                habitacion.setIdHotel(cursorHabitaciones.getInt(1));
                habitacion.setNombre(cursorHabitaciones.getString(2));
                habitacion.setDescripcion(cursorHabitaciones.getString(3));
                habitacion.setPrecioNoche(cursorHabitaciones.getDouble(4));
                habitacion.setImagen(cursorHabitaciones.getString(5));
                habitacion.setEstado(cursorHabitaciones.getInt(6));
                try {
                    habitacion.setFechaIngreso(formatoFechaHora.parse(cursorHabitaciones.getString(7)));
                    habitacion.setFechaModificacion(formatoFechaHora.parse(cursorHabitaciones.getString(8)));
                } catch (ParseException e) {
                    Log.i("mensaje feridem", e.getMessage());}
                listaHabitaciones.add(habitacion);
            } while (cursorHabitaciones.moveToNext());
        }
        cursorHabitaciones.close();
        return listaHabitaciones;
    }

    public Habitacion obtenerPorId(int IdHabitacion) {
        Habitacion habitacion = new Habitacion();
        HabitacionDAC habitacionDAC = new HabitacionDAC(contexto);
        Cursor cursorHabitaciones = habitacionDAC.leerRegistrosActivos();
        if (cursorHabitaciones.moveToFirst()) {
            habitacion = new Habitacion();
            habitacion.setId(cursorHabitaciones.getInt(0));
            habitacion.setIdHotel(cursorHabitaciones.getInt(1));
            habitacion.setNombre(cursorHabitaciones.getString(2));
            habitacion.setDescripcion(cursorHabitaciones.getString(3));
            habitacion.setPrecioNoche(cursorHabitaciones.getDouble(4));
            habitacion.setImagen(cursorHabitaciones.getString(5));
            habitacion.setEstado(cursorHabitaciones.getInt(6));
            try {
                habitacion.setFechaIngreso(formatoFechaHora.parse(cursorHabitaciones.getString(7)));
                habitacion.setFechaModificacion(formatoFechaHora.parse(cursorHabitaciones.getString(8)));
            } catch (ParseException e) {
                Log.i("mensaje feridem", e.getMessage());
            }
        }
        return habitacion;
    }
}
