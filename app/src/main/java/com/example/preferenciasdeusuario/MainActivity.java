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

    private EditText inputNome;
    private Button btnSalvar;
    private TextView textResultado;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNome = findViewById(R.id.inputNome);
        btnSalvar = findViewById(R.id.btnSalvar);
        textResultado = findViewById(R.id.textResultado);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = preferences.edit();

                if(inputNome.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Preencha o nome", Toast.LENGTH_SHORT).show();
                    return;
                }

                String nome = inputNome.getText().toString();

                editor.putString("nome", nome);
                editor.commit();
                textResultado.setText("Olá, " + nome);
            }
        });

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

        if(preferences.contains("nome")) {
            String nome = preferences.getString("nome", "usuario nao definido SEXO");
            textResultado.setText("Olá, " + nome);
        }



    }
}