package com.feridem.android.logicanegocio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.R;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.interfazusuario.HabitacionDetallesActivity;

import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter<HabitacionesVistaSoporte> {
    private ArrayList<Habitacion> listaHabitaciones;
    private LayoutInflater infladorLayout;
    private Context contexto;

    public AdaptadorLista(ArrayList<Habitacion> listaHabitaciones, Context contexto) {
        this.contexto = contexto;
        this.infladorLayout = LayoutInflater.from(contexto);
        this.listaHabitaciones = listaHabitaciones;
    }

    @NonNull
    public HabitacionesVistaSoporte onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = infladorLayout.inflate(R.layout.lista_habitaciones, padre, false);
        return new HabitacionesVistaSoporte(vista, contexto);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitacionesVistaSoporte soporte, final int posicion) {
        soporte.desplegar(listaHabitaciones.get(posicion));
        soporte.habitacionTarjeta.setOnClickListener(vista -> seleccionarHabitacion(posicion));
    }

    private void seleccionarHabitacion (int posicion) {
        Intent intencion = new Intent(contexto, HabitacionDetallesActivity.class);
        intencion.putExtra("habitacion_seleccionada", posicion);
        contexto.startActivity(intencion);
        //        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaHabitaciones.size();
    }

 }
