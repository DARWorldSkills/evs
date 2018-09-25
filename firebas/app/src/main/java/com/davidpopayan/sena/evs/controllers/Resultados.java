package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.ManagerDB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Resultados extends AppCompatActivity {

    //Declaracion de variables

    TextView txtImcFinal, txtResultadoImc, txtPresionAF, txtEstadoPresionA, txtPuntajeRiesgoD, txtRiesgoCardiovascular;
    double im, res;
    int riesgoDiabetes, riesgoCardioVascular;

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
        riesgoDiabetesF();
        calcularRiesgoCardiovascular();
        RiesgoCardiovascularEnPorcentaje();
        inputData();
    }

    private void RiesgoCardiovascularEnPorcentaje() {

        if (MenuP.datos.getGenero().equals("M")){
            if (riesgoCardioVascular < 0){
                Toast.makeText(this, "3% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }
            if (riesgoCardioVascular > 0 && riesgoCardioVascular < 6){
                Toast.makeText(this, "10% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }
            if (riesgoCardioVascular >6 && riesgoCardioVascular < 9){
                Toast.makeText(this, "20% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }
            if (riesgoCardioVascular > 9 && riesgoCardioVascular < 15){
                Toast.makeText(this, "53% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }

        }else {
            if (riesgoCardioVascular < 0){
                Toast.makeText(this, "2% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }
            if (riesgoCardioVascular > 0 && riesgoCardioVascular < 6){
                Toast.makeText(this, "5% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }
            if (riesgoCardioVascular >6 && riesgoCardioVascular < 9){
                Toast.makeText(this, "8% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }
            if (riesgoCardioVascular > 9 && riesgoCardioVascular < 15){
                Toast.makeText(this, "20% de que sufras riesgos", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //Generamos la operacion para obtener el puntaje y saber cuanto es el porcentaje del  riesgo

    private void calcularRiesgoCardiovascular() {
        riesgoCardioVascular = PrimerForm.edadPuntaje + datosPersonales.puntajePresionS + EncuestaTres.tmpFuma + EncuestaTres.tmpDiabe;

        txtRiesgoCardiovascular.setText(Integer.toString(riesgoCardioVascular));

    }
    //Hacemos la sumatoria te todos los puntos de las encuentas y dependiendo a eso vemos que tan grave es el riesgo

    private void riesgoDiabetesF() {

        riesgoDiabetes = PrimerForm.edadPuntaje + datosPersonales.puntaje + datosPersonales.puntajePABD + Encuesta.tmp + Encuesta.tmp2
                + EncuestaDos.tmp2 + EncuestaDos.tmp3 + EncuestaTres.tmp3;

        txtPuntajeRiesgoD.setText(Integer.toString(riesgoDiabetes));

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
        txtPuntajeRiesgoD = findViewById(R.id.txtPuntajeRiesgoD);
        txtRiesgoCardiovascular = findViewById(R.id.txtRiesgoCardiovascular);
    }

    private void inputData() {
        ManagerDB managerDB = new ManagerDB(this);

        Toast.makeText(this, "Se ha guardado correctamente"+MenuP.datos.getNumero(), Toast.LENGTH_SHORT).show();
        switch (MenuP.ingresar){
            case 0:

                managerDB.updateData(MenuP.datos);
                break;

            case 1:

                managerDB.inputData(MenuP.datos);
                break;
        }
    }

    public void finalizar(View view) {
        Intent intent = new Intent(Resultados.this,MenuP.class);
        startActivity(intent);
        finish();
    }
}
