package com.davidpopayan.sena.evs.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IniciarSesion extends AppCompatActivity {
    EditText txtUsuario;
    EditText txtContrasena;
    Button btnIniciarSesion;
    List<Usuario> usuarioList=new ArrayList<>();
    SharedPreferences sharedPreferences;
    TextView txtRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        sharedPreferences = getSharedPreferences("usuarios",MODE_PRIVATE);
        consultar();
        inizialite();
    }

    private void inizialite() {
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtRegistrar = findViewById(R.id.txtRegistrar);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar();
            }
        });
        txtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesion.this, CrearUsuario.class);
                startActivity(intent);
            }
        });

    }


    private void consultar() {

        FirebaseApp.initializeApp(this);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ambiente = reference.child("Usuario");
        ambiente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Usuario>> t = new GenericTypeIndicator<ArrayList<Usuario>>(){};
                usuarioList = dataSnapshot.getValue(t);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void verificar() {
        final SharedPreferences.Editor editor =sharedPreferences.edit();
        String nombre = txtUsuario.getText().toString();
        String clave = txtContrasena.getText().toString();
        boolean bandera =false;

        if (nombre.length()>0 && clave.length()>0) {
            if (usuarioList != null) {
                for (int i = 0; i < usuarioList.size(); i++) {
                    if (usuarioList.get(i).getUsername().equals(nombre)){
                        bandera = true;
                        if (usuarioList.get(i).getContrasena().equals(clave)){

                            if (usuarioList.get(i).getActivado().equals("si")) {
                                MenuP.usuario = usuarioList.get(i);
                                Intent intent = new Intent(IniciarSesion.this, MenuP.class);
                                editor.putString("nombre", usuarioList.get(i).getNombre());
                                editor.putString("username", nombre);
                                editor.putString("contrasena", clave);
                                editor.putString("activado", "si");
                                editor.putString("online", "si");
                                if (MenuP.usuario.getRango().equals("superuser")) {
                                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                                    editor.putString("superuser", "si");

                                } else {
                                    editor.putString("superuser", "no");
                                }

                                editor.commit();
                                startActivity(intent);
                                i = usuarioList.size();
                                finish();
                            }else {
                                Toast.makeText(this, "Este usuario se encuentra bloqueado, por favor hable con el administrador", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            txtContrasena.setError("Por favor ingrese la clave correspondiente");
                            Toast.makeText(this, "Clave incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (usuarioList.size()<1){

                    if (sharedPreferences.getString("username","no disponible").equals(nombre)){
                        bandera = true;
                        if (sharedPreferences.getString("contrasena","no disponible").equals(clave)){
                            Intent intent = new Intent(IniciarSesion.this,MenuP.class);
                            editor.putString("username",nombre);
                            editor.putString("contrasena",clave);
                            editor.putString("activado","si");
                            editor.putString("online","no");
                            if (sharedPreferences.getString("superuser","no").equals("si")) {
                                editor.putString("superuser", "si");
                                MenuP.usuario.setRango("superuser");
                                MenuP.usuario.setUsername(sharedPreferences.getString("usuario","no disponible"));

                            }else {
                                editor.putString("superuser", "no");
                                MenuP.usuario.setRango("ninguno");
                            }
                            editor.commit();
                            startActivity(intent);
                            finish();


                        }else{
                            txtContrasena.setError("Por favor ingrese la clave correspondiente");
                            Toast.makeText(this, "Clave incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    if (bandera == false) {
                        txtUsuario.setError("El usuario no está registrado en la base de datos");
                        Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "" + usuarioList.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }else {
            if (nombre.length()<1){
                txtUsuario.setError("Por favor ingresa nombre de usuario");
            }

            if (nombre.length()<1){
                txtContrasena.setError("Por favor ingresa la clave");
            }
            Toast.makeText(this, "Falta uno o los dos campos por ingresar", Toast.LENGTH_SHORT).show();
        }

    }

}
