package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Cliente;
import com.example.hotelsierra.modelo.Habitacion;
import com.example.hotelsierra.modelo.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ControladorFormularioReserva {

    @FXML
    private Button btnAceptarReserva;

    @FXML
    private Button btnAgregarClienteReserva;

    @FXML
    private Button btnBuscarHabitacionReserva;

    @FXML
    private Button btnCalcularCostos;

    @FXML
    private Button btnCancelarReserva;

    @FXML
    private TextField campoApellidoClienteReserva;

    @FXML
    private TextField campoCantidadHabitaciones;

    @FXML
    private TextField campoCantidadHuespedes;

    @FXML
    private ComboBox<String> cbxTipoHabitacionReserva;

    @FXML
    private TextField campoCostoNoche;

    @FXML
    private TextField campoCostoTotal;

    @FXML
    private TextField campoDocumentoClienteReserva;

    @FXML
    private TextField campoMontoAbonado;

    @FXML
    private TextField campoMontoReserva;

    @FXML
    private TextField campoNombreClienteReserva;

    @FXML
    private ComboBox<String> cbxAbonoReserva;

    @FXML
    private ComboBox<String> cbxEstadoReserva;

    @FXML
    private DatePicker dpFechaLlegadaReserva;

    @FXML
    private DatePicker dpFechaSalidaReserva;

    @FXML
    private ListView<Habitacion> listaHabitacionesReserva;


    // Cliente actual (para modificación)
    private Reserva reservaActual;


    @FXML
    public void initialize() {
        // Agregar datos a los ComboBox
        listaHabitacionesReserva.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listaHabitacionesReserva.setCellFactory(param -> new ListCell<Habitacion>() {
            @Override
            protected void updateItem(Habitacion habitacion, boolean empty) {
                super.updateItem(habitacion, empty);
                if (empty || habitacion == null) {
                    setText(null);
                } else {
                    setText("Número: " + habitacion.getNumero() + ", Categoría: " + habitacion.getCategoria());
                }
            }
        });
        cbxAbonoReserva.getItems().addAll("Sí", "No");
        cbxEstadoReserva.getItems().addAll("Pendiente", "Confirmada", "Anulada");
        // Evento para cbxAbonoReserva
        cbxAbonoReserva.setOnAction(event -> manejarSeleccionAbono());

        // Evento para cambios en campoMontoAbonado
        //campoMontoAbonado.textProperty().addListener((observable, oldValue, newValue) -> validarMontoAbonado());
        campoMontoAbonado.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // Cuando el campo pierde el foco (newValue es false), se llama a la validación
            if (!newValue && !campoMontoAbonado.getText().isEmpty()) {
                validarMontoAbonado();
            }
        });

        // Deshabilitar días anteriores a la fecha actual en dpFechaLlegadaReserva
        Callback<DatePicker, DateCell> dayCellFactoryLlegada = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Deshabilitar los días antes del día actual
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;"); // Estilo para indicar que está deshabilitado
                }
            }
        };
        dpFechaLlegadaReserva.setDayCellFactory(dayCellFactoryLlegada);

        // Deshabilitar días anteriores a la fecha actual en dpFechaSalidaReserva
        Callback<DatePicker, DateCell> dayCellFactorySalida = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Deshabilitar los días antes del día actual
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;"); // Estilo para indicar que está deshabilitado
                }
            }
        };
        dpFechaSalidaReserva.setDayCellFactory(dayCellFactorySalida);

        dpFechaLlegadaReserva.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                mostrarAlerta("Fecha inválida", "La fecha de llegada no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
                dpFechaLlegadaReserva.setValue(null);
            }
        });

        dpFechaSalidaReserva.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                mostrarAlerta("Fecha inválida", "La fecha de salida no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
                dpFechaSalidaReserva.setValue(null);
            }
        });

        // Inicializar ComboBox con los tipos de habitación para Consulta de habitaciones
        cbxTipoHabitacionReserva.getItems().addAll("Estandar", "Lujo", "Ambas");

    }

    public void cargarHabitacionesDisponibles(List<Habitacion> resultadoConsulta) {
        ObservableList<Habitacion> habitacionesDisponibles = FXCollections.observableArrayList(resultadoConsulta);
        listaHabitacionesReserva.setItems(habitacionesDisponibles);
    }
    private void manejarSeleccionAbono() {
        if (cbxAbonoReserva.getValue().equals("Sí")) {
            // Si el cliente abonó el 50%, confirmar la reserva y autocompletar el monto abonado
            cbxEstadoReserva.setValue("Confirmada");

            // Obtener el valor numérico de campoMontoReserva y formatearlo
            try {
                double montoReserva = Double.parseDouble(campoMontoReserva.getText());
                campoMontoAbonado.setText(String.format(Locale.US, "%.2f", montoReserva));
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El valor de Monto Reserva no es válido.", Alert.AlertType.ERROR);
            }

            campoMontoAbonado.setDisable(true); // Deshabilita el campo para evitar cambios
        } else {
            // Si no abonó el 50%, permitir al recepcionista ingresar el monto manualmente
            campoMontoAbonado.clear(); // Limpia el campo
            campoMontoAbonado.setDisable(false); // Habilita el campo para ingreso manual
            campoMontoAbonado.requestFocus(); // Enfoca el campo
        }
    }



    private void validarMontoAbonado() {
        try {
            double montoAbonado = Double.parseDouble(campoMontoAbonado.getText());
            double montoReserva = Double.parseDouble(campoMontoReserva.getText());

            if (montoAbonado >= montoReserva) {
                cbxEstadoReserva.setValue("Confirmada");
            } else {
                cbxEstadoReserva.setValue("Pendiente");
            }
        } catch (NumberFormatException e) {
            // Manejo del caso en que el campoMontoAbonado no contenga un número válido
            campoMontoAbonado.clear(); // Opción de limpiar si es no numérico
        }
    }


    public void buscarClientePorDocumento() {
        String documento = campoDocumentoClienteReserva.getText().trim();

        // Verifica que el campo de documento no esté vacío
        if (documento.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingrese un documento.", Alert.AlertType.ERROR);
            return;
        }

        Cliente cliente = Cliente.obtenerClientePorDocumento(documento);

        if (cliente != null) {
            // Completa los campos con los datos del cliente
            campoNombreClienteReserva.setText(cliente.getNombre());
            campoApellidoClienteReserva.setText(cliente.getApellido());
        } else {
            // Muestra la alerta y habilita el botón para agregar un nuevo cliente
            mostrarAlerta("Cliente no encontrado", "El cliente no existe en el sistema. Presione 'Agregar Cliente' para registrar.", Alert.AlertType.ERROR);
            btnAgregarClienteReserva.setDisable(false); // Asegúrate de que btnAgregarClienteReserva esté habilitado
            campoNombreClienteReserva.clear();
            campoApellidoClienteReserva.clear();


        }
    }
    @FXML
    private void abrirFormularioClienteReserva() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/formCliente.fxml"));
            Parent root = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Registrar Cliente");
            dialog.getDialogPane().setContent(root);
            //dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el formulario de cliente.");
        }
    }

    @FXML
    private void buscarHabitacionesDisponibles() {
        try {
            int cantidadHabitaciones = Integer.parseInt(campoCantidadHabitaciones.getText());
            int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            LocalDate fechaLlegada = dpFechaLlegadaReserva.getValue();
            LocalDate fechaSalida = dpFechaSalidaReserva.getValue();
            String categoria = cbxTipoHabitacionReserva.getValue(); // Obtener el valor del ComboBox


            Reserva reserva = new Reserva();
            List<Habitacion> habitacionesDisponibles = reserva.obtenerHabitacionesDisponibles(fechaLlegada, fechaSalida, cantidadHuespedes, cantidadHabitaciones, categoria);

            if (habitacionesDisponibles.isEmpty()) {

                mostrarAlerta("Error", "No se encontraron habitaciones disponibles.", Alert.AlertType.ERROR);

            }

            if (fechaLlegada == null || fechaSalida == null || !fechaSalida.isAfter(fechaLlegada)) {
                mostrarAlerta("Error","Por favor, ingresa fechas válidas de llegada y salida.", Alert.AlertType.ERROR);
                return;
            }

            System.out.println(habitacionesDisponibles);

            // Crear una lista observable de tipo Habitacion
            ObservableList<Habitacion> habitacionesObservable = FXCollections.observableArrayList(habitacionesDisponibles);

            // Asignar la lista de habitaciones a la ListView directamente
            listaHabitacionesReserva.setItems(habitacionesObservable);

            // Configurar el CellFactory
            listaHabitacionesReserva.setCellFactory(param -> new ListCell<Habitacion>() {
                @Override
                protected void updateItem(Habitacion habitacion, boolean empty) {
                    super.updateItem(habitacion, empty);
                    if (empty || habitacion == null) {
                        setText(null);
                    } else {
                        setText(String.format("N° %d - Capacidad: %d, Single: %d, Doble: %d, Categoria: %s, Precio: %.2f",
                                habitacion.getNumero(), habitacion.getCapacidad(), habitacion.getCamasSingle(),
                                habitacion.getCamasDoble(), habitacion.getCategoria(), habitacion.getPrecio()));
                    }
                }
            });

        }catch (NumberFormatException e) {
                mostrarAlerta("Error", "Por favor, ingresa valores numéricos válidos para los campos de huéspedes y habitaciones.", Alert.AlertType.ERROR);
            }
    }

    @FXML
    private void calcularCostos() {
        // Obtener las habitaciones seleccionadas
        ObservableList<Habitacion> habitacionesSeleccionadas = listaHabitacionesReserva.getSelectionModel().getSelectedItems();

        // Verificar si hay habitaciones seleccionadas
        if (habitacionesSeleccionadas.isEmpty()) {
            // Muestra una alerta si no se seleccionan habitaciones
            mostrarAlerta("Error","Por favor, selecciona al menos una habitación para calcular los costos.", Alert.AlertType.ERROR);
            return;
        }

        // Calcular la suma de los precios por noche de las habitaciones seleccionadas
        double costoPorNoche = habitacionesSeleccionadas.stream()
                .mapToDouble(Habitacion::getPrecio)
                .sum();

        // Calcular la cantidad de noches de estadía
        LocalDate fechaLlegada = dpFechaLlegadaReserva.getValue();
        LocalDate fechaSalida = dpFechaSalidaReserva.getValue();
        if (fechaLlegada == null || fechaSalida == null || !fechaSalida.isAfter(fechaLlegada)) {
            mostrarAlerta("Error","Por favor, ingresa fechas válidas de llegada y salida.", Alert.AlertType.ERROR);
            return;
        }

        long cantidadNoches = java.time.temporal.ChronoUnit.DAYS.between(fechaLlegada, fechaSalida);

        // Calcular los costos
        double costoTotal = costoPorNoche * cantidadNoches;
        double montoReserva = 0.5 * costoTotal;

        // Completar los campos en la interfaz con formato de punto decimal
        campoCostoNoche.setText(String.format(Locale.US, "%.2f", costoPorNoche));
        campoCostoTotal.setText(String.format(Locale.US, "%.2f", costoTotal));
        campoMontoReserva.setText(String.format(Locale.US, "%.2f", montoReserva));
    }


    // Botón Aceptar - Crea o modifica la reserva en la base de datos
    /*
    @FXML
    private void crearOmodificarReserva() {
        try {
            // Verificar si los campos obligatorios están vacíos
            if (validarDatosReserva()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }

            // Obtener datos del formulario
            Cliente cliente = obtenerClienteDesdeFormulario();
            LocalDate fechaLlegada = dpFechaLlegadaReserva.getValue();
            LocalDate fechaSalida = dpFechaSalidaReserva.getValue();
            int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            String estado = cbxEstadoReserva.getValue();
            double costoNoche = Double.parseDouble(campoCostoNoche.getText());
            double costoTotal = Double.parseDouble(campoCostoTotal.getText());
            double montoReserva = Double.parseDouble(campoMontoReserva.getText());
            double montoAbonado = Double.parseDouble(campoMontoAbonado.getText());
            String abonoReserva = cbxAbonoReserva.getValue();

            // Obtener la lista de IDs de habitaciones seleccionadas
            List<Integer> habitaciones = listaHabitacionesReserva.getSelectionModel().getSelectedItems()
                    .stream()
                    .map(Habitacion::getIdHabitacion)
                    .collect(Collectors.toList());

            boolean exito = false;

            if (reservaActual == null) {
                // Crear una nueva reserva
                Reserva nuevaReserva = new Reserva();
                exito = nuevaReserva.registrarReserva(
                        cliente.getIdCliente(),
                        habitaciones.size(),
                        cantidadHuespedes,
                        fechaLlegada,
                        fechaSalida,
                        costoNoche,
                        costoTotal,
                        montoReserva,
                        montoAbonado,
                        abonoReserva,
                        estado,
                        habitaciones
                );
                System.out.println(nuevaReserva);
            } else {
                // Modificar la reserva existente
                reservaActual.modificarReserva(
                        reservaActual.getIdReserva(),
                        cliente.getIdCliente(),
                        habitaciones.size(),
                        cantidadHuespedes,
                        fechaLlegada,
                        fechaSalida,
                        costoNoche,
                        costoTotal,
                        montoReserva,
                        montoAbonado,
                        abonoReserva,
                        estado,
                        habitaciones
                );
                exito = true;
            }

            if (exito) {
                mostrarAlerta("Éxito", "La reserva ha sido creada/modificada correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarReserva.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo crear/modificar la reserva.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para los campos de costos y montos.", Alert.AlertType.ERROR);
        }
    }


     */

    @FXML
    private void crearReserva() {
        try {
            // Verificar si los campos obligatorios están vacíos
            if (!validarDatosReserva()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }

            if (!validarFechasReserva()) {
                mostrarAlerta("Error", "La fecha de llegada o la fecha de salida no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
                return;
            }

            // Obtener la lista de IDs de habitaciones seleccionadas
            List<Integer> habitaciones = listaHabitacionesReserva.getSelectionModel().getSelectedItems()
                    .stream()
                    .map(Habitacion::getIdHabitacion)
                    .collect(Collectors.toList());

            // Obtener la cantidad de habitaciones seleccionadas y validar si coincide con el campo
            int cantidadHabitacionesSeleccionadas = habitaciones.size();
            int cantidadHabitacionesIngresadas = Integer.parseInt(campoCantidadHabitaciones.getText());

            if (cantidadHabitacionesSeleccionadas != cantidadHabitacionesIngresadas) {
                mostrarAlerta("Error", "La cantidad de habitaciones seleccionadas no coincide con el valor ingresado en Cant. de Habitaciones.", Alert.AlertType.ERROR);
                return;
            }

            // Validar que la capacidad de las habitaciones seleccionadas sea suficiente para los huéspedes
            int capacidadTotal = 0;
            for (Integer idHabitacion : habitaciones) {
                Habitacion habitacion = Habitacion.obtenerHabitacionPorId(idHabitacion);
                capacidadTotal += habitacion.getCapacidad();
            }


            int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            if (cantidadHuespedes > capacidadTotal) {
                mostrarAlerta("Error", "La cantidad de huéspedes excede la capacidad de las habitaciones seleccionadas.", Alert.AlertType.ERROR);
                return;
            }
            // Obtener datos del formulario
            Cliente cliente = obtenerClienteDesdeFormulario();
            LocalDate fechaLlegada = dpFechaLlegadaReserva.getValue();
            LocalDate fechaSalida = dpFechaSalidaReserva.getValue();
            //int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            String estado = cbxEstadoReserva.getValue();
            double costoNoche = Double.parseDouble(campoCostoNoche.getText());
            double costoTotal = Double.parseDouble(campoCostoTotal.getText());
            double montoReserva = Double.parseDouble(campoMontoReserva.getText());
            double montoAbonado = Double.parseDouble(campoMontoAbonado.getText());
            String abonoReserva = cbxAbonoReserva.getValue();


            // Imprimir para depuración
            System.out.println("Documento Cliente: " + cliente.getIdCliente());
            System.out.println("Fecha de Llegada: " + fechaLlegada);
            System.out.println("Fecha de Salida: " + fechaSalida);
            System.out.println("Cantidad de Huéspedes: " + cantidadHuespedes);
            System.out.println("Cantidad de Habitaciones: " + habitaciones.size());
            System.out.println("Costo por Noche: " + costoNoche);
            System.out.println("Costo Total: " + costoTotal);
            System.out.println("Monto de la Reserva: " + montoReserva);
            System.out.println("Monto Abonado: " + montoAbonado);
            System.out.println("Estado de la Reserva: " + estado);
            System.out.println("Abono de Reserva: " + abonoReserva);

            // Crear una nueva reserva
            Reserva nuevaReserva = new Reserva();
            boolean exito = nuevaReserva.registrarReserva(
                    cliente.getIdCliente(),
                    habitaciones.size(),
                    cantidadHuespedes,
                    fechaLlegada,
                    fechaSalida,
                    costoNoche,
                    costoTotal,
                    montoReserva,
                    montoAbonado,
                    abonoReserva,
                    estado,
                    habitaciones
            );

            if (exito) {
                mostrarAlerta("Éxito", "La reserva ha sido creada correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarReserva.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo crear la reserva.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para los campos de costos y montos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modificarReserva() {
        try {
            if (!validarDatosReserva()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }
            if (!validarFechasReserva()) {
                mostrarAlerta("Error", "La fecha de llegada o la fecha de salida no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
                return;
            }

            List<Integer> habitaciones = listaHabitacionesReserva.getSelectionModel().getSelectedItems()
                    .stream()
                    .map(Habitacion::getIdHabitacion)
                    .collect(Collectors.toList());

            // Obtener la cantidad de habitaciones seleccionadas y validar si coincide con el campo
            int cantidadHabitacionesSeleccionadas = habitaciones.size();
            int cantidadHabitacionesIngresadas = Integer.parseInt(campoCantidadHabitaciones.getText());

            if (cantidadHabitacionesSeleccionadas != cantidadHabitacionesIngresadas) {
                mostrarAlerta("Error", "La cantidad de habitaciones seleccionadas no coincide con el valor ingresado.", Alert.AlertType.ERROR);
                return;
            }

            // Validar que la capacidad de las habitaciones seleccionadas sea suficiente para los huéspedes
            int capacidadTotal = 0;

            for (Integer idHabitacion : habitaciones) {
                Habitacion habitacion = Habitacion.obtenerHabitacionPorId(idHabitacion);
                capacidadTotal += habitacion.getCapacidad();
            }


            int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            if (cantidadHuespedes > capacidadTotal) {
                mostrarAlerta("Error", "La cantidad de huéspedes excede la capacidad de las habitaciones seleccionadas.", Alert.AlertType.ERROR);
                return;
            }

            Cliente cliente = obtenerClienteDesdeFormulario();
            LocalDate fechaLlegada = dpFechaLlegadaReserva.getValue();
            LocalDate fechaSalida = dpFechaSalidaReserva.getValue();
            //int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            String estado = cbxEstadoReserva.getValue();

            // Parsear los valores asegurándose de que el formato sea compatible con Double
            double costoNoche = Double.parseDouble(campoCostoNoche.getText().replace(",", "."));
            double costoTotal = Double.parseDouble(campoCostoTotal.getText().replace(",", "."));
            double montoReserva = Double.parseDouble(campoMontoReserva.getText().replace(",", "."));
            double montoAbonado = Double.parseDouble(campoMontoAbonado.getText().replace(",", "."));
            String abonoReserva = cbxAbonoReserva.getValue();

            boolean exito = false;

            if (reservaActual != null) {
                exito = reservaActual.modificarReserva(
                        reservaActual.getIdReserva(),
                        cliente.getIdCliente(),
                        habitaciones.size(),
                        cantidadHuespedes,
                        fechaLlegada,
                        fechaSalida,
                        costoNoche,
                        costoTotal,
                        montoReserva,
                        montoAbonado,
                        abonoReserva,
                        estado,
                        habitaciones
                );
            }

            if (exito) {
                mostrarAlerta("Éxito", "La reserva ha sido modificada correctamente.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnAceptarReserva.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo modificar la reserva.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese valores numéricos válidos para los campos de costos y montos.", Alert.AlertType.ERROR);
        }
    }





    // Método para obtener el cliente desde el formulario
    private Cliente obtenerClienteDesdeFormulario() {
        String documentoCliente = campoDocumentoClienteReserva.getText();

        if (documentoCliente.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el documento del cliente.", Alert.AlertType.ERROR);
            return null;
        }

        try {

            // Llama al método para buscar al cliente por documento
            Cliente cliente = Cliente.obtenerClientePorDocumento(documentoCliente);

            if (cliente == null) {
                mostrarAlerta("Error", "No se encontró un cliente con el documento ingresado.", Alert.AlertType.ERROR);
            }

            return cliente;

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El documento debe ser un número válido.", Alert.AlertType.ERROR);
            return null;
        }
    }




    // Método para cargar los datos de la reserva en el formulario

/*
    public void cargarDatos(Reserva reserva) {
        reservaActual = reserva;

        // Verificar si el cliente asociado existe y cargar sus datos
        Cliente cliente = reserva.getCliente();
        if (cliente != null) {
            campoDocumentoClienteReserva.setText(String.valueOf(cliente.getDocumento()));
            campoNombreClienteReserva.setText(cliente.getNombre());
            campoApellidoClienteReserva.setText(cliente.getApellido());
        } else {
            mostrarAlerta("Error", "No se encontró un cliente asociado con esta reserva.", Alert.AlertType.ERROR);
        }

        // Cargar el resto de los datos de la reserva
        dpFechaLlegadaReserva.setValue(reserva.getFechaLlegada());
        dpFechaSalidaReserva.setValue(reserva.getFechaSalida());
        campoCantidadHuespedes.setText(String.valueOf(reserva.getCantidadHuespedes()));
        campoCantidadHabitaciones.setText(String.valueOf(reserva.getCantidadHabitaciones()));
        campoCostoNoche.setText(String.format("%.2f", reserva.getCostoNoche()));
        campoCostoTotal.setText(String.format("%.2f", reserva.getCostoTotal()));
        campoMontoReserva.setText(String.format("%.2f", reserva.getMontoReserva()));
        campoMontoAbonado.setText(String.format("%.2f", reserva.getMontoAbonado()));
        cbxAbonoReserva.setValue(reserva.getAbonoReserva());
        cbxEstadoReserva.setValue(reserva.getEstado());

        // Cargar las habitaciones seleccionadas en la ListView
        List<String> numerosHabitaciones = Arrays.asList(reserva.getHabitaciones().split(", "));
        ObservableList<Habitacion> habitacionesSeleccionadas = FXCollections.observableArrayList();
        for (Habitacion habitacion : listaHabitacionesReserva.getItems()) {
            if (numerosHabitaciones.contains(String.valueOf(habitacion.getNumero()))) {
                habitacionesSeleccionadas.add(habitacion);
            }
        }
        listaHabitacionesReserva.getSelectionModel().clearSelection();
        for (Habitacion habitacion : habitacionesSeleccionadas) {
            listaHabitacionesReserva.getSelectionModel().select(habitacion);
        }
    }

 */



    public void cargarDatos(Reserva reserva) {
        reservaActual = reserva;

        Cliente cliente = reserva.getCliente();
        if (cliente != null) {
            campoDocumentoClienteReserva.setText(String.valueOf(cliente.getDocumento()));
            campoNombreClienteReserva.setText(cliente.getNombre());
            campoApellidoClienteReserva.setText(cliente.getApellido());
        } else {
            mostrarAlerta("Error", "No se encontró un cliente asociado con esta reserva.", Alert.AlertType.ERROR);
        }

        // Crear un DecimalFormat que siempre use el punto como separador decimal
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
        decimalSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.00", decimalSymbols);

        // Formatear los datos con el formato correcto
        dpFechaLlegadaReserva.setValue(reserva.getFechaLlegada());
        dpFechaSalidaReserva.setValue(reserva.getFechaSalida());
        campoCantidadHuespedes.setText(String.valueOf(reserva.getCantidadHuespedes()));
        campoCantidadHabitaciones.setText(String.valueOf(reserva.getCantidadHabitaciones()));
        campoCostoNoche.setText(decimalFormat.format(reserva.getCostoNoche()));
        campoCostoTotal.setText(decimalFormat.format(reserva.getCostoTotal()));
        campoMontoReserva.setText(decimalFormat.format(reserva.getMontoReserva()));
        campoMontoAbonado.setText(decimalFormat.format(reserva.getMontoAbonado()));
        cbxAbonoReserva.setValue(reserva.getAbonoReserva());
        cbxEstadoReserva.setValue(reserva.getEstado());

        List<Habitacion> habitacionesReservadas = reserva.obtenerHabitacionesPorReserva(reserva.getIdReserva());
        ObservableList<Habitacion> habitacionesObservableList = FXCollections.observableArrayList(habitacionesReservadas);
        listaHabitacionesReserva.setItems(habitacionesObservableList);

        listaHabitacionesReserva.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Habitacion habitacion, boolean empty) {
                super.updateItem(habitacion, empty);
                if (empty || habitacion == null) {
                    setText(null);
                } else {
                    setText("Número: " + habitacion.getNumero() +
                            ", Capacidad: " + habitacion.getCapacidad() +
                            ", Camas Single: " + habitacion.getCamasSingle() +
                            ", Camas Doble: " + habitacion.getCamasDoble() +
                            ", Categoría: " + habitacion.getCategoria() +
                            ", Precio: $" + decimalFormat.format(habitacion.getPrecio()));
                }
            }
        });

        listaHabitacionesReserva.getSelectionModel().selectAll();
    }




    //Boton Cancelar
    @FXML
    private void cancelarReserva(){
        Stage stage = (Stage) btnCancelarReserva.getScene().getWindow();
        stage.close();

    }
    /*
    public boolean validarDatosReserva() {
        // Verifica que se haya seleccionado un cliente
        if (campoDocumentoClienteReserva.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el documento del cliente.");
            return false;
        }

        // Verifica que se hayan ingresado las fechas
        if (dpFechaLlegadaReserva.getValue() == null || dpFechaSalidaReserva.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar las fechas de llegada y salida.");
            return false;
        }

        // Verifica que la fecha de llegada sea antes que la de salida
        if (dpFechaLlegadaReserva.getValue().isAfter(dpFechaSalidaReserva.getValue())) {
            mostrarAlerta("Error", "La fecha de llegada debe ser antes que la de salida.");
            return false;
        }

        // Verifica que se haya seleccionado al menos una habitación
        if (listaHabitacionesReserva.getSelectionModel().getSelectedItems().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar al menos una habitación.");
            return false;
        }

        // Verifica que la cantidad de huéspedes sea un número válido y positivo
        try {
            int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            if (cantidadHuespedes <= 0) {
                mostrarAlerta("Error", "La cantidad de huéspedes debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cantidad de huéspedes debe ser un número.");
            return false;
        }

        // Verifica que la cantidad de habitaciones sea un número válido y positivo
        try {
            int cantidadHabitaciones = Integer.parseInt(campoCantidadHabitaciones.getText());
            if (cantidadHabitaciones <= 0) {
                mostrarAlerta("Error", "La cantidad de habitaciones debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cantidad de habitaciones debe ser un número.");
            return false;
        }
        /*
        // Verifica que el costo por noche sea un número positivo
        if (campoCostoNoche.getText().isEmpty() || Double.parseDouble(campoCostoNoche.getText()) <= 0) {
            mostrarAlerta("Error", "El costo por noche debe ser un número positivo.");
            return false;
        }

        // Verifica que el costo total sea un número positivo
        if (campoCostoTotal.getText().isEmpty() || Double.parseDouble(campoCostoTotal.getText()) <= 0) {
            mostrarAlerta("Error", "El costo total debe ser un número positivo.");
            return false;
        }

        // Verifica que el monto de la reserva sea un número positivo
        if (campoMontoReserva.getText().isEmpty() || Double.parseDouble(campoMontoReserva.getText()) <= 0) {
            mostrarAlerta("Error", "El monto de la reserva debe ser un número positivo.");
            return false;
        }

        // Verifica que el monto abonado sea válido si se selecciona 'No' en el abono
        if (cbxAbonoReserva.getValue().equals("No") && (campoMontoAbonado.getText().isEmpty() || Double.parseDouble(campoMontoAbonado.getText()) < 0)) {
            mostrarAlerta("Error", "Debe ingresar un monto válido para el monto abonado.");
            return false;
        }

        // Verifica que el estado de la reserva esté seleccionado
        if (cbxEstadoReserva.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar el estado de la reserva.");
            return false;
        }

        // Si todas las validaciones se cumplen
        return true;
    }
    */

    public boolean validarDatosReserva() {
        // Verifica que se haya seleccionado un cliente
        System.out.println("Documento Cliente: " + campoDocumentoClienteReserva.getText());
        if (campoDocumentoClienteReserva.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el documento del cliente.");
            return false;
        }

        // Verifica que se hayan ingresado las fechas
        System.out.println("Fecha de Llegada: " + dpFechaLlegadaReserva.getValue());
        System.out.println("Fecha de Salida: " + dpFechaSalidaReserva.getValue());
        if (dpFechaLlegadaReserva.getValue() == null || dpFechaSalidaReserva.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar las fechas de llegada y salida.");
            return false;
        }

        // Verifica que la fecha de llegada sea antes que la de salida
        if (dpFechaLlegadaReserva.getValue().isAfter(dpFechaSalidaReserva.getValue())) {
            mostrarAlerta("Error", "La fecha de llegada debe ser antes que la de salida.");
            return false;
        }

        // Verifica que se haya seleccionado al menos una habitación
        System.out.println("Habitaciones seleccionadas: " + listaHabitacionesReserva.getSelectionModel().getSelectedItems().size());
        if (listaHabitacionesReserva.getSelectionModel().getSelectedItems().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar al menos una habitación.");
            return false;
        }

        // Verifica que la cantidad de huéspedes sea un número válido y positivo
        System.out.println("Cantidad de Huéspedes: " + campoCantidadHuespedes.getText());
        try {
            int cantidadHuespedes = Integer.parseInt(campoCantidadHuespedes.getText());
            if (cantidadHuespedes <= 0) {
                mostrarAlerta("Error", "La cantidad de huéspedes debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cantidad de huéspedes debe ser un número.");
            return false;
        }

        // Verifica que la cantidad de habitaciones sea un número válido y positivo
        System.out.println("Cantidad de Habitaciones: " + campoCantidadHabitaciones.getText());
        try {
            int cantidadHabitaciones = Integer.parseInt(campoCantidadHabitaciones.getText());
            if (cantidadHabitaciones <= 0) {
                mostrarAlerta("Error", "La cantidad de habitaciones debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cantidad de habitaciones debe ser un número.");
            return false;
        }

        // Verifica que el costo por noche sea un número positivo
        System.out.println("Costo por Noche: " + campoCostoNoche.getText());
        if (campoCostoNoche.getText().isEmpty() || Double.parseDouble(campoCostoNoche.getText()) <= 0) {
            mostrarAlerta("Error", "El costo por noche debe ser un número positivo.");
            return false;
        }

        // Verifica que el costo total sea un número positivo
        System.out.println("Costo Total: " + campoCostoTotal.getText());
        if (campoCostoTotal.getText().isEmpty() || Double.parseDouble(campoCostoTotal.getText()) <= 0) {
            mostrarAlerta("Error", "El costo total debe ser un número positivo.");
            return false;
        }

        // Verifica que el monto de la reserva sea un número positivo
        System.out.println("Monto de la Reserva: " + campoMontoReserva.getText());
        if (campoMontoReserva.getText().isEmpty() || Double.parseDouble(campoMontoReserva.getText()) <= 0) {
            mostrarAlerta("Error", "El monto de la reserva debe ser un número positivo.");
            return false;
        }

        // Verifica que el monto abonado sea válido si se selecciona 'No' en el abono
        System.out.println("Monto Abonado: " + campoMontoAbonado.getText());
        if (cbxAbonoReserva.getValue().equals("No") && (campoMontoAbonado.getText().isEmpty() || Double.parseDouble(campoMontoAbonado.getText()) < 0)) {
            mostrarAlerta("Error", "Debe ingresar un monto válido para el monto abonado.");
            return false;
        }

        // Verifica que el estado de la reserva esté seleccionado
        System.out.println("Estado de la Reserva: " + cbxEstadoReserva.getValue());
        if (cbxEstadoReserva.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar el estado de la reserva.");
            return false;
        }

        // Si todas las validaciones se cumplen
        return true;
    }

    private boolean validarFechasReserva() {
        LocalDate fechaLlegada = dpFechaLlegadaReserva.getValue();
        LocalDate fechaSalida = dpFechaSalidaReserva.getValue();
        LocalDate fechaActual = LocalDate.now();

        if (fechaLlegada == null || fechaSalida == null) {
            mostrarAlerta("Error", "Por favor, seleccione las fechas de llegada y salida.", Alert.AlertType.ERROR);
            return false;
        }

        if (fechaLlegada.isBefore(fechaActual)) {
            mostrarAlerta("Fecha inválida", "La fecha de llegada no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
            return false;
        }

        if (fechaSalida.isBefore(fechaActual)) {
            mostrarAlerta("Fecha inválida", "La fecha de salida no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
            return false;
        }

        if (fechaSalida.isBefore(fechaLlegada)) {
            mostrarAlerta("Fecha inválida", "La fecha de salida no puede ser anterior a la fecha de llegada.", Alert.AlertType.ERROR);
            return false;
        }

        return true;  // Todas las fechas son válidas
    }


    // Método para mostrar alertas al usuario
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }



}

