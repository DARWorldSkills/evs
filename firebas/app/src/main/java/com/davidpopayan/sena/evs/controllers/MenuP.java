package com.davidpopayan.sena.evs.controllers;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.AdapterDatos;
import com.davidpopayan.sena.evs.controllers.models.Datos;
import com.davidpopayan.sena.evs.controllers.models.ManagerDB;

import java.util.List;

public class MenuP extends AppCompatActivity implements SearchView.OnQueryTextListener{

    FloatingActionButton btnNuevo;
    RecyclerView recyclerView;

    MenuItem buscardorItem;
    SearchView searchView;
    Datos datos = new Datos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_p);

        inicializar();

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuP.this, PrimerForm.class);
                startActivity(intent);
            }
        });

        inputAdapter();
    }

    private void inicializar() {
        btnNuevo = findViewById(R.id.btnNuevoPerfil);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buscar, menu);
        buscardorItem = menu.findItem(R.id.itBuscar);
        searchView = (SearchView) buscardorItem.getActionView();
        searchView.setQueryHint("Busque por número de identificación");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    private void inputAdapter() {
        ManagerDB managerDB = new ManagerDB(this);
        List<Datos> datosList = managerDB.listaDatos();
        AdapterDatos adapterDatos = new AdapterDatos(datosList);
        recyclerView.setAdapter(adapterDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
    }

    private void inputAdapterBuscando(String identificacion) {
        ManagerDB managerDB = new ManagerDB(this);
        List<Datos> datosList = managerDB.listaDatosPorIdentificacion(identificacion);
        AdapterDatos adapterDatos = new AdapterDatos(datosList);
        recyclerView.setAdapter(adapterDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itBuscar){

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        inputAdapterBuscando(newText);
        return false;
    }
}
