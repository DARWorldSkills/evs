package com.davidpopayan.sena.evs.controllers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CrearUsuario extends AppCompatActivity {
    EditText txtNombre, txtUser, txtContrasena, txtConfirmar;
    Button btnCrearUsuario;
    List<Usuario> usuarioList = new ArrayList<>();
    boolean bandera = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        bandera = false;
        iniziliate();
        consultar();
    }

    private void iniziliate() {
        txtNombre = findViewById(R.id.txtNombre);
        txtUser = findViewById(R.id.txtUser);
        txtContrasena = findViewById(R.id.txtContrasena);
        txtConfirmar = findViewById(R.id.txtConfirmar);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();

            }
        });
    }


    private void consultar() {

        FirebaseApp.initializeApp(this);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usuarios = reference.child("Usuario");
        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Usuario>> t = new GenericTypeIndicator<ArrayList<Usuario>>(){};
                usuarioList = dataSnapshot.getValue(t);
                bandera = true;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void crearUsuario() {
        if (bandera) {
            btnCrearUsuario.setEnabled(false);
            int contador = 0;
            String nombre = txtNombre.getText().toString();
            String user = txtUser.getText().toString();
            String contrasena = txtContrasena.getText().toString();
            String confirmar = txtConfirmar.getText().toString();

            if (nombre.length() > 0) {
                contador++;
            } else {
                txtNombre.setError("Por favor no deje este campo vacio");
            }

            if (user.length() > 0) {
                contador++;
            } else {
                txtUser.setError("Por favor no deje este campo vacio");
            }

            if (contrasena.length() > 0) {
                contador++;
            } else {
                txtContrasena.setError("Por favor no deje este campo vacio");
            }

            if (confirmar.length() > 0) {
                contador++;
            } else {
                txtConfirmar.setError("Por favor no deje este campo vacio");
            }


            if (contador == 4) {
                int contador1 = 0;
                for (int i = 0; i < usuarioList.size(); i++) {
                    if (usuarioList.get(i).equals(user)) {
                        contador1++;
                    }
                }

                if (contador1 == 0) {

                    if (contrasena.equals(confirmar)) {

                        Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
                        FirebaseApp.initializeApp(this);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference usuarios = reference.child("Usuario").child(Integer.toString(usuarioList.size()));
                        usuarios.child("activado").setValue("si");
                        usuarios.child("contrasena").setValue(contrasena);
                        usuarios.child("nombre").setValue(nombre);
                        usuarios.child("rango").setValue("ninguno");
                        usuarios.child("username").setValue(user);
                        Toast.makeText(this, "Se ha registrado correctamente, por favor inicie sesión", Toast.LENGTH_LONG).show();
                        finish();


                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        txtConfirmar.setError("Las contraseñas no son las mismas");
                        btnCrearUsuario.setEnabled(true);
                    }

                }else {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                    btnCrearUsuario.setEnabled(true);
                }

            } else {
                Toast.makeText(this, "Faltan campos por ingreasr", Toast.LENGTH_SHORT).show();
                btnCrearUsuario.setEnabled(true);
            }
        }else {
            Toast.makeText(this, "Oprima de nuevo", Toast.LENGTH_SHORT).show();
            btnCrearUsuario.setEnabled(true);
        }

    }
}
