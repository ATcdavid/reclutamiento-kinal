package com.kinal.reclutamiento.dto;

public class RegistroUsuarioDTO {
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String password;
    private final RolDTO rol;

    public RegistroUsuarioDTO(String nombre, String apellido, String correo, String password, RolDTO rol) {
        this.nombre = nombre; this.apellido = apellido; this.correo = correo;
        this.password = password; this.rol = rol;
    }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public RolDTO getRol() { return rol; }
}
