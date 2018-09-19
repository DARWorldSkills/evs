package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;

import java.text.DecimalFormat;

public class datosPersonales extends AppCompatActivity implements View.OnClickListener{

    EditText txtAltura, txtPeso, txtIMC, txtPABD, txtPAS, txtPd;
    Button btnCalcular, btnIr;
    double altura,peso,pesof;
    public static int Puntaje;
    public static double imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        this.setTitle("Datos Personales");
        inicializar();
        escuchar();
    }

    private void escuchar() {
        btnCalcular.setOnClickListener(this);
        btnIr.setOnClickListener(this);
    }

    //Referenciamos todos los elementos que vamos a utilizar
    private void inicializar() {
        btnCalcular = findViewById(R.id.btnCalcular);
        btnIr = findViewById(R.id.btnSiguiente4);
        ////////////////////////////////////////////////
        txtAltura = findViewById(R.id.txtAltura);
        txtPeso = findViewById(R.id.txtPeso);
        txtIMC = findViewById(R.id.txtIMC);
        txtPABD = findViewById(R.id.txtPABD);
        txtPAS = findViewById(R.id.txtPAS);
        txtPd = findViewById(R.id.txtPd);
    }

    //Validamos que los campos no esten vacios
    private void validar() {
        int valida = 0;


        if (txtAltura.getText().toString().length()>0){
            valida++;
        }else {
            txtAltura.setError("Falta Campo");
        }
        if (txtPeso.getText().toString().length()>0){
            valida++;
        }else {
            txtPeso.setError("Falta Campo");
        }
        if (txtIMC.getText().toString().length()>0){
            valida++;
        }else {
            txtIMC.setError("Falta Campo");
        }
        if (txtPABD.getText().toString().length()>0){
            valida++;
        }else {
            txtPABD.setError("Falta Campo");
        }
        if (txtPAS.getText().toString().length()>0){
            valida++;
        }else {
            txtPAS.setError("Falta Campo");
        }
        if (txtPd.getText().toString().length()>0){
            valida++;
        }else {
            txtPd.setError("Falta Campo");
        }
        if (valida == 6){
            Intent intent = new Intent(datosPersonales.this, Encuesta.class);
            startActivity(intent);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCalcular:

                calcularIMC();
                ClasificacionIMC();
                break;

            case R.id.btnSiguiente4:
                validar();
                break;
        }
    }

    //Formula para calcular el IMC de la persona
    private double calcularIMC() {
        altura = Double.parseDouble(txtAltura.getText().toString());
        peso = Double.parseDouble(txtPeso.getText().toString());
        pesof = altura/100;
        imc = peso/(Math.pow(pesof,2));
        DecimalFormat df = new DecimalFormat("#.00");
        txtIMC.setText(df.format(imc));
        return imc;
    }

    //Validamos en que estado esta la persona
    private void ClasificacionIMC() {
        if (calcularIMC() < 16){
            Toast.makeText(this, "Delgadez Severa", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 16 && calcularIMC() < 17){
            Toast.makeText(this, "Delgadez moderada", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 17 && calcularIMC() < 18.50){
            Toast.makeText(this, "Delgadez aceptable", Toast.LENGTH_SHORT).show();
        }
        else  if (calcularIMC() > 18.50 && calcularIMC() < 25){
            Toast.makeText(this, "Peso Normal", Toast.LENGTH_SHORT).show();
        }

        else if (calcularIMC() >25 && calcularIMC() < 30){
            Toast.makeText(this, "Sobrepeso", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 30 && calcularIMC() < 35){
            Toast.makeText(this, "Obeso: Tipo I", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 35 && calcularIMC() < 40){
            Toast.makeText(this, "Obeso: Tipo II", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() >40){
            Toast.makeText(this, "Obeso: Tipo III", Toast.LENGTH_SHORT).show();
        }
    }


}
