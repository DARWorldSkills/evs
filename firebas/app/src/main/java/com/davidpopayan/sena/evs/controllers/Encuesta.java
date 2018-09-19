package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.davidpopayan.sena.evs.R;

public class Encuesta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        this.setTitle("Cuestionario");
    }

    public void siguienteEn(View view) {

        Intent intent = new Intent(Encuesta.this, EncuestaDos.class);
        startActivity(intent);
    }
}
