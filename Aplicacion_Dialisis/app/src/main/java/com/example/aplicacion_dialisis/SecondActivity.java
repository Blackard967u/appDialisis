package com.example.aplicacion_dialisis;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.aplicacion_dialisis.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bundle = getIntent().getExtras();
        String age = String.valueOf(bundle.getInt("edad"));
        String height = String.valueOf(bundle.getInt("altura"));
        String weight = String.valueOf(bundle.getFloat("peso"));
        String uresis = String.valueOf(bundle.getFloat("uresis"));
        String superficieCorporal = String.valueOf(bundle.getFloat("superficieCorporal"));
        String tipoTransportador = String.valueOf(bundle.getString("tipoTransportador"));
        String volumen = bundle.getString("volumen");
        String bolsas = bundle.getString("bolsas");


        binding.rstEdad.setText(age);
        binding.rstHeight.setText(height);
        binding.rstWeight.setText(weight);
        binding.rstUresis.setText(uresis);
        binding.rstSuperficieCorp.setText(superficieCorporal);
        binding.rstTipoTransportador.setText(tipoTransportador);
        binding.rstDialisisManualVolumen.setText(volumen);
        binding.rstDialisisManualBolsas.setText(bolsas);


        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }

}