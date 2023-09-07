package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.entidades.Usuario;
import com.feridem.android.logicanegocio.fachada.RegistroSesionBL;
import com.feridem.android.logicanegocio.fachada.UsuarioBL;

public class ActPerfilActivity extends AppCompatActivity {
    private EditText nombreUsuarioAc, correoUsuarioAc,celularUsuarioAc;
    private Button guardarInfo;
    private static final PerfilFragment pf = new PerfilFragment();
    private UsuarioBL usBL;
    private RegistroSesionBL rgBL;

    private Usuario usuario ;
    private String nombUsuario,correoUsuario,celUsuario;

    /**
     * onCreate:
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_perfil);
        nombreUsuarioAc=(EditText) findViewById(R.id.ingresarNombreAct);
        correoUsuarioAc=(EditText) findViewById(R.id.ingresarCorreoAct);
        celularUsuarioAc=(EditText) findViewById(R.id.ingresarNumeroCelularAct);
        guardarInfo=(Button) findViewById(R.id.guardarPerfil);

    }

    public void guardarInfo(View view) throws AppException {
        nombUsuario=nombreUsuarioAc.getText().toString();
        correoUsuario=correoUsuarioAc.getText().toString();
        celUsuario=celularUsuarioAc.getText().toString();
        actualizarInfo(nombUsuario,correoUsuario,celUsuario);
        cargarActivity(pf);
    }

    public void actualizarInfo(String nombre1, String correo2, String celular2) throws AppException {
        usBL= new UsuarioBL(this);
        rgBL= new RegistroSesionBL(this);
        int usActivo= rgBL.obtenerIdUsuarioConectado();
        usuario=usBL.obtenerPorId(usActivo);
        int idRol= usuario.getIdRol();
        usBL.actualizarRegistro(idRol,nombre1,correo2,celular2);

    }
    private void cargarActivity(Fragment fragmento) {
        Intent intent= new Intent(this, BarraNavegacionActivity.class);
        startActivity(intent);
    }

}