package com.davidpopayan.sena.evs.controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.AdapterDatos;
import com.davidpopayan.sena.evs.controllers.models.Datos;
import com.davidpopayan.sena.evs.controllers.models.ManagerDB;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MenuP extends AppCompatActivity implements SearchView.OnQueryTextListener{

    List<Datos> datosList=new ArrayList<>();
    FloatingActionButton btnNuevo;
    RecyclerView recyclerView;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=100;
    MenuItem buscardorItem;
    SearchView searchView;
    public static Datos datos = new Datos();
    public static int ingresar=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_p);

        inicializar();
        this.setTitle("Estilo de vida saludable");

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuP.this, PrimerForm.class);
                startActivity(intent);
                ingresar=1;
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
        getMenuInflater().inflate(R.menu.exportar, menu);
        buscardorItem = menu.findItem(R.id.itBuscar);
        searchView = (SearchView) buscardorItem.getActionView();
        searchView.setQueryHint("Busque por número de identificación");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    private void inputAdapter() {

        ManagerDB managerDB = new ManagerDB(this);
        datosList = managerDB.listaDatos();
        AdapterDatos adapterDatos = new AdapterDatos(datosList);
        recyclerView.setAdapter(adapterDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        adapterDatos.setNewListener(new AdapterDatos.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                datos = datosList.get(position);
                Intent intent = new Intent(MenuP.this,PrimerForm.class);
                startActivity(intent);
                Toast.makeText(MenuP.this, ""+datos.getNumero(), Toast.LENGTH_SHORT).show();
                ingresar=0;
            }
        });
    }

    private void inputAdapterBuscando(String identificacion) {
        ManagerDB managerDB = new ManagerDB(this);
        final List<Datos> datosList = managerDB.listaDatosPorIdentificacion(identificacion);
        AdapterDatos adapterDatos = new AdapterDatos(datosList);
        recyclerView.setAdapter(adapterDatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adapterDatos.setNewListener(new AdapterDatos.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                datos = datosList.get(position);
                Intent intent = new Intent(MenuP.this,PrimerForm.class);
                startActivity(intent);
                ingresar=0;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemExportar){
            pedirPermiso();
            exportarEnCSV();

        }
        return super.onOptionsItemSelected(item);
    }

    private void pedirPermiso() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                exportarEnCSV();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


    }

    private void exportarEnCSV() {

        final Dialog dialog = new Dialog(MenuP.this);
        dialog.setContentView(R.layout.item_busqueda);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final DatePicker datePicker = dialog.findViewById(R.id.calendario);
        Button btnAceptar =  dialog.findViewById(R.id.btnExportar);
        Button btnCancelar =  dialog.findViewById(R.id.btnCancelar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mes = datePicker.getMonth()+1;
                String fecha1 = datePicker.getDayOfMonth()+"/"+mes+"/"+datePicker.getYear();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                try {
                    date =dateFormat.parse(fecha1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = dateFormat1.format(date);
                List<Datos> tmpListDatos = new ArrayList<>();
                for (int i=0; i<datosList.size(); i++){
                    tmpListDatos.add(datosList.get(i));
                }

                System.out.println(tmpListDatos);
                Iterator<Datos> it = tmpListDatos.iterator();
                List<Datos> tmpDatos1 = new ArrayList<>();
                try {
                    while (it.hasNext()) {
                        Datos current = it.next();
                        if (!current.getFecTamitaje().equals(fecha)) {
                            it.remove();
                        }else {
                            Toast.makeText(MenuP.this, ""+fecha, Toast.LENGTH_SHORT).show();
                            tmpDatos1.add(current);
                        }
                    }



                    File exportDir = new File(Environment.getExternalStorageDirectory(), "");
                    if (!exportDir.exists())
                    {
                        exportDir.mkdirs();
                    }

                    File file = new File(exportDir, "Tamitaje.csv");


                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    for (int i=0; i<tmpDatos1.size();i++) {
                        //Log.e("MainActivity", String.valueOf(datos.get(i).split(",")));
                        Datos tmpDatos = tmpDatos1.get(i);
                        String arrStr[] = {String.valueOf((tmpDatos.getNumero())),
                                tmpDatos.getFecTamitaje(),tmpDatos.getNombreCompleto(),tmpDatos.getTipoID(),(tmpDatos.getNumeroId()),
                                tmpDatos.getNombreEPS(),tmpDatos.getiPS(),(tmpDatos.getTelefono()),tmpDatos.getDireccion(),tmpDatos.getFecNac(),
                                String.valueOf((tmpDatos.getEdad())),tmpDatos.getGenero(), String.valueOf((tmpDatos.getTalla())),
                                String.valueOf((tmpDatos.getPeso())), String.valueOf((tmpDatos.getPerimetroAbdominal())),tmpDatos.getRealizarActividadFisicaD(),
                                tmpDatos.getFrecuenciaVerdurasFrutas(),tmpDatos.getMedicamentosHipertension(),tmpDatos.getGlucosaAlta(),tmpDatos.getDiabetesFamiliares(),
                                String.valueOf((tmpDatos.getImc())),tmpDatos.getClasificacionIMC(),tmpDatos.getRiesgoDeDiabetes(),tmpDatos.getPresionArterial(),
                                (tmpDatos.getPresionDiastolica()),tmpDatos.getDiabetes(),tmpDatos.getFuma(),tmpDatos.getPorcentajeRiesgo(),tmpDatos.getRiesgoCardio(),
                                tmpDatos.getPacientePresentaR(),tmpDatos.getDetalleRiesgoPaciente()};

                        csvWrite.writeNext(arrStr);

                    }
                    csvWrite.close();
                    if (tmpDatos1.size()<1){
                        Toast.makeText(MenuP.this, "No hay tamitaje registrados en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MenuP.this, "El archivo está en la dirección" + exportDir + "archivoCompleto.csv", Toast.LENGTH_SHORT).show();
                    }

                    dialog.cancel();

                }catch (Exception e){
                    Toast.makeText(MenuP.this, "No hay tamitajes registrados en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                    Log.e("Error",e.getMessage());
                }




            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });



        dialog.show();





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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    exportarEnCSV();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }
}
