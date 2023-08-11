package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feridem.android.R;
import com.feridem.android.interfazdatos.BaseUsuarios;
import com.feridem.android.interfazdatos.GestorBaseDatos;
import com.feridem.android.interfazdatos.Usuarios;
import com.feridem.android.logicanegocio.ValidarDatos;
import com.feridem.android.logicanegocio.ValidarInicarSesion;

import java.util.ArrayList;

public class IniciarSesionActivity extends AppCompatActivity {
    private EditText ingresarCorreo,
                     ingresarContrasenia;
    private Button botonRegistrarse;
    private Button botonIniciarSesion;
    private ValidarInicarSesion validarInicarSesion;

    private ArrayList<Usuarios> listaUsuarios;
    private BaseUsuarios baseUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        inicializarRecursos();

        botonRegistrarse.setOnClickListener(this::irRegistro);
        botonIniciarSesion.setOnClickListener(this::irPrincipal);
    }

    private void inicializarRecursos() {
        ingresarCorreo = findViewById(R.id.ingresarCorreo);
        ingresarContrasenia = findViewById(R.id.ingresarContrasenia);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        baseUsuarios = new BaseUsuarios(this);
    }
    private void irRegistro(View view) {
        Intent siguiente = new Intent(this, RegistroActivity.class);
        startActivity(siguiente);
    }

    private void irPrincipal(View view) {
        Intent siguiente = new Intent(this, BarraNavegacionActivity.class);
        if (!ValidarDatos.campoLleno(this, ingresarCorreo) &&
                !ValidarDatos.campoLleno(this, ingresarContrasenia))
            return;

        listaUsuarios = baseUsuarios.leerUsuarios();
        validarInicarSesion = new ValidarInicarSesion(this, listaUsuarios);

        if (!validarInicarSesion.validarCuentaUsuario(ingresarCorreo.getText().toString(), ingresarContrasenia.getText().toString()))
            return;
        startActivity(siguiente);
    }


}