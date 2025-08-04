package com.example.hotelsierra.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ControladorPrincipal {

    public static String rolUsuario; // Almacenar el rol del usuario

    @FXML
    private Button btnCerrarSesion;
    private ControladorInicio controladorInicio; // Variable para guardar la referencia al controlador del Inicio
    @FXML
    private Label labelNombreUsuario;

    @FXML
    private Label labelRolUsuario;

    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private Tab tabInicio;

    @FXML
    private Tab tabUsuarios;
   @FXML
    private Tab tabClientes;
    @FXML
    private Tab tabHabitaciones;
    @FXML
    private Tab tabReservas;

    @FXML
    public void initialize() {
        try {
            // Cargar el contenido de cada pestaña
            AnchorPane inicioPane = FXMLLoader.load(getClass().getResource("/vistas/inicio.fxml"));
            tabInicio.setContent(inicioPane);

            AnchorPane usuariosPane = FXMLLoader.load(getClass().getResource("/vistas/usuario.fxml"));
            tabUsuarios.setContent(usuariosPane);

            AnchorPane clientesPane = FXMLLoader.load(getClass().getResource("/vistas/clientes.fxml"));
            tabClientes.setContent(clientesPane);

            AnchorPane habitacionesPane = FXMLLoader.load(getClass().getResource("/vistas/habitaciones.fxml"));
            tabHabitaciones.setContent(habitacionesPane);

            AnchorPane reservasPane = FXMLLoader.load(getClass().getResource("/vistas/reservas.fxml"));
            tabReservas.setContent(reservasPane);

            if ("Recepcionista".equalsIgnoreCase(rolUsuario)) {
                tabUsuarios.setDisable(true);
            }

            // Asociar el evento para actualizar el gráfico cada vez que se selecciona la pestaña de inicio
            tabInicio.setOnSelectionChanged(event -> {
                if (tabInicio.isSelected() && controladorInicio != null) {
                    controladorInicio.actualizarPieChart();
                }
            });

            // Cargar el FXML del Inicio y obtener el controlador correspondiente
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
                Parent root = loader.load();
                controladorInicio = loader.getController();
                tabInicio.setContent(root);
            } catch (IOException e) {
                System.err.println("Error al cargar el FXML de Inicio: " + e.getMessage());
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void cerrarSesion() {
        // Crear una alerta de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Cierre de Sesión");
        confirmacion.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
        confirmacion.setContentText("Se cerrará la sesión actual y se abrirá la ventana de inicio de sesión.");

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                // Cerrar la ventana actual
                Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
                stage.close();

                // Abrir la ventana de login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/login.fxml"));
                Parent root = loader.load();

                Stage loginStage = new Stage();
                loginStage.setTitle("Iniciar Sesión");
                loginStage.setScene(new Scene(root));
                loginStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar la ventana de inicio de sesión.", Alert.AlertType.ERROR);
            }
        }
    }


    // Método para setear el nombre y rol del usuario en la interfaz
    public void setUsuarioInfo(String nombreUsuario, String rolUsuario) {
        labelNombreUsuario.setText("Hola, " + nombreUsuario);
        labelRolUsuario.setText(rolUsuario);
    }


    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}