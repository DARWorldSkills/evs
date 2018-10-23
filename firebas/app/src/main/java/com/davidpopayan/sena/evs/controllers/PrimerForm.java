package com.davidpopayan.sena.evs.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Datos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class PrimerForm extends AppCompatActivity {

    //Variables
    Spinner spinnerGenero, spinnerEps, spinnerIps;
    Button btnSiguiente;
    RadioButton rbtnCC, rbtnTI, rbtnRC;
    EditText txtNombre, txtIdentificacion, txtEps, txtIps,txtNumero, txtDireccion, txtFechadenacimiento, txtEdad;
    Button btnInputFecNac;
    int cero = 0, uno =1 ,dos = 2, tres = 3, cuatro = 4,cinco = 5, seis =6;
    int edad;
    int tmpSiEdad=0;
    public static int edadPuntaje;
    Datos datos = new Datos();
    List<String> genero = new ArrayList<>();
    List<String>eps = new ArrayList<>();
    List<String>ips= new ArrayList<>();
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_form);

        this.setTitle("Datos Personales");
        activity= this;
        //Creamos Metodos
        inicializar();
        listarEps();
        listarGenero();
        listarIps();
        escucharRadioButtons();
        ingresarValores();
        insertarFechaNac();


    }
    private void listarIps() {
        ips = new ArrayList<>();
        ips.add("ASOCIACIÓN INDÍGENA DEL CAUCA IPSI");
        ips.add("CAJA DE COMPENSACION FAMILIAR DEL CAUCA");
        ips.add("CENTRO DE SALUD 31 DE MARZO - ESE POPAYAN");
        ips.add("CENTRO DE SALUD DE PUEBLILLO POPAYAN ESE");
        ips.add("CENTRO DE SALUD LOMA DE LA VIRGEN");
        ips.add("CENTRO DE SALUD SUR OCCIDENTE POPAYAN ESE");
        ips.add("CENTRO DE SALUD SUR ORIENTE POPAYAN ESE");
        ips.add("CENTRO DE SALUD YANACONAS");
        ips.add("CLINICA DE LA ESTANCIA SA");
        ips.add("CLINICA SANTA GRACIA DUMIAN MEDICAL SAS");
        ips.add("CORPORACION IPS SALUDCOOP");
        ips.add("COSMITET LTDA CORPORACIÓ");
        ips.add("CRUZ ROJA COLOMBIANA SECCIONAL CAUCA");
        ips.add("DUMIAN MEDICAL SAS");
        ips.add("EPS SANITAS CENTRO MEDICO POPAYAN");
        ips.add("HOME HEALTH SALUD EN CASA SAS IPS");
        ips.add("HOSPITAL MARIA OCCIDENTE ESE POPAYAN");
        ips.add("HOSPITAL SUSANA LOPEZ DE VALENCIA ESE");
        ips.add("HOSPITAL TORIBIO MAYA - ESE POPAYAN");
        ips.add("HOSPITAL UNIVERSITARIO SAN JOSE DE POPAYAN");
        ips.add("UNIDAD DE SALUD UNICAUCA");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ips);
        spinnerIps.setAdapter(adapter);

    }

    private void listarEps() {
        eps = new ArrayList<>();
        eps.add("ASMET SALUD EPS");
        eps.add("COOMEVA SALUD EPS");
        eps.add("EMSSANAR EPS");
        eps.add("SECRETARIA DE SALUD DEPARTAMENTAL");
        eps.add("SALUD VIDA");
        eps.add("SURA");
        eps.add("NUEVA EPS");
        eps.add("ASOCIASION INDIGENA ");
        eps.add("SEGURAS DEL ESTADO");
        eps.add("POSITIVA COMPAÑIA DE SEGUROS");
        eps.add("CAPRECOM EPS");
        eps.add("CONTRATO PARTICULAR");
        eps.add("SERVICIO OCCIDENTAL DE SALUD");
        eps.add("ALIANSALUD");
        eps.add("ALIANZA MEDELLIN ANTIOQUIA ");
        eps.add("ALIANZA SEGUROS");
        eps.add("AXA COLPATRIA");
        eps.add("CAFE SALUD");
        eps.add("CAJA DE COMPENSACIÓN FAMILIAR");
        eps.add("CAPITAL SALUD EPS");
        eps.add("CAPRESOCA");
        eps.add("SANITAS");
        eps.add("COOSALUD");
        eps.add("COMPENSAR");
        eps.add("DTS SECRETARIA DE SALUD");
        eps.add("COSMITET");
        eps.add("CRUZ ROJA");
        eps.add("LA EQUIDAD ARL");
        eps.add("LA PREVISORA");
        eps.add("LIBERTY SEGUROS");
        eps.add("ENTIDAD PROMOTORA DE SALUD");
        eps.add("CRUZ BLANCA");
        eps.add("EPS Y MEDICINA PREPAGADA");
        eps.add("MEDIMAS");
        eps.add("MUNDIAL DE SEGUROS");
        eps.add("POLICIA METROPOLITANA");
        eps.add("POSITIVA");
        eps.add("SEGUROS DE VIDA DE ESTADO");
        eps.add("SOS INTERNATIONAL");
        eps.add("UNIVERSIDAD DEL CAUCA UNIDAD DE SALUD");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, eps);
        spinnerEps.setAdapter(adapter);
    }

    //Ingresamos los datos de la base de datos
    private void ingresarValores() {
        if (MenuP.ingresar==0 || MenuP.ingresar==2){
            Datos datos =MenuP.datos;
            txtNombre.setText(datos.getNombreCompleto());
            txtIdentificacion.setText(datos.getNumeroId());
            //txtEps.setText(datos.getNombreEPS());
            //txtIps.setText(datos.getiPS());
            txtNumero.setText(datos.getTelefono());
            txtDireccion.setText(datos.getDireccion());
            txtFechadenacimiento.setText(datos.getFecNac());

            if (txtIdentificacion.getText().toString().length()<1){

                rbtnCC.setChecked(false);
                txtIdentificacion.setEnabled(false);
            }else {
                rbtnCC.setChecked(true);
                txtIdentificacion.setEnabled(true);
            }

            for (int i=0; i<genero.size();i++){
                if (genero.get(i).equals(datos.getGenero())){
                    spinnerGenero.setSelection(i);
                }
            }

            for (int i=0; i<eps.size();i++){
                if (eps.get(i).equals(datos.getNombreEPS())){
                    spinnerEps.setSelection(i);
                }
            }
            txtEdad.setText(Integer.toString(calcularEdad(datos.getFecNac())));

        }
    }

    //Operacion la cual nos ayuda a calcular la edad de la persona
    public int calcularEdad(String fechaNac){
        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = simpleDateFormat.parse(fechaNac);
            int diaDelMes = Integer.parseInt(simpleDateFormat.format(now).substring(3,5));
            int diaNac=Integer.parseInt(simpleDateFormat.format(date).substring(3,5));
            int nowMonth = now.getMonth()+1;
            int nowYear = now.getYear();
            edad = nowYear - date.getYear();
            Log.e("dada", String.valueOf(diaNac));
            Log.e("dada", String.valueOf(diaDelMes));
            if (date.getMonth() > nowMonth) {
                edad--;
            }
            else if (date.getMonth()+1 == nowMonth) {
                int nowDay = diaDelMes;

                if (diaNac > nowDay) {
                    edad--;

                }else {
                    //edad=-1;
                }
            }

            return edad;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //Listamos los datos de genero
    private void listarGenero() {
        genero = new ArrayList<>();
        genero.add("Masculino");
        genero.add("Femenino");
        genero.add("Indefinido");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genero);
        spinnerGenero.setAdapter(adapter);
    }

    //Referenciamos todos los campos del layout que vamos a utilizar
    private void inicializar() {
        spinnerGenero = findViewById(R.id.spinnerGenero);
        spinnerEps = findViewById(R.id.spinnerEps);
        spinnerIps = findViewById(R.id.spinnerIps);
        //////////////////////////////////////////////
        btnSiguiente = findViewById(R.id.btnSiguiente);
        /////////////////////////////////////////////
        txtNombre = findViewById(R.id.txtNombre);
        txtIdentificacion = findViewById(R.id.txtIdentificacion);
        txtNumero = findViewById(R.id.txtNumero);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtFechadenacimiento = findViewById(R.id.txtFechadenacimiento);
        txtEdad = findViewById(R.id.txtEdad);
        rbtnCC = findViewById(R.id.rbtnCC);
        rbtnTI = findViewById(R.id.rbtnTI);
        rbtnRC = findViewById(R.id.rbtnRc);
        btnInputFecNac = findViewById(R.id.btnInputFecNac);
    }

    //Evento el cual nos ayuda a escuchar el boton
    public void siguiente(View view) {
        asiganarPuntajes();
        validar();
    }
    //Asignamos puntaje dependiendo de la edad de la persona
    private void asiganarPuntajes() {
        if (edad< 45){
            edadPuntaje = cero;
            //Toast.makeText(this, ""+edadPuntaje+" ", Toast.LENGTH_SHORT).show();
        }
        else if (edad >45 && edad<54){
            edadPuntaje = dos;
            //Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
        else if (edad >55 && edad <64){
            edadPuntaje = tres;
            //Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
        else if (edad > 64){
            edadPuntaje = cuatro;
            //Toast.makeText(this, ""+edadPuntaje, Toast.LENGTH_SHORT).show();
        }
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
        rbtnRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtIdentificacion.setEnabled(true);
            }
        });
    }

    public void insertarFechaNac(){
        btnInputFecNac.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PrimerForm.this);
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
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                            calcularEdad(format.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date.after(new Date()) || dateFormat.format(date).equals(dateFormat.format(new Date())) ){
                            Toast.makeText(PrimerForm.this, "Edad incorrecta", Toast.LENGTH_SHORT).show();
                        }else {
                            txtFechadenacimiento.setText(dateFormat.format(date));
                            txtEdad.setText(Integer.toString(edad));
                            dialog.cancel();
                        }
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
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
        if (validar == 6){
            inputData();
            Intent intent = new Intent(PrimerForm.this, datosPersonales.class);
            startActivity(intent);

        }else {
            Toast.makeText(activity, "Faltan campos por ingresar", Toast.LENGTH_SHORT).show();
        }

    }

    public void inputData(){
        datos = MenuP.datos;
        datos.setNombreCompleto(txtNombre.getText().toString());
        datos.setNumeroId(txtIdentificacion.getText().toString());
        datos.setNombreEPS(spinnerEps.getSelectedItem().toString());
        datos.setiPS(spinnerIps.getSelectedItem().toString());
        datos.setTelefono(txtNumero.getText().toString());
        datos.setDireccion(txtDireccion.getText().toString());
        datos.setFecNac(txtFechadenacimiento.getText().toString());
        datos.setEdad(edad);
        datos.setGenero(spinnerGenero.getSelectedItem().toString());
        if (rbtnCC.isChecked()){
            datos.setTipoID("CC");
        }

        if (rbtnTI.isChecked()){
            datos.setTipoID("TI");
        }

        if (rbtnRC.isChecked()){
            datos.setTipoID("RC");
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        datos.setFecTamitaje(dateFormat.format(date));


        MenuP.datos = datos;

    }
}
