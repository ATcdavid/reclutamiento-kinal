package com.kinal.reclutamiento.controller;

import com.kinal.reclutamiento.dto.RegistroUsuarioDTO;
import com.kinal.reclutamiento.dto.RolDTO;
import com.kinal.reclutamiento.exception.AppException;
import com.kinal.reclutamiento.service.AuthService;
import com.kinal.reclutamiento.view.AlertUtil;
import com.kinal.reclutamiento.view.SceneManager;
import com.kinal.reclutamiento.view.Vista;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {
    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<RolDTO> cmbRol;

    public RegistroController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void initialize() {
        try {
            cmbRol.setItems(FXCollections.observableArrayList(authService.listarRoles()));
        } catch (AppException e) {
            AlertUtil.error(e);
        }
    }

    @FXML
    private void onRegistrar() {
        try {
            authService.registrar(new RegistroUsuarioDTO(txtNombre.getText(), txtApellido.getText(),
                    txtCorreo.getText(), txtPassword.getText(), cmbRol.getValue()));
            AlertUtil.info("Registro exitoso", "Usuario creado. Ya puede iniciar sesión.");
            sceneManager.mostrar(Vista.LOGIN);
        } catch (AppException e) {
            AlertUtil.error(e);
        }
    }

    @FXML
    private void onVolver() {
        sceneManager.mostrar(Vista.LOGIN);
    }
}
