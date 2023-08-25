package com.feridem.android.interfazusuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feridem.android.R;
import com.feridem.android.logicanegocio.fachada.HabitacionBL;
import com.feridem.android.logicanegocio.entidades.Habitacion;
import com.feridem.android.logicanegocio.AdaptadorLista;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitacionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    List<Habitacion> listaHabitaciones = new ArrayList<>();

    public HabitacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitacionFragment.
     */
    public static HabitacionFragment newInstance(String param1, String param2) {
        HabitacionFragment fragment = new HabitacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HabitacionBL habitacionBL = new HabitacionBL(getContext());
        listaHabitaciones = habitacionBL.obtenerRegistrosActivos();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_habitacion, container, false);

        RecyclerView recyclerView = vista.findViewById(R.id.recyclerView);
        AdaptadorLista adaptadorLista = new AdaptadorLista(listaHabitaciones, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorLista);

        return vista;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Agregar el fragmento a la pila de retroceso (back stack)
//        requireActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragmento_contenedor, this)
//                .addToBackStack(null)
//                .commit();
//    }
}