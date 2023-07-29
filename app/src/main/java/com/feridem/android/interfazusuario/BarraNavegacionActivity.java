package com.feridem.android.interfazusuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

//import com.feridem.android.databinding.ActivityMainBinding;
import com.feridem.android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class BarraNavegacionActivity extends AppCompatActivity {
    private static final HabitacionFragment  hf = new HabitacionFragment();
    private static final ReservacionFragment rf = new ReservacionFragment();
    private static final PerfilFragment pf = new PerfilFragment();
    static BottomNavigationView bn;
    FragmentManager gestorFragmento;
    Fragment fragmentoActual;
//    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_navegacion);

        cargarFragmento(hf);
        bn = findViewById(R.id.barraNavegacion);
        bn.setOnItemSelectedListener(itemSeleccionado);

    }

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

    private final NavigationBarView.OnItemSelectedListener itemSeleccionado = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.habitacion) {
                cargarFragmento(hf);
            } else if (item.getItemId() == R.id.reservacion) {
                cargarFragmento(rf);
            } else if (item.getItemId() == R.id.perfil) {
                cargarFragmento(pf);
            }
            return true;
        }
    };

    private void cargarFragmento (Fragment fragmento) {
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmento_contenedor,fragmento);
        transaccion.commit();
    }
}