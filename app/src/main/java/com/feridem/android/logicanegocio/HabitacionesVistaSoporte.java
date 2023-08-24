package com.feridem.android.logicanegocio;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.interfazdatos.basedatos.GestorHotel;
import com.feridem.android.interfazdatos.modeloentidad.Habitacion;
import com.feridem.android.R;
import com.feridem.android.interfazdatos.modeloentidad.Hotel;

import java.util.ArrayList;

public class HabitacionesVistaSoporte extends RecyclerView.ViewHolder {
    private final Context contexto;
    protected CardView habitacionTarjeta;
    private final ImageView imagenHabitacion;
    private TextView nombreHabitacion, nombreHotel, direccionHotel, precioHabitacion;
    private GestorHotel gestorHotel;

    public HabitacionesVistaSoporte(View vistaItem, Context contexto) {
        super(vistaItem);
        this.contexto = contexto;
        nombreHabitacion    = vistaItem.findViewById(R.id.nombre_habitacion);
        nombreHotel         = vistaItem.findViewById(R.id.nombre_hotel);
        direccionHotel      = vistaItem.findViewById(R.id.direccion_habitacion);
        imagenHabitacion    = vistaItem.findViewById(R.id.imagen_habitacion);
        precioHabitacion    = vistaItem.findViewById(R.id.precio_habitacion);
        habitacionTarjeta   = vistaItem.findViewById(R.id.habitacion_tarjeta);
        gestorHotel = new GestorHotel(contexto);
    }

    public void desplegar(Habitacion habitacion) {
        int imagenResource = contexto.getResources().getIdentifier(habitacion.getImagen(), "drawable", contexto.getPackageName());
        String formatoPrecio = String.format("$%.0f", habitacion.getPrecioNoche());
        ArrayList<Hotel> listaHoteles = gestorHotel.leerHoteles();

        imagenHabitacion.setImageResource(imagenResource);
        Log.i("mensaje feridem", imagenResource + ":");
        nombreHabitacion.setText(habitacion.getNombre());
        precioHabitacion.setText(formatoPrecio);
        for (Hotel hotel : listaHoteles)
            if (habitacion.getIdHotel() == hotel.getId()) {
                nombreHotel.setText(hotel.getNombre());
                direccionHotel.setText(hotel.getDireccion());
            }
    }
}
