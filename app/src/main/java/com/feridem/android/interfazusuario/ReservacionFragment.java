package com.feridem.android.interfazusuario;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.AdaptadorHabitaciones;
import com.feridem.android.logicanegocio.AdaptadorReservaciones;
import com.feridem.android.logicanegocio.entidades.HabitacionReservada;
import com.feridem.android.logicanegocio.fachada.HabitacionBL;
import com.feridem.android.logicanegocio.fachada.HabitacionReservadaBL;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservacionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private Button botonGenerar;
    private List<HabitacionReservada> listaReservaciones;
    public ReservacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservacionFragment newInstance(String param1, String param2) {
        ReservacionFragment fragment = new ReservacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HabitacionReservadaBL habitacionReservadaBL = new HabitacionReservadaBL(getContext());
        try {
            listaReservaciones = habitacionReservadaBL.obtenerRegistrosActivos();
        } catch (AppException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_reservacion, container, false);
//        imageView = vista.findViewById(R.id.imagen_qr);
//        botonGenerar = vista.findViewById(R.id.boton_generar_codigo);
//        botonGenerar.setOnClickListener(this::generarQR);

        RecyclerView recyclerView = vista.findViewById(R.id.recyclerView);
        AdaptadorReservaciones adaptadorReservaciones = new AdaptadorReservaciones(listaReservaciones, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorReservaciones);

        return vista;
    }

    private void generarQR(View vista) {

        String reservaInfo = "Nombre de Usuario|Nombre de Habitación|Nombre de Hotel|2023-07-10|2023-07-15|150.00";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(reservaInfo, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder barcodeEncoder;
            barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}