package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;

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
    private Bitmap qrBitmap;


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
        fechaEntrada        = findViewById(R.id.dia_entrada);
        fechaSalida         = findViewById(R.id.dia_salida);
        totalNoches         = findViewById(R.id.total_noches);
        precioTotal         = findViewById(R.id.precio_total);
        codigoQR            = findViewById(R.id.codigo_qr);
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
        fechaEntrada.setText(habitacionReservada.getFechaEntrada());
        fechaSalida.setText(habitacionReservada.getFechaSalida());
        totalNoches.setText(String.valueOf(habitacionReservada.getTotalNoches()));
        precioTotal.setText(String.format("$%.0f", habitacionReservada.getPrecioTotal()));
    }

    private void generarQR(String textoQR) throws AppException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textoQR, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder barcodeEncoder;
            barcodeEncoder = new BarcodeEncoder();
            qrBitmap = barcodeEncoder.createBitmap(bitMatrix);
            codigoQR.setImageBitmap(qrBitmap);
        } catch (WriterException error) {
            throw new AppException(error, getClass(), "generarQR()");
        }
    }

    private void compartirQR(View vista) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png"); // Cambia el tipo de datos según el formato de tu código QR

        // Agrega la imagen del código QR al intent
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        qrBitmap.compress(Bitmap.CompressFormat.PNG, 250, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), qrBitmap, "QR Code", null);
        Uri qrCodeUri = Uri.parse(path);
        shareIntent.putExtra(Intent.EXTRA_STREAM, qrCodeUri);

        // Puedes agregar un mensaje opcional
        shareIntent.putExtra(Intent.EXTRA_TEXT, "¡Mira este código QR!");

        // Abre la actividad de compartir
        startActivity(Intent.createChooser(shareIntent, "Compartir código QR usando..."));
    }
}