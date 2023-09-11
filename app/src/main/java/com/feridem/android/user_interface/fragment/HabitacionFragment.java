package com.feridem.android.user_interface.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.managers.HabitacionBL;
import com.feridem.android.business_logic.entities.Habitacion;
import com.feridem.android.business_logic.utilities.AdaptadorHabitaciones;

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

    /**
     * onCreate: Se encarga de crear el framento "HabitacionFragment", en el activity barra de navegacion.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listaHabitaciones = new HabitacionBL(getContext()).obtenerRegistrosActivos();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_habitacion, container, false);

        RecyclerView recyclerView = vista.findViewById(R.id.recyclerView);
        AdaptadorHabitaciones adaptadorHabitaciones = new AdaptadorHabitaciones(listaHabitaciones, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorHabitaciones);

        return vista;
    }
}