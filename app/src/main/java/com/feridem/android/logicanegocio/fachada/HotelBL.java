package com.feridem.android.logicanegocio.fachada;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.basedatos.GestorBaseDatos;
import com.feridem.android.logicanegocio.entidades.Hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HotelBL extends GestorBaseDatos {
    private Context contexto;
    public HotelBL(@Nullable Context contexto) {
        super(contexto);
        this.contexto = contexto;
    }

    public ArrayList<Hotel> leerHoteles() {
        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(contexto);
        SQLiteDatabase sqLiteDatabase = gestorBaseDatos.getWritableDatabase();

        ArrayList<Hotel> listaHoteles = new ArrayList<>();
        Hotel hotel;
        Cursor cursorHoteles;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cursorHoteles = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLA_HOTEL, null);

        if (cursorHoteles.moveToFirst()) {
            do {
                hotel = new Hotel();
                hotel.setId(cursorHoteles.getInt(0));
                hotel.setNombre(cursorHoteles.getString(1));
                hotel.setCiudad(cursorHoteles.getString(2));
                hotel.setDireccion(cursorHoteles.getString(3));
                hotel.setLatitud(cursorHoteles.getDouble(4));
                hotel.setLongitud(cursorHoteles.getDouble(5));
                hotel.setEstado(cursorHoteles.getInt(6));
                try {
                    hotel.setFechaIngreso(formatoFecha.parse(cursorHoteles.getString(7)));
                    hotel.setFechaModificacion(formatoFecha.parse(cursorHoteles.getString(8)));
                } catch (ParseException e) {
                    Log.i("mensaje feridem", e.getMessage());}
                listaHoteles.add(hotel);
            } while (cursorHoteles.moveToNext());
        }

        cursorHoteles.close();

        return listaHoteles;
    }
}
