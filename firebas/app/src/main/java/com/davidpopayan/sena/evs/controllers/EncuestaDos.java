package com.davidpopayan.sena.evs.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Datos;

public class EncuestaDos extends AppCompatActivity {

    //Variables
    RadioButton btnSiMedicamentos, btnNoMedicamentos, btnSiGlucosa, btnNoGlucosa;
    int cero = 0, uno =1 ,dos = 2,  cuatro = 4,cinco = 5, seis =6;
    public static int tmp2, tmp3;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_dos);
        this.setTitle("Cuestionario");
        activity=this;
        //Creamos los metodos
        inicializar();

    }

    //Puntaje de la persona segun la encuesta
    private void resultadosPersonaE2() {

        if (btnSiGlucosa.isChecked()){
            tmp2 = cinco;
            Toast.makeText(this, ""+ cinco, Toast.LENGTH_SHORT).show();
        }
        else if (btnNoGlucosa.isChecked()){
            tmp2 = cero;
            Toast.makeText(this, ""+ cero, Toast.LENGTH_SHORT).show();
        }
        else if (btnSiMedicamentos.isChecked()){
            tmp3 = dos;
            Toast.makeText(this, ""+ dos, Toast.LENGTH_SHORT).show();
        }
        else if (btnNoMedicamentos.isChecked()){
            tmp3 = cero;
            Toast.makeText(this, ""+ cero, Toast.LENGTH_SHORT).show();
        }
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
        resultadosPersonaE2();
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
                inputData();
                startActivity(intent);
            }else {
                Toast.makeText(this, "Falta Respuesta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void inputData() {
        Datos datos = MenuP.datos;
        if (btnSiMedicamentos.isChecked()){
            datos.setMedicamentosHipertension("SI");
        }else {
            datos.setMedicamentosHipertension("NO");
        }

        if (btnSiGlucosa.isChecked()){
            datos.setGlucosaAlta("SI");
        }else {
            datos.setGlucosaAlta("NO");
        }
        MenuP.datos= datos;
    }
}
