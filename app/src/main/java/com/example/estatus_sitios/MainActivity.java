package com.example.estatus_sitios;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    TextView txtTitulo;
    Button imgTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());

        txtTitulo = findViewById(R.id.txtTitle);
        imgTitulo = findViewById(R.id.imgSearch);
        imgTitulo.setVisibility(View.INVISIBLE);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                txtTitulo.setText(R.string.titleHome);
                imgTitulo.setVisibility(View.INVISIBLE);
                break;
            case R.id.navigation_availability:
                fragment = new AvailabilityFragment();
                txtTitulo.setText(R.string.titleAvailability);
                imgTitulo.setVisibility(View.VISIBLE);
                break;
            case R.id.navigation_profitability:
                fragment = new ProfitabilityFragment();
                txtTitulo.setText(R.string.titleProfitability);
                imgTitulo.setVisibility(View.VISIBLE);
                break;
        }
        return loadFragment(fragment);
    }
}
