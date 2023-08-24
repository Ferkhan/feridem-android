package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.feridem.android.R;

import java.util.Calendar;

public class HabitacionDetallesActivity extends AppCompatActivity {
    TextView nombreCuarto;
    TextView nombreHotel;
    TextView direccion;
    TextView precioNoche;
    TextView descripcion;
    TextView totalNoches;
    EditText fechaEntrada;
    EditText fechaSalida;
    Calendar calendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion_detalles);

        inicializarRecursos();
        String pata = "hola";
        fechaEntrada.setOnClickListener(vista -> seleccionarFecha(fechaEntrada));
        fechaSalida.setOnClickListener(vista -> seleccionarFecha(fechaSalida));
    }



    private void inicializarRecursos() {
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

}