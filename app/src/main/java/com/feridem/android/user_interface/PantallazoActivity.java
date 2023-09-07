package com.feridem.android.user_interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.business_logic.entidades.RegistroSesion;
import com.feridem.android.business_logic.fachada.RegistroSesionBL;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Esta es la ventana correspondiente al splash.
 */
public class PantallazoActivity extends AppCompatActivity {
    private RegistroSesionBL rgBL;
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
        TimerTask tarea= new TimerTask() {
            @Override
            public void run() {
                rgBL = new RegistroSesionBL(PantallazoActivity.this);
                registroSesion=new RegistroSesion();
                try {
                    registroSesion=rgBL.obtenerRegistroConectado();
                    if(registroSesion!=null ){
                        Intent intent= new Intent( PantallazoActivity.this, IniciarSesionActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (registroSesion==null){
                        Intent intent= new Intent( PantallazoActivity.this, BarraNavegacionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (AppException e) {
                    throw new RuntimeException(e);
                }

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