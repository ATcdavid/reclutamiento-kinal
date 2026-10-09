package com.kinal.reclutamiento.view;

public enum Vista {
    LOGIN("/view/login.fxml", "Iniciar sesión"),
    REGISTRO("/view/registro.fxml", "Crear cuenta"),
    INVENTARIO("/view/inventario.fxml", "Inventario de aspirantes");

    private final String ruta;
    private final String titulo;

    Vista(String ruta, String titulo) { this.ruta = ruta; this.titulo = titulo; }
    public String getRuta() { return ruta; }
    public String getTitulo() { return titulo; }
}
