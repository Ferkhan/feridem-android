package com.feridem.android.user_interface.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.feridem.android.R;
import com.feridem.android.business_logic.entidades.RegistroSesion;
import com.feridem.android.business_logic.fachada.RegistroSesionBL;
import com.feridem.android.data_access.HotelDAC;
import com.feridem.android.user_interface.fragment.PerfilFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Esta es la ventana correspondiente al splash.
 */
public class PantallazoActivity extends AppCompatActivity {
    private RegistroSesionBL registroSesionBL;
    private static final PerfilFragment pf = new PerfilFragment();
    private RegistroSesion registroSesion;

    /**
     * onCreate: Se encarga de crear el Splash.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallazo);

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

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                registroSesionBL = new RegistroSesionBL(PantallazoActivity.this);
                registroSesion = new RegistroSesion();
                Intent intent;
                try {

                    registroSesion = registroSesionBL.obtenerRegistroConectado();
                    if(registroSesion != null ) {
                        Log.i("AppException","registroSesion conectado");
                        intent= new Intent( PantallazoActivity.this, BarraNavegacionActivity.class);
                        finish();
                    }else {
                        intent= new Intent( PantallazoActivity.this, IniciarSesionActivity.class);
                        Log.i("AppException","registroSesion nulo");
                        finish();
                    }
                    startActivity(intent);
                } catch (Exception e) {
                    Log.i("AppException","catch error");

                }

                Log.i("AppException","Entrando al splash");
            }
        };

        Timer tiempoPantalla= new Timer();
        tiempoPantalla.schedule(tarea,3000);

    }

    /**
     * onDestroy: Desturye el splash cuando se cierra la app.
     */
    @Override
    protected  void onDestroy() {
        super.onDestroy();

    }
}