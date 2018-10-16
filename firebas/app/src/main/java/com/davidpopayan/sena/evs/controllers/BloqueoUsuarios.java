package com.davidpopayan.sena.evs.controllers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.davidpopayan.sena.evs.R;
import com.davidpopayan.sena.evs.controllers.models.AdapterUsuarios;
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

public class BloqueoUsuarios extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Usuario> usuarioList = new ArrayList<>();
    Button btnGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo_usuarios);
        iniziliate();
        consultar();
        guardar();
    }

    private void iniziliate() {
        recyclerView =findViewById(R.id.recyclerView);
        btnGuardar = findViewById(R.id.btnGuardar);
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
                try {
                    inputAdapter();

                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inputAdapter() {
        AdapterUsuarios adapterUsuarios = new AdapterUsuarios(usuarioList);
        recyclerView.setAdapter(adapterUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(BloqueoUsuarios.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
    }

    private void guardar() {

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseApp.initializeApp(BloqueoUsuarios.this);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference usuarios = reference.child("Usuario");

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0; i<usuarioList.size();i++){
                            if (!usuarioList.get(i).getUsername().equals(MenuP.usuario.getUsername())) {
                                Toast.makeText(BloqueoUsuarios.this, ""+usuarioList.get(i).getActivado(), Toast.LENGTH_SHORT).show();
                                usuarios.child(Integer.toString(i)).child("activado").setValue(usuarioList.get(i).getActivado());
                            }

                        }

                    }
                });

            }
        });

    }


}
