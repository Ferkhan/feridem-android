package com.feridem.android.user_interface.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.ValidarDatos;
import com.feridem.android.business_logic.entidades.Usuario;
import com.feridem.android.business_logic.fachada.RegistroSesionBL;
import com.feridem.android.business_logic.fachada.UsuarioBL;

/**
 * Esta es la ventana para actualizar la información del usuario.
 */
public class ActualizarPerfilActivity extends AppCompatActivity {
    private EditText
            nombreUsuarioAc,
            correoUsuarioAc,
            celularUsuarioAc;
    private Button guardarInfo;
    private Usuario usuarioActivo;
    private UsuarioBL usuarioBL;
    private RegistroSesionBL registroSesionBL;

    /**
     * onCreate: Se encarga de la creación del activity.
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);

        try {
            inicializarRecursos();
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        guardarInfo.setOnClickListener(view -> {
            try {
                guardarInfo(view);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * inicializarRecursos: se encarga de emparejar el identificador con el componente
     */
    private void inicializarRecursos() throws AppException {
        int idUsuario;

        nombreUsuarioAc     = findViewById(R.id.ingresarNombreAct);
        correoUsuarioAc     = findViewById(R.id.ingresarCorreoAct);
        celularUsuarioAc    = findViewById(R.id.ingresarNumeroCelularAct);
        guardarInfo         = findViewById(R.id.guardarPerfil);

        usuarioBL           = new UsuarioBL(this);
        registroSesionBL    = new RegistroSesionBL(this);
        idUsuario           = registroSesionBL.obtenerIdUsuarioConectado();
        usuarioActivo       = usuarioBL.obtenerPorId(idUsuario);

        nombreUsuarioAc.setText(usuarioActivo.getNombre());
        correoUsuarioAc.setText(usuarioActivo.getCorreo());
        celularUsuarioAc.setText(usuarioActivo.getCelular());
    }

    /**
     * guardarInfo: Se encarga de guardar la informacion que el usuario quiero Actualizar.
     * @param view
     * @throws AppException
     */
    private void guardarInfo(View view) throws AppException {
        if (!ValidarDatos.campoLleno(this, nombreUsuarioAc) ||
                !ValidarDatos.campoLleno(this, correoUsuarioAc) ||
                !ValidarDatos.campoLleno(this, celularUsuarioAc))
            return;

        if (!ValidarDatos.longitudTextoMaxMin(this, nombreUsuarioAc, "El nombre", 3, 25) ||
                !ValidarDatos.validarCorreo(this,correoUsuarioAc ) ||
                !ValidarDatos.longitudCelular(this, celularUsuarioAc, 10))
            return;

        String nombreUsuario  = nombreUsuarioAc.getText().toString();
        String correoUsuario  = correoUsuarioAc.getText().toString();
        String celularUsuario = celularUsuarioAc.getText().toString();

        usuarioBL.actualizarRegistro(usuarioActivo.getIdRol(), nombreUsuario, correoUsuario, celularUsuario);
        Toast.makeText(this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();

        finish();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BarraNavegacionActivity.class);
        startActivity(intent);
    }

}