package com.kinal.reclutamiento.exception;

public class AuthException extends AppException {
    public AuthException(String mensaje) { super(mensaje); }
    @Override public String getTitulo() { return "Error de autenticación"; }
}
