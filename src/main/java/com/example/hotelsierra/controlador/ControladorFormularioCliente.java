package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class ControladorFormularioCliente {


    @FXML
    private Button btnAceptarCliente;

    @FXML
    private Button btnCancelarCliente;

    @FXML
    private TextField campoApellidoCliente;

    @FXML
    private TextField campoCodigoPostalCliente;

    @FXML
    private TextField campoDireccionCliente;

    @FXML
    private TextField campoDocumentoCliente;

    @FXML
    private TextField campoEmailCliente;

    @FXML
    private TextField campoLocalidadCliente;

    @FXML
    private TextField campoNacionalidadCliente;

    @FXML
    private TextField campoPaisCliente;

    @FXML
    private TextField campoNombreCliente;

    @FXML
    private TextField campoProvinciaCliente;

    @FXML
    private TextField campoTelefonoCliente;

    @FXML
    private ComboBox<String> cbxTipoCliente;

    @FXML
    private ComboBox<String> cbxTipoDocumentoCliente;

    @FXML
    private DatePicker dpFechaDeNacimientoCliente;




    // Cliente actual (para modificación)
    private Cliente clienteActual;

    @FXML
    public void initialize() {
        // Agregar datos a los ComboBox
        cbxTipoCliente.getItems().addAll("Ocasional", "Habitual");
        cbxTipoDocumentoCliente.getItems().addAll("DNI", "Pasaporte");
    }

    // Botón Aceptar - Crea o modifica el usuario en la base de datos
    @FXML
    private void crearCliente() {
        try {
            // Obtener datos del formulario
            String nombre = campoNombreCliente.getText();
            String apellido = campoApellidoCliente.getText();
            String tipoDocumento = cbxTipoDocumentoCliente.getValue();
            String documento = campoDocumentoCliente.getText();
            LocalDate fechaNacimiento = dpFechaDeNacimientoCliente.getValue();
            String nacionalidad = campoNacionalidadCliente.getText();
            String direccion = campoDireccionCliente.getText();
            String localidad = campoLocalidadCliente.getText();
            String provincia = campoProvinciaCliente.getText();
            String codigoPostal = campoCodigoPostalCliente.getText();
            String email = campoEmailCliente.getText();
            String telefono = campoTelefonoCliente.getText();
            String tipoCliente = cbxTipoCliente.getValue();
            String pais = campoPaisCliente.getText();

            // Verificar si todos los campos están completos
            if (nombre.isEmpty() || apellido.isEmpty() || tipoDocumento == null || documento.isEmpty() || fechaNacimiento == null ||
                    nacionalidad.isEmpty() || direccion.isEmpty() || localidad.isEmpty() || provincia.isEmpty() || codigoPostal.isEmpty()
                    || email.isEmpty() || telefono.isEmpty() || tipoCliente == null || pais.isEmpty()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }
            // Validar la longitud del documento
            if (documento.length() < 7 || documento.length() > 9) {
                mostrarAlerta("Error", "El documento debe tener una longitud entre 7 y 9 caracteres.", Alert.AlertType.ERROR);
                return;
            }

            // Validar la mayoría de edad
            LocalDate fechaActual = LocalDate.now();
            Period edad = Period.between(fechaNacimiento, fechaActual);
            if (edad.getYears() < 18) {
                mostrarAlerta("Error", "El cliente debe ser mayor de edad (18 años o más).", Alert.AlertType.ERROR);
                return;
            }

            if (validarSoloLetras(nombre) || validarSoloLetras(apellido) || validarSoloLetras(pais) ||
                    validarSoloLetras(provincia) || validarSoloLetras(nacionalidad)) {
                mostrarAlerta("Error", "Los campos Nombre, Apellido, Nacionalidad, Provincia y País solo deben contener letras.", Alert.AlertType.ERROR);
                return;
            }

            if (validarSoloNumeros(telefono)) {
                mostrarAlerta("Error", "El campo Teléfono solo debe contener números.", Alert.AlertType.ERROR);
                return;
            }

            if (validarEmail(email)) {
                mostrarAlerta("Error", "El correo electrónico debe contener '@'.", Alert.AlertType.ERROR);
                return;
            }


            Cliente nuevoCliente = new Cliente();
            boolean exito = nuevoCliente.crearCliente(nombre, apellido, tipoDocumento, documento, fechaNacimiento, nacionalidad, direccion,
                    localidad, provincia, codigoPostal, email, telefono, tipoCliente, pais);

            if (exito) {
                // Cerrar la ventana si la creación fue exitosa
                mostrarAlerta("Éxito", "El cliente ha sido creado correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarCliente.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo crear el cliente.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Código de error SQL que indica violación de la restricción UNIQUE
                mostrarAlerta("Error", "Ya existe un cliente registrado con el documento proporcionado.", Alert.AlertType.ERROR);
            } else {
                // Otros errores de SQL
                mostrarAlerta("Error", "Error en la base de datos. Por favor, intente nuevamente.", Alert.AlertType.ERROR);
                System.err.println("Error SQL: " + e.getMessage());
            }
        }
    }

    @FXML
    private void modificarCliente() {
        if (clienteActual == null) {
            mostrarAlerta("Error", "No se ha seleccionado un cliente para modificar.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Obtener datos del formulario
            String nombre = campoNombreCliente.getText();
            String apellido = campoApellidoCliente.getText();
            String tipoDocumento = cbxTipoDocumentoCliente.getValue();
            String documento = campoDocumentoCliente.getText();
            LocalDate fechaNacimiento = dpFechaDeNacimientoCliente.getValue();
            String nacionalidad = campoNacionalidadCliente.getText();
            String direccion = campoDireccionCliente.getText();
            String localidad = campoLocalidadCliente.getText();
            String provincia = campoProvinciaCliente.getText();
            String codigoPostal = campoCodigoPostalCliente.getText();
            String email = campoEmailCliente.getText();
            String telefono = campoTelefonoCliente.getText();
            String tipoCliente = cbxTipoCliente.getValue();
            String pais = campoPaisCliente.getText();

            // Verificar si todos los campos están completos
            if (nombre.isEmpty() || apellido.isEmpty() || tipoDocumento == null || documento.isEmpty() || fechaNacimiento == null ||
                    nacionalidad.isEmpty() || direccion.isEmpty() || localidad.isEmpty() || provincia.isEmpty() || codigoPostal.isEmpty()
                    || email.isEmpty() || telefono.isEmpty() || tipoCliente == null || pais.isEmpty()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }

            // Validar la longitud del documento
            if (documento.length() < 7 || documento.length() > 9) {
                mostrarAlerta("Error", "El documento debe tener una longitud entre 7 y 9 caracteres.", Alert.AlertType.ERROR);
                return;
            }

            // Validar la mayoría de edad
            LocalDate fechaActual = LocalDate.now();
            Period edad = Period.between(fechaNacimiento, fechaActual);
            if (edad.getYears() < 18) {
                mostrarAlerta("Error", "El cliente debe ser mayor de edad (18 años o más).", Alert.AlertType.ERROR);
                return;
            }


            if (validarSoloLetras(nombre) || validarSoloLetras(apellido) || validarSoloLetras(pais) ||
                    validarSoloLetras(provincia) || validarSoloLetras(nacionalidad)) {
                mostrarAlerta("Error", "Los campos Nombre, Apellido, País, Provincia y Nacionalidad solo deben contener letras.", Alert.AlertType.ERROR);
                return;
            }

            if (validarSoloNumeros(telefono)) {
                mostrarAlerta("Error", "El campo Teléfono solo debe contener números.", Alert.AlertType.ERROR);
                return;
            }

            if (validarEmail(email)) {
                mostrarAlerta("Error", "El correo electrónico debe contener '@'.", Alert.AlertType.ERROR);
                return;
            }

            // Modificar el cliente existente
            boolean exito = clienteActual.modificarCliente(clienteActual.getIdCliente(), nombre, apellido, tipoDocumento, documento, fechaNacimiento, nacionalidad, direccion,
                    localidad, provincia, codigoPostal, email, telefono, tipoCliente, pais);

            if (exito) {
                // Cerrar la ventana si la modificación fue exitosa
                mostrarAlerta("Éxito", "El cliente ha sido modificado correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarCliente.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo modificar el cliente.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Código de error SQL que indica violación de la restricción UNIQUE
                mostrarAlerta("Error", "Ya existe un cliente registrado con el documento proporcionado.", Alert.AlertType.ERROR);
            } else {
                // Otros errores de SQL
                mostrarAlerta("Error", "Error en la base de datos. Por favor, intente nuevamente.", Alert.AlertType.ERROR);
                System.err.println("Error SQL: " + e.getMessage());
            }
        }
    }


    //Boton Cancelar
    @FXML
    private void cancelarCliente(){
        Stage stage = (Stage) btnCancelarCliente.getScene().getWindow();
        stage.close();

    }


    // Método para cargar los datos del usuario en el formulario
    public void cargarDatos(Cliente cliente) {
        clienteActual = cliente;


        campoNombreCliente.setText(cliente.getNombre());
        campoApellidoCliente.setText(cliente.getApellido());
        cbxTipoDocumentoCliente.setValue(cliente.getTipoDocumento());
        campoDocumentoCliente.setText(cliente.getDocumento());
        dpFechaDeNacimientoCliente.setValue(cliente.getFechaNacimiento());
        campoNacionalidadCliente.setText(cliente.getNacionalidad());
        campoDireccionCliente.setText(cliente.getDireccion());
        campoLocalidadCliente.setText(cliente.getLocalidad());
        campoProvinciaCliente.setText(cliente.getProvincia());
        campoPaisCliente.setText(cliente.getPais());
        campoCodigoPostalCliente.setText(cliente.getCodigoPostal());
        campoEmailCliente.setText(cliente.getEmail());
        campoTelefonoCliente.setText(cliente.getTelefono());
        cbxTipoCliente.setValue(cliente.getTipoCliente());




    }

    // Valida que el campo solo tenga letras
    private boolean validarSoloLetras(String texto) {
        return !texto.matches("[a-zA-Z\\s]+");
    }

    // Valida que el campo solo tenga números
    private boolean validarSoloNumeros(String texto) {
        return !texto.matches("\\d+");
    }

    // Valida un email con el símbolo '@'
    private boolean validarEmail(String email) {
        return !email.contains("@");
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
