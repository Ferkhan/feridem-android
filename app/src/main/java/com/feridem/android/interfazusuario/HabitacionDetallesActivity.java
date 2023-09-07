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
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;
import com.feridem.android.logicanegocio.entidades.RegistroSesion;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.fachada.HabitacionReservadaBL;
import com.feridem.android.logicanegocio.fachada.HotelBL;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.logicanegocio.fachada.RegistroSesionBL;
import com.feridem.android.logicanegocio.entidades.Hotel;
import com.feridem.android.logicanegocio.fachada.UsuarioBL;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Calendar;

/**
 * Esta es la ventana que tiene los detalles sobre la habitación.
 */
public class HabitacionDetallesActivity extends AppCompatActivity {
    private ImageView imagen;
    private TextView
            nombreCuarto,
            nombreHotel,
            direccion,
            precioNoche,
            descripcion,
            totalNoches;
    private EditText
            fechaEntrada,
            fechaSalida;
    private Calendar
            calendario,
            calendarioEntrada,
            calendarioSalida;
    private int
            anioEntrada,
            anioSalida,
            mesEntrada,
            mesSalida,
            diaEntrada,
            diaSalida;
    private Button botonReserva;
    private DatePickerDialog seleccionarFecha;
    private long diasEntreFechas;
    private Hotel hotel;
    private Usuario usuario;
    private Habitacion habitacion;

    /**
     * onCreate: Se encarga de crear la ventana denominada HabitacionDetalles
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion_detalles);

        inicializarRecursos();
        fechaEntrada.setOnClickListener(this::seleccionarFechaEntrada);
        fechaSalida.setOnClickListener(this::seleccionarFechaSalida);
        botonReserva.setOnClickListener(vista -> {
            try {
                irFactura(vista);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            establecerDatos();
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
    }


    /**
     * inicalizarRecursos: Se encarga de iniciar recursos correspondientes a TexteView,EditText, Buttons, Calendar, etc.
     */
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
        calendario  = calendarioEntrada = calendarioSalida = Calendar.getInstance();
        anioEntrada = calendario.get(Calendar.YEAR);
        mesEntrada  = mesSalida = calendario.get(Calendar.MONTH);
        diaEntrada  = diaSalida = calendario.get(Calendar.DAY_OF_MONTH);
        diaSalida  += 1;
    }

    /**
     * seleccionarFechaEntrada: Se encarga de crear un DatePickerDialog, para que el usuario seleccione la fecha de entrada.
     * @param vista: Representa la vista que fue interactuada para mostrar el DatePickerDialog
     */
    private void seleccionarFechaEntrada(View vista) {
        calendario = Calendar.getInstance();
        seleccionarFecha = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "-" + (month + 1) + "-" + year;
            anioEntrada = year;
            mesEntrada = month;
            diaEntrada = dayOfMonth;
            fechaEntrada.setText(fecha);
            calendarioEntrada = Calendar.getInstance();
            calendarioEntrada.clear();
            calendarioEntrada.set(anioEntrada, mesEntrada, diaEntrada);
            actualizarCampoFecha(fechaSalida);
        }, anioEntrada, mesEntrada, diaEntrada);
        seleccionarFecha.getDatePicker().setMinDate(calendario.getTimeInMillis());
        seleccionarFecha.show();
    }

    /**
     * seleccionarFechaSalida:Se encarga de crear un DatePickerDialog, para que el usuario seleccione la fecha de salida.
     * @param vista: Representa la vista que fue interactuada para mostrar el DatePickerDialog
     */
    private void seleccionarFechaSalida(View vista) {
        calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 1);
        seleccionarFecha = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "-" + (month + 1) + "-" + year;
            anioSalida = year;
            mesSalida = month;
            diaSalida = dayOfMonth;
            fechaSalida.setText(fecha);
            calendarioSalida = Calendar.getInstance();
            calendarioSalida.clear();
            calendarioSalida.set(anioSalida, mesSalida, diaSalida);
            actualizarCampoFecha(fechaEntrada);
        }, anioSalida, mesSalida, diaSalida);
        seleccionarFecha.getDatePicker().setMinDate(calendario.getTimeInMillis());
        seleccionarFecha.show();
    }

    /**
     * actualizarCampoFecha: Se encarga de actualizar la información en los campos de fechas para la reservación y el número de noches.
     * @param campoFecha
     */
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

    /**
     * establecerDatos: Se encarga de establecer la información para el nombre del cuarto, el precio, la descripción, el nombre del hotel y la dirección.
     * @throws AppException
     */
    private void establecerDatos() throws AppException {
        habitacion = (Habitacion) getIntent().getSerializableExtra("habitacion_seleccionada");
        HotelBL hotelBL = new HotelBL(this);
        hotel = hotelBL.obtenerPorId(habitacion.getIdHotel());
        int imagenResource = getResources().getIdentifier(habitacion.getImagen(), "drawable", getPackageName());

        imagen.setImageResource(imagenResource);
        nombreCuarto.setText(habitacion.getNombre());
        precioNoche.setText(String.format("$%.0f", habitacion.getPrecioNoche()));
        descripcion.setText(habitacion.getDescripcion());
        nombreHotel.setText(hotel.getNombre());
        direccion.setText(hotel.getDireccion());
    }

    private void irFactura(View vista) throws AppException {
        if (fechaEntrada.getText().toString().isEmpty() || fechaSalida.getText().toString().isEmpty()) {
            Toast.makeText(this, "Llene todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }


        Toast.makeText(this, "Habitación Reservada con éxito", Toast.LENGTH_SHORT).show();
        reservar();
        finish();
    }

    private void reservar() throws AppException {
        double precioTotal = (Integer.parseInt(totalNoches.getText().toString()) * habitacion.getPrecioNoche());

        usuario = new UsuarioBL(this).obtenerPorId(new RegistroSesionBL(this).obtenerIdUsuarioConectado());

        long id = new HabitacionReservadaBL(this).ingresarRegistro(
                habitacion.getId(), usuario.getId(), fechaEntrada.getText().toString(),
                fechaSalida.getText().toString(), Integer.parseInt(totalNoches.getText().toString()),
                habitacion.getPrecioNoche(), precioTotal, generarTextoQr());
        Intent irActivity = new Intent(this, FacturaActivity.class);
        HabitacionReservada habitacionReservada = new HabitacionReservadaBL(this).obtenerPorId((int) id);
        irActivity.putExtra("reservacion_seleccionada", habitacionReservada);
        startActivity(irActivity);

    }

    private String generarTextoQr() {
        return usuario.getNombre() + "$|&"
                + hotel.getNombre() + "$|&"
                + habitacion.getNombre() + "$|&"
                + fechaEntrada.getText().toString() + "$|&"
                + fechaSalida.getText().toString() + "$|&";
    }
}