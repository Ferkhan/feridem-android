package com.feridem.android.user_interface.fragment;

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

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.entidades.Usuario;
import com.feridem.android.business_logic.fachada.RegistroSesionBL;
import com.feridem.android.business_logic.fachada.UsuarioBL;
import com.feridem.android.user_interface.activity.ActualizarPerfilActivity;
import com.feridem.android.user_interface.activity.IniciarSesionActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button
            editPerfil,
            cerrarSesion;

    private TextView
            nomUsuario,
            correoUs,
            celularUs;
    private UsuarioBL usuarioBL;
    private RegistroSesionBL registroSesionBL;
    private Usuario usuario;

    public PerfilFragment() {
        // Constructor vacío
    }

    /**
     * onCreate: Se encarga de crear el fragmento "perfil", dentro de la ventana barra de navegación.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView:Se encarga de crear la interfaz de usuario asociada al fragmento
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return a element of type view
     */
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
            establecerDatosPerfil();
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        editPerfil.setOnClickListener(this::irEditarPerfil);
        cerrarSesion.setOnClickListener(vista -> {
            try {
                cerrarSesion(vista);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        });

        return view;
    }

    /**
     * editarPerfil: Se encarga de lanzar a una ventana de Actualización de información del perfil, al aplastar un botón.
     * @param view
     */
    public void irEditarPerfil(View view){
        Intent editPerfilIntent = new Intent(getActivity(), ActualizarPerfilActivity.class);
        startActivity(editPerfilIntent);
        requireActivity().finish();
    }

    /**
     * mostrarDatosPerfil: Se encarga de obtener los datos que el usuario a ingresado en el Registro.
      * @throws AppException
     */
    public void establecerDatosPerfil() throws AppException {
        Context context = getActivity();
        usuarioBL = new UsuarioBL(context);
        registroSesionBL = new RegistroSesionBL(context);

        int idUsuarioActivo= registroSesionBL.obtenerIdUsuarioConectado();
        usuario = usuarioBL.obtenerPorId(idUsuarioActivo);
        nomUsuario.setText(usuario.getNombre());
        correoUs.setText(usuario.getCorreo());
        celularUs.setText(usuario.getCelular());
    }

    /**
     * cerrarSesion: Se encarga de cerrar la sesión y lanzar el activity de iniciar sesión.
     * @param view
     * @throws AppException
     */
    public void cerrarSesion(View view ) throws AppException {
        Context contexto = getActivity();
        registroSesionBL = new RegistroSesionBL(contexto);

        if(registroSesionBL.desconectarUsuario()){
            Intent intent= new Intent(contexto, IniciarSesionActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }
}