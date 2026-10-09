package com.kinal.reclutamiento.dto;

public class LoginDTO {
    private final String correo;
    private final String password;
    public LoginDTO(String correo, String password) { this.correo = correo; this.password = password; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
}
