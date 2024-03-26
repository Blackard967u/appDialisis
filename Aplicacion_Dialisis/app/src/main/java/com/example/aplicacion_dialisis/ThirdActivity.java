package com.example.aplicacion_dialisis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.aplicacion_dialisis.databinding.ActivitySecondBinding;
import com.example.aplicacion_dialisis.databinding.ActivityThirdBinding;

public class ThirdActivity extends AppCompatActivity {

    ActivityThirdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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


        binding.TipoTransportadorAutoComTxtView.setAdapter(adapter);
        binding.NombresSuperficieCorporalAutoComTxtView.setAdapter(adapterFormulas);
        binding.calcularBtn.setOnClickListener(view -> funcionCalcular());

    }


    public float funcionSuperficieCorporal(float height, float weight) {
        try {
            String item = binding.NombresSuperficieCorporalAutoComTxtView.getText().toString();
            float superficieCorporal;
            if (item.equals("Du Bois and Du Bois")) {
                superficieCorporal = (float) (Math.pow(weight, 0.425) * Math.pow(height, 0.725) * 0.007184);
            } else if (item.equals("Haycock")) {
                superficieCorporal = (float) (Math.pow(weight, 0.5378) * Math.pow(height, 0.3974) * 0.024265);
            } else if (item.equals("Gehan George")) {
                superficieCorporal = (float) (Math.pow(weight, 0.51456) * Math.pow(height, 0.42246) * 0.0235);
            } else if (item.equals("Mosteller")) {
                superficieCorporal = (float) (Math.pow((weight * height / 3600), 0.5));
            } else {
                superficieCorporal = -1;
            }
            return superficieCorporal;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public String funcionCalcularVolumen(float superfieCorporal) {
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

    private void funcionCalcular() {
        String volumen, bolsas;
        float height = Float.parseFloat(String.valueOf(binding.heightTxtinp.getText())),
                weight = Float.parseFloat(String.valueOf(binding.weightTxtinp.getText()));
        float uresis = Float.parseFloat(String.valueOf(binding.uresisTxtinp.getText()));
        volumen = funcionCalcularVolumen(funcionSuperficieCorporal(height, weight));
        bolsas = funcionCalcularDialisisPeritoneal(uresis);

        Toast.makeText(this, "Volumen: "+volumen, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Bolsas: "+bolsas, Toast.LENGTH_SHORT).show();
    }


}