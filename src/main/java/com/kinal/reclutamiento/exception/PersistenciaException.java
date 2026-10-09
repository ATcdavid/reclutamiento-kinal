package com.kinal.reclutamiento.exception;

public class PersistenciaException extends AppException {
    public PersistenciaException(String mensaje, Throwable causa) { super(mensaje, causa); }
    @Override public String getTitulo() { return "Error de base de datos"; }
}
