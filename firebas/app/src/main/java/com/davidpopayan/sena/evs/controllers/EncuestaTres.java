package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.davidpopayan.sena.evs.R;

public class EncuestaTres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_tres);
        this.setTitle("Cuestionario");
    }

    public void resultado(View view) {

        Intent intent = new Intent(EncuestaTres.this, Resultados.class);
        startActivity(intent);
    }
}
