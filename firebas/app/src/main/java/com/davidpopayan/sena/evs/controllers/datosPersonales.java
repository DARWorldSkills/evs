
package com.davidpopayan.sena.evs.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Datos;

import java.text.DecimalFormat;

public class datosPersonales extends AppCompatActivity implements View.OnClickListener{

    EditText txtAltura, txtPeso, txtIMC, txtPABD, txtPAS, txtPd,txtPresionArterial,txtGlucometria;
    Button btnCalcular, btnIr, btnCalcularParterial;
    double altura,peso,pesof, sistolica, diastolica, pas;
    int resultado;
    int cero = 0, uno =1 ,dos = 2, tres = 3, cuatro = 4,cinco = 5, seis =6;
    public static int puntaje, puntajePABD, puntajePresionS;
    public static double imc, glucometria;
    public static String tmp1, tmp2;
    public static String glucosa;
    public static double res;
    public static double sist;
    public static double diast;
    int validacion=1;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        this.setTitle("Datos Personales");
        activity = this;
        inicializar();
        escuchar();


    }

    //Asiganamos puntajes dependiendo la presion sistoica del pasiente
    private void puntajePresionSistolica() {

        if (sistolica < 120){
            puntajePresionS = cero;
            //Toast.makeText(this, ""+puntajePresionS, Toast.LENGTH_SHORT).show();
        }
        else if (sistolica >120 && sistolica < 129){
            puntajePresionS = cero;
            //Toast.makeText(this, ""+puntajePresionS, Toast.LENGTH_SHORT).show();
        }
        else if (sistolica >129 &&  sistolica <139){
            puntajePresionS = uno;
            //Toast.makeText(this, ""+puntajePresionS, Toast.LENGTH_SHORT).show();
        }
        else if (sistolica >139 && sistolica < 159){
            puntajePresionS = dos;
            //Toast.makeText(this, ""+ puntajePresionS, Toast.LENGTH_SHORT).show();
        }
        else if (sistolica > 160){
            puntajePresionS = tres;
            //Toast.makeText(this, ""+puntajePresionS, Toast.LENGTH_SHORT).show();
        }
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
        txtGlucometria = findViewById(R.id.txtGlucometria);

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
        if (txtGlucometria.getText().toString().length()>0){
            valida++;
        }else {
            txtGlucometria.setError("Falta Campo");
        }


        if (valida == 7 && validacion==0){
            inputData();
            Intent intent = new Intent(datosPersonales.this, Encuesta.class);
            startActivity(intent);

        }
        if (validacion>0){
            Toast.makeText(activity, "Por favor calcule la presión arterial", Toast.LENGTH_SHORT).show();
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
                validacion=0;
                obtenerDatoDiastolica();
                validacionDeTipoPresionArterial();
                validarNumeroPAS();


                
                //calcularPrecionArterial();
                //clasificacionPrecionArterial();
                break;
            case R.id.btnSiguiente4:
                calcularGlucometria();
                asignarPuntajes();
                puntajePresionSistolica();
                validar();


                break;
        }
    }

    //Validamos que la presion arterial sitolica no sea mayor a 180
    private void validarNumeroPAS() {

        if (sist >= 200) {
            validacion++;
            txtPAS.setError("No puede ser mayor de 200");
        } else if (sist < 100 && diast > 80) {
            validacion++;
            txtPd.setError("No puede ser mayor a 80");


        } else if (sist >= 100 && sist < 120 && diast > 80) {
            validacion++;
            txtPd.setError("No puede ser mayor a 80");
        } else if (sist >= 100 && sist < 120 && diast < 70) {
            validacion++;
            txtPd.setError("No puede ser Menor a 70");


        } else if (sist >= 120 && sist < 140 && diast > 90) {
            validacion++;
            txtPd.setError("No puede ser Mayor a 90");
        } else if (sist >= 120 && sist < 140 && diast < 80){
            validacion++;
            txtPd.setError("No puede ser Menor a 80");


        }else if (sist >=140 && diast >200) {
            validacion++;
            txtPd.setError("No puede ser mayor a 200");

        }else if (sist >=140 && diast <90){
            validacion++;
            txtPd.setError("No puede ser Menor a 90");
        }
    }

    //Vemos en que tipo de estado esta la persona
    private void validacionDeTipoPresionArterial() {
        
        if (sist <=100 && diast <=80){
            Toast.makeText(activity, "Hipotension", Toast.LENGTH_SHORT).show();
        }else if (sist > 100 && diast >= 80 && diast <=90&& sist <=129){
            Toast.makeText(activity, "Presion Normal", Toast.LENGTH_SHORT).show();
        }
        else if (sist >= 130 && diast >=80 && sist <=139 && diast <=90){
            Toast.makeText(activity, "Pre-Hipertension", Toast.LENGTH_SHORT).show();
        }
        else if (sist >= 140 && diast >=90){
            Toast.makeText(activity, "Presion Arterial Alta", Toast.LENGTH_SHORT).show();
        }
    }


    //Obtenemos el valor que el usuario ingresa en el campo de la presión sistolica
    private void obtenerDatoDiastolica() {
        if (txtPAS.getText().toString().length()>0) {
            if (txtPd.getText().toString().length() > 0) {

                try {
                    sist = Double.parseDouble(txtPAS.getText().toString());
                    diast = Double.parseDouble(txtPd.getText().toString());
                } catch (Exception e) {
                    validacion++;
                    Toast.makeText(activity, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }

            } else {
                txtPd.setError("Necesitas esté campo");
            }

        }else {
            txtPAS.setError("Necesitas este campo");
        }
    }

    //Asignamos puntajes dependiendo el IMC de la persona y el perimetro abdominal
    private void asignarPuntajes() {
        if (calcularIMC() < 25){
            puntaje = cero;
            //Toast.makeText(this, ""+puntaje, Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 25 && calcularIMC() < 30){
            puntaje = uno;
            //Toast.makeText(this, ""+puntaje, Toast.LENGTH_SHORT).show();
        }
        else if (calcularIMC() > 30){
            puntaje = tres;
            //Toast.makeText(this, ""+ puntaje, Toast.LENGTH_SHORT).show();
        }
        if (MenuP.datos.getGenero().equals("M")){
           if (res < 94){
               puntajePABD = cero;
               //Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
           }
           else if (res > 94 && res <102){
               puntajePABD = tres;
               //Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
           }
           else if (res > 102){
               puntajePABD = cuatro;
               //Toast.makeText(this, ""+ puntajePABD, Toast.LENGTH_SHORT).show();
           }
        }else {
            if (res < 80){
                puntajePABD = cero;
                //Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
            }
            else if (res > 80 && res < 88){
                puntajePABD = tres;
                //Toast.makeText(this, ""+puntajePABD, Toast.LENGTH_SHORT).show();
            }
            else if (res > 88){
                puntajePABD = cuatro;
                //Toast.makeText(this, ""+ puntajePABD, Toast.LENGTH_SHORT).show();
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
    private void calcularPresionArterial() {

        if (txtPAS.getText().toString().length()>0){
            if (txtPd.getText().toString().length()>0){
                sistolica = Double.parseDouble(txtPAS.getText().toString());
                diastolica = Double.parseDouble(txtPd.getText().toString());

                float tmp1 = (float) sistolica;
                float tmp2 = (float) diastolica;

                try {
                    res = (tmp1 + (2 * tmp2)) / 3;

                    resultado = (int) (sistolica / diastolica);
                    txtPresionArterial.setText(Double.toString(res));
                }catch (Exception e){
                    Toast.makeText(this, "No ingrese valores negativos o de valor 0", Toast.LENGTH_SHORT).show();
                }


            }else {
                txtPd.setError("Por favor ingrese este campo");
                Toast.makeText(this, "No se puede calcular la presión arterial porque faltan campos", Toast.LENGTH_SHORT).show();
            }
        }else {
            txtPAS.setError("Por favor ingrese este campo");
            Toast.makeText(this, "No se puede calcular la presión arterial porque faltan campos", Toast.LENGTH_SHORT).show();
        }


    }

    //Formula para calcular el IMC de la persona
    private double calcularIMC() {
        if (txtAltura.getText().toString().length()>0) {

            if (txtPeso.getText().toString().length()>0) {
                altura = Double.parseDouble(txtAltura.getText().toString());
                peso = Double.parseDouble(txtPeso.getText().toString());
                pesof = altura / 100;
                try {
                    imc = peso / (Math.pow(pesof, 2));
                    DecimalFormat df = new DecimalFormat("#.00");
                    txtIMC.setText(df.format(imc));
                }catch (Exception e){
                    Toast.makeText(this, "No ingrese valores negativos o de valor 0", Toast.LENGTH_SHORT).show();
                }

            }else {
                txtPeso.setError("Por favor ingrese este campo");
                Toast.makeText(this, "No se puede calcular la presión arterial porque faltan campos", Toast.LENGTH_SHORT).show();
            }
        }else {
            txtAltura.setError("Por favor ingrese este campo");
            Toast.makeText(this, "No se puede calcular la presión arterial porque faltan campos", Toast.LENGTH_SHORT).show();
        }
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

    private void calcularGlucometria(){
        glucometria= Float.parseFloat(txtGlucometria.getText().toString());
        if (glucometria<70){
            glucosa = "Baja (Hipoglicemia)";
        }

        if (glucometria>=70 && glucometria<120){
            glucosa = "Normal";
        }

        if (glucometria>=120 && glucometria<180){
            glucosa = "Elevada";
        }

        if (glucometria>=180){
            glucosa = "Muy elevada";
        }

    }

    public void inputData(){
        Datos datos = MenuP.datos;
        datos.setTalla((int) altura);
        datos.setPeso((int) peso);
        datos.setPresionAS(String.valueOf(sist));
        datos.setPresionDiastolica(String.valueOf(diast));
        datos.setClasificacionIMC(tmp1);
        datos.setImc((int) imc);
        datos.setPerimetroAbdominal(Integer.parseInt(txtPABD.getText().toString()));
        datos.setGlucosaAlta(glucosa);
        datos.setGlucometria(String.valueOf(glucometria));
        MenuP.datos = datos;
    }


}
