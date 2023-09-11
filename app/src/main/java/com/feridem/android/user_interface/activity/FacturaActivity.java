package com.feridem.android.user_interface.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.entidades.Habitacion;
import com.feridem.android.business_logic.entidades.HabitacionReservada;
import com.feridem.android.business_logic.entidades.Hotel;
import com.feridem.android.business_logic.entidades.Usuario;
import com.feridem.android.business_logic.fachada.HabitacionBL;
import com.feridem.android.business_logic.fachada.HotelBL;
import com.feridem.android.business_logic.fachada.UsuarioBL;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

/**
 * Esta es la ventana correspondiente a la factura de una reservación.
 */
public class FacturaActivity extends AppCompatActivity {
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

    /**
     * onCreate: Se encarga de crear la ventana o activitym en este caso la Factura.
     * @param savedInstanceState
     */
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

    /**
     * inicializarRecursos: Se encarga de iniciar los Textos, botones, al relacionarlos con su Id.
     */
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

    /**
     * irBarraNavegacion: Permite navegar hacia atrás teniendo en cuenta la jerarquía de los fragmentos.
     * @param vista : representa el diseño de un fragmento en la interfaz de usuario.
     */
    private void irBarraNavegacion(View vista) {
        onBackPressed();
    }

    /**
     * establecerDatos: Establece la información sobre habitación, hotel, usuario.
     * @throws AppException
     */
    private void establecerDatos() throws AppException {
        HabitacionReservada habitacionReservada = (HabitacionReservada) getIntent().getSerializableExtra("reservacion_seleccionada");
        Habitacion habitacion   = new HabitacionBL(this).obtenerPorId(habitacionReservada.getIdHabitacion());
        Hotel hotel             = new HotelBL(this).obtenerPorId(habitacion.getIdHotel());
        Usuario usuario         = new UsuarioBL(this).obtenerPorId(habitacionReservada.getIdUsuario());

        generarQR(habitacionReservada.getCodigoQR());
        nombreHabitacion.setText(habitacion.getNombre());
        nombreHotel.setText(hotel.getNombre());
        nombreUsuario.setText(String.format("Reservado por %s", usuario.getNombre()));
        direccion.setText(hotel.getDireccion());
        fechaEntrada.setText(String.format("Entrada %s desde las 2pm", habitacionReservada.getFechaEntrada()));
        fechaSalida.setText(String.format("Salida %s hasta las 12pm", habitacionReservada.getFechaSalida()));
        totalNoches.setText(String.format("Reservado por %s noches", habitacionReservada.getTotalNoches()));
        precioTotal.setText(String.format("Precio total a pagar $%.2f", habitacionReservada.getPrecioTotal()));
    }

    /**
     * generarQr:Se encarga de generar un código QR a partir de un texto proporcionado como parámetro y mostrarlo en una vista de imagen
     * @param textoQR: Texto único correpondiente a la reservación.
     * @throws AppException
     */
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

    /**
     * compartirQr: Se encarga de compartir una imagen del código QR a través de aplicaciones y servicios de uso compartido disponibles en el dispositivo
     * @param vista
     */
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