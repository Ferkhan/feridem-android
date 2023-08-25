package com.feridem.android.logicanegocio;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.logicanegocio.fachada.HotelBL;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.R;
import com.feridem.android.logicanegocio.entidades.Hotel;

import java.util.List;

public class HabitacionesVistaSoporte extends RecyclerView.ViewHolder {
    private final Context contexto;
    protected CardView habitacionTarjeta;
    private final ImageView imagenHabitacion;
    private TextView nombreHabitacion, nombreHotel, direccionHotel, precioHabitacion;
    private HotelBL hotelBL;

    public HabitacionesVistaSoporte(View vistaItem, Context contexto) {
        super(vistaItem);
        this.contexto = contexto;
        nombreHabitacion    = vistaItem.findViewById(R.id.nombre_habitacion);
        nombreHotel         = vistaItem.findViewById(R.id.nombre_hotel);
        direccionHotel      = vistaItem.findViewById(R.id.direccion_habitacion);
        imagenHabitacion    = vistaItem.findViewById(R.id.imagen_habitacion);
        precioHabitacion    = vistaItem.findViewById(R.id.precio_habitacion);
        habitacionTarjeta   = vistaItem.findViewById(R.id.habitacion_tarjeta);
        hotelBL = new HotelBL(contexto);
    }

    public void desplegar(Habitacion habitacion) {
        int imagenResource = contexto.getResources().getIdentifier(habitacion.getImagen(), "drawable", contexto.getPackageName());
        String formatoPrecio = String.format("$%.0f", habitacion.getPrecioNoche());
        Hotel hotel = hotelBL.obtenerPorId(habitacion.getIdHotel());

        imagenHabitacion.setImageResource(imagenResource);
        nombreHabitacion.setText(habitacion.getNombre());
        precioHabitacion.setText(formatoPrecio);
        nombreHotel.setText(hotel.getNombre());
        direccionHotel.setText(hotel.getDireccion());
    }
}
