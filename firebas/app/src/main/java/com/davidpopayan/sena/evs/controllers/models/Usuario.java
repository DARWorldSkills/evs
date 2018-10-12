package com.davidpopayan.sena.evs.controllers.models;

/**
 * Created by PRREK on 11/10/2018.
 */

public class Usuario {
    private String username;
    private String contrasena;

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
