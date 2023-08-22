package com.feridem.android.interfazdatos.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.modeloentidad.Habitacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GestorHabitacion extends GestorBaseDatos {
    private Context contexto;

    public GestorHabitacion(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

    public ArrayList<Habitacion> leerHabitaciones() {
        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
        SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();

        ArrayList<Habitacion> listaHabitaciones = new ArrayList<>();
        Habitacion habitacion;
        Cursor cursorHabitaciones;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cursorHabitaciones = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLA_HABITACION, null);

        if (cursorHabitaciones.moveToFirst()) {
            do {
                habitacion = new Habitacion();
                habitacion.setId(cursorHabitaciones.getInt(0));
                habitacion.setIdHotel(cursorHabitaciones.getInt(1));
                habitacion.setNombre(cursorHabitaciones.getString(2));
                habitacion.setDescripcion(cursorHabitaciones.getString(3));
                habitacion.setPrecioNoche(cursorHabitaciones.getInt(4));
                habitacion.setImagen(cursorHabitaciones.getString(5));
                habitacion.setEstado(cursorHabitaciones.getInt(6));
                try {
                    habitacion.setFechaIngreso(formatoFecha.parse(cursorHabitaciones.getString(7)));
                    habitacion.setFechaModificacion(formatoFecha.parse(cursorHabitaciones.getString(8)));
                } catch (ParseException e) {
                    Log.i("mensaje feridem", e.getMessage());}
                listaHabitaciones.add(habitacion);
            } while (cursorHabitaciones.moveToNext());
        }

        cursorHabitaciones.close();

        return listaHabitaciones;
    }
}
