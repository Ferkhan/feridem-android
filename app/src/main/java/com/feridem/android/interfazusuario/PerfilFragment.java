package com.feridem.android.interfazusuario;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.entidades.RegistroSesion;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.fachada.RegistroSesionBL;
import com.feridem.android.logicanegocio.fachada.UsuarioBL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button editPerfil, cerrarSesion;

    private TextView nomUsuario,correoUs,celularUs;
    private UsuarioBL usBL;
    private RegistroSesionBL rgBL;

    private Usuario usuario ;
    private RegistroSesion registroSesion;
    private String nombUsuario,correoUsuario,celUsuario;
    public PerfilFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        editPerfil = view.findViewById(R.id.editPerfil);
        cerrarSesion=view.findViewById(R.id.cerrarSesion);
        nomUsuario=view.findViewById(R.id.nombreUsuario2);
        correoUs=view.findViewById(R.id.correoUsuario2);
        celularUs=view.findViewById(R.id.numUsuario2);

        try {
            mostrarDatosPerfil();
            nomUsuario.setText(nombUsuario);
            correoUs.setText(correoUsuario);
            celularUs.setText(celUsuario);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        editPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPerfil(view);
            }
        });
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cerrarSesion(view);
                } catch (AppException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;
    }
    public void editarPerfil (View view){
        Context context = getActivity();
        Intent editPerfilIntent = new Intent(context, ActPerfilActivity.class);
        startActivity(editPerfilIntent);

        }
    public void mostrarDatosPerfil() throws AppException {
        Context context = getActivity();
        usBL= new UsuarioBL(context);
        rgBL= new RegistroSesionBL(context);

        int usActivo= rgBL.obtenerIdUsuarioConectado();
        usuario=usBL.obtenerPorId(usActivo);
        nombUsuario = usuario.getNombre().toString();
        correoUsuario=usuario.getCorreo().toString();
        celUsuario=usuario.getCelular().toString();
    }
    public void cerrarSesion(View view ) throws AppException {
        Context context = getActivity();
        rgBL= new RegistroSesionBL(context);
        boolean verUsuarioConect= rgBL.desconectarUsuario();
        if(verUsuarioConect==true){
            Intent intent= new Intent(context,IniciarSesionActivity.class);
            startActivity(intent);
        }
    }
    }