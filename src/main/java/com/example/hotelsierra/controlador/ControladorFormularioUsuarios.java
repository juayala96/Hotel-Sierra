package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ControladorFormularioUsuarios {

    // Campos del formulario
    @FXML
    private TextField campoNombreUsuario;
    @FXML
    private TextField campoApellidoUsuario;
    @FXML
    private TextField campoDniUsuario;
    @FXML
    private TextField campoEmailUsuario;
    @FXML
    private TextField campoContraseniaUsuario;
    @FXML
    private ComboBox<String> cbxRolUsuario;
    @FXML
    private Button btnAceptarUsuario;
    @FXML
    private Button btnCancelarUsuario;

    // Usuario actual (para modificación)
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        // Agregar roles al ComboBox
        cbxRolUsuario.getItems().addAll("Administrador", "Recepcionista");
    }

    // Botón Aceptar - Crea o modifica el usuario en la base de datos
    @FXML
    private void crearUsuario() {
        try {
            // Obtener datos del formulario
            String nombre = campoNombreUsuario.getText();
            String apellido = campoApellidoUsuario.getText();
            int dni = Integer.parseInt(campoDniUsuario.getText());
            String email = campoEmailUsuario.getText();
            String contrasenia = campoContraseniaUsuario.getText();
            String rol = cbxRolUsuario.getValue();

            // Verificar si todos los campos están completos
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || contrasenia.isEmpty() || rol == null) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }

            if (validarSoloLetras(nombre) || validarSoloLetras(apellido)) {
                mostrarAlerta("Error", "Los campos Nombre y Apellido solo deben contener letras.", Alert.AlertType.ERROR);
                return;
            }

            if (validarEmail(email)) {
                mostrarAlerta("Error", "El correo electrónico debe contener '@'.", Alert.AlertType.ERROR);
                return;
            }

            // Crear un nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            boolean exito = nuevoUsuario.crearUsuario(nombre, apellido, dni, email, contrasenia, rol);

            if (exito) {
                mostrarAlerta("Éxito", "El usuario ha sido creado correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarUsuario.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo crear el usuario.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Código de error SQL que indica violación de la restricción UNIQUE
                mostrarAlerta("Error", "Ya existe un usuario registrado con el DNI proporcionado.", Alert.AlertType.ERROR);
            } else {
                // Otros errores de SQL
                mostrarAlerta("Error", "Error en la base de datos. Por favor, intente nuevamente.", Alert.AlertType.ERROR);
                System.err.println("Error SQL: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            // Manejar errores por valores incorrectos en los campos
            mostrarAlerta("Error", "Debe ingresar un valor numérico válido para el DNI.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modificarUsuario() {
        try {
            // Obtener datos del formulario
            String nombre = campoNombreUsuario.getText();
            String apellido = campoApellidoUsuario.getText();
            int dni = Integer.parseInt(campoDniUsuario.getText());
            String email = campoEmailUsuario.getText();
            String contrasenia = campoContraseniaUsuario.getText();
            String rol = cbxRolUsuario.getValue();

            // Verificar si todos los campos están completos
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || contrasenia.isEmpty() || rol == null) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }
            if (validarSoloLetras(nombre) || validarSoloLetras(apellido)) {
                mostrarAlerta("Error", "Los campos Nombre y Apellido solo deben contener letras.", Alert.AlertType.ERROR);
                return;
            }

            if (validarEmail(email)) {
                mostrarAlerta("Error", "El correo electrónico debe contener '@'.", Alert.AlertType.ERROR);
                return;
            }

            // Modificar el usuario existente
            boolean exito = usuarioActual.modificarUsuario(usuarioActual.getIdUsuario(), nombre, apellido, dni, email, contrasenia, rol);

            if (exito) {
                // Cerrar la ventana si la modificación fue exitosa
                mostrarAlerta("Éxito", "El usuario ha sido modificado correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarUsuario.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo modificar el usuario.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Código de error SQL que indica violación de la restricción UNIQUE
                mostrarAlerta("Error", "Ya existe un usuario registrado con el DNI proporcionado.", Alert.AlertType.ERROR);
            } else {
                // Otros errores de SQL
                mostrarAlerta("Error", "Error en la base de datos. Por favor, intente nuevamente.", Alert.AlertType.ERROR);
                System.err.println("Error SQL: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            // Manejar errores por valores incorrectos en los campos
            mostrarAlerta("Error", "Debe ingresar un valor numérico válido para el DNI.", Alert.AlertType.ERROR);
        }
    }





    //Boton Cancelar
    @FXML
    private void cancelarUsuario(){
        Stage stage = (Stage) btnCancelarUsuario.getScene().getWindow();
        stage.close();

    }


    // Método para cargar los datos del usuario en el formulario
    public void cargarDatos(Usuario usuario) {
        usuarioActual = usuario;

        // Cargar datos en los campos
        campoNombreUsuario.setText(usuario.getNombre());
        campoApellidoUsuario.setText(usuario.getApellido());
        campoDniUsuario.setText(String.valueOf(usuario.getDni()));
        campoEmailUsuario.setText(usuario.getEmail());
        campoContraseniaUsuario.setText(usuario.getContrasenia());
        cbxRolUsuario.setValue(usuario.getRol());
    }

    // Valida que el campo solo tenga letras
    private boolean validarSoloLetras(String texto) {
        return !texto.matches("[a-zA-Z\\s]+");
    }

    // Valida que el campo solo tenga números
    private boolean validarSoloNumeros(String texto) {
        return texto.matches("\\d+");
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
