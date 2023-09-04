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

public class AdaptadorReservaciones extends RecyclerView.Adapter<ReservacionesVistaSoporte> {
    private final Context CONTEXTO;
    private List<HabitacionReservada> listaReservaciones;
    private LayoutInflater infladorLayout;

    public AdaptadorReservaciones(List<HabitacionReservada> listaReservaciones, Context CONTEXTO) {
        this.CONTEXTO = CONTEXTO;
        this.infladorLayout = LayoutInflater.from(CONTEXTO);
        this.listaReservaciones = listaReservaciones;
    }

    @NonNull
    public ReservacionesVistaSoporte onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = infladorLayout.inflate(R.layout.lista_reservaciones, padre, false);
        return new ReservacionesVistaSoporte(vista, CONTEXTO);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservacionesVistaSoporte soporte, int posicion) {
        try {
            soporte.desplegar(listaReservaciones.get(posicion));
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
        soporte.reservacionTargeta.setOnClickListener(vista -> seleccionarReservacion(listaReservaciones.get(posicion)));
    }

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
