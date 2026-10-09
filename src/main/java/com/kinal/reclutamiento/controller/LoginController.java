package com.kinal.reclutamiento.controller;

import com.kinal.reclutamiento.dto.LoginDTO;
import com.kinal.reclutamiento.dto.UsuarioDTO;
import com.kinal.reclutamiento.exception.AppException;
import com.kinal.reclutamiento.service.AuthService;
import com.kinal.reclutamiento.view.AlertUtil;
import com.kinal.reclutamiento.view.SceneManager;
import com.kinal.reclutamiento.view.SesionUsuario;
import com.kinal.reclutamiento.view.Vista;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private final AuthService authService;
    private final SceneManager sceneManager;
    private final SesionUsuario sesion;

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    public LoginController(AuthService authService, SceneManager sceneManager, SesionUsuario sesion) {
        this.authService = authService;
        this.sceneManager = sceneManager;
        this.sesion = sesion;
    }

    @FXML
    private void onIniciarSesion() {
        try {
            UsuarioDTO usuario = authService.iniciarSesion(
                    new LoginDTO(txtCorreo.getText(), txtPassword.getText()));
            sesion.iniciar(usuario);
            AlertUtil.info("Bienvenido", "Hola " + usuario.getNombreCompleto()
                    + "\nHa iniciado sesión con el rol: " + usuario.getRol());
            sceneManager.mostrar(Vista.INVENTARIO);
        } catch (AppException e) {
            AlertUtil.error(e);
        }
    }

    @FXML
    private void onIrARegistro() {
        sceneManager.mostrar(Vista.REGISTRO);
    }
}
