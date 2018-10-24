package com.davidpopayan.sena.evs.controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.AdapterDatos;
import com.davidpopayan.sena.evs.controllers.models.Datos;
import com.davidpopayan.sena.evs.controllers.models.ManagerDB;
import com.davidpopayan.sena.evs.controllers.models.Usuario;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MenuP extends AppCompatActivity implements OnClickListener{

    List<Datos> datosList=new ArrayList<>();
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=100;
    public static Datos datos = new Datos();
    public static int ingresar=0;
    File archivo;
    SharedPreferences preferences;
    public static Usuario usuario = new Usuario();
    EditText txtBusqueda;
    Button btnBusqueda, btnExportar ,btnCerrarSesion, btnNuevo;
    public static Activity activity;
    public static MenuP menuP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_p);
        inicializar();
        this.setTitle("Estilo de vida saludable");

        btnNuevo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuP.this, PrimerForm.class);
                startActivity(intent);
                ingresar=1;
            }
        });
        btnBusqueda.setOnClickListener(this);
        btnExportar.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);
        activity=this;
        menuP=this;
    }

    private void inicializar() {
        btnNuevo = findViewById(R.id.btnNuevoPerfil);
        txtBusqueda = findViewById(R.id.txtBusqueda);
        btnBusqueda = findViewById(R.id.btnBusqueda);
        btnExportar = findViewById(R.id.btnExportar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exportar, menu);
        preferences = getSharedPreferences("usuarios",MODE_PRIVATE);
        try {
            if (!usuario.getRango().equals("superuser")){
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
            }else {
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(true);
            }

            if (usuario.getRango().equals("administrador")){
                menu.getItem(1).setVisible(true);
            }
        }catch (Exception e){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id==R.id.itemBloquear){
            Intent intent = new Intent(MenuP.this,BloqueoUsuarios.class);
            startActivity(intent);

        }

        if (id==R.id.itemCrearUsuario){
            Intent intent = new Intent(MenuP.this,CrearUsuario.class);
            startActivity(intent);

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
        final Button btnAceptar =  dialog.findViewById(R.id.btnExportar);
        Button btnCancelar =  dialog.findViewById(R.id.btnCancelar);

        btnAceptar.setOnClickListener(new OnClickListener() {
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
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
                String fecha = dateFormat1.format(date);
                ManagerDB managerDB = new ManagerDB(MenuP.this);
                List<Datos> tmpListDatos = managerDB.listaDatos();
                //Log.e("asd",""+tmpListDatos.get(0).getFecTamitaje()+" "+fecha);
                System.out.println(tmpListDatos);
                Iterator<Datos> it = tmpListDatos.iterator();
                List<Datos> tmpDatos1 = new ArrayList<>();
                try {
                    while (it.hasNext()) {
                        Datos current = it.next();
                        if (!current.getFecTamitaje().equals(fecha)) {
                            it.remove();
                        }else {
                            tmpDatos1.add(current);
                        }
                    }
                    File exportDir = new File(Environment.getExternalStorageDirectory(), "Tamitajes");
                    if (!exportDir.exists())
                    {
                        exportDir.mkdirs();
                    }
                    archivo = new File(exportDir, "Tamitaje "+fecha.substring(0,2)+"-"+fecha.substring(3,5)+"-"+fecha.substring(6,8)+".csv");
                    archivo.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(archivo));
                    for (int i=0; i<tmpDatos1.size();i++) {
                        Datos tmpDatos = tmpDatos1.get(i);
                        String arrStr[] = {String.valueOf((tmpDatos.getNumero())),
                                tmpDatos.getFecTamitaje(),tmpDatos.getNombreCompleto(),tmpDatos.getTipoID(),(tmpDatos.getNumeroId()),
                                tmpDatos.getNombreEPS(),tmpDatos.getiPS(),(tmpDatos.getTelefono()),tmpDatos.getDireccion(),tmpDatos.getFecNac(),
                                String.valueOf((tmpDatos.getEdad())),tmpDatos.getGenero(), String.valueOf((tmpDatos.getTalla())),
                                String.valueOf((tmpDatos.getPeso())), String.valueOf((tmpDatos.getPerimetroAbdominal())),tmpDatos.getRealizarActividadFisicaD(),
                                tmpDatos.getFrecuenciaVerdurasFrutas(),tmpDatos.getMedicamentosHipertension(),tmpDatos.getGlucosaAlta(),tmpDatos.getDiabetesFamiliares(),
                                String.valueOf((tmpDatos.getImc())),tmpDatos.getClasificacionIMC(),tmpDatos.getRiesgoDeDiabetes(),tmpDatos.getPresionArterial(),
                                (tmpDatos.getPresionDiastolica()),tmpDatos.getDiabetes(),tmpDatos.getFuma(),tmpDatos.getRealiza()};

                        csvWrite.writeNext(arrStr);

                    }
                    csvWrite.close();
                    managerDB.modificarExportar(tmpDatos1);
                    if (tmpDatos1.size()<1){
                        Toast.makeText(MenuP.this, "No hay tamitaje registrados en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MenuP.this, "El archivo está en la carpeta Tamitajes", Toast.LENGTH_SHORT).show();
                        enviarCorreo(String.valueOf(exportDir));
                        generarAlerta();
                    }

                    dialog.cancel();

                }catch (Exception e){
                    Toast.makeText(MenuP.this, "No hay tamitajes registrados en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                    Log.e("Error",e.getMessage());
                }
            }
        });
        btnCancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void enviarCorreo(String direccion) {
        File file = archivo;
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"rodseiya20008@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Envio de archivo CSV");
        intent.putExtra(Intent.EXTRA_TEXT,"Hola te envio este archivo ");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        startActivity(Intent.createChooser(intent,"Enviar email mediante"));


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
                    Toast.makeText(activity, "No se puede exportar el archivo por falta de permisos", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void generarAlerta(){
        ManagerDB managerDB = new ManagerDB(this);
        int realizados = managerDB.realizados();
        int exportados = managerDB.exportados();
        String mensaje ="Tamitajes realizados: "+realizados+"\n"+
                "Tamitajes exportados: "+exportados;
        final Dialog dialog1 = new Dialog(MenuP.this);
        dialog1.setContentView(R.layout.item_resultados);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txtResultados = dialog1.findViewById(R.id.txtResultado);
        txtResultados.setText(mensaje);
        Button btnCancelar = dialog1.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });
        try {
            dialog1.show();
        }catch (Exception e) {
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBusqueda:
                generarBusqueda();
                break;
            case R.id.btnExportar:
                pedirPermiso();
                exportarEnCSV();

                break;
            case R.id.btnCerrarSesion:
                cerrarSesion();
                break;
        }
    }

    public void cerrarSesion(){
        try {
            SharedPreferences sharedPreferences =getSharedPreferences("usuarios",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("activado","no");
            editor.commit();
            Intent intent = new Intent(MenuP.this,IniciarSesion.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            Toast.makeText(activity, "Por favor oprime de nuevo", Toast.LENGTH_SHORT).show();
        }

    }

    public void generarBusqueda(){
        if (txtBusqueda.getText().length()>0){
            ManagerDB managerDB = new ManagerDB(this);
            List<Datos> listarDatos = managerDB.listaDatosPorIdentificacion(txtBusqueda.getText().toString());
            List<Datos> listarDatos1 = managerDB.listaDatosPorIdentificacion1(txtBusqueda.getText().toString());
            if (listarDatos.size()>0){
                Intent intent = new Intent(MenuP.this,PrimerForm.class);
                if (listarDatos1.size()>0){
                    ingresar=0;
                    txtBusqueda.setText("");
                    datos=listarDatos1.get(0);
                }else {
                    ingresar=2;
                    txtBusqueda.setText("");
                    datos=listarDatos.get(0);
                }
                startActivity(intent);

            }else {
                Toast.makeText(activity, "El documento no está registrao en la base de datos", Toast.LENGTH_SHORT).show();
            }



        }else {
            Toast.makeText(activity, "Por favor ingrese un número de documento valido", Toast.LENGTH_SHORT).show();
        }
    }
}
