package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;
import com.feridem.android.logicanegocio.entidades.Hotel;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.fachada.HabitacionBL;
import com.feridem.android.logicanegocio.fachada.HotelBL;
import com.feridem.android.logicanegocio.fachada.UsuarioBL;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class FacturaActivity extends AppCompatActivity {
    private HabitacionReservada habitacionReservada;
    private Habitacion habitacion;
    private Hotel hotel;
    private Usuario usuario;
    private TextView
            nombreHabitacion,
            nombreHotel,
            direccion,
            nombreUsuario,
            fechaEntrada,
            fechaSalida,
            totalNoches,
            precioTotal;
    private ImageView codigoQR;
    private Button botonAceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        inicializarRecursos();
        try {
            establecerDatos();
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        botonAceptar.setOnClickListener(this::irBarraNavegacion);


    }

    private void inicializarRecursos() {
        botonAceptar        = findViewById(R.id.boton_aceptar);
        nombreHabitacion    = findViewById(R.id.nombre_habitacion);
        nombreHotel         = findViewById(R.id.nombre_hotel);
        nombreUsuario       = findViewById(R.id.usuario);
        direccion           = findViewById(R.id.direccion);
        fechaEntrada        = findViewById(R.id.fecha_entrada);
        fechaSalida         = findViewById(R.id.fecha_salida);
        totalNoches         = findViewById(R.id.total_noches);
        precioTotal         = findViewById(R.id.precio_total);
    }

    private void irBarraNavegacion(View vista) {
        onBackPressed();
    }

    private void establecerDatos() throws AppException {
        habitacionReservada = (HabitacionReservada) getIntent().getSerializableExtra("reservacion_seleccionada");

        habitacion = new HabitacionBL(this).obtenerPorId(habitacionReservada.getIdHabitacion());
        hotel = new HotelBL(this).obtenerPorId(habitacion.getIdHotel());
        usuario = new UsuarioBL(this).obtenerPorId(habitacionReservada.getIdUsuario());

        generarQR(habitacionReservada.getCodigoQR());
        nombreHabitacion.setText(habitacion.getNombre());
        nombreHotel.setText(hotel.getNombre());
        nombreUsuario.setText(usuario.getNombre());
        direccion.setText(hotel.getDireccion());
        fechaEntrada.setText(habitacionReservada.getFechaEntrada().toString());
        fechaSalida.setText(habitacionReservada.getFechaSalida().toString());
        totalNoches.setText(habitacionReservada.getTotalNoches());
        precioTotal.setText(String.format("$%.0f", habitacionReservada.getPrecioTotal()));
    }

    private void generarQR(String textoQR) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textoQR, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder barcodeEncoder;
            barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            codigoQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}