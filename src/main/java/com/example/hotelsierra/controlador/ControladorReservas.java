package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Cliente;
import com.example.hotelsierra.modelo.Habitacion;
import com.example.hotelsierra.modelo.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorReservas {

    @FXML
    private Button btnActualizarTablaReserva;

    @FXML
    private Button btnAnularReserva;

    @FXML
    private Button btnBuscarFiltroReserva;

    @FXML
    private Button btnEliminarReserva;

    @FXML
    private Button btnModificarReserva;

    @FXML
    private Button btnRegistrarReserva;

    @FXML
    private Button btnCheckIn;


    @FXML
    private Button btnAgregarPago;

    @FXML
    private TextField campoBuscarReserva;

    @FXML
    private ComboBox<String> cbxFiltroBuscarReserva;

    @FXML
    private TableColumn<Reserva, String> colAbonoReserva;

    @FXML
    private TableColumn<Reserva, String> colApellidoClienteReserva;

    @FXML
    private TableColumn<Reserva, Integer> colCantidadHabitacionesReserva;

    @FXML
    private TableColumn<Reserva, Integer> colCantidadHuespedesReserva;

    @FXML
    private TableColumn<Reserva, Double> colCostoNocheReserva;

    @FXML
    private TableColumn<Reserva, Double> colCostoTotalReserva;

    @FXML
    private TableColumn<Reserva, String> colDocumentoClienteReserva;

    @FXML
    private TableColumn<Reserva, String> colEstadoReserva;

    @FXML
    private TableColumn<Reserva, String> colFechaLlegadaReserva;

    @FXML
    private TableColumn<Reserva, String> colFechaSalidaReserva;

    @FXML
    private TableColumn<Reserva, String> colHabitacionesReserva;

    @FXML
    private TableColumn<Reserva, Double> colMontoAbonadoReserva;

    @FXML
    private TableColumn<Reserva, Double> colMontoReserva;

    @FXML
    private TableColumn<Reserva, String> colNombreClienteReserva;

    @FXML
    private Label lblBuscarReserva;

    @FXML
    private TableView<Reserva> tablaReservas;


    private ObservableList<Reserva> listaReservas;

    // Formato de fecha deseado
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    public void initialize() {
        // Configuración de las columnas para la tabla de reservas

        colNombreClienteReserva.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue().getCliente();
            return new SimpleStringProperty(cliente != null ? cliente.getNombre() : "");
        });

        colApellidoClienteReserva.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue().getCliente();
            return new SimpleStringProperty(cliente != null ? cliente.getApellido() : "");
        });

        colDocumentoClienteReserva.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue().getCliente();
            return new SimpleStringProperty(cliente != null ? cliente.getDocumento() : "");
        });

        colCantidadHabitacionesReserva.setCellValueFactory(new PropertyValueFactory<>("cantidadHabitaciones"));
        colCantidadHuespedesReserva.setCellValueFactory(new PropertyValueFactory<>("cantidadHuespedes"));

        colFechaLlegadaReserva.setCellValueFactory(cellData -> {
            LocalDate fechaLlegada = cellData.getValue().getFechaLlegada();
            return new SimpleStringProperty(fechaLlegada != null ? fechaLlegada.format(formatter) : "");
        });

        colFechaSalidaReserva.setCellValueFactory(cellData -> {
            LocalDate fechaSalida = cellData.getValue().getFechaSalida();
            return new SimpleStringProperty(fechaSalida != null ? fechaSalida.format(formatter) : "");
        });


        colCostoNocheReserva.setCellValueFactory(new PropertyValueFactory<>("costoNoche"));
        colCostoTotalReserva.setCellValueFactory(new PropertyValueFactory<>("costoTotal"));
        colMontoReserva.setCellValueFactory(new PropertyValueFactory<>("montoReserva"));
        colMontoAbonadoReserva.setCellValueFactory(new PropertyValueFactory<>("montoAbonado"));
        //colAbonoReserva.setCellValueFactory(new PropertyValueFactory<>("abonoReserva"));
        colEstadoReserva.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Para mostrar habitaciones como una lista en una sola celda
        colHabitacionesReserva.setCellValueFactory(cellData -> {
            String habitaciones = cellData.getValue().getHabitaciones();
            // Dividir las habitaciones por coma y extraer solo los números
            String habitacionesSoloNumeros = Arrays.stream(habitaciones.split(","))
                    .map(h -> h.replaceAll("\\D+", "").trim())  // Eliminar cualquier cosa que no sea un número
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(habitacionesSoloNumeros);
        });



        // Cargar datos en la tabla
        cargarReservasEnTabla();

        // Inicializar opciones del ComboBox
        cbxFiltroBuscarReserva.getItems().addAll("Documento", "Nombre", "Apellido");

        // Configurar evento del botón
        btnBuscarFiltroReserva.setOnAction(event -> buscarReservas());
    }

    @FXML
    private void recargarTablaReservas() {
        cargarReservasEnTabla();
    }

    public void cargarReservasEnTabla() {
        listaReservas = Reserva.obtenerListaReservas();
        tablaReservas.setItems(listaReservas);
    }




    //-------- Registrar reserva ----//
    @FXML
    private void abrirFormularioReserva() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formReserva.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Reserva");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaReservas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarReserva() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada != null) {
            abrirFormularioReserva(reservaSeleccionada);
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una reserva.", Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void anularReserva() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada != null) {
            // Confirmación antes de anular la reserva
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Anulación");
            confirmacion.setHeaderText("¿Estás seguro de que deseas anular esta reserva?");
            confirmacion.setContentText("Esta acción cambiará el estado de la reserva a 'Anulada' y no se podrá revertir.");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    boolean exito = reservaSeleccionada.anularReserva(reservaSeleccionada.getIdReserva());

                    if (exito) {
                        // Actualizar el estado en la interfaz
                        reservaSeleccionada.setEstado("Anulada");
                        tablaReservas.refresh();

                        mostrarAlerta("Éxito", "La reserva ha sido anulada correctamente.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Error", "No se pudo anular la reserva.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una reserva para anular.", Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void eliminarReserva() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada != null) {
            // Confirmación antes de eliminar la reserva
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("Eliminar Reserva");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar la reserva seleccionada?");

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    boolean exito = reservaSeleccionada.eliminarReserva(reservaSeleccionada.getIdReserva());

                    if (exito) {
                        // Eliminar la reserva de la tabla en la interfaz
                        listaReservas.remove(reservaSeleccionada);
                        tablaReservas.refresh();

                        mostrarAlerta("Éxito", "La reserva ha sido eliminada correctamente.", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar la reserva.", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una reserva para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void realizarCheckIn() {
        // Verificar si hay una reserva seleccionada en la tabla de reservas
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una reserva para realizar el check-in.", Alert.AlertType.ERROR);
            return;
        }

        // Obtener el estado actual de la reserva directamente desde la base de datos
        String estadoReserva = Reserva.obtenerEstadoReserva(reservaSeleccionada.getIdReserva());
        if (estadoReserva == null || !estadoReserva.equalsIgnoreCase("Confirmada")) {
            mostrarAlerta("Error", "Solo puede realizar el check-in de una reserva confirmada.", Alert.AlertType.ERROR);
            return;
        }

        // Verificar si la fecha actual está dentro del rango de fechas de la reserva
        LocalDate fechaHoy = LocalDate.now();
        if (fechaHoy.isBefore(reservaSeleccionada.getFechaLlegada()) || fechaHoy.isAfter(reservaSeleccionada.getFechaSalida())) {
            mostrarAlerta("Error", "El check-in no puede realizarse antes de la fecha de llegada ni despues de la fecha de salida.", Alert.AlertType.ERROR);
            return;
        }

        // Verificar si ya se ha realizado el check-in
        List<Habitacion> habitaciones = Reserva.obtenerHabitacionesPorReserva(reservaSeleccionada.getIdReserva());
        for (Habitacion habitacion : habitaciones) {
            boolean yaOcupada = Reserva.habitacionOcupada(habitacion.getIdHabitacion(), reservaSeleccionada.getIdReserva());
            if (yaOcupada) {
                mostrarAlerta("Error", "El check-in ya se ha realizado para esta reserva.", Alert.AlertType.ERROR);
                return;
            }
        }

        // Confirmar la acción de check-in
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Check-In");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea realizar el check-in para la reserva seleccionada?");
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (habitaciones.isEmpty()) {
                mostrarAlerta("Error", "No se encontraron habitaciones asociadas a la reserva seleccionada.", Alert.AlertType.ERROR);
                return;
            }

            // Cambiar el estado de cada habitación a "Ocupada"
            LocalDate fechaInicio = reservaSeleccionada.getFechaLlegada();
            LocalDate fechaFin = reservaSeleccionada.getFechaSalida();
            Reserva reserva = new Reserva();
            for (Habitacion habitacion : habitaciones) {
                reserva.actualizarEstadoHabitacionHistorial(
                        habitacion.getIdHabitacion(),
                        "Ocupada",
                        fechaInicio,
                        fechaFin,
                        reservaSeleccionada.getIdReserva(),
                        "checkIn"
                );
            }

            // Mostrar mensaje de éxito
            mostrarAlerta("Éxito", "Check-in realizado exitosamente.", Alert.AlertType.INFORMATION);
        }
    }



/* //25/11/2024 19:02
    @FXML
    private void realizarCheckIn() {
        // Verificar si hay una reserva seleccionada en la tabla de reservas
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una reserva para realizar el check-in.", Alert.AlertType.ERROR);
            return;
        }

        // Confirmar la acción de check-in
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Check-In");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea realizar el check-in para la reserva seleccionada?");
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Obtener las habitaciones asociadas a la reserva seleccionada
            List<Habitacion> habitaciones = Reserva.obtenerHabitacionesPorReserva(reservaSeleccionada.getIdReserva());

            if (habitaciones.isEmpty()) {
                mostrarAlerta("Error", "No se encontraron habitaciones asociadas a la reserva seleccionada.", Alert.AlertType.ERROR);
                return;
            }

            // Cambiar el estado de cada habitación a "Ocupada"
            LocalDate fechaInicio = reservaSeleccionada.getFechaLlegada();
            LocalDate fechaFin = reservaSeleccionada.getFechaSalida();
            Reserva reserva = new Reserva();
            for (Habitacion habitacion : habitaciones) {
                // Utilizar el método estático de la clase Reserva para actualizar el estado

                reserva.actualizarEstadoHabitacionHistorial(
                        habitacion.getIdHabitacion(),
                        "Ocupada",
                        fechaInicio,
                        fechaFin,
                        reservaSeleccionada.getIdReserva(),
                        "checkIn"
                );
            }

            // Mostrar mensaje de éxito
            mostrarAlerta("Éxito", "Check-in realizado exitosamente. Las habitaciones ahora están ocupadas.", Alert.AlertType.INFORMATION);
        }
    }

 */
    /*
    @FXML
    private void realizarCheckOut() {
        // Verificar que haya una reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor, seleccione una reserva.", Alert.AlertType.WARNING);
            return;
        }

        // Solicitar confirmación para realizar el check-out
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setHeaderText("Confirmación de Check-Out");
        alertaConfirmacion.setContentText("¿Está seguro de que desea realizar el Check-Out de esta reserva?");
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Obtener las habitaciones asociadas a la reserva seleccionada
            List<Habitacion> habitacionesList = Reserva.obtenerHabitacionesPorReserva(reservaSeleccionada.getIdReserva());

            for (Habitacion habitacion : habitacionesList) {
                // Actualizar el estado de cada habitación a "Libre" en estadoHabitacionHistorial
                actualizarEstadoHabitacionHistorial(habitacion.getIdHabitacion(), "Libre",
                        LocalDate.now(), LocalDate.now(), reservaSeleccionada.getIdReserva());
            }



            mostrarAlerta("Éxito", "El Check-Out se ha realizado correctamente.", Alert.AlertType.INFORMATION);

            // Actualizar la tabla de reservas después del Check-Out
            tablaReservas.refresh();
        }
    }
    */


    @FXML
    private void realizarCheckOut() {
        // Verificar que haya una reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor, seleccione una reserva.", Alert.AlertType.WARNING);
            return;
        }

        // Solicitar confirmación para realizar el check-out
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setHeaderText("Confirmación de Check-Out");
        alertaConfirmacion.setContentText("¿Está seguro de que desea realizar el Check-Out de esta reserva?");
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Obtener las habitaciones asociadas a la reserva seleccionada
            List<Habitacion> habitacionesList = Reserva.obtenerHabitacionesPorReserva(reservaSeleccionada.getIdReserva());
            Reserva reserva = new Reserva();

            // Verificar si todas las habitaciones están en estado "Ocupada"
            for (Habitacion habitacion : habitacionesList) {
                boolean estaOcupada = Reserva.habitacionOcupada(habitacion.getIdHabitacion(), reservaSeleccionada.getIdReserva());
                if (!estaOcupada) {
                    mostrarAlerta("Error", "No se puede realizar el Check-Out de la habitación sin realizar antes el Check-In.", Alert.AlertType.ERROR);
                    return;
                }
            }

            // Si todas las habitaciones están ocupadas, proceder con el check-out
            for (Habitacion habitacion : habitacionesList) {
                // Actualizar la fecha de fin del registro correspondiente en estadoHabitacionHistorial
                reserva.actualizarFechaFinEstadoHabitacionHistorial(habitacion.getIdHabitacion(), reservaSeleccionada.getIdReserva(), LocalDateTime.now());
            }

            mostrarAlerta("Éxito", "Check-out realizado correctamente.", Alert.AlertType.INFORMATION);

            // Actualizar la tabla de reservas después del Check-Out
            tablaReservas.refresh();
        }
    }





    private void abrirFormularioReserva(Reserva reserva) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formModificarReserva.fxml"));
            Parent root = loader.load();

            ControladorFormularioReserva controladorFormulario = loader.getController();
            controladorFormulario.cargarDatos(reserva);

            Stage stage = new Stage();
            stage.setTitle("Modificar Reserva");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Recargar la tabla después de cerrar el formulario
            recargarTablaReservas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Metodo de filtro para buscar reservas usando el documento, nombre o apellido del cliente
    private void buscarReservas() {
        // Obtener el filtro seleccionado y el valor ingresado
        String filtro = cbxFiltroBuscarReserva.getValue();
        String valor = campoBuscarReserva.getText().trim();

        // Validar que se seleccionó un filtro y se ingresó un valor
        if (filtro == null || filtro.isEmpty()) {
            mostrarAlerta("Error", "Por favor, selecciona un filtro para buscar.", Alert.AlertType.ERROR);
            return;
        }

        if (valor.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa un valor para buscar.", Alert.AlertType.ERROR);
            return;
        }

        // Llamar al método buscarReservas de la clase Reserva
        Reserva reserva = new Reserva();
        List<Reserva> reservasEncontradas = reserva.buscarReservas(filtro, valor);

        // Mostrar resultados en la tabla
        ObservableList<Reserva> listaReservas = FXCollections.observableArrayList(reservasEncontradas);
        tablaReservas.setItems(listaReservas);
    }

    @FXML
    private void abrirFormularioAgregarPago() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor, seleccione una reserva.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formAgregarPago.fxml"));

            Parent root = loader.load();

            // Obtener el controlador del formulario Agregar Pago
            ControladorFormularioPago controladorAgregarPago = loader.getController();
            controladorAgregarPago.setReservaActual(reservaSeleccionada);

            // Mostrar la ventana para agregar pago
            Stage stage = new Stage();
            stage.setTitle("Agregar Pago");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Después de cerrar la ventana, recargar la tabla de reservas
            recargarTablaReservas();

        } catch (IOException e) {
            System.err.println("Error al abrir la ventana de agregar pago: " + e.getMessage());
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