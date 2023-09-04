package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.feridem.android.R;

public class FacturaActivity extends AppCompatActivity {
    private Button botonAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        inicializarRecursos();
        botonAceptar.setOnClickListener(this::irBarraNavegacion);


    }

    private void inicializarRecursos() {
        botonAceptar = findViewById(R.id.boton_aceptar);
    }

    private void irBarraNavegacion(View vista) {
        onBackPressed();
    }





}