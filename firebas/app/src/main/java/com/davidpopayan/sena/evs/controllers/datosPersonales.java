
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

    EditText txtAltura, txtPeso, txtIMC, txtPABD, txtPAS, txtPd,txtPresionArterial;
    Button btnCalcular, btnIr, btnCalcularParterial;
    double altura,peso,pesof, sistolica, diastolica;
    int resultado;
    int cero = 0, uno =1 ,dos = 2, tres = 3, cuatro = 4,cinco = 5, seis =6;
    public static int puntaje, puntajePABD;
    public static double imc;
    public static String tmp1, tmp2;
    public static double res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        this.setTitle("Datos Personales");
        inicializar();
        escuchar();

    }

    //Evento de los botones
    private void escuchar() {
        btnCalcular.setOnClickListener(this);
        btnIr.setOnClickListener(this);
        btnCalcularParterial.setOnClickListener(this);
    }

    //Referenciamos todos los elementos que vamos a utilizar
    private void inicializar() {
        btnCalcular = findViewById(R.id.btnCalcular);
        btnIr = findViewById(R.id.btnSiguiente4);
        btnCalcularParterial = findViewById(R.id.btnCalcularParterial);
        ////////////////////////////////////////////////
        txtAltura = findViewById(R.id.txtAltura);
        txtPeso = findViewById(R.id.txtPeso);
        txtIMC = findViewById(R.id.txtIMC);
        txtPABD = findViewById(R.id.txtPABD);
        txtPAS = findViewById(R.id.txtPAS);
        txtPd = findViewById(R.id.txtPd);
        txtPresionArterial = findViewById(R.id.txtPresionArterial);
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

            case R.id.btnCalcularParterial:
                calcularPrecionArterial();
                clasificacionPrecionArterial();
                break;
            case R.id.btnSiguiente4:
                asignarPuntajes();
                validar();
                break;
        }
    }

    //Asignamos puntajes dependiendo el IMC de la persona y el perimetro abdominal
    private void asignarPuntajes() {
        if (calcularIMC() < 25){
            puntaje = cero;
            Toast.makeText(this, ""+puntaje, Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 25 && calcularIMC() < 30){
            puntaje = uno;
            Toast.makeText(this, ""+puntaje, Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 30){
            puntaje = tres;
            Toast.makeText(this, ""+ puntaje, Toast.LENGTH_SHORT).show();
        }
        if (MenuP.datos.getGenero().equals("M")){
           if (res < 94){
               puntajePABD = cero;
               Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
           }
           else if (res > 94 && res <102){
               puntajePABD = tres;
               Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
           }
           else if (res > 102){
               puntajePABD = cuatro;
               Toast.makeText(this, ""+ puntajePABD, Toast.LENGTH_SHORT).show();
           }
        }else {
            if (res < 80){
                puntajePABD = cero;
                Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
            }
            else if (res > 80 && res < 88){
                puntajePABD = tres;
                Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
            }
            else if (res > 88){
                puntajePABD = cuatro;
                Toast.makeText(this, ""+ puntajePABD, Toast.LENGTH_SHORT).show();
            }

        }
    }

    //Nivel de precion arterial de la persona
    private void clasificacionPrecionArterial() {
        if ( resultado < 70){
            tmp2 = "Normal bajo";
            Toast.makeText(this, "Normal bajo", Toast.LENGTH_SHORT).show();
        }
        else if (resultado >70 && resultado < 105){
            tmp2 = "Normal alto";
            Toast.makeText(this, "Normal alto", Toast.LENGTH_SHORT).show();
        }
        else if (resultado >105 && resultado < 300){
            tmp2 = "Limite Alto";
            Toast.makeText(this, "Limite Alto", Toast.LENGTH_SHORT).show();
        }
        else if (resultado == 0){
            tmp2 = "No puedes tener la presión arterial en 0";
            Toast.makeText(this, "No puedes tener la presión arterial en 0", Toast.LENGTH_SHORT).show();
        }


    }

    //Formula para calcular la precionArterial
    private void calcularPrecionArterial() {

        sistolica = Double.parseDouble(txtPAS.getText().toString());
        diastolica = Double.parseDouble(txtPd.getText().toString());

        float tmp1 = (float) sistolica;
        float tmp2 = (float) diastolica;

        res = (tmp1 + (2*tmp2))/3;

        resultado = (int) (sistolica / diastolica);
        txtPresionArterial.setText(Double.toString(res));

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
            tmp1 = "Delgadez Severa";
            Toast.makeText(this, "Delgadez Severa", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 16 && calcularIMC() < 17){
            tmp1 = "Delgadez moderada";
            Toast.makeText(this, "Delgadez moderada", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 17 && calcularIMC() < 18.50){
            tmp1 = "Delgadez aceptable";
            Toast.makeText(this, "Delgadez aceptable", Toast.LENGTH_SHORT).show();
        }
        else  if (calcularIMC() > 18.50 && calcularIMC() < 25){
            tmp1 = "Peso Normal";
            Toast.makeText(this, "Peso Normal", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() >25 && calcularIMC() < 30){
            tmp1 = "Sobrepeso";
            Toast.makeText(this, "Sobrepeso", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 30 && calcularIMC() < 35){
            tmp1 = "Obeso: Tipo I";
            Toast.makeText(this, "Obeso: Tipo I", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 35 && calcularIMC() < 40){
            tmp1 = "Obeso: Tipo II";
            Toast.makeText(this, "Obeso: Tipo II", Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() >40){
            tmp1 = "Obeso: Tipo III";
            Toast.makeText(this, "Obeso: Tipo III", Toast.LENGTH_SHORT).show();
        }
    }


}
