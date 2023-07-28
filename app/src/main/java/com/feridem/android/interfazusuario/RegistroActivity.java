package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feridem.android.R;
import com.feridem.android.logicanegocio.ValidarDatos;

public class RegistroActivity extends AppCompatActivity {
    private Button botonIniciarSesion;
    private Button botonRegistrarse;
    private EditText ingresarNombre,
                     ingresarCorreo,
                     ingresarCelular,
                     ingresarContrasenia,
                     confirmarContrasenia;
    private Context contexto;
//    private TextView texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarRecursos();
        botonIniciarSesion.setOnClickListener(this::irIniciarSesion);
        botonRegistrarse.setOnClickListener(this::irPrincipal);
//        texto.setOnClickListener(this::mensaje);
    }

    private void inicializarRecursos() {
        contexto = this;
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        ingresarNombre = findViewById(R.id.ingresarNombreCompleto);
        ingresarCorreo = findViewById(R.id.ingresarCorreo);
        ingresarCelular = findViewById(R.id.ingresarNumeroCelular);
        ingresarContrasenia = findViewById(R.id.ingresarContrasenia);
        confirmarContrasenia = findViewById(R.id.ingresarConfirmarContrasenia);
//        texto = findViewById(R.id.nombreCompleto);
    }

    private void irIniciarSesion(View vista) { onBackPressed(); }

    private void irPrincipal(View view) {
        Intent siguiente = new Intent(contexto, BarraNavegacionActivity.class);
        if (ValidarDatos.campoLleno(ingresarNombre, contexto) &&
                ValidarDatos.campoLleno(ingresarCorreo, contexto) &&
                ValidarDatos.campoLleno(ingresarCelular,contexto) &&
                ValidarDatos.campoLleno(ingresarContrasenia, contexto) &&
                ValidarDatos.campoLleno(confirmarContrasenia, contexto))
            if (ValidarDatos.confirmarContrasenia(ingresarContrasenia, confirmarContrasenia, this))
                startActivity(siguiente);
    }

//    private void mensaje(View view) {
//        Toast.makeText(this,"Hola papito", Toast.LENGTH_SHORT).show();
//    }
}