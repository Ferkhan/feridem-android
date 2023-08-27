package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.fachada.HotelBL;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.logicanegocio.entidades.Hotel;

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
    private Button botonReserva;
    private Calendar calendario;
    private Calendar calendarioEntrada;
    private Calendar calendarioSalida;
    private DatePickerDialog seleccionarFecha;
    private int anioEntrada;
    private int anioSalida;
    private int mesEntrada;
    private int mesSalida;
    private int diaEntrada;
    private int diaSalida;
    private long diasEntreFechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion_detalles);

        inicializarRecursos();
        fechaEntrada.setOnClickListener(this::seleccionarFechaEntrada);
        fechaSalida.setOnClickListener(this::seleccionarFechaSalida);
        botonReserva.setOnClickListener(this::irFactura);

        try {
            establecerDatos();
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
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
        botonReserva    = findViewById(R.id.boton_reservar);
        calendario = calendarioEntrada = calendarioSalida = Calendar.getInstance();
        anioEntrada = calendario.get(Calendar.YEAR);
        mesEntrada = mesSalida = calendario.get(Calendar.MONTH);
        diaEntrada = diaSalida = calendario.get(Calendar.DAY_OF_MONTH);
        diaSalida += 1;
    }

    private void seleccionarFechaEntrada(View vista) {
        calendario = Calendar.getInstance();
        seleccionarFecha = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
            anioEntrada = year;
            mesEntrada = month;
            diaEntrada = dayOfMonth;
            fechaEntrada.setText(fecha);
            calendarioEntrada = Calendar.getInstance();
            calendarioEntrada.clear();
            calendarioEntrada.set(anioEntrada, mesEntrada, diaEntrada);
            Log.i("AppException", String.valueOf(calendarioEntrada.getTimeInMillis()));
            actualizarCampoFecha(fechaSalida);
        }, anioEntrada, mesEntrada, diaEntrada);
        seleccionarFecha.getDatePicker().setMinDate(calendario.getTimeInMillis());
        seleccionarFecha.show();
    }

    private void seleccionarFechaSalida(View vista) {
        calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 1);
        seleccionarFecha = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
            anioSalida = year;
            mesSalida = month;
            diaSalida = dayOfMonth;
            fechaSalida.setText(fecha);
            calendarioSalida = Calendar.getInstance();
            calendarioSalida.clear();
            calendarioSalida.set(anioSalida, mesSalida, diaSalida);
            Log.i("AppException", String.valueOf(calendarioSalida.getTimeInMillis()));
            actualizarCampoFecha(fechaEntrada);
        }, anioSalida, mesSalida, diaSalida);
        seleccionarFecha.getDatePicker().setMinDate(calendario.getTimeInMillis());
        seleccionarFecha.show();
    }

    private void actualizarCampoFecha(EditText campoFecha) {
        if (calendarioEntrada.after(calendarioSalida)|| calendarioSalida.equals(calendarioEntrada))
            campoFecha.setText("");

        if (fechaEntrada.getText().toString().isEmpty() || fechaSalida.getText().toString().isEmpty()) {
            totalNoches.setText("");
        } else {
            diasEntreFechas = (calendarioSalida.getTimeInMillis() - calendarioEntrada.getTimeInMillis());
            diasEntreFechas =  diasEntreFechas / (24 * 60 * 60 * 1000);
            totalNoches.setText(String.valueOf(diasEntreFechas));
        }
    }

    private void establecerDatos() throws AppException {
        Habitacion habitacionSeleccionada = (Habitacion) getIntent().getSerializableExtra("habitacion_seleccionada");
        HotelBL hotelBL = new HotelBL(this);
        Hotel hotel = hotelBL.obtenerPorId(habitacionSeleccionada.getIdHotel());
        int imagenResource = getResources().getIdentifier(habitacionSeleccionada.getImagen(), "drawable", getPackageName());

        imagen.setImageResource(imagenResource);
        nombreCuarto.setText(habitacionSeleccionada.getNombre());
        precioNoche.setText(String.format("$%.0f", habitacionSeleccionada.getPrecioNoche()));
        descripcion.setText(habitacionSeleccionada.getDescripcion());
        nombreHotel.setText(hotel.getNombre());
        direccion.setText(hotel.getDireccion());
    }

    private void irFactura(View vista) {
        if (fechaEntrada.getText().toString().isEmpty() || fechaSalida.getText().toString().isEmpty()) {
            Toast.makeText(this, "Llene todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent irActivity = new Intent(this, FacturaActivity.class);
        Toast.makeText(this, "Habitación Reservada con éxito", Toast.LENGTH_SHORT).show();
        startActivity(irActivity);
    }
}