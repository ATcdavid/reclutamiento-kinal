package com.kinal.reclutamiento.model;

public class Usuario {
    private final int id;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String passwordHash;
    private final Rol rol;

    public Usuario(int id, String nombre, String apellido, String correo, String passwordHash, Rol rol) {
        this.id = id; this.nombre = nombre; this.apellido = apellido;
        this.correo = correo; this.passwordHash = passwordHash; this.rol = rol;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getPasswordHash() { return passwordHash; }
    public Rol getRol() { return rol; }
}
