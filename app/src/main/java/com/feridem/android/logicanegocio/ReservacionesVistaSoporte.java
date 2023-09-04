package com.feridem.android.logicanegocio;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.fachada.HabitacionBL;
import com.feridem.android.logicanegocio.fachada.HotelBL;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.R;
import com.feridem.android.logicanegocio.entidades.Hotel;
import com.feridem.android.logicanegocio.fachada.UsuarioBL;

public class ReservacionesVistaSoporte extends RecyclerView.ViewHolder {
    private final Context CONTEXTO;
    private final ImageView IMAGEN_HABITACION;
    protected CardView reservacionTargeta;
    private TextView nombreHabitacion, nombreHotel, direccionHotel, precioHabitacion;
    private HotelBL hotelBL;
    private HabitacionBL habitacionBL;
    private UsuarioBL usuarioBL;
    private Habitacion habitacion;
    private Usuario usuario;
    private Hotel hotel;

    public ReservacionesVistaSoporte(View vistaItem, Context contexto) {
        super(vistaItem);
        this.CONTEXTO = contexto;

        nombreHabitacion    = vistaItem.findViewById(R.id.nombre_habitacion);
        nombreHotel         = vistaItem.findViewById(R.id.nombre_hotel);
        direccionHotel      = vistaItem.findViewById(R.id.direccion_habitacion);
        IMAGEN_HABITACION   = vistaItem.findViewById(R.id.imagen_habitacion);
        precioHabitacion    = vistaItem.findViewById(R.id.precio_habitacion);
        reservacionTargeta  = vistaItem.findViewById(R.id.habitacion_tarjeta);

        habitacionBL = new HabitacionBL(contexto);
        usuarioBL    = new UsuarioBL(contexto);
        hotelBL      = new HotelBL(contexto);

    }

    public void desplegar(HabitacionReservada habitacionReservada) throws AppException {
        int imagenResource = CONTEXTO.getResources().getIdentifier(habitacion.getImagen(), "drawable", CONTEXTO.getPackageName());
        String formatoPrecio = String.format("$%.0f", habitacionReservada.getPrecioNoche());
        usuario    = usuarioBL.obtenerPorId(habitacionReservada.getIdUsuario());
        habitacion = habitacionBL.obtenerPorId(habitacionReservada.getIdHabitacion());
        hotel      = hotelBL.obtenerPorId(habitacion.getIdHotel());

        IMAGEN_HABITACION.setImageResource(imagenResource);
        nombreHabitacion.setText(habitacionReservada.getNombre());
        precioHabitacion.setText(formatoPrecio);
        nombreHotel.setText(hotel.getNombre());
        direccionHotel.setText(hotel.getDireccion());
    }
}
