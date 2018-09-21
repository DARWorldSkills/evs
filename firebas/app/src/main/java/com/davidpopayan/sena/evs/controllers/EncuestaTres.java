package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;

public class EncuestaTres extends AppCompatActivity {

    //Variables
    RadioButton btnSiDiabetesPadres, btnNinguno, btnSiDiabetesAbuelo, btnSiDiabetes, btnNoDiabetes, btnSiFumas, btnNoFumas;
    int cero = 0, uno =1 ,dos = 2, tres = 3, cuatro = 4,cinco = 5, seis =6;
    public static  int tmp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_tres);
        this.setTitle("Cuestionario");

        //Creamos Metodos
        inicializar();

    }

    //Asignamos puntaje a cada pregunta de la encueta
    private void resuladosPersonaE3() {
        if (btnSiDiabetesPadres.isChecked()){
            tmp3 = cinco;
            Toast.makeText(this, ""+cinco, Toast.LENGTH_SHORT).show();
        }
        else if (btnSiDiabetesAbuelo.isChecked()){
            tmp3 = tres;
            Toast.makeText(this, ""+tres, Toast.LENGTH_SHORT).show();
        }
        else if (btnNinguno.isChecked()){
            tmp3 = cero;
            Toast.makeText(this, ""+ cero, Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para referenciar todos los elementos del layout
    private void inicializar() {

        btnSiDiabetesPadres = findViewById(R.id.btnSiPadresDiabetes);
        btnNinguno = findViewById(R.id.btnNinguno);
        btnSiDiabetesAbuelo = findViewById(R.id.btnSiDiabetesAbuelo);
        btnSiDiabetes = findViewById(R.id.btnSiDiabetes);
        btnNoDiabetes = findViewById(R.id.btnNoDiabetes);
        btnSiFumas = findViewById(R.id.btnSiFumas);
        btnNoFumas = findViewById(R.id.btnNoFumas);
    }

    //Evento del boton siguiente
    public void resultado(View view) {
        resuladosPersonaE3();
        validar();
    }
    //Validamos que los campos no estén vacios
    private void validar() {
        int validar = 0;

        if (validar == 0){
            if (btnSiDiabetesPadres.isChecked()){
                validar++;
            }
            if (btnNinguno.isChecked()){
                validar++;
            }
            if (btnSiDiabetesAbuelo.isChecked()){
                validar++;

            }
            if (btnSiDiabetes.isChecked()){
                validar++;
            }
            if (btnNoDiabetes.isChecked()){
                validar++;
            }
            if (btnSiFumas.isChecked()){
                validar++;
            }
            if (btnNoFumas.isChecked()){
                validar++;
            }
            if (validar == 3){
                Intent intent = new Intent(EncuestaTres.this, Resultados.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Faltan Respuestas", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
