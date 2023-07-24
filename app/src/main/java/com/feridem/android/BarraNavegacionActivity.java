package com.feridem.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

//import com.feridem.android.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class BarraNavegacionActivity extends AppCompatActivity {
    private static final HabitacionFragment  hf = new HabitacionFragment();
    private static final ReservacionFragment rf = new ReservacionFragment();
    private static final PerfilFragment      pf = new PerfilFragment();
    static BottomNavigationView bn;
//    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_navegacion);

        cargarFragmento(hf);
        bn = findViewById(R.id.barraNavegacion);
        bn.setOnItemSelectedListener(itemSeleccionado);

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
//            switch (item.getItemId()) {
//                case R.id.habitacion:
//                    cargarFragmento(new HabitacionFragment());
//                    break;
//                case R.id.reservacion:
//                    cargarFragmento(new ReservacionFragment());
//                    break;
//                case R.id.perfil:
//                    cargarFragmento(new PerfilFragment());
//                    break;
//            }
//            return true;
        }
    };

    private void cargarFragmento (Fragment fragmento) {
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmento_contenedor,fragmento);
        transaccion.commit();
    }
}