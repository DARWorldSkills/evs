package com.davidpopayan.sena.evs.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Datos;

public class Encuesta extends AppCompatActivity {

    RadioButton btnSiDeporte,btnNoDeporte, btnSiTodosLosDias, btnNotodoslosdia;
    int cero = 0, uno =1 ,dos = 2,  cuatro = 4, seis =6;
    public static int tmp, tmp2;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        this.setTitle("Cuestionario");
        activity = this;
        inicializar();
    }

    //Referenciamos los campos del layout
    private void inicializar() {

        btnSiDeporte = findViewById(R.id.btnSiDeporte);
        btnNoDeporte = findViewById(R.id.btnNoDeporte);
        btnSiTodosLosDias = findViewById(R.id.btnTodosLosDias);
        btnNotodoslosdia = findViewById(R.id.btnNotodoslosdias);

    }

    //Evento del boton siguiente
    public void siguienteEn(View view) {

        resultadoPersona();
        validar();

    }

    //Asignar valores a cada selector de la encuesta
    private void resultadoPersona() {

        if (btnSiDeporte.isChecked()){
            tmp = cero;
            //Toast.makeText(this, ""+tmp, Toast.LENGTH_SHORT).show();
        }
        else if (btnNoDeporte.isChecked()){
            tmp = dos;
            //Toast.makeText(this, ""+dos, Toast.LENGTH_SHORT).show();
        }
        else if (btnSiTodosLosDias.isChecked()){
            tmp2 = cero;
            //Toast.makeText(this, "" +cero, Toast.LENGTH_SHORT).show();

        }
        else if (btnNotodoslosdia.isChecked()){
            tmp2 = uno;
            //Toast.makeText(this, ""+ uno, Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo que nos ayuda a validar que no falte ninguna respuesta
    private void validar() {
        int validar = 0;

        if (validar == 0){
            String action = "";
            if (btnSiDeporte.isChecked()){
                validar++;
                action = "Si";
            }
            if (btnNoDeporte.isChecked()){
                validar++;
                action = "No";
            }
            if (validar == 1){
                Intent intent = new Intent(Encuesta.this, EncuestaDos.class);
                inputData();
                startActivity(intent);
            }else {
                Toast.makeText(this, "Falta Respuesta ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void inputData() {
        Datos datos = MenuP.datos;
        if (btnSiDeporte.isChecked()){
            datos.setRealizarActividadFisicaD("SI");
        }else {
            datos.setRealizarActividadFisicaD("NO");
        }

        if (btnSiTodosLosDias.isChecked()){
            datos.setFrecuenciaVerdurasFrutas("SI");
        }else {
            datos.setFrecuenciaVerdurasFrutas("NO");
        }
        MenuP.datos = datos;
    }
}
