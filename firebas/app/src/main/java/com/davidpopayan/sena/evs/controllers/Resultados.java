package com.davidpopayan.sena.evs.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.ManagerDB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Resultados extends AppCompatActivity {

    //Declaracion de variables
    public static  ImageView imgImcUno, imgArterial, imgDiabetes, imgCardio;
    double im, res;
    double sitolica, diastolica;
    int riesgoDiabetes, riesgoCardioVascular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        this.setTitle("Resultados del paciente");

        //Creamos Metodos
        inicializar();
        obtenerImc();
        obtenerPresionArterial();
        riesgoDiabetesF();
        calcularRiesgoCardiovascular();
        RiesgoCardiovascularEnPorcentaje();
        inputData();
        RiesgoDiabetes();
        DetalleDeImc();
        DetallePresionArterial();
        DetalleDiabetes();
        DetalleRiesgoCardiovascular();

    }

    //Cambiamos la imagen dependiendo al tipo de riesgo cardiovascular que presenta
    private void DetalleRiesgoCardiovascular() {


    }

    //Cambiamos la imagen para ver que riesgo tiene de tener diabetes
    private void DetalleDiabetes() {

        if (riesgoDiabetes > 12){
            Glide.with(this).load((R.drawable.diabtestres)).crossFade().into(imgDiabetes);
        }
        else if (riesgoDiabetes >= 10){
            Glide.with(this).load((R.drawable.diabetesdos)).crossFade().into(imgDiabetes);
        }
        else if (riesgoDiabetes > 8){

            Glide.with(this).load((R.drawable.diabetesuno)).crossFade().into(imgDiabetes);
        }else {

            Glide.with(this).load((R.drawable.diabetesuno)).crossFade().into(imgDiabetes);
        }
    }


    //Cambiamos la imagenes de la presion arterial
    private void DetallePresionArterial() {
        if (sitolica <100 && diastolica <80){
            Glide.with(this).load((R.drawable.a1)).crossFade().into(imgArterial);

        }else if (sitolica >= 100 && diastolica >= 80 && diastolica <=90 && sitolica <129){
            Glide.with(this).load((R.drawable.a2)).crossFade().into(imgArterial);
        }
        else if (sitolica >= 130 && diastolica >=80 && sitolica <=139 && diastolica <=90){
            Glide.with(this).load((R.drawable.a3)).crossFade().into(imgArterial);
        }
        else if (sitolica >= 140 && diastolica >=90){

            Glide.with(this).load((R.drawable.a4)).crossFade().into(imgArterial);

        }
    }

    //Cambiar la imagen del IMC
    private void DetalleDeImc() {

        if (im <= 18.5){
            Glide.with(this).load((R.drawable.imcuno)).crossFade().into(imgImcUno);
        }else if (im >18.5 && im < 25){
            Glide.with(this).load((R.drawable.imcdos)).crossFade().into(imgImcUno);
        }else if (im >25 && im < 30){

            Glide.with(this).load((R.drawable.imctercero)).crossFade().into(imgImcUno);
        }else if (im >30 && im < 40){

            Glide.with(this).load((R.drawable.imccuarto)).crossFade().into(imgImcUno);
        }else if (im >40 ){

            Glide.with(this).load((R.drawable.imccuarto)).crossFade().into(imgImcUno);
        }

        finalizarActivities();
    }

    private void finalizarActivities() {
        PrimerForm.activity.finish();
        Encuesta.activity.finish();
        EncuestaDos.activity.finish();
        EncuestaTres.activity.finish();
        datosPersonales.activity.finish();

    }

    //Calculamos el riego que puede tener la persona
    private void RiesgoDiabetes() {
        if (riesgoDiabetes > 12){

            Toast.makeText(this, "Riesgo Alto", Toast.LENGTH_SHORT).show();

        }
        else if (riesgoDiabetes >= 10){
            Toast.makeText(this, "Riesgo Moderado", Toast.LENGTH_SHORT).show();



        }
        else if (riesgoDiabetes > 8){
            Toast.makeText(this, "Riesgo Bajo", Toast.LENGTH_SHORT).show();


        }
    }

    //Vemos en que porcentaje esta la persona para tener Riesgos cardiovasculares
    private void RiesgoCardiovascularEnPorcentaje()     {

        if (MenuP.datos.getGenero().equals("Masculino")){

            if (riesgoCardioVascular < 0){
                Glide.with(this).load((R.drawable.cardiouno)).crossFade().into(imgCardio);
            }
            if (riesgoCardioVascular >= 0 && riesgoCardioVascular < 6){
                Toast.makeText(this, "10% de que sufras riesgos", Toast.LENGTH_SHORT).show();
                Glide.with(this).load((R.drawable.cardiodos)).crossFade().into(imgCardio);
            }
            if (riesgoCardioVascular >=6 && riesgoCardioVascular < 9){
                Toast.makeText(this, "20% de que sufras riesgos", Toast.LENGTH_SHORT).show();
                Glide.with(this).load((R.drawable.cardiotres)).crossFade().into(imgCardio);
            }
            if (riesgoCardioVascular >= 9 && riesgoCardioVascular < 15){
                Toast.makeText(this, "53% de que sufras riesgos", Toast.LENGTH_SHORT).show();
                Glide.with(this).load((R.drawable.cardiocuatro)).crossFade().into(imgCardio);
            }

        }else {
            if (riesgoCardioVascular < 0){
                Toast.makeText(this, "2% de que sufras riesgos", Toast.LENGTH_SHORT).show();

                Glide.with(this).load((R.drawable.cardiouno)).crossFade().into(imgCardio);
            }
            if (riesgoCardioVascular >= 0 && riesgoCardioVascular < 6){
                Toast.makeText(this, "5% de que sufras riesgos", Toast.LENGTH_SHORT).show();


                Glide.with(this).load((R.drawable.cardiodos)).crossFade().into(imgCardio);
            }
            if (riesgoCardioVascular >=6 && riesgoCardioVascular < 9){
                Toast.makeText(this, "8% de que sufras riesgos", Toast.LENGTH_SHORT).show();

                Glide.with(this).load((R.drawable.cardiotres)).crossFade().into(imgCardio);
            }
            if (riesgoCardioVascular >= 9 && riesgoCardioVascular < 15){
                Toast.makeText(this, "20% de que sufras riesgos", Toast.LENGTH_SHORT).show();

                Glide.with(this).load((R.drawable.cardiocuatro)).crossFade().into(imgCardio);
            }

        }
    }

    //Generamos la operacion para obtener el puntaje y saber cuanto es el porcentaje del  riesgo
    private void calcularRiesgoCardiovascular() {
        riesgoCardioVascular = PrimerForm.edadPuntaje + datosPersonales.puntajePresionS + EncuestaTres.tmpFuma + EncuestaTres.tmpDiabe;

    }

    //Hacemos la sumatoria te todos los puntos de las encuentas y dependiendo a eso vemos que tan grave es el riesgo
    private void riesgoDiabetesF() {

        riesgoDiabetes = PrimerForm.edadPuntaje + datosPersonales.puntaje + datosPersonales.puntajePABD + Encuesta.tmp + Encuesta.tmp2
                + EncuestaDos.tmp2 + EncuestaDos.tmp3 + EncuestaTres.tmp3;

    }

    //Obtenemos el resultado de la presion arterial que se calcula en los datos personales
    private void obtenerPresionArterial() {

        sitolica = datosPersonales.sist;
        diastolica = datosPersonales.diast;
        Log.e("sistolica", String.valueOf(sitolica));
        Log.e("diastolica", String.valueOf(diastolica));
    }

    //Obtenemos de la clase datosPersonales el IMC de la persona
    private void obtenerImc() {
        im = datosPersonales.imc;
        im = Math.round(im * 100) / 100d;

    }

    //Referenciamos Todos los campos del layout
    private void inicializar() {

        imgImcUno = findViewById(R.id.imgImcUno);
        imgArterial = findViewById(R.id.imgArterial);
        imgDiabetes = findViewById(R.id.imgDiabetes);
        imgCardio = findViewById(R.id.imgCardio);
    }

    //Cargamos los datos
    private void inputData() {
        ManagerDB managerDB = new ManagerDB(this);

        MenuP.datos.setRealiza(MenuP.usuario.getNombre());
        Toast.makeText(this, "Se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        switch (MenuP.ingresar){
            case 0:


                managerDB.updateData(MenuP.datos);
                break;

            case 1:

                managerDB.inputData(MenuP.datos);
                break;

            case 2:
                managerDB.inputData(MenuP.datos);
                break;
        }
    }

    //Evento del boton que te devuelve al menu principal
    public void finalizar(View view) {
        MenuP.menuP.generarAlerta();
        finish();
    }
}
