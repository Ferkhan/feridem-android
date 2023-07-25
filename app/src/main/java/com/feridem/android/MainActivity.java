package com.feridem.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText ingresarCorreo,
                     ingresarContrasenia;
    private Button botonRegistrarse;
    private Button botonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarRecursos();

        botonRegistrarse.setOnClickListener(this::irRegistro);
        botonIniciarSesion.setOnClickListener(this::irPrincipal);
    }

    private void inicializarRecursos() {
        ingresarCorreo = findViewById(R.id.ingresarCorreo);
        ingresarContrasenia = findViewById(R.id.ingresarContrasenia);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
    }
    private void irRegistro(View view) {
        Intent siguiente = new Intent(this, RegistroActivity.class);
        startActivity(siguiente);
    }

    private void irPrincipal(View view) {
        Intent siguiente = new Intent(this, BarraNavegacionActivity.class);
        if (ValidarDatos.campoLleno(ingresarCorreo, this) &&
                ValidarDatos.campoLleno(ingresarContrasenia, this))
            startActivity(siguiente);
    }




}