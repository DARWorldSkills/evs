package com.davidpopayan.sena.evs.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.davidpopayan.sena.evs.R;

import java.util.ArrayList;
import java.util.List;

public class PrimerForm extends AppCompatActivity {

    Spinner spinnerGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_form);

        inicializar();
        listarGenero();

    }

    private void listarGenero() {
        List<String>genero = new ArrayList<>();
        genero.add("Masculino");
        genero.add("Femenino");
        genero.add("Indefinido");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genero);
        spinnerGenero.setAdapter(adapter);
    }


    private void inicializar() {
        spinnerGenero = findViewById(R.id.spinnerGenero);
    }
}
