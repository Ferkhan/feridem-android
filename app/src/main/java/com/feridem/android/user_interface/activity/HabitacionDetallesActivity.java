package com.feridem.android.user_interface.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.entities.HabitacionReservada;
import com.feridem.android.business_logic.entities.Usuario;
import com.feridem.android.business_logic.managers.HabitacionReservadaBL;
import com.feridem.android.business_logic.managers.HotelBL;
import com.feridem.android.business_logic.entities.Habitacion;
import com.feridem.android.business_logic.managers.RegistroSesionBL;
import com.feridem.android.business_logic.entities.Hotel;
import com.feridem.android.business_logic.managers.UsuarioBL;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

/**
 * Esta es la ventana que tiene los detalles sobre la habitacion.
 */
public class HabitacionDetallesActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private ImageView imagen;
    SupportMapFragment soporteMapa;
    private TextView
            nombreCuarto,
            nombreHotel,
            precioNoche,
            descripcion,
            totalNoches,
            precioTotalText;
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
        establecerEscuchadores();
    }


    /**
     * inicalizarRecursos: Se encarga de inicializar los recursos y componentes.
     */
    private void inicializarRecursos() {

        imagen          = findViewById(R.id.imagen_habitacion);
        nombreCuarto    = findViewById(R.id.nombre_habitacion);
        nombreHotel     = findViewById(R.id.nombre_hotel);
        precioNoche     = findViewById(R.id.precio_habitacion);
        descripcion     = findViewById(R.id.descripcion);
        totalNoches     = findViewById(R.id.total_noches);
        fechaEntrada    = findViewById(R.id.fecha_entrada);
        fechaSalida     = findViewById(R.id.fecha_salida);
        precioTotalText = findViewById(R.id.precio_total);
        botonReserva    = findViewById(R.id.boton_reservar);
        soporteMapa     = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);

        calendario  = calendarioEntrada = calendarioSalida = Calendar.getInstance();
        anioEntrada = calendario.get(Calendar.YEAR);
        mesEntrada  = mesSalida = calendario.get(Calendar.MONTH);
        diaEntrada  = diaSalida = calendario.get(Calendar.DAY_OF_MONTH);
        diaSalida  += 1;

        soporteMapa.getMapAsync(this);
    }

    /**
     * establecerEscuchadores: gestiona y activa los listenings para los botones
     */
    private void establecerEscuchadores() {
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
     * seleccionarFechaEntrada: Se encarga de crear un DatePickerDialog, para que el usuario seleccione
     * la fecha de entrada.
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
     * seleccionarFechaSalida:Se encarga de crear un DatePickerDialog, para que el usuario seleccione
     * la fecha de salida.
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
     * actualizarCampoFecha: Se encarga de actualizar la informacion en los campos de fechas para la
     * reservacion y el numero de noches.
     * @param campoFecha almacenta
     */
    @SuppressLint("DefaultLocale")
    private void actualizarCampoFecha(EditText campoFecha) {

        if (calendarioEntrada.after(calendarioSalida)|| calendarioSalida.equals(calendarioEntrada))
            campoFecha.setText("");

        if (fechaEntrada.getText().toString().isEmpty() || fechaSalida.getText().toString().isEmpty()) {
            totalNoches.setText("Noches totales 0");
            precioTotalText.setText("Precio total de $ 0");
        } else {
            diasEntreFechas = (calendarioSalida.getTimeInMillis() - calendarioEntrada.getTimeInMillis());
            diasEntreFechas =  diasEntreFechas / (24 * 60 * 60 * 1000);
            totalNoches.setText(String.format("Noches totales %d", diasEntreFechas));
            precioTotalText.setText(String.format("Precio total de $ %.2f", diasEntreFechas * habitacion.getPrecioNoche()));
        }
    }

    /**
     * establecerDatos: Se encarga de establecer la informacion para el nombre del cuarto, el precio, la descripcion, el nombre del hotel y la direccion.
     * @throws AppException
     */
    @SuppressLint("DefaultLocale")
    private void establecerDatos() throws AppException {
        habitacion = (Habitacion) getIntent().getSerializableExtra("habitacion_seleccionada");
        HotelBL hotelBL = new HotelBL(this);
        hotel = hotelBL.obtenerPorId(habitacion.getIdHotel());
        int imagenResource = getResources().getIdentifier(habitacion.getImagen(), "drawable", getPackageName());
        imagen.setImageResource(imagenResource);
        nombreCuarto.setText(habitacion.getNombre());
        nombreHotel.setText(hotel.getNombre());
        descripcion.setText(habitacion.getDescripcion());
        precioNoche.setText(String.format("$%.0f por noche", habitacion.getPrecioNoche()));
        totalNoches.setText("Noches totales 0");
        precioTotalText.setText("Precio total de $ 0");
    }

    /**
     * Evalua y crea el intent para poder ir a la ventana de la factura.
     * @param vista
     * @throws AppException
     */
    private void irFactura(View vista) throws AppException {
        if (fechaEntrada.getText().toString().isEmpty() || fechaSalida.getText().toString().isEmpty()) {
            Toast.makeText(this, "Llene todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        reservar();
        Toast.makeText(this, "Habitación Reservada con éxito", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * reservar: establece los datos del usuario y la habitacion para hacer la reserva
     * @throws AppException
     */
    private void reservar() throws AppException {
        double precioTotal = diasEntreFechas * habitacion.getPrecioNoche();

        usuario = new UsuarioBL(this).obtenerPorId(new RegistroSesionBL(this).obtenerIdUsuarioConectado());

        long id = new HabitacionReservadaBL(this).ingresarRegistro(
                        habitacion.getId(), usuario.getId(), fechaEntrada.getText().toString(),
                        fechaSalida.getText().toString(), (int) diasEntreFechas,
                        habitacion.getPrecioNoche(), precioTotal, generarTextoQr());
        Intent irActivity = new Intent(this, FacturaActivity.class);
        HabitacionReservada habitacionReservada = new HabitacionReservadaBL(this).obtenerPorId((int) id);
        irActivity.putExtra("reservacion_seleccionada", habitacionReservada);
        startActivity(irActivity);
    }

    /**
     * generarTextoQR: genera el texto para que pueda ser empaquetado por un código qr
     * @return un string con el texto junto
     */
    private String generarTextoQr() {
        return usuario.getNombre() + "$|&"
                + hotel.getNombre() + "$|&"
                + habitacion.getNombre() + "$|&"
                + fechaEntrada.getText().toString() + "$|&"
                + fechaSalida.getText().toString() + "$|&";
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng coordenadas = new LatLng(hotel.getLatitud(), hotel.getLongitud());
        googleMap.addMarker(new MarkerOptions().position(coordenadas));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordenadas));

        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Intent intent = new Intent(this, MapaActivity.class);
        intent.putExtra("latitud", hotel.getLatitud());
        intent.putExtra("longitud", hotel.getLongitud());
        startActivity(intent);
    }
}