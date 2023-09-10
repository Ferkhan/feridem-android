package com.feridem.android.user_interface.fragment;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CamaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamaraFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private Button botonEscanear;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    private EditText
            usuario,
            habitacion,
            hotel,
            vigencia;
    private Date
            fechaActual,
            fechaEntrada,
            fechaSalida;
    public CamaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CamaraFragment.
     */
    public static CamaraFragment newInstance(String param1, String param2) {
        CamaraFragment fragment = new CamaraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    /**
     * onCreate: Se encarga de crear el framento denominado CamaraFragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    /**
     * onCreateView: Se encarga de crear la interfaz de usuario asociada al fragmento
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return vista: Contiene la interfaz de usuario configurada y lista para ser mostrada en el fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_camara, container, false);

        iniciarRecursos(vista);

        try {
            botonEscanear.setOnClickListener(this::escanearCodigo);
        } catch (Exception error) {
            Log.i("AppException", "botonEscaner");
        }
        return vista;
    }

    private void iniciarRecursos(View vista) {
        botonEscanear = vista.findViewById(R.id.boton_escaner);
        usuario       = vista.findViewById(R.id.usuario);
        hotel         = vista.findViewById(R.id.nombre_hotel);
        habitacion    = vista.findViewById(R.id.nombre_habitacion);
        vigencia      = vista.findViewById(R.id.estado_reservacion);
        fechaActual   = new Date();
        fechaEntrada  = null;
        fechaSalida   = null;

        barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if (result.getContents() == null) {
                        limpiarInformacion();
                        Toast.makeText(getContext(), "Escaner cancelado", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            decodificarQR(result.getContents());
                        } catch (AppException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private void escanearCodigo(View vista) {
        ScanOptions integrador = new ScanOptions();

        try {
            integrador.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            integrador.setPrompt("Escanea el código proporcionado por Feridem");
            integrador.setCameraId(0);
            integrador.setBeepEnabled(true);
            integrador.setBarcodeImageEnabled(true);
            integrador.setOrientationLocked(true);
            barcodeLauncher.launch(integrador);

        } catch (Exception error) {
            error.printStackTrace();
        }
        Toast.makeText(getContext(), "Escaneando Código QR", Toast.LENGTH_SHORT).show();
    }



    private void limpiarInformacion() {
        usuario.setText("");
        hotel.setText("");
        habitacion.setText("");
        vigencia.setText("");
    }

    private void decodificarQR(String codigo) throws AppException {
        String[] decodificado = codigo.split("\\$\\|&");
        if (decodificado.length == 5) {
            usuario.setText(decodificado[0]);
            hotel.setText(decodificado[1]);
            habitacion.setText(decodificado[2]);
            establecerVigencia(compararFechas(decodificado[3], decodificado[4]));
        } else {
            limpiarInformacion();
            Toast.makeText(getContext(), "Código no válido", Toast.LENGTH_LONG).show();
        }
    }

    private int compararFechas (String entrada, String salida) throws AppException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            fechaEntrada = formatoFecha.parse(entrada + " 14:00:00");
            fechaSalida = formatoFecha.parse(salida + " 12:00:00");
            Log.i("AppException", entrada + " 14:00:00" + ": entrada");
            Log.i("AppException", salida + " 12:00:00" + ": salida");
            Log.i("AppException", fechaActual.toString() + ": actual");
        } catch (ParseException error) {
            throw new AppException(error, getClass(), "establecerVigencia()");
        }
        if (fechaActual.compareTo(fechaEntrada) < 0)
            return -1;

        if (fechaActual.compareTo(fechaSalida) > 0)
            return 1;


        return 0;
    }

    private void establecerVigencia(int comparador) {
        Log.i("AppException", comparador + ": comparador");
        if (comparador > 0) {
            vigencia.setText("Caducada");
            vigencia.setTextColor(getResources().getColor(R.color.caducada, null));
        } else if (comparador < 0) {
            vigencia.setText("Pendiente");
            vigencia.setTextColor(getResources().getColor(R.color.pendiente, null));
        } else {
            vigencia.setText("Confirmada");
            vigencia.setTextColor(getResources().getColor(R.color.confirmada, null));
        }
    }
}