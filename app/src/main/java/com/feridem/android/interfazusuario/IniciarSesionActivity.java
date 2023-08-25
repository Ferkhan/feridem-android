package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feridem.android.R;
import com.feridem.android.interfazdatos.basedatos.GestorBaseDatos;
import com.feridem.android.logicanegocio.ValidarDatos;
import com.feridem.android.logicanegocio.ValidarIniciarSesion;

public class IniciarSesionActivity extends AppCompatActivity {
    private EditText ingresarCorreo;
    private EditText ingresarContrasena;
    private Button botonRegistrarse;
    private Button botonIniciarSesion;
    private ValidarIniciarSesion validarIniciarSesion;
    private boolean activarHacks = true;

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
        ingresarContrasena = findViewById(R.id.ingresarContrasenia);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);

        GestorBaseDatos gestorBaseDatos = new GestorBaseDatos(this);
        try{
            gestorBaseDatos.comprobarBaseDatos();
        } catch (Exception e) {
            Log.i("mensaje feridem", e.getMessage());
        }
        try {
            gestorBaseDatos.abrirBaseDatos();
        } catch (Exception e) {
            Log.i("mensaje feridem", e.getMessage());

        }
    }

    private void irRegistro(View view) {
        Intent siguiente = new Intent(this, RegistroActivity.class);
        startActivity(siguiente);
    }

    private void irPrincipal(View view) {
        Intent siguiente = new Intent(this, BarraNavegacionActivity.class);

        // -------------------Hacks-------------------
        if (activarHacks) startActivity(siguiente);
        // -------------------------------------------

        if (!ValidarDatos.campoLleno(this, ingresarCorreo) &&
                !ValidarDatos.campoLleno(this, ingresarContrasena))
            return;


        validarIniciarSesion = new ValidarIniciarSesion(this);

        if (!validarIniciarSesion.validarCuentaUsuario(ingresarCorreo.getText().toString(), ingresarContrasena.getText().toString()))
            return;
        startActivity(siguiente);
        finish();
    }


}