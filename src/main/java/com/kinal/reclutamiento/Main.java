package com.kinal.reclutamiento;

import com.kinal.reclutamiento.config.DatabaseConnection;
import com.kinal.reclutamiento.controller.InventarioController;
import com.kinal.reclutamiento.controller.LoginController;
import com.kinal.reclutamiento.controller.RegistroController;
import com.kinal.reclutamiento.repository.AspiranteRepository;
import com.kinal.reclutamiento.repository.AspiranteRepositoryJdbc;
import com.kinal.reclutamiento.repository.RolRepository;
import com.kinal.reclutamiento.repository.RolRepositoryJdbc;
import com.kinal.reclutamiento.repository.UsuarioRepository;
import com.kinal.reclutamiento.repository.UsuarioRepositoryJdbc;
import com.kinal.reclutamiento.service.AspiranteService;
import com.kinal.reclutamiento.service.AspiranteServiceImpl;
import com.kinal.reclutamiento.service.AuthService;
import com.kinal.reclutamiento.service.AuthServiceImpl;
import com.kinal.reclutamiento.view.SceneManager;
import com.kinal.reclutamiento.view.SesionUsuario;
import com.kinal.reclutamiento.view.Vista;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.application.Application;
import javafx.stage.Stage;

/** Raíz de composición: aquí (y solo aquí) se crean e inyectan las dependencias. */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseConnection database = new DatabaseConnection();

        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc(database);
        RolRepository rolRepository = new RolRepositoryJdbc(database);
        AspiranteRepository aspiranteRepository = new AspiranteRepositoryJdbc(database);

        AuthService authService = new AuthServiceImpl(usuarioRepository, rolRepository);
        AspiranteService aspiranteService = new AspiranteServiceImpl(aspiranteRepository);

        SesionUsuario sesion = new SesionUsuario();
        SceneManager sceneManager = new SceneManager(stage);

        Map<Class<?>, Supplier<Object>> fabricas = new HashMap<>();
        fabricas.put(LoginController.class,
                () -> new LoginController(authService, sceneManager, sesion));
        fabricas.put(RegistroController.class,
                () -> new RegistroController(authService, sceneManager));
        fabricas.put(InventarioController.class,
                () -> new InventarioController(aspiranteService, sceneManager, sesion));

        sceneManager.setControllerFactory(tipo -> fabricas.get(tipo).get());
        sceneManager.mostrar(Vista.LOGIN);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
