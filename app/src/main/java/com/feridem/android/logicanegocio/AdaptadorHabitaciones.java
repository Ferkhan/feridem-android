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
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.interfazusuario.HabitacionDetallesActivity;

import java.util.List;

public class AdaptadorHabitaciones extends RecyclerView.Adapter<HabitacionesVistaSoporte> {
    private List<Habitacion> listaHabitaciones;
    private LayoutInflater infladorLayout;
    private final Context CONTEXTO;

    public AdaptadorHabitaciones(List<Habitacion> listaHabitaciones, Context CONTEXTO) {
        this.CONTEXTO = CONTEXTO;
        this.infladorLayout = LayoutInflater.from(CONTEXTO);
        this.listaHabitaciones = listaHabitaciones;
    }

    @NonNull
    public HabitacionesVistaSoporte onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = infladorLayout.inflate(R.layout.lista_habitaciones, padre, false);
        return new HabitacionesVistaSoporte(vista, CONTEXTO);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitacionesVistaSoporte soporte, final int posicion) {
        try {
            soporte.desplegar(listaHabitaciones.get(posicion));
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
        soporte.habitacionTarjeta.setOnClickListener(vista -> seleccionarHabitacion(listaHabitaciones.get(posicion)));
    }

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
