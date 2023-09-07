package com.feridem.android.interfazusuario;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.feridem.android.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_camara, container, false);

        botonEscanear = vista.findViewById(R.id.boton_escaner);
        barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if (result.getContents() == null) {
                        Toast.makeText(getContext(), "Escaner cancelado", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Escaneado: " + result.getContents(), Toast.LENGTH_LONG).show();
                    }
                });
        try {
            Log.i("AppException", "intentando abrir cámara");
            botonEscanear.setOnClickListener(this::escanearCodigo);
            Log.i("AppException", "después de intentando abrir cámara");
        } catch (Exception error) {
            Log.i("AppException", "error al abrir cámara");
            error.printStackTrace();

        }

        return vista;
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
}