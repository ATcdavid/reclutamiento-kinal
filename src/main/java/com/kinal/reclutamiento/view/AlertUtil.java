package com.kinal.reclutamiento.view;

import com.kinal.reclutamiento.exception.AppException;
import com.kinal.reclutamiento.exception.ValidacionException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AlertUtil {
    private AlertUtil() { }

    public static void info(String titulo, String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    public static void error(AppException e) {
        Alert.AlertType tipo = (e instanceof ValidacionException)
                ? Alert.AlertType.WARNING : Alert.AlertType.ERROR;
        mostrar(tipo, e.getTitulo(), e.getMessage());
    }

    public static boolean confirmar(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        return alert.showAndWait().filter(b -> b == ButtonType.YES).isPresent();
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
