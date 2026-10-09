package com.kinal.reclutamiento.exception;

/** Excepción base del dominio; cada subtipo define el título del Alert. */
public abstract class AppException extends RuntimeException {
    protected AppException(String mensaje) { super(mensaje); }
    protected AppException(String mensaje, Throwable causa) { super(mensaje, causa); }
    public abstract String getTitulo();
}
