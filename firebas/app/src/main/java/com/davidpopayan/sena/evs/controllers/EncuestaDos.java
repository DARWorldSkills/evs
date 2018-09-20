package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;

public class EncuestaDos extends AppCompatActivity {

    //Variables
    RadioButton btnSiMedicamentos, btnNoMedicamentos, btnSiGlucosa, btnNoGlucosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_dos);
        this.setTitle("Cuestionario");

        //Creamos los metodos
        inicializar();
    }

    //Referenciamos los elements que vamos a utilizar
    private void inicializar() {
        btnSiMedicamentos = findViewById(R.id.btnSiMedicamentos);
        btnNoMedicamentos = findViewById(R.id.btnNoMedicamentos);
        btnSiGlucosa = findViewById(R.id.btnSiGlucosa);
        btnNoGlucosa = findViewById(R.id.btnNoGlucosa);
    }

    //Evento del boton de siguiente
    public void siguientetres(View view) {
        validar();
    }

    //Validamos que los campos no esten vacios para poder seguir a la siguiente interfaz
    private void validar() {
        int validar = 0;

        if (validar == 0){
            if (btnSiMedicamentos.isChecked()){
                validar++;
            }
            if (btnNoMedicamentos.isChecked()){
                validar++;
            }
            if (validar == 1){
                Intent intent = new Intent(EncuestaDos.this, EncuestaTres.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Falta Respuesta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
