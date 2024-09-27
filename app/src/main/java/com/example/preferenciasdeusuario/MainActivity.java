package com.example.preferenciasdeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputAltura;
    private EditText inputPeso;
    private Button btnSalvar;
    private TextView textImc, textPeso, textAltura;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputAltura = findViewById(R.id.inputAltura);
        inputPeso = findViewById(R.id.inputPeso);
        btnSalvar = findViewById(R.id.btnSalvar);
        textImc = findViewById(R.id.textImc);
        textPeso = findViewById(R.id.textPeso);
        textAltura = findViewById(R.id.textAltura);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = preferences.edit();

                String stringAltura = inputAltura.getText().toString();
                String stringPeso = inputPeso.getText().toString();

                if(stringAltura.equals("") || stringPeso.equals("") || !isConvertibleToFloat(stringAltura) || !isConvertibleToFloat(stringPeso)) {
                    Toast.makeText(getApplicationContext(), "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
                    return;
                }

                editor.putString("altura", stringAltura);
                editor.putString("peso", stringPeso);
                editor.commit();

                Float altura = convertStringToFloat(stringAltura);
                Float peso = convertStringToFloat(stringPeso);

                textImc.setText("Seu IMC é: " + calcImc(peso, altura).toString());
                textAltura.setText("Sua altura: " + stringAltura + "m");
                textPeso.setText("Seu peso: " + stringPeso + "kg");
            }
        });

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

        if(preferences.contains("altura") && preferences.contains("peso")) {
            String stringAltura = preferences.getString("altura", "altura nao definida");
            String stringPeso = preferences.getString("peso", "peso nao definido");
            if (!isConvertibleToFloat(stringAltura) || !isConvertibleToFloat(stringPeso)) {
                Toast.makeText(getApplicationContext(), "Erro no calculo do IMC", Toast.LENGTH_SHORT).show();
                return;
            }
            Float altura = convertStringToFloat(stringAltura);
            Float peso = convertStringToFloat(stringPeso);
            textImc.setText("Seu IMC é: " + calcImc(peso, altura).toString());
            textAltura.setText("Sua altura: " + stringAltura + "m");
            textPeso.setText("Seu peso: " + stringPeso + "kg");
        }
    }

    public Float calcImc(Float peso, Float altura) {
        return peso / (altura * altura);
    }

    public static boolean isConvertibleToFloat(String input) {
        String formattedInput = input.replace(",", ".");
        try {
            Float.parseFloat(formattedInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Float convertStringToFloat(String input) {
        input = input.replace(",", ".");
        return Float.parseFloat(input);
    }

}