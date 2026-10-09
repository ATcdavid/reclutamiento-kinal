package com.kinal.reclutamiento.controller;

import com.kinal.reclutamiento.dto.AspiranteDTO;
import com.kinal.reclutamiento.exception.AppException;
import com.kinal.reclutamiento.exception.ValidacionException;
import com.kinal.reclutamiento.service.AspiranteService;
import com.kinal.reclutamiento.view.AlertUtil;
import com.kinal.reclutamiento.view.SceneManager;
import com.kinal.reclutamiento.view.SesionUsuario;
import com.kinal.reclutamiento.view.Vista;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventarioController {
    private final AspiranteService aspiranteService;
    private final SceneManager sceneManager;
    private final SesionUsuario sesion;

    @FXML private Label lblUsuario;
    @FXML private TableView<AspiranteDTO> tablaAspirantes;
    @FXML private TableColumn<AspiranteDTO, Integer> colId;
    @FXML private TableColumn<AspiranteDTO, String> colNombre;
    @FXML private TableColumn<AspiranteDTO, String> colApellido;
    @FXML private TableColumn<AspiranteDTO, String> colCorreo;
    @FXML private TableColumn<AspiranteDTO, String> colTelefono;
    @FXML private TableColumn<AspiranteDTO, String> colDireccion;
    @FXML private TableColumn<AspiranteDTO, Integer> colEdad;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEdad;

    private int idSeleccionado = 0;

    public InventarioController(AspiranteService aspiranteService, SceneManager sceneManager,
                                SesionUsuario sesion) {
        this.aspiranteService = aspiranteService;
        this.sceneManager = sceneManager;
        this.sesion = sesion;
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        tablaAspirantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, actual) -> {
                    if (actual != null) {
                        cargarFormulario(actual);
                    }
                });

        if (sesion.getUsuario() != null) {
            lblUsuario.setText(sesion.getUsuario().getNombreCompleto()
                    + "  |  Rol: " + sesion.getUsuario().getRol());
        }
        refrescarTabla();
    }

    @FXML
    private void onAgregar() {
        ejecutar(() -> {
            aspiranteService.agregar(leerFormulario(0));
            AlertUtil.info("Aspirante agregado", "El aspirante se registró correctamente.");
        });
    }

    @FXML
    private void onActualizar() {
        ejecutar(() -> {
            aspiranteService.actualizar(leerFormulario(idSeleccionado));
            AlertUtil.info("Aspirante actualizado", "Los datos se actualizaron correctamente.");
        });
    }

    @FXML
    private void onEliminar() {
        ejecutar(() -> {
            if (idSeleccionado <= 0) {
                throw new ValidacionException("Seleccione un aspirante de la tabla para eliminar.");
            }
            if (AlertUtil.confirmar("Eliminar aspirante", "¿Desea eliminar al aspirante seleccionado?")) {
                aspiranteService.eliminar(idSeleccionado);
                AlertUtil.info("Aspirante eliminado", "El registro fue eliminado.");
            }
        });
    }

    @FXML
    private void onLimpiar() {
        tablaAspirantes.getSelectionModel().clearSelection();
        limpiarFormulario();
    }

    @FXML
    private void onCerrarSesion() {
        sesion.cerrar();
        sceneManager.mostrar(Vista.LOGIN);
    }

    /** Ejecuta una acción y deja tabla y formulario actualizados, mostrando Alerts si falla. */
    private void ejecutar(Runnable accion) {
        try {
            accion.run();
            tablaAspirantes.getSelectionModel().clearSelection();
            limpiarFormulario();
            refrescarTabla();
        } catch (AppException e) {
            AlertUtil.error(e);
        }
    }

    private void refrescarTabla() {
        try {
            tablaAspirantes.setItems(FXCollections.observableArrayList(aspiranteService.listar()));
        } catch (AppException e) {
            AlertUtil.error(e);
        }
    }

    private AspiranteDTO leerFormulario(int id) {
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("La edad debe ser un número entero.");
        }
        return new AspiranteDTO(id, txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtDireccion.getText(), edad);
    }

    private void cargarFormulario(AspiranteDTO a) {
        idSeleccionado = a.getId();
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtCorreo.setText(a.getCorreo());
        txtTelefono.setText(a.getTelefono());
        txtDireccion.setText(a.getDireccion());
        txtEdad.setText(String.valueOf(a.getEdad()));
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEdad.clear();
    }
}
