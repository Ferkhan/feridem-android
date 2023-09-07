package com.feridem.android.interfazusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.feridem.android.R;
import com.feridem.android.framework.AppException;
import com.feridem.android.logicanegocio.entidades.RegistroSesion;
import com.feridem.android.logicanegocio.fachada.RegistroSesionBL;

import java.util.Timer;
import java.util.TimerTask;

public class PantallazoActivity extends AppCompatActivity {
    private RegistroSesionBL rgBL;
    private static final PerfilFragment pf = new PerfilFragment();
    private RegistroSesion registroSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallazo);
        TimerTask tarea= new TimerTask() {
            @Override
            public void run() {
                rgBL=new RegistroSesionBL(PantallazoActivity.this);
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
        tiempoPantalla.schedule(tarea,5000);
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();

    }
}