package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;

public class Encuesta extends AppCompatActivity {

    RadioButton btnSiDeporte,btnNoDeporte, btnSiTodosLosDias, btnNotodoslosdia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        this.setTitle("Cuestionario");

        inicializar();
    }

    private void inicializar() {

        btnSiDeporte = findViewById(R.id.btnSiDeporte);
        btnNoDeporte = findViewById(R.id.btnNoDeporte);
        btnSiTodosLosDias = findViewById(R.id.btnTodosLosDias);
        btnNotodoslosdia = findViewById(R.id.btnNotodoslosdias);

    }

    public void siguienteEn(View view) {

        validar();

    }

    private void validar() {
        int validar = 0;

        if (validar == 0){
            String action = "";
            if (btnSiDeporte.isChecked()){
                validar++;
                action = "si";
            }
            if (btnNoDeporte.isChecked()){
                validar++;
                action = "no";
            }
            if (validar == 2){
                Intent intent = new Intent(Encuesta.this, EncuestaDos.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Falta respuesta ", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
