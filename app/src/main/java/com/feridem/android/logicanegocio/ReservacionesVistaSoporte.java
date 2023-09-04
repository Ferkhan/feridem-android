package com.feridem.android.logicanegocio;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;
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
    private TextView
            nombreHabitacion,
            nombreHotel,
            fechaEntrada,
            fechaSalida;
    private HotelBL hotelBL;
    private HabitacionBL habitacionBL;
    private UsuarioBL usuarioBL;
    private Habitacion habitacion;
    private Hotel hotel;

    public ReservacionesVistaSoporte(View vistaItem, Context contexto) {
        super(vistaItem);
        this.CONTEXTO = contexto;

        nombreHabitacion    = vistaItem.findViewById(R.id.nombre_habitacion);
        nombreHotel         = vistaItem.findViewById(R.id.nombre_hotel);
        fechaEntrada        = vistaItem.findViewById(R.id.fecha_entrada);
        fechaSalida         = vistaItem.findViewById(R.id.fecha_salida);
        reservacionTargeta  = vistaItem.findViewById(R.id.habitacion_tarjeta);
        IMAGEN_HABITACION   = vistaItem.findViewById(R.id.imagen_habitacion);

        habitacionBL = new HabitacionBL(contexto);
        hotelBL      = new HotelBL(contexto);

    }

    public void desplegar(HabitacionReservada habitacionReservada) throws AppException {
        int imagenResource = CONTEXTO.getResources().getIdentifier(habitacion.getImagen(), "drawable", CONTEXTO.getPackageName());
        habitacion = habitacionBL.obtenerPorId(habitacionReservada.getIdHabitacion());
        hotel      = hotelBL.obtenerPorId(habitacion.getIdHotel());

        IMAGEN_HABITACION.setImageResource(imagenResource);
        nombreHabitacion.setText(habitacion.getNombre());
        nombreHotel.setText(hotel.getNombre());
        fechaEntrada.setText(habitacionReservada.getFechaEntrada().toString());
        fechaSalida.setText(habitacionReservada.getFechaSalida().toString());
    }
}
