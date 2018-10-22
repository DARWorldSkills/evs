package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Datos;
import com.davidpopayan.sena.evs.controllers.models.ManagerDB;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("usuarios",MODE_PRIVATE);

        try {
            inputData();
        } catch (IOException e) {
            e.printStackTrace();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, MenuP.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask,2000);
        }

    }

    private void inputData() throws IOException {
        ManagerDB managerDB = new ManagerDB(this);
        List<Datos> list = managerDB.listaDatos();
        Datos datos = new Datos();
        if (list.size()<1){
            String linea;
            InputStream is = getResources().openRawResource(R.raw.datos);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if (is!=null){
                while ((linea = reader.readLine())!=null){
                    String [] strings = linea.split(";");
                    datos.setNombreCompleto(strings[9]+" "+strings[10]+" "+strings[8]+" "+strings[7]);
                    datos.setDireccion(strings[12]);
                    datos.setFecNac(strings[3]);
                    datos.setNumeroId(strings[5]);
                    datos.setTelefono(strings[5]);
                    datos.setNombreEPS(strings[11]);
                    datos.setGenero(strings[4]);
                    datos.setFecTamitaje("18/05/2018");
                    datos.setRealiza("Sistema");
                    managerDB.inputData(datos);

                }
            }
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (sharedPreferences.getString("username","0").equals("0") || sharedPreferences.getString("activado","no").equals("no")) {
                    Intent intent = new Intent(Splash.this, IniciarSesion.class);
                    startActivity(intent);
                    finish();
                }else {

                    if (sharedPreferences.getString("superuser","no").equals("superuser")) {
                        MenuP.usuario.setRango("superuser");

                    }else {
                        if (sharedPreferences.getString("superuser","no").equals("administrador")){

                            MenuP.usuario.setRango("administrador");

                        }else {
                            MenuP.usuario.setRango("ninguno");
                        }
                    }
                    MenuP.usuario.setNombre(sharedPreferences.getString("nombre","No disponible"));
                    Log.e("nombre1",MenuP.usuario.getNombre());

                    MenuP.usuario.setUsername(sharedPreferences.getString("username","0"));
                    Intent intent = new Intent(Splash.this, MenuP.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,2000);
    }
}
