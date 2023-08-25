package com.feridem.android.logicanegocio.fachada;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.feridem.android.interfazdatos.basedatos.GestorBaseDatos;
import com.feridem.android.interfazdatos.basedatos.HotelDAC;
import com.feridem.android.logicanegocio.entidades.Hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HotelBL extends GestorBaseDatos {


    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public HotelBL(@Nullable Context contexto) {
        super(contexto);
    }

    public List<Hotel> obtenerRegistrosActivos() {
        List<Hotel> listaHoteles = new ArrayList<>();
        Hotel hotel;
        HotelDAC hotelDAC = new HotelDAC(contexto);
        Cursor cursorHoteles = hotelDAC.leerRegistrosActivos();

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
                    hotel.setFechaIngreso(formatoFechaHora.parse(cursorHoteles.getString(7)));
                    hotel.setFechaModificacion(formatoFechaHora.parse(cursorHoteles.getString(8)));
                } catch (ParseException e) {
                    Log.i("mensaje feridem", e.getMessage());}
                listaHoteles.add(hotel);
            } while (cursorHoteles.moveToNext());
        }
        cursorHoteles.close();
        return listaHoteles;
    }

    public Hotel obtenerPorId(int IdHotel) {
        Hotel hotel = new Hotel();
        HotelDAC habitacionDAC = new HotelDAC(contexto);
        Cursor cursorHoteles = habitacionDAC.leerPorId(IdHotel);
        if (cursorHoteles.moveToFirst()) {
            hotel = new Hotel();
            hotel.setId(cursorHoteles.getInt(0));
            hotel.setNombre(cursorHoteles.getString(1));
            hotel.setCiudad(cursorHoteles.getString(2));
            hotel.setDireccion(cursorHoteles.getString(3));
            hotel.setLatitud(cursorHoteles.getDouble(4));
            hotel.setLongitud(cursorHoteles.getDouble(5));
            hotel.setEstado(cursorHoteles.getInt(6));
            try {
                hotel.setFechaIngreso(formatoFechaHora.parse(cursorHoteles.getString(7)));
                hotel.setFechaModificacion(formatoFechaHora.parse(cursorHoteles.getString(8)));
            } catch (ParseException e) {
                Log.i("mensaje feridem", e.getMessage());
            }
        }
        return hotel;
    }
}
