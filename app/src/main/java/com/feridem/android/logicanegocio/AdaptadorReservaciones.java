package com.feridem.android.logicanegocio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.interfazusuario.FacturaActivity;
import com.feridem.android.interfazusuario.HabitacionDetallesActivity;
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;

import java.util.List;

/**
 * corresponde al adaptador para las reservaciones que realiza el usuario.
 */
public class AdaptadorReservaciones extends RecyclerView.Adapter<ReservacionesVistaSoporte> {
    private final Context CONTEXTO;
    private List<HabitacionReservada> listaReservaciones;
    private LayoutInflater infladorLayout;

    public AdaptadorReservaciones(List<HabitacionReservada> listaReservaciones, Context CONTEXTO) {
        this.CONTEXTO = CONTEXTO;
        this.infladorLayout = LayoutInflater.from(CONTEXTO);
        this.listaReservaciones = listaReservaciones;
    }

    /**
     * onCreateViewHolder: se utiliza para crear instancias de soporte para las vistas de los elementos en la lista de reservaciones, inflando la vista a partir de un archivo XML de diseño
     * y devolviendo una nueva instancia de ReservacionesVistaSoporte para esa vista.
     * @param padre The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param tipoVista The view type of the new View.
     *
     * @return
     */
    @NonNull
    public ReservacionesVistaSoporte onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = infladorLayout.inflate(R.layout.lista_reservaciones, padre, false);
        return new ReservacionesVistaSoporte(vista, CONTEXTO);
    }

    /**
     * onBindViewHolder:  vincula los datos de un elemento de la lista de reservaciones con la vista correspondiente
     * y configura un OnClickListener en la vista para permitir la interacción del usuario
     * @param soporte The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param posicion The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ReservacionesVistaSoporte soporte, int posicion) {
        try {
            soporte.desplegar(listaReservaciones.get(posicion));
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
        soporte.reservacionTargeta.setOnClickListener(vista -> seleccionarReservacion(listaReservaciones.get(posicion)));
    }

    /**
     * seleccionarReservacion:  inicia la actividad FacturaActivity
     * y pasa la información de la reservación seleccionada a esa actividad para que pueda mostrar los detalles de la factura
     * @param habitacionReservada
     */
    private void seleccionarReservacion(HabitacionReservada habitacionReservada) {
        Intent intencion = new Intent(CONTEXTO, FacturaActivity.class);
        intencion.putExtra("reservacion_seleccionada", habitacionReservada);
        CONTEXTO.startActivity(intencion);
    }

    @Override
    public int getItemCount() {
        return listaReservaciones.size();
    }

}
