package com.feridem.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button botonRegistrarse;
    private Button botonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);

        botonRegistrarse.setOnClickListener(this::irRegistro);
        botonIniciarSesion.setOnClickListener(this::irPrincipal);
    }

    private void irRegistro(View view) {
        Intent siguiente = new Intent(this, RegistroActivity.class);
        startActivity(siguiente);
    }

    private void irPrincipal(View view) {
        Intent siguiente = new Intent(this, BarraNavegacionActivity.class);
        startActivity(siguiente);
    }
}