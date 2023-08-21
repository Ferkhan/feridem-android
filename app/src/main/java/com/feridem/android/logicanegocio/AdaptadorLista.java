package com.feridem.android.logicanegocio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.R;
import com.feridem.android.interfazdatos.modeloentidad.Habitacion;


import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ViewHolder> {
    private ArrayList<Habitacion> listaHabitaciones;
    private LayoutInflater infladorLayout;
    private Context contexto;

    public AdaptadorLista(ArrayList<Habitacion> listaHabitaciones, Context contexto) {
        this.contexto = contexto;
        this.infladorLayout = LayoutInflater.from(contexto);
        this.listaHabitaciones = listaHabitaciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = infladorLayout.inflate(R.layout.lista_habitaciones, null);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindData(listaHabitaciones.get(position));
    }


    public void setHabitaciones(ArrayList<Habitacion> habitaciones ) { listaHabitaciones = habitaciones; }


    @Override
    public int getItemCount() {
        return listaHabitaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenHabitacion;
        TextView nombreHabitacion, nombreHotel, direccionHotel, precioHabitacion;

        ViewHolder(View vistaItem) {
            super(vistaItem);
            nombreHabitacion    = vistaItem.findViewById(R.id.nombre_habitacion);
            nombreHotel         = vistaItem.findViewById(R.id.nombre_hotel);
            direccionHotel      = vistaItem.findViewById(R.id.direccion_habitacion);
            imagenHabitacion    = vistaItem.findViewById(R.id.imagen_habitacion);
            precioHabitacion    = vistaItem.findViewById(R.id.precio_habitacion);
        }


        void bindData(Habitacion item) {
            int imagenResource = contexto.getResources().getIdentifier(item.getImagen(), "drawable", contexto.getPackageName());
            String formatoPrecio = "$" + item.getPrecio();
            imagenHabitacion.setImageResource(imagenResource);
            nombreHabitacion.setText(item.getNombre());
            nombreHotel.setText(item.getHotel());
            precioHabitacion.setText(formatoPrecio);
            direccionHotel.setText(item.getDireccion());
        }
    }

}
