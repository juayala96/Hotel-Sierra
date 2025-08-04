package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Cliente;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladorClientes {

    //Botones

    @FXML
    private Button btnCrearCliente;

    @FXML
    private Button btnEliminarCliente;

    @FXML
    private Button btnModificarCliente;

    @FXML
    private Button btnRecargarTablaClientes;

    //Tabla Clientes

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> colApellidoCliente;

    @FXML
    private TableColumn<Cliente, String> colCodigoPostalCliente;

    @FXML
    private TableColumn<Cliente, String> colDireccionCliente;

    @FXML
    private TableColumn<Cliente, String> colDocumentoCliente;

    @FXML
    private TableColumn<Cliente, String> colEmailCliente;

    @FXML
    private TableColumn<Cliente, String> colFechaNacimientoCliente = new TableColumn<>("Fecha de Nacimiento");

    @FXML
    private TableColumn<Cliente, String> colLocalidadCliente;

    @FXML
    private TableColumn<Cliente, String> colPaisCliente;

    @FXML
    private TableColumn<Cliente, String> colNacionalidadCliente;

    @FXML
    private TableColumn<Cliente, String> colNombreCliente;

    @FXML
    private TableColumn<Cliente, String> colProvinciaCliente;

    @FXML
    private TableColumn<Cliente, String> colTelefonoCliente;

    @FXML
    private TableColumn<Cliente, String> colTipoDeCliente;

    @FXML
    private TableColumn<Cliente, String> colTipoDocumentoCliente;



    private ObservableList<Cliente> listaClientes;

    // Formato de fecha deseado
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    public void initialize() {
        // Configurando las columnas de la tabla
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTipoDocumentoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        colDocumentoCliente.setCellValueFactory(new PropertyValueFactory<>("documento"));

        colFechaNacimientoCliente.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFechaNacimiento();
            return new SimpleStringProperty(fecha != null ? fecha.format(formatter) : "");});

        //colFechaNacimientoCliente.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colNacionalidadCliente.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colLocalidadCliente.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        colProvinciaCliente.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        colCodigoPostalCliente.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        colEmailCliente.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTipoDeCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colPaisCliente.setCellValueFactory(new PropertyValueFactory<>("pais"));

        // Cargar datos en la tabla
        cargarClientesEnTabla();
    }

    @FXML
    private void recargarTablaClientes() {
        cargarClientesEnTabla();
    }

    public void cargarClientesEnTabla() {
        listaClientes = Cliente.obtenerListaClientes();
        tablaClientes.setItems(listaClientes);
    }

    //-------- Crear cliente ----//
    @FXML
    private void abrirFormularioCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formCliente.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaClientes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            abrirFormularioCliente(clienteSeleccionado);
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona un cliente.", Alert.AlertType.WARNING);
        }
    }

    private void abrirFormularioCliente(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formModificarCliente.fxml"));
            Parent root = loader.load();

            ControladorFormularioCliente controladorFormulario = loader.getController();
            controladorFormulario.cargarDatos(cliente);

            Stage stage = new Stage();
            stage.setTitle("Modificar Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaClientes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarCliente() {
        // Obtener el cliente seleccionado en la tabla
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            // Confirmación antes de eliminar el cliente
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar Cliente");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar al cliente seleccionado?");

            // Esperar la respuesta del cliente
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    boolean exito = clienteSeleccionado.eliminarCliente(clienteSeleccionado.getIdCliente());

                    if (exito) {
                        // Eliminar el cliente de la tabla también
                        listaClientes.remove(clienteSeleccionado);
                        tablaClientes.refresh();

                        mostrarAlerta("Éxito", "El cliente ha sido eliminado correctamente.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el cliente.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona un cliente para eliminar.", Alert.AlertType.WARNING);
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
