package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.davidpopayan.sena.evs.R;

public class datosPersonales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        this.setTitle("Datos Personales");
    }

    public void irEncuenta(View view) {

        Intent intent = new Intent(datosPersonales.this, Encuesta.class);
        startActivity(intent);
    }
}
