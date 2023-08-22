package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.interfazdatos.basedatos.GestorUsuario;
import com.feridem.android.interfazdatos.basedatos.GestorUsuarioCredencial;
import com.feridem.android.logicanegocio.ValidarDatos;

public class RegistroActivity extends AppCompatActivity {
    private Button botonIniciarSesion;
    private Button botonRegistrarse;
    private EditText ingresarNombre,
                     ingresarCorreo,
                     ingresarCelular,
                     ingresarContrasenia,
                     confirmarContrasenia;

//    private TextView texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarRecursos();
        botonIniciarSesion.setOnClickListener(this::irIniciarSesion);
        botonRegistrarse.setOnClickListener(this::irPrincipal);
    }

    private void inicializarRecursos() {
        botonIniciarSesion =    findViewById(R.id.botonIniciarSesion);
        botonRegistrarse =      findViewById(R.id.botonRegistrarse);
        ingresarNombre =        findViewById(R.id.ingresarNombreCompleto);
        ingresarCorreo =        findViewById(R.id.ingresarCorreo);
        ingresarCelular =       findViewById(R.id.ingresarNumeroCelular);
        ingresarContrasenia =   findViewById(R.id.ingresarContrasenia);
        confirmarContrasenia =  findViewById(R.id.ingresarConfirmarContrasenia);
    }

    private void irIniciarSesion(View vista) {
        onBackPressed();
    }

    private void irPrincipal(View view) {
        if (!ValidarDatos.campoLleno(this, ingresarNombre) ||
                !ValidarDatos.campoLleno(this, ingresarCorreo) ||
                !ValidarDatos.campoLleno(this, ingresarCelular) ||
                !ValidarDatos.campoLleno(this, ingresarContrasenia) ||
                !ValidarDatos.campoLleno(this, confirmarContrasenia))
            return;

        if (!ValidarDatos.longitudTextoMaxMin(this, ingresarNombre, "El nombre", 3, 25) ||
                !ValidarDatos.validarCorreo(this, ingresarCorreo) ||
                !ValidarDatos.longitudCelular(this, ingresarCelular, 10) ||
                !ValidarDatos.longitudTextoMaxMin(this, ingresarContrasenia, "La contraseña", 5, 15) ||
                !ValidarDatos.confirmarContrasenia(this, ingresarContrasenia, confirmarContrasenia))
            return;

        Intent siguienteActivity = new Intent(this, BarraNavegacionActivity.class);
        GestorUsuario gestorUsuario = new GestorUsuario(this);
        GestorUsuarioCredencial gestorUsuarioCredencial = new GestorUsuarioCredencial(this);
        long idUsuario = gestorUsuario.insertarUsuario(ingresarNombre.getText().toString(), ingresarCorreo.getText().toString(),
                                      ingresarCelular.getText().toString());
        long idCredencial = gestorUsuarioCredencial.insertarCredencial((int)idUsuario, ingresarContrasenia.getText().toString());

        if (idUsuario > 0 && idCredencial >= 0) {
            Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show();
            startActivity(siguienteActivity);
        } else {
            Toast.makeText(this, "ERROR al intentar crear la cuenta", Toast.LENGTH_SHORT).show();
        }

    }
}