package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.interfazdatos.basedatos.GestorHabitacion;
import com.feridem.android.interfazdatos.basedatos.GestorHotel;
import com.feridem.android.interfazdatos.modeloentidad.Habitacion;
import com.feridem.android.interfazdatos.modeloentidad.Hotel;

import java.util.ArrayList;
import java.util.Calendar;

public class HabitacionDetallesActivity extends AppCompatActivity {
    private ImageView imagen;
    private TextView nombreCuarto;
    private TextView nombreHotel;
    private TextView direccion;
    private TextView precioNoche;
    private TextView descripcion;
    private TextView totalNoches;
    private EditText fechaEntrada;
    private EditText fechaSalida;
    private Calendar calendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion_detalles);

        inicializarRecursos();
        fechaEntrada.setOnClickListener(vista -> seleccionarFecha(fechaEntrada));
        fechaSalida.setOnClickListener(vista -> seleccionarFecha(fechaSalida));
        establecerDatos();
    }



    private void inicializarRecursos() {
        imagen          = findViewById(R.id.imagen_habitacion);
        nombreCuarto    = findViewById(R.id.nombre_habitacion);
        nombreHotel     = findViewById(R.id.nombre_hotel);
        direccion       = findViewById(R.id.direccion);
        precioNoche     = findViewById(R.id.precio_habitacion);
        descripcion     = findViewById(R.id.descripcion);
        totalNoches     = findViewById(R.id.total_noches);
        fechaEntrada    = findViewById(R.id.fecha_entrada);
        fechaSalida     = findViewById(R.id.fecha_salida);
        calendario      = Calendar.getInstance();
    }

    private void seleccionarFecha(EditText editarFecha) {
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog seleccionarFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                editarFecha.setText(fecha);
            }
        }, anio, mes, dia);

        seleccionarFecha.show();
    }

    private void establecerDatos() {
        int posicion = getIntent().getIntExtra("habitacion_seleccionada", 0);
        GestorHabitacion gestorHabitacion = new GestorHabitacion(this);
        GestorHotel gestorHotel = new GestorHotel(this);
        ArrayList<Habitacion> listaHabitaciones = gestorHabitacion.leerHabitaciones();
        ArrayList<Hotel> listaHoteles = gestorHotel.leerHoteles();
        Habitacion habitacionSeleccionada = listaHabitaciones.get(posicion);
        int imagenResource = getResources().getIdentifier(habitacionSeleccionada.getImagen(), "drawable", getPackageName());

        imagen.setImageResource(imagenResource);
        nombreCuarto.setText(habitacionSeleccionada.getNombre());
        precioNoche.setText(String.format("$%.0f", habitacionSeleccionada.getPrecioNoche()));
        descripcion.setText(habitacionSeleccionada.getDescripcion());
//        totalNoches.setText("");
        for (Hotel hotel : listaHoteles)
            if (habitacionSeleccionada.getIdHotel() == hotel.getId()) {
                nombreHotel.setText(hotel.getNombre());
                direccion.setText(hotel.getDireccion());
            }
    }

}