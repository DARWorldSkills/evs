package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.davidpopayan.sena.evs.R;

import java.util.ArrayList;
import java.util.List;

public class PrimerForm extends AppCompatActivity {

    //Variables
    Spinner spinnerGenero;
    Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_form);

        this.setTitle("Datos Personales");
        //Creamos Metodos
        inicializar();
        listarGenero();

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
        btnSiguiente = findViewById(R.id.btnSiguiente);
    }

    //Evento el cual nos ayuda a escuchar el boton
    public void siguiente(View view) {
        Intent intent = new Intent(PrimerForm.this, Encuesta.class);
        startActivity(intent);
    }
}
