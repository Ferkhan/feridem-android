package com.feridem.android.user_interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.interfazdatos.data_access.HotelDAC;
import com.feridem.android.business_logic.ValidarDatos;
import com.feridem.android.business_logic.VerificarDatos;

/**
 * Esta es la ventana de inicio de sesión.
 */
public class IniciarSesionActivity extends AppCompatActivity {
    private EditText ingresarCorreo;
    private EditText ingresarContrasena;
    private Button botonRegistrarse;
    private Button botonIniciarSesion;
    private VerificarDatos verificarDatos;
    private boolean activarHacks = false;

    /**
     * onCreate: Se encarga de crear la ventana de iniciar sesión
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        inicializarRecursos();

        botonRegistrarse.setOnClickListener(this::irRegistro);
        botonIniciarSesion.setOnClickListener(this::irPrincipal);

        HotelDAC hotelDAC = new HotelDAC(this);
        try{
            hotelDAC.comprobarBaseDatos();
        } catch (Exception error) {
            Log.i("FeridemException", error.getMessage());
        }
        try {
            hotelDAC.abrirBaseDatos();
        } catch (Exception error) {
            Log.i("FeridemException", error.getMessage());
        }
    }

    /**
     * inicializarRecursos: Se encarga de iniciar TextView, Buttons, EditText, a partir de su id.
     */
    private void inicializarRecursos() {
        ingresarCorreo = findViewById(R.id.ingresarCorreo);
        ingresarContrasena = findViewById(R.id.ingresarContrasenia);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);

    }

    /**
     * irRegistro: Se encarga de ir al activity registro, cuando se presione un botón.
     * @param view
     */
    private void irRegistro(View view) {
        Intent siguiente = new Intent(this, RegistroActivity.class);
        startActivity(siguiente);
    }

    /**
     * irPrincipal: Se encarga de ir a la barra de navegación, cuando se presione un botón.
     * @param view
     */
    private void irPrincipal(View view) {
        Intent siguiente = new Intent(this, BarraNavegacionActivity.class);

        // -------------------Hacks-------------------
        if (activarHacks) startActivity(siguiente);
        // -------------------------------------------

        if (!ValidarDatos.campoLleno(this, ingresarCorreo) ||
                !ValidarDatos.campoLleno(this, ingresarContrasena))
            return;


        verificarDatos = new VerificarDatos(this);
        try {
            if (!verificarDatos.verificarCuentaUsuario(ingresarCorreo.getText().toString(), ingresarContrasena.getText().toString()))
                return;
        } catch (AppException error) {
            throw new RuntimeException(error);
        }

        startActivity(siguiente);
        finish();
    }


}