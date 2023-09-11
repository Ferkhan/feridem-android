package com.feridem.android.user_interface.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.ValidarDatos;
import com.feridem.android.business_logic.entidades.Usuario;
import com.feridem.android.business_logic.fachada.RegistroSesionBL;
import com.feridem.android.business_logic.fachada.UsuarioBL;
import com.feridem.android.user_interface.fragment.PerfilFragment;

/**
 * Esta es la ventana para acutalizar la información del usuario.
 */
public class ActPerfilActivity extends AppCompatActivity {
    private EditText
            nombreUsuarioAc,
            correoUsuarioAc,
            celularUsuarioAc;
    private Button guardarInfo;
    private static final PerfilFragment pf = new PerfilFragment();
    private UsuarioBL usBL;
    private RegistroSesionBL rgBL;

    private Usuario usuario ;
    private String nombUsuario,correoUsuario,celUsuario;

    /**
     * onCreate: Se encarga de la creación del activity.
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

    /**
     * guardarInfo: Se encarga de guardar la información que el usuario quiero Actualizar.
     * @param view
     * @throws AppException
     */
    @SuppressLint("SuspiciousIndentation")
    public void guardarInfo(View view) throws AppException {
        if (!ValidarDatos.campoLleno(this, nombreUsuarioAc) ||
                !ValidarDatos.campoLleno(this, correoUsuarioAc) ||
                !ValidarDatos.campoLleno(this, celularUsuarioAc))
        return;
        if (!ValidarDatos.longitudTextoMaxMin(this, nombreUsuarioAc, "El nombre", 3, 25) ||
                !ValidarDatos.validarCorreo(this,correoUsuarioAc ) ||
                !ValidarDatos.longitudCelular(this, celularUsuarioAc, 10))
            return;

        nombUsuario=nombreUsuarioAc.getText().toString();
        correoUsuario=correoUsuarioAc.getText().toString();
        celUsuario=celularUsuarioAc.getText().toString();
        actualizarInfo(nombUsuario,correoUsuario,celUsuario);
        cargarActivity(pf);
    }

    /**
     * actualizarInfo: Se encarga de actualizar la informacion nueva que ha estipulado el usuario, en la base de datos.
     * @param nombre1
     * @param correo2
     * @param celular2
     * @throws AppException
     */
    public void actualizarInfo(String nombre1, String correo2, String celular2) throws AppException {
        usBL= new UsuarioBL(this);
        rgBL= new RegistroSesionBL(this);
        int usActivo= rgBL.obtenerIdUsuarioConectado();
        usuario=usBL.obtenerPorId(usActivo);
        int idRol= usuario.getIdRol();
        usBL.actualizarRegistro(idRol,nombre1,correo2,celular2);

    }

    /**
     * cargarActivity: Se encarga de cargar una nueva ventana, una vez que se terminó el proceso de actualizar información.
     * @param fragmento
     */
    private void cargarActivity(Fragment fragmento) {
        Intent intent= new Intent(this, BarraNavegacionActivity.class);
        startActivity(intent);
    }

}