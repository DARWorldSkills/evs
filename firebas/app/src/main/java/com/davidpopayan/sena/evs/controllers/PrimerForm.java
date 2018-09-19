package com.davidpopayan.sena.evs.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.davidpopayan.sena.evs.R;

import java.util.ArrayList;
import java.util.List;

public class PrimerForm extends AppCompatActivity {

    Spinner spinnerEps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_form);

        inicializar();

    }


    private void inicializar() {
    }
}
