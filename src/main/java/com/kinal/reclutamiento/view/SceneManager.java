package com.kinal.reclutamiento.view;

import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/** Orquestador de vistas: único lugar que carga FXML y cambia la escena. */
public class SceneManager {
    private final Stage stage;
    private Callback<Class<?>, Object> controllerFactory;

    public SceneManager(Stage stage) { this.stage = stage; }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void mostrar(Vista vista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista.getRuta()));
            loader.setControllerFactory(controllerFactory);
            Parent raiz = loader.load();
            stage.setTitle(vista.getTitulo());
            stage.setScene(new Scene(raiz));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo cargar la vista " + vista, e);
        }
    }
}
