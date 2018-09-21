package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Datos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrimerForm extends AppCompatActivity {

    //Variables
    Spinner spinnerGenero;
    Button btnSiguiente;
    RadioButton rbtnCC, rbtnTI;
    EditText txtNombre, txtIdentificacion, txtEps, txtIps,txtNumero, txtDireccion, txtFechadenacimiento, txtEdad;
    int cero = 0, uno =1 ,dos = 2, tres = 3, cuatro = 4,cinco = 5, seis =6;
    int edad;
    public static int edadPuntaje;
    List<String> genero = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_form);

        this.setTitle("Datos Personales");
        //Creamos Metodos
        inicializar();
        listarGenero();
        escucharRadioButtons();
        ingresarValores();

    }

    //Ingresamos los datos de la base de datos
    private void ingresarValores() {
        if (MenuP.ingresar==0){
            Datos datos =MenuP.datos;
            txtNombre.setText(datos.getNombreCompleto());
            txtIdentificacion.setText(datos.getNumeroId());
            txtEps.setText(datos.getNombreEPS());
            txtIps.setText(datos.getiPS());
            txtNumero.setText(datos.getTelefono());
            txtDireccion.setText(datos.getDireccion());
            txtFechadenacimiento.setText(datos.getFecNac());
            for (int i=0; i<genero.size();i++){
                if (genero.get(i).substring(0,1).equals(datos.getGenero())){
                    spinnerGenero.setSelection(i);
                }
            }
            txtEdad.setText(Integer.toString(calcularEdad(datos.getFecNac())));

        }
    }

    //Operacion la cual nos ayuda a calcular la edad de la persona
    public int calcularEdad(String fechaNac){
        Date date = new Date();
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = simpleDateFormat.parse(fechaNac);
            edad = date1.getYear()-date.getYear();
            return edad;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //Listamos los datos de genero
    private void listarGenero() {
        genero = new ArrayList<>();
        genero.add("Masculino");
        genero.add("Femenino");
        genero.add("Indefinido");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genero);
        spinnerGenero.setAdapter(adapter);
    }

    //Referenciamos todos los campos del layout que vamos a utilizar
    private void inicializar() {
        spinnerGenero = findViewById(R.id.spinnerGenero);
        //////////////////////////////////////////////
        btnSiguiente = findViewById(R.id.btnSiguiente);
        /////////////////////////////////////////////
        txtNombre = findViewById(R.id.txtNombre);
        txtIdentificacion = findViewById(R.id.txtIdentificacion);
        txtEps = findViewById(R.id.txtEps);
        txtIps = findViewById(R.id.txtIps);
        txtNumero = findViewById(R.id.txtNumero);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtFechadenacimiento = findViewById(R.id.txtFechadenacimiento);
        txtEdad = findViewById(R.id.txtEdad);
        rbtnCC = findViewById(R.id.rbtnCC);
        rbtnTI = findViewById(R.id.rbtnTI);
    }

    //Evento el cual nos ayuda a escuchar el boton
    public void siguiente(View view) {
        asiganarPuntajes();
        validar();
    }
    //Asignamos puntaje dependiendo de la edad de la persona
    private void asiganarPuntajes() {
        if (edad< 45){
            edadPuntaje = cero;
            Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
        else if (edad >45 && edad<54){
            edadPuntaje = dos;
            Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
        else if (edad >55 && edad <64){
            edadPuntaje = tres;
            Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
        else if (edad > 64){
            edadPuntaje = cuatro;
            Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
    }

    //Validamos el tipo de identidicacion
    public void escucharRadioButtons(){
        rbtnTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtIdentificacion.setEnabled(true);
            }
        });

        rbtnCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtIdentificacion.setEnabled(true);
            }
        });
    }

    //se validan que los campos no esten vacios
    private void validar() {
        int validar = 0;
        if (txtNombre.getText().toString().length()>0){
            validar++;
        }else{
            txtNombre.setError("Falta Campo");
        }
        if (txtIdentificacion.getText().toString().length()>0){
            validar++;
        }else{
            txtIdentificacion.setError("Falta Campo");
        }
        if (txtEps.getText().toString().length()>0){
            validar++;
        }else{
            txtEps.setError("Falta Campo");
        }
        if (txtIps.getText().toString().length()>0){
            validar++;
        }else{
            txtIps.setError("Falta Campo");
        }
        if (txtNumero.getText().toString().length()>0){
            validar++;
        }else{
            txtNumero.setError("Falta Campo");
        }

        if (txtDireccion.getText().toString().length()>0){
            validar++;
        }else{
            txtDireccion.setError("Falta Campo");
        }

        if (txtFechadenacimiento.getText().toString().length()>0){
            validar++;
        }else{
            txtFechadenacimiento.setError("Falta Campo");
        }

        if (txtEdad.getText().toString().length()>0){
            validar++;
        }else{
            txtEdad.setError("Falta Campo");
        }
        if (validar == 8){
            Intent intent = new Intent(PrimerForm.this, datosPersonales.class);
            startActivity(intent);

        }

    }
}
