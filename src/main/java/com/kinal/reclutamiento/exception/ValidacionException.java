package com.kinal.reclutamiento.exception;

public class ValidacionException extends AppException {
    public ValidacionException(String mensaje) { super(mensaje); }
    @Override public String getTitulo() { return "Datos inválidos"; }
}
