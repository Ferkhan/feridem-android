package com.feridem.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroActivity extends AppCompatActivity {
    private Button botonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        botonIniciarSesion.setOnClickListener(this::irIniciarSesion);
    }

    private void irIniciarSesion(View vista) {
//        Intent siguiente = new Intent(this, MainActivity.class);
        onBackPressed();
    }
}