package com.example.hotelsierra.controlador;

import com.example.hotelsierra.Conexion;
import com.example.hotelsierra.modelo.Habitacion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;


public class ControladorFormularioHabitacion {

    @FXML
    private Button btnAceptarHabitacion;

    @FXML
    private Button btnCancelarHabitacion;

    @FXML
    private TextField campoCamasHabitacion;

    @FXML
    private TextField campoCapacidadHabitacion;

    @FXML
    private TextField campoNumeroHabitacion;

    @FXML
    private TextField campoPrecioHabitacion;

    @FXML
    private TextField campoCamaSingle;

    @FXML
    private TextField campoCamaDoble;

    @FXML
    private ComboBox<String> cbxCategoriaHabitacion;

    @FXML
    private ComboBox<String> cbxEstadoHabitacion;


    // Cliente actual (para modificación)
    private Habitacion habitacionActual;

    @FXML
    public void initialize() {
        // Agregar datos a los ComboBox
        cbxCategoriaHabitacion.getItems().addAll("Estándar", "Lujo");
        //cbxTipoCamaHabitacion.getItems().addAll("Single", "Doble");
        //cbxEstadoHabitacion.getItems().addAll("Libre", "Reservada", "Ocupada");
    }
    
    
    /*
    // Botón Aceptar - Crea o modifica el usuario en la base de datos
    @FXML
    private void crearOmodificarHabitacion() {
        try {
            // Verificar si los campos obligatorios están vacíos
            if (campoNumeroHabitacion.getText().isEmpty() || campoCapacidadHabitacion.getText().isEmpty() ||
                    campoCamasHabitacion.getText().isEmpty() || campoPrecioHabitacion.getText().isEmpty() ||
                    campoCamaSingle.getText().isEmpty() || campoCamaDoble.getText().isEmpty() ||
                    cbxCategoriaHabitacion.getValue() == null) {

                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }

            // Obtener datos del formulario
            int numero = Integer.parseInt(campoNumeroHabitacion.getText());
            int capacidad = Integer.parseInt(campoCapacidadHabitacion.getText());
            int camas = Integer.parseInt(campoCamasHabitacion.getText());
            int camaSingle = Integer.parseInt(campoCamaSingle.getText());
            int camaDoble = Integer.parseInt(campoCamaDoble.getText());
            double precio = Double.parseDouble(campoPrecioHabitacion.getText());
            String categoria = cbxCategoriaHabitacion.getValue();

            boolean exito = false;

            if (habitacionActual == null) {
                // Crear una nueva habitación
                Habitacion nuevaHabitacion = new Habitacion();
                exito = nuevaHabitacion.crearHabitacion(numero, capacidad, camas, camaSingle, camaDoble, categoria, precio);

                if (exito) {
                    // Registrar el estado inicial como "Libre" en estadoHabitacionHistorial
                    Connection con = Conexion.leerConexion();
                    PreparedStatement pstm = null;

                    try {
                        String consultaEstadoInicial = "INSERT INTO estadoHabitacionHistorial (idHabitacion, estado, fechaInicio) VALUES (?, ?, ?)";
                        pstm = con.prepareStatement(consultaEstadoInicial);
                        pstm.setInt(1, nuevaHabitacion.getIdHabitacion()); // Suponiendo que el ID de la nueva habitación está disponible
                        pstm.setString(2, "Libre"); // Estado inicial como "Libre"
                        pstm.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Fecha de inicio es la actual

                        pstm.executeUpdate();

                    } catch (SQLException e) {
                        System.err.println("Error al registrar el estado inicial de la habitación: " + e.getMessage());
                    } finally {
                        try {
                            if (pstm != null) pstm.close();
                            if (con != null) con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                // Modificar la habitación existente
                habitacionActual.modificarHabitacion(habitacionActual.getIdHabitacion(), numero, capacidad, camas, camaSingle, camaDoble, categoria, precio);
                exito = true;
            }

            if (exito) {
                mostrarAlerta("Éxito", "La habitación ha sido creada/modificada correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarHabitacion.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo crear/modificar la habitación.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para número, capacidad, camas y precio.", Alert.AlertType.ERROR);
        }
    }

     */
    @FXML
    private void crearHabitacion() {
        try {
            // Obtener datos del formulario
            int numero = Integer.parseInt(campoNumeroHabitacion.getText());
            int capacidad = Integer.parseInt(campoCapacidadHabitacion.getText());
            int cantidadCamas = Integer.parseInt(campoCamasHabitacion.getText());
            int camasSingle = Integer.parseInt(campoCamaSingle.getText());
            int camasDoble = Integer.parseInt(campoCamaDoble.getText());
            double precio = Double.parseDouble(campoPrecioHabitacion.getText());
            String categoria = cbxCategoriaHabitacion.getValue();

            // Verificar si todos los campos están completos
            if (cbxCategoriaHabitacion.getValue() == null || campoNumeroHabitacion.getText().isEmpty() ||
                    campoCapacidadHabitacion.getText().isEmpty() || campoCamasHabitacion.getText().isEmpty() ||
                    campoCamaSingle.getText().isEmpty() || campoCamaDoble.getText().isEmpty() ||
                    campoPrecioHabitacion.getText().isEmpty()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }

            int capacidadCalculada = camasSingle + (camasDoble * 2);

            if (capacidad != capacidadCalculada) {
                mostrarAlerta("Error", "La capacidad ingresada no coincide con la suma de camas single y dobles. Revise los valores.", Alert.AlertType.ERROR);
                return; // Salir del método si la validación falla
            }

            // Crear una nueva habitación con estado "Libre"
            Habitacion nuevaHabitacion = new Habitacion();
            boolean exito = nuevaHabitacion.crearHabitacion(numero, capacidad, cantidadCamas, camasSingle, camasDoble, categoria, precio);

            if (exito) {
                // Cerrar la ventana si la creación fue exitosa
                mostrarAlerta("Éxito", "La habitación ha sido creada correctamente", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarHabitacion.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo crear la habitación.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Código de error SQL que indica violación de la restricción UNIQUE
                mostrarAlerta("Error", "Ya existe una habitacion registrada con el número ingresado.", Alert.AlertType.ERROR);
            } else {
                // Otros errores de SQL
                mostrarAlerta("Error", "Error en la base de datos. Por favor, intente nuevamente.", Alert.AlertType.ERROR);
                System.err.println("Error SQL: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para los campos de número, capacidad, camas y precio.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void modificarHabitacion() {
        try {
            // Obtener datos del formulario
            int numero = Integer.parseInt(campoNumeroHabitacion.getText());
            int capacidad = Integer.parseInt(campoCapacidadHabitacion.getText());
            int cantidadCamas = Integer.parseInt(campoCamasHabitacion.getText());
            int camasSingle = Integer.parseInt(campoCamaSingle.getText());
            int camasDoble = Integer.parseInt(campoCamaDoble.getText());
            double precio = Double.parseDouble(campoPrecioHabitacion.getText().replace(",", "."));
            String categoria = cbxCategoriaHabitacion.getValue();

            // Verificar si todos los campos están completos
            if (campoNumeroHabitacion.getText().isEmpty() || campoCapacidadHabitacion.getText().isEmpty() ||
                    campoCamasHabitacion.getText().isEmpty() || campoCamaSingle.getText().isEmpty() || campoCamaDoble.getText().isEmpty() ||
                    campoPrecioHabitacion.getText().isEmpty() || cbxCategoriaHabitacion.getValue() == null) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }
            int capacidadCalculada = camasSingle + (camasDoble * 2);

            if (capacidad != capacidadCalculada) {
                mostrarAlerta("Error", "La capacidad ingresada no coincide con la suma de camas single y dobles. Revise los valores.", Alert.AlertType.ERROR);
                return; // Salir del método si la validación falla
            }

            // Modificar la habitación existente
            boolean exito = habitacionActual.modificarHabitacion(habitacionActual.getIdHabitacion(), numero, capacidad, cantidadCamas, camasSingle, camasDoble, categoria, precio);

            if (exito) {
                // Cerrar la ventana si la modificación fue exitosa
                mostrarAlerta("Éxito", "La habitación ha sido modificada correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarHabitacion.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo modificar la habitación.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Código de error SQL que indica violación de la restricción UNIQUE
                mostrarAlerta("Error", "Ya existe una habitacion registrada con el número ingresado.", Alert.AlertType.ERROR);
            } else {
                // Otros errores de SQL
                mostrarAlerta("Error", "Error en la base de datos. Por favor, intente nuevamente.", Alert.AlertType.ERROR);
                System.err.println("Error SQL: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para número, capacidad, camas y precio.", Alert.AlertType.ERROR);
        }
    }





    //Boton Cancelar
    @FXML
    private void cancelarHabitacion(){
        Stage stage = (Stage) btnCancelarHabitacion.getScene().getWindow();
        stage.close();

    }


    // Método para cargar los datos del usuario en el formulario
    public void cargarDatos(Habitacion habitacion) {
        habitacionActual = habitacion;

        // Cargar datos en los campos de texto y ComboBox
        campoNumeroHabitacion.setText(String.valueOf(habitacion.getNumero()));
        campoCapacidadHabitacion.setText(String.valueOf(habitacion.getCapacidad()));
        campoCamasHabitacion.setText(String.valueOf(habitacion.getCantidadCamas()));
        campoCamaSingle.setText(String.valueOf(habitacion.getCamasSingle()));
        campoCamaDoble.setText(String.valueOf(habitacion.getCamasDoble()));
        cbxCategoriaHabitacion.setValue(habitacion.getCategoria());
        campoPrecioHabitacion.setText(String.valueOf(habitacion.getPrecio()));
        cbxEstadoHabitacion.setValue(habitacion.getEstado());
    }


    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
