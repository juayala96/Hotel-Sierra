package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.LoginUsuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorLogin {
    public ControladorLogin() {}

    @FXML
    private TextField campoDni;
    @FXML
    private PasswordField campoContrasenia;
    @FXML
    private Button btnIngresarLogin;

    // ------------------------------------------- Login Botón (Aceptar) -------------------------------------------------
    @FXML
    private void ingresarLogin(ActionEvent event) throws IOException {
        if (comprobarValoresNumericos(campoDni) && comprobarValores(campoContrasenia)) {
            int dni = Integer.parseInt(campoDni.getText());
            String contrasenia = campoContrasenia.getText();

            LoginUsuario usuarioLogin = new LoginUsuario();
            Integer idUsuario = usuarioLogin.loginUsuario(dni, contrasenia);

            if (idUsuario != null) {
                String nombreUsuario = usuarioLogin.obtenerNombreUsuario(idUsuario);
                String rolUsuario = usuarioLogin.obtenerRolDelUsuario(idUsuario);
                if (rolUsuario.equalsIgnoreCase("Recepcionista")) {
                    ControladorPrincipal.rolUsuario = "Recepcionista";
                } else if (rolUsuario.equalsIgnoreCase("Administrador")) {
                    ControladorPrincipal.rolUsuario = "Administrador";
                }
                abrirPantallaPrincipal(nombreUsuario, rolUsuario);
            } else {
                mostrarAlerta("Iniciar sesión", "DNI o contraseña incorrectos.");
            }

        } else {
            mostrarAlerta("Iniciar sesión", "Debe completar todos los campos correctamente y asegurarse de que el DNI contenga solo números.");
        }
    }

    // ------------------------------------------- Botón Salir -------------------------------------------------
    @FXML
    private void salirLogin() {
        Platform.exit();
    }

    // ---------------------------------- Validar Campos ------------------------------------
    private boolean comprobarValoresNumericos(TextField campo) {
        String valor = campo.getText();
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(valor);
            return valor.length() >= 7; // Puedes ajustar este mínimo si es necesario
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean comprobarValores(PasswordField campo) {
        String valor = campo.getText();
        return valor != null && !valor.trim().isEmpty() && valor.length() >= 4;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void abrirPantallaPrincipal(String nombreUsuario, String rolUsuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/principal.fxml"));
        BorderPane root = loader.load();

        // Obtener el controlador de la vista principal y setear la información del usuario
        ControladorPrincipal controladorPrincipal = loader.getController();
        controladorPrincipal.setUsuarioInfo(nombreUsuario, rolUsuario);

        Scene escena = new Scene(root);
        Stage escenario = new Stage();
        escenario.setTitle("Hotel Sierra");
        escenario.setScene(escena);
        //escenario.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/imagenes/icono.png")).toString()));
        escenario.show();

        // Cerrar ventana de inicio de sesión al abrir la principal
        Stage myEscena = (Stage) this.btnIngresarLogin.getScene().getWindow();
        myEscena.close();
    }





}
