package com.feridem.android.business_logic.utilities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.entities.HabitacionReservada;
import com.feridem.android.business_logic.managers.HabitacionBL;
import com.feridem.android.business_logic.managers.HotelBL;
import com.feridem.android.business_logic.entities.Habitacion;
import com.feridem.android.R;
import com.feridem.android.business_logic.entities.Hotel;

/**
 * Corresponde a la vista de las reservaciones.
 */
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
    private Habitacion habitacion;
    private Hotel hotel;

    public ReservacionesVistaSoporte(View vistaItem, Context contexto) {
        super(vistaItem);
        this.CONTEXTO = contexto;

        nombreHabitacion    = vistaItem.findViewById(R.id.nombre_habitacion);
        nombreHotel         = vistaItem.findViewById(R.id.nombre_hotel);
        fechaEntrada        = vistaItem.findViewById(R.id.fecha_entrada);
        fechaSalida         = vistaItem.findViewById(R.id.fecha_salida);
        reservacionTargeta  = vistaItem.findViewById(R.id.reservacion_tarjeta);
        IMAGEN_HABITACION   = vistaItem.findViewById(R.id.imagen_habitacion);

        habitacionBL = new HabitacionBL(contexto);
        hotelBL      = new HotelBL(contexto);

    }

    /**
     * desplegar: oma una instancia de HabitacionReservada como entrada y utiliza sus propiedades para
     * llenar diferentes vistas de la interfaz de usuario con la informacion de la habitacion reservada
     * y el hotel al que pertenece.
     * @param habitacionReservada
     * @throws AppException
     */
    public void desplegar(HabitacionReservada habitacionReservada) throws AppException {
        habitacion          = habitacionBL.obtenerPorId(habitacionReservada.getIdHabitacion());
        hotel               = hotelBL.obtenerPorId(habitacion.getIdHotel());
        int imagenResource  = CONTEXTO.getResources().getIdentifier(habitacion.getImagen(), "drawable", CONTEXTO.getPackageName());

        IMAGEN_HABITACION.setImageResource(imagenResource);
        nombreHabitacion.setText(habitacion.getNombre());
        nombreHotel.setText(hotel.getNombre());
        fechaEntrada.setText(habitacionReservada.getFechaEntrada());
        fechaSalida.setText(habitacionReservada.getFechaSalida());
    }
}
