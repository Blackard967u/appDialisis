package com.example.aplicacion_dialisis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.example.aplicacion_dialisis.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
//    private androidx.appcompat.app.ActionBarDrawerToggle ActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        /*        -------------------------Hooks-------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*        -------------------------Tool bar------------------------*/
        setSupportActionBar(toolbar);

        /*        -------------------------Navigation drawer menu-------------------------*/

        //hide or show items

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_three).setVisible(false);
        menu.findItem(R.id.nav_two).setVisible(false);
        menu.findItem(R.id.nav_one).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);



        //ACTIVITY DESIGN
        String[] transportadores = getResources().getStringArray(R.array.transportadores);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_transportadores,
                transportadores
        );

        String[] formulas = getResources().getStringArray(R.array.FormularSuperficieCorporal);

        ArrayAdapter<String> adapterFormulas = new ArrayAdapter<>(
                this,
                R.layout.list_item_superficiecorporal,
                formulas
        );

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.TipoTransportadorAutoComTxtView.setAdapter(adapter);
        binding.calcularBtn.setOnClickListener(view -> funcionCalcular());

        binding.TipoTransportadorAutoComTxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.funcionOcultarTeclado();
            }
        });
    }


    private void funcionOcultarTeclado() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    public float funcionSuperficieCorporal(float height, float weight) {
        try {
            float superficieCorporal;
            superficieCorporal = (float) (Math.pow(weight, 0.425) * Math.pow(height, 0.725) * 0.007184);

            return superficieCorporal;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public String funcionCalcularVolumenDialisisPeritoneal(float superfieCorporal) {
        try {
            String result = "";
            if(superfieCorporal<1.55){
                result = "1500ml";
            } else if (superfieCorporal<1.83 & superfieCorporal>1.55) {
                result = "1500ml - 1800ml";
            } else if (superfieCorporal>1.83) {
                result = "2000ml";
            }
            return result;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public String funcionCalcularVolumenDialisisAutomatizada(float superfieCorporal) {
        try {
            String result = "";
            if(superfieCorporal<1.55){
                result = "6 litros";
            } else if (superfieCorporal<1.83 & superfieCorporal>1.55) {
                result = "8 litros";
            } else if (superfieCorporal>1.83) {
                result = "9 - 10 litros";
            }
            return result;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public String funcionCalcularDialisisPeritoneal(float uresis){
        try {
            String item = binding.TipoTransportadorAutoComTxtView.getText().toString();
            String bolsas;
            if (item.equals("Bajo")) {
                bolsas = "1.5% = 4 bolsas";
            } else if (item.equals("Promedio bajo") & uresis>=500) {
                bolsas = "1.5% = 3 bolsas --- 2.5% = 1 bolsa";
            } else if (item.equals("Promedio bajo") & uresis<500) {
                bolsas = "2.5% = 2 bolsas --- 1.5% = 2 bolsas";
            }else if (item.equals("Promedio alto") & uresis>=500) {
                bolsas = "1.5% = 2 bolsas --- 2.5% = 2 bolsas";
            } else if (item.equals("Promedio alto") & uresis<500) {
                bolsas = "1.5% = 2 bolsas --- 2.5% = 2 bolsas";
            }else if (item.equals("Alto")) {
                bolsas = "2.5% = 3 bolsas --- 4.25% = 1 bolsa";
            } else {
                bolsas = "";
            }
            return bolsas;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public String funcionCalcularDialisisAutomatizada(float uresis){
        try {
            String item = binding.TipoTransportadorAutoComTxtView.getText().toString();
            String bolsas;
            if (item.equals("Bajo")) {
                bolsas = "1.5% = 4 bolsas";
            } else if (item.equals("Promedio bajo") & uresis>=500) {
                bolsas = "Promedio bajo mayor a 500";
            } else if (item.equals("Promedio bajo") & uresis<500) {
                bolsas = "Promedio bajo menor a 500";
            }else if (item.equals("Promedio alto") & uresis>=500) {
                bolsas = "promedio alto mayor a 500";
            } else if (item.equals("Promedio alto") & uresis<500) {
                bolsas = "promedio alto menor a 500";
            }else if (item.equals("Alto")& uresis<500) {
                bolsas = "Alto menor a 500";
            }else if (item.equals("Alto") & uresis>=500) {
                bolsas = "Alto mayor a 500";
            } else {
                bolsas = "";
            }
            return bolsas;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void funcionCalcular() {

        int age = Integer.parseInt(String.valueOf(binding.ageTxtinp.getText()));
        int height = Integer.parseInt(String.valueOf(binding.heightTxtinp.getText()));
        float weight = Float.parseFloat(String.valueOf(binding.weightTxtinp.getText()));
        float superficieCorporal = funcionSuperficieCorporal(height,weight);
        float uresis = Float.parseFloat(String.valueOf(binding.uresisTxtinp.getText()));
        String itemTipoTranspor = binding.TipoTransportadorAutoComTxtView.getText().toString();
        String volumen, bolsas;
        volumen = funcionCalcularVolumenDialisisPeritoneal(funcionSuperficieCorporal(height, weight));
        bolsas = funcionCalcularDialisisPeritoneal(uresis);

        if(!binding.ageTxtinp.getText().toString().isEmpty()
                ||!binding.heightTxtinp.getText().toString().isEmpty()
                ||!binding.weightTxtinp.getText().toString().isEmpty()
                ||!binding.uresisTxtinp.getText().toString().isEmpty())
        {
            Intent secondIntent = new Intent(MainActivity.this, SecondActivity.class);
            secondIntent.putExtra("edad", age);
            secondIntent.putExtra("altura", height);
            secondIntent.putExtra("peso", weight);
            secondIntent.putExtra("uresis", uresis);
            secondIntent.putExtra("superficieCorporal", superficieCorporal);
            secondIntent.putExtra("tipoTransportador", itemTipoTranspor);
            secondIntent.putExtra("volumen", volumen);
            secondIntent.putExtra("bolsas", bolsas);
            startActivity(secondIntent);
        }else{
            Toast.makeText(this, "No pueden quedar campos vacios", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
        switch (Item.getItemId()){
            case R.id.nav_home:
                break;
            case  R.id.nav_one:
                Intent secondIntent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(secondIntent);
                break;

            case  R.id.nav_two:
                Intent thirdIntent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(thirdIntent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}