package com.feridem.android.logicanegocio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.feridem.android.R;
import com.feridem.android.interfazdatos.Habitaciones;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorHabitaciones extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Habitaciones> listaHabitaciones;
    private LayoutInflater infladorLayout;
    private Context contexto;

    public AdaptadorHabitaciones(ArrayList<Habitaciones> listaHabitaciones, Context contexto) {
        this.infladorLayout = LayoutInflater.from(contexto);
        this.contexto = contexto;
        this.listaHabitaciones = listaHabitaciones;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = infladorLayout.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int podsition) {
        holder.bindData(listaHabitaciones.get(position));
    }


    public void setHabitaciones(ArrayList<Habitaciones> habitaciones ) { listaHabitaciones = habitaciones}

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenHabitacion;
        TextView nombreHabitacion, nombreHotel, direccionHabitacion, precioHabitacion;

        ViewHolder(View vistaItem) {
            super(vistaItem);
            imagenHabitacion = itemView.findViewById(R.id.imagen_habitacion);
            nombreHabitacion = itemView.findViewById(R.id.nombre_habitacion);
            nombreHotel = itemView.findViewById(R.id.nombre_hotel);
            direccionHabitacion = itemView.findViewById(R.id.direccion_habitacion);
        }

        void bindData(final Habitaciones item) {
            int imagenResource = getResources().getIdentifier(item.getImagen(), "drawable", getPackageName());
            imagenHabitacion.setImageResource(imagenResource);
            nombreHabitacion.setText(item.getNombre());
            nombreHotel.setText(item.getHotel());
            direccionHabitacion.setText(item.getPrecio());
        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
