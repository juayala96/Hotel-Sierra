package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Habitacion;
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

public class ControladorHabitaciones {

    @FXML
    private Button btnCrearHabitacion;

    @FXML
    private Button btnEliminarHabitacion;

    @FXML
    private Button btnModificarHabitacion;

    @FXML
    private Button btnRecargaTablaHabitaciones;

    @FXML
    private TableColumn<Habitacion, Integer> colNumeroHabitacion;

    @FXML
    private TableColumn<Habitacion, Integer> colCapacidadHabitacion;

    @FXML
    private TableColumn<Habitacion, Integer> colCamasHabitacion;
    @FXML
    private TableColumn<Habitacion, Integer> colCamasSingleHabitacion;
    @FXML
    private TableColumn<Habitacion, Integer> colCamasDobleHabitacion;

    @FXML
    private TableColumn<Habitacion, Integer> colCategoriaHabitacion;

    @FXML
    private TableColumn<Habitacion, Double> colPrecioHabitacion;

    @FXML
    private TableColumn<Habitacion, String> colEstadoHabitacion;


    @FXML
    private TableView<Habitacion> tablaHabitaciones;

    private ObservableList<Habitacion> listaHabitaciones;

    @FXML
    public void initialize() {
        // Configurando las columnas de la tabla para enlazarlas con los atributos de Habitacion
        colNumeroHabitacion.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colCapacidadHabitacion.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        colCamasHabitacion.setCellValueFactory(new PropertyValueFactory<>("cantidadCamas"));
        colCamasSingleHabitacion.setCellValueFactory(new PropertyValueFactory<>("camasSingle"));
        colCamasDobleHabitacion.setCellValueFactory(new PropertyValueFactory<>("camasDoble"));
        colCategoriaHabitacion.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecioHabitacion.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstadoHabitacion.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar datos en la tabla
        cargarHabitacionesEnTabla();
    }


    @FXML
    private void recargarTablaHabitaciones() {
        cargarHabitacionesEnTabla();
    }

    public void cargarHabitacionesEnTabla() {
        listaHabitaciones = Habitacion.obtenerListaHabitaciones();
        tablaHabitaciones.setItems(listaHabitaciones);
    }

    //-------- Crear habitacion ----//
    @FXML
    private void abrirFormularioHabitacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formHabitacion.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Habitación");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaHabitaciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarHabitacion() {
        Habitacion habitacionSeleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();

        if (habitacionSeleccionada != null) {
            abrirFormularioHabitacion(habitacionSeleccionada);
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una habitacion.", Alert.AlertType.WARNING);
        }
    }

    //Modificar habitacion
    private void abrirFormularioHabitacion(Habitacion habitacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formModificarHabitacion.fxml"));
            Parent root = loader.load();

            ControladorFormularioHabitacion controladorFormulario = loader.getController();
            controladorFormulario.cargarDatos(habitacion);

            Stage stage = new Stage();
            stage.setTitle("Modificar Habitacion");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaHabitaciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarHabitacion() {
        // Obtener el habitacion seleccionado en la tabla
        Habitacion habitacionSeleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();

        if (habitacionSeleccionada != null) {
            // Confirmación antes de eliminar el habitacion
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar Habitación");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar la habitación seleccionada?");

            // Esperar la respuesta del habitacion
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    boolean exito = habitacionSeleccionada.eliminarHabitacion(habitacionSeleccionada.getIdHabitacion());
                    if (exito) {
                        // Eliminar el habitacion de la tabla también
                        listaHabitaciones.remove(habitacionSeleccionada);
                        tablaHabitaciones.refresh();

                        mostrarAlerta("Éxito", "La habitacion ha sido eliminada correctamente.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar la habitación.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una habitacion para eliminar.", Alert.AlertType.WARNING);
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

