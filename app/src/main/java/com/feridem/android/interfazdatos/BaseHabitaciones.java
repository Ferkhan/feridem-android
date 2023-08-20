package com.feridem.android.interfazdatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseHabitaciones extends GestorBaseDatos {
    Context contexto;

    public BaseHabitaciones(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

    public ArrayList<Habitaciones> leerHabitaciones() {
        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
        SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();

        ArrayList<Habitaciones> listaHabitaciones = new ArrayList<>();
        Habitaciones habitacion;
        Cursor cursorHabitaciones;

        cursorHabitaciones = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLA_USUARIOS, null);

        if (cursorHabitaciones.moveToFirst()) {
            do {
                habitacion = new Habitaciones();
                habitacion.setId(cursorHabitaciones.getInt(0));
                habitacion.setNombre(cursorHabitaciones.getString(1));
                habitacion.setDescripcion(cursorHabitaciones.getString(2));
                habitacion.setPrecio(cursorHabitaciones.getInt(4));
                habitacion.setHotel(cursorHabitaciones.getString(5));
                habitacion.setImagen(cursorHabitaciones.getString(6));
                listaHabitaciones.add(habitacion);
            } while (cursorHabitaciones.moveToNext());
        }

        cursorHabitaciones.close();

        return listaHabitaciones;
    }
}
