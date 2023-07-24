package com.feridem.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

//import com.feridem.android.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class BarraNavegacionActivity extends AppCompatActivity {
    private static HabitacionFragment  hf = new HabitacionFragment();
    private static ReservacionFragment rf = new ReservacionFragment();
    private static PerfilFragment      pf = new PerfilFragment();
    static BottomNavigationView bn;
//    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_barra_navegacion);

        cargarFragmento(new HabitacionFragment());
        bn = findViewById(R.id.barraNavegacion);

        bn.setOnItemSelectedListener(mOnItemSelectedListener);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomDialog();
//            }
//        });
    }

    private final NavigationBarView.OnItemSelectedListener mOnItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.habitacion:
                    cargarFragmento(new HabitacionFragment());
//                case R.id.encabezado:
//                    cargarFragmento(new ReservacionFragment());
//                    return true;
//                case R.id.noCuenta:
//                    cargarFragmento(new PerfilFragment());
//                    return true;
            }
            return false;
            };
    };



    private void cargarFragmento (Fragment fragmento) {
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmento_contenedor,fragmento);
        transaccion.commit();
    }
}