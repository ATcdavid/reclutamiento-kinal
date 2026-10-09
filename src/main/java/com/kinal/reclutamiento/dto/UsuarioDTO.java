package com.kinal.reclutamiento.dto;

/** Datos de usuario que viajan a la vista (nunca incluye el hash). */
public class UsuarioDTO {
    private final int id;
    private final String nombreCompleto;
    private final String correo;
    private final String rol;

    public UsuarioDTO(int id, String nombreCompleto, String correo, String rol) {
        this.id = id; this.nombreCompleto = nombreCompleto; this.correo = correo; this.rol = rol;
    }
    public int getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
}
