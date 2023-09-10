package com.feridem.android.user_interface.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

//import com.feridem.android.databinding.ActivityMainBinding;
import com.feridem.android.R;
import com.feridem.android.user_interface.fragment.CamaraFragment;
import com.feridem.android.user_interface.fragment.HabitacionFragment;
import com.feridem.android.user_interface.fragment.PerfilFragment;
import com.feridem.android.user_interface.fragment.ReservacionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Esta es ventana que tiene la barra de navegación.
 */
public class BarraNavegacionActivity extends AppCompatActivity {
    private static final HabitacionFragment hf = new HabitacionFragment();
    private static final ReservacionFragment rf = new ReservacionFragment();
    private static final PerfilFragment pf = new PerfilFragment();
    private static final CamaraFragment cf = new CamaraFragment();
    static BottomNavigationView bn;
    FragmentManager gestorFragmento;
    Fragment fragmentoActual;
    Button botonEscanear;
    Context contexto = this;
//    ActivityMainBinding binding;

    /**
     * onCreate: Se encarga se crear la ventana o activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_navegacion);

        cargarFragmento(hf);

        inicializarRecursos();


    }

    /**
     * inicializarRecursos: Se encarga de inicar la barra de navegación y el item de la barra seleccionado.
     */
    private void inicializarRecursos() {
        bn = findViewById(R.id.barraNavegacion);
        bn.setOnItemSelectedListener(itemSeleccionado);

    }

    /**
     * onBackPressed: Se encarga
     */
    @Override
    public void onBackPressed() {
        gestorFragmento = getSupportFragmentManager();
        fragmentoActual = getSupportFragmentManager().findFragmentById(R.id.fragmento_contenedor);
        if (fragmentoActual instanceof HabitacionFragment) {
//            moveTaskToBack(true);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            cargarFragmento(hf);
            bn.setSelectedItemId(R.id.habitacion);
        }
    }

    /**
     * Método asignado a un botón, para el cambio entre framentos dentro de una ventana.
     */
    private final NavigationBarView.OnItemSelectedListener itemSeleccionado = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.habitacion) {
                cargarFragmento(hf);
            } else if (item.getItemId() == R.id.reservacion) {
                cargarFragmento(rf);
            } else if (item.getItemId() == R.id.perfil) {
                cargarFragmento(pf);
            } else if (item.getItemId() == R.id.camara) {
                cargarFragmento(cf);
            }
            return true;
        }
    };


    /**
     * cargarFragmento: Se encarga de generar el fragmento, dependiendo del que ha sido seleccionado.
     * @param fragmento: Representa el framgneto que se quiere cargar.
     */
    private void cargarFragmento (Fragment fragmento) {
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmento_contenedor,fragmento);
        transaccion.commit();
    }
}