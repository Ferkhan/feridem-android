package com.feridem.android.business_logic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.entidades.Habitacion;
import com.feridem.android.user_interface.activity.HabitacionDetallesActivity;

import java.util.List;

/**
 * Corresponde al adaptador para la vista de las habitaciones.
 */
public class AdaptadorHabitaciones extends RecyclerView.Adapter<HabitacionesVistaSoporte> {
    private List<Habitacion> listaHabitaciones;
    private LayoutInflater infladorLayout;
    private final Context CONTEXTO;

    public AdaptadorHabitaciones(List<Habitacion> listaHabitaciones, Context CONTEXTO) {
        this.CONTEXTO = CONTEXTO;
        this.infladorLayout = LayoutInflater.from(CONTEXTO);
        this.listaHabitaciones = listaHabitaciones;
    }

    /**
     * onCreateViewHolder:  crear una nueva vista (basada en el diseño XML) que representará un elemento de la lista en un RecyclerView.
     * @param padre The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param tipoVista The view type of the new View.
     *
     * @return
     */
    @NonNull
    public HabitacionesVistaSoporte onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = infladorLayout.inflate(R.layout.lista_habitaciones, padre, false);
        return new HabitacionesVistaSoporte(vista, CONTEXTO);
    }

    /**
     * onBindViewHolder:  Se utiliza para enlazar los datos de un elemento en la posición posicion de la lista de datos con la vista que representa ese elemento.
     * @param soporte The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param posicion The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HabitacionesVistaSoporte soporte, final int posicion) {
        try {
            soporte.desplegar(listaHabitaciones.get(posicion));
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
        soporte.habitacionTarjeta.setOnClickListener(vista -> seleccionarHabitacion(listaHabitaciones.get(posicion)));
    }

    /**
     * seleccionarHabitacion: Se encarga de realizar una acción cuando se selecciona una habitación en la interfaz de usuario.
     * @param habitacion
     */
    private void seleccionarHabitacion (Habitacion habitacion) {
        Intent intencion = new Intent(CONTEXTO, HabitacionDetallesActivity.class);
        intencion.putExtra("habitacion_seleccionada", habitacion);
        CONTEXTO.startActivity(intencion);
        //        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaHabitaciones.size();
    }

 }
