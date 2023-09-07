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

    /**
     * onCreate: Se encarga de crear el fragmento "reservacion" en el activity "barra de navegacion".
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listaReservaciones = new HabitacionReservadaBL(getContext()).obtenerRegistrosActivos();
        } catch (AppException error) {
            throw new RuntimeException(error);
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
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_reservacion, container, false);

        RecyclerView recyclerView = vista.findViewById(R.id.recycler);
        AdaptadorReservaciones adaptadorReservaciones = new AdaptadorReservaciones(listaReservaciones, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorReservaciones);

        return vista;
    }

    /**
     * generarQr: Se encarga generar un código QR basado en la cadena de texto reservaInfo y mostrándolo en un ImageView
     * @param vista
     */
    private void generarQR(View vista) {

        String reservaInfo = "Nombre de Usuario$|&Nombre de Habitación&|&Nombre de Hotel|2023-07-10|2023-07-15|150.00";
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