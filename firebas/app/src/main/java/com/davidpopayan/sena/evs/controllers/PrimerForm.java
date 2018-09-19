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

import com.davidpopayan.sena.evs.R;

import java.util.ArrayList;
import java.util.List;

public class PrimerForm extends AppCompatActivity {

    //Variables
    Spinner spinnerGenero;
    Button btnSiguiente;
    RadioButton rbtnCC, rbtnTI;
    EditText txtNombre, txtIdentificacion, txtEps, txtIps,txtNumero, txtDireccion, txtFechadenacimiento, txtEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_form);

        this.setTitle("Datos Personales");
        //Creamos Metodos
        inicializar();
        listarGenero();
        escucharRadioButtons();

    }

    //Listamos los datos de genero
    private void listarGenero() {
        List<String>genero = new ArrayList<>();
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
        validar();


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
