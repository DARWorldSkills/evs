package com.davidpopayan.sena.evs.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.davidpopayan.sena.evs.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Resultados extends AppCompatActivity {

    //Declaracion de variables

    TextView txtImcFinal, txtResultadoImc, txtPresionAF, txtEstadoPresionA;
    double im, res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        this.setTitle("Resultados del paciente");

        //Creamos Metodos
        inicializar();
        obtenerImc();
        obtenerResultadoImc();
        obtenerPresionArterial();
        estadoPresionA();
    }

    //Obtenemos de la clase datosPersonales el dato del estado de la presion arterial de la persona
    private void estadoPresionA() {
        txtEstadoPresionA.setText(datosPersonales.tmp2);
    }

    //Obtenemos el resultado de la presion arterial que se calcula en los datos personales
    private void obtenerPresionArterial() {

        res = datosPersonales.res;
        res = Math.round(res * 100) / 100d;
        txtPresionAF.setText(Double.toString(res));
    }

    //Vemos el estado de la persona
    private void obtenerResultadoImc() {
        txtResultadoImc.setText(datosPersonales.tmp1);
    }

    //Obtenemos de la clase datosPersonales el IMC de la persona
    private void obtenerImc() {
        im = datosPersonales.imc;
        im = Math.round(im * 100) / 100d;
        txtImcFinal.setText(Double.toString(im));
    }

    //Referenciamos Todos los campos del layout
    private void inicializar() {

        txtImcFinal = findViewById(R.id.txtImcFinal);
        txtResultadoImc = findViewById(R.id.txtResultadoImc);
        txtPresionAF = findViewById(R.id.txtPresionA);
        txtEstadoPresionA = findViewById(R.id.txtEstadoPresionA);
    }

}
