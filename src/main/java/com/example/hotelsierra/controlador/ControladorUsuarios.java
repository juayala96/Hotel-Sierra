package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Usuario;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorUsuarios {

    //Botones
    @FXML
    private Button btnCrearUsuario;

    @FXML
    private Button btnEliminarUsuario;

    @FXML
    private Button btnModificarUsuario;

    @FXML
    private Button btnRecargarTablaUsuarios;

    //Tabla
    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;

    @FXML
    private TableColumn<Usuario, String> colApellidoUsuario;

    @FXML
    private TableColumn<Usuario, String> colContraseniaUsuario;

    @FXML
    private TableColumn<Usuario, Integer> colDniUsuario;

    @FXML
    private TableColumn<Usuario, String> colEmailUsuario;

    @FXML
    private TableColumn<Usuario, String> colRolUsuario;

    private ObservableList<Usuario> listaUsuarios;

    @FXML
    public void initialize() {
        // Configurando las columnas de la tabla
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoUsuario.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colDniUsuario.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colEmailUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContraseniaUsuario.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));
        colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("rol"));

        // Cargar datos en la tabla
        cargarUsuariosEnTabla();
    }

    @FXML
    private void recargarTablaUsuarios() {
        cargarUsuariosEnTabla();
    }

    public void cargarUsuariosEnTabla() {
        listaUsuarios = Usuario.obtenerListaUsuarios();
        tablaUsuarios.setItems(listaUsuarios);
    }

    //-------- Crear usuario ----//
    @FXML
    private void abrirFormularioUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Usuario");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaUsuarios();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarUsuario() {
        Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            abrirFormularioUsuario(usuarioSeleccionado);
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona un usuario.", Alert.AlertType.WARNING);
        }
    }

    private void abrirFormularioUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formModificarUsuario.fxml"));
            Parent root = loader.load();

            ControladorFormularioUsuarios controladorFormulario = loader.getController();
            controladorFormulario.cargarDatos(usuario);

            Stage stage = new Stage();
            stage.setTitle("Modificar Usuario");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaUsuarios();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarUsuario() {
        // Obtener el usuario seleccionado en la tabla
        Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            // Confirmación antes de eliminar el usuario
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar Usuario");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar al usuario seleccionado?");

            // Esperar la respuesta del usuario
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    boolean exito = usuarioSeleccionado.eliminarUsuario(usuarioSeleccionado.getIdUsuario());

                    if (exito) {
                        // Eliminar el usuario de la tabla también
                        listaUsuarios.remove(usuarioSeleccionado);
                        tablaUsuarios.refresh();

                        mostrarAlerta("Éxito", "El usuario ha sido eliminado correctamente.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el usuario.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona un usuario para eliminar.", Alert.AlertType.WARNING);
        }
    }




    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}