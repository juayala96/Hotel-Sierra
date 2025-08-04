package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Habitacion;
import com.example.hotelsierra.modelo.InformeHabitaciones;
import com.example.hotelsierra.modelo.Inicio;
import com.example.hotelsierra.modelo.Reserva;
import com.itextpdf.layout.borders.Border;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Side;
import javafx.util.Callback;


import java.time.LocalDate;
import java.util.List;

public class ControladorInicio {

    @FXML
    private DatePicker dpFechaLlegadaInicio;

    @FXML
    private DatePicker dpFechaSalidaInicio;

    @FXML
    private TextField campoHuespedesInicio;

    @FXML
    private TextField campoHabitacionesInicio;

    @FXML
    private Button btnBuscarHabitacionesInicio;

    @FXML
    private PieChart pieChartHabitaciones;

    @FXML
    private TableView<Habitacion> tablaHabitacionesInicio;

    @FXML
    private ComboBox<String> cbxMesInforme;

    @FXML
    private ComboBox<String> cbxAnioInforme;

    @FXML
    private ComboBox<String> cbxTipoHabitacionInforme;
    @FXML
    private ComboBox<String> cbxTipoHabitacionConsulta;


    @FXML
    private Button btnGenerarInforme;

    @FXML
    private TableColumn<Habitacion, Integer> colNumero;
    @FXML
    private TableColumn<Habitacion, Integer> colCapacidad;
    @FXML
    private TableColumn<Habitacion, Integer> colCamasSingle;
    @FXML
    private TableColumn<Habitacion, Integer> colCamasDoble;
    @FXML
    private TableColumn<Habitacion, String> colCategoria;
    @FXML
    private TableColumn<Habitacion, Double> colPrecio;

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        colCamasSingle.setCellValueFactory(new PropertyValueFactory<>("camasSingle"));
        colCamasDoble.setCellValueFactory(new PropertyValueFactory<>("camasDoble"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));


        btnBuscarHabitacionesInicio.setOnAction(event -> buscarHabitacionesDisponibles());

        cargarDatosPieChart();


        // Inicializa ComboBox con los meses
        cbxMesInforme.getItems().addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");

        // Inicializa ComboBox con el rango de años (últimos 10 años)
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            cbxAnioInforme.getItems().add(String.valueOf(i));
        }


        cbxTipoHabitacionInforme.getItems().addAll("Estandar", "Lujo", "Ambas");

        // Deshabilita días anteriores a la fecha actual en dpFechaLlegadaReserva
        Callback<DatePicker, DateCell> dayCellFactoryLlegada = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Deshabilitar los días antes del día actual
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
        dpFechaLlegadaInicio.setDayCellFactory(dayCellFactoryLlegada);

        // Deshabilita días anteriores a la fecha actual en dpFechaSalidaReserva
        Callback<DatePicker, DateCell> dayCellFactorySalida = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Deshabilitar los días antes del día actual
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
        dpFechaSalidaInicio.setDayCellFactory(dayCellFactorySalida);

        dpFechaLlegadaInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                mostrarAlerta("Fecha inválida", "La fecha de llegada no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
                dpFechaLlegadaInicio.setValue(null);
            }
        });

        dpFechaSalidaInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                mostrarAlerta("Fecha inválida", "La fecha de salida no puede ser anterior a la fecha actual.", Alert.AlertType.ERROR);
                dpFechaSalidaInicio.setValue(null);
            }
        });


        cbxTipoHabitacionConsulta.getItems().addAll("Estandar", "Lujo", "Ambas");
    }
    @FXML
    private void buscarHabitacionesDisponibles() {

        try {
            LocalDate fechaLlegada = dpFechaLlegadaInicio.getValue();
            LocalDate fechaSalida = dpFechaSalidaInicio.getValue();
            int cantidadHuespedes = Integer.parseInt(campoHuespedesInicio.getText());
            int cantidadHabitaciones = Integer.parseInt(campoHabitacionesInicio.getText());
            String categoria = cbxTipoHabitacionConsulta.getValue(); // Obtener el valor del ComboBox

            Inicio inicio = new Inicio();
            List<Habitacion> habitacionesDisponibles = inicio.obtenerHabitacionesDisponibles(fechaLlegada, fechaSalida, cantidadHuespedes, cantidadHabitaciones, categoria);

            if (habitacionesDisponibles.isEmpty()) {
                mostrarAlerta("Error", "No se encontraron habitaciones disponibles.", Alert.AlertType.ERROR);
            }

            // Mostrar los resultados en la tabla
            ObservableList<Habitacion> listaHabitaciones = FXCollections.observableArrayList(habitacionesDisponibles);
            tablaHabitacionesInicio.setItems(listaHabitaciones);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingresa valores numéricos válidos para los campos de huéspedes y habitaciones.", Alert.AlertType.ERROR);
        }
    }


    /* 26/11/2024 16:28
    private void buscarHabitacionesDisponibles() {
        try {
            // Obtener los valores de los campos
            LocalDate fechaLlegada = dpFechaLlegadaInicio.getValue();
            LocalDate fechaSalida = dpFechaSalidaInicio.getValue();
            int cantidadHuespedes = Integer.parseInt(campoHuespedesInicio.getText());
            int cantidadHabitaciones = Integer.parseInt(campoHabitacionesInicio.getText());

            // Validar que las fechas sean válidas
            if (fechaLlegada == null || fechaSalida == null || !fechaSalida.isAfter(fechaLlegada)) {
                mostrarAlerta("Error", "Por favor, ingresa fechas válidas de llegada y salida.", Alert.AlertType.ERROR);
                return;
            }


            // Llamar al método obtenerHabitacionesDisponibles de la clase Inicio
            Inicio inicio = new Inicio();
            List<Habitacion> habitacionesDisponibles = inicio.obtenerHabitacionesDisponibles(fechaLlegada, fechaSalida, cantidadHuespedes, cantidadHabitaciones);


            //Prueba llamando al metodo de la clase Reserva

            Reserva reserva = new Reserva();
            List<Habitacion> habitacionesDisponibles = reserva.obtenerHabitacionesDisponibles(fechaLlegada, fechaSalida, cantidadHuespedes, cantidadHabitaciones);



            // Mostrar los resultados en la tabla
            ObservableList<Habitacion> listaHabitaciones = FXCollections.observableArrayList(habitacionesDisponibles);
            tablaHabitacionesInicio.setItems(listaHabitaciones);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingresa valores numéricos válidos para los campos de huéspedes y habitaciones.", Alert.AlertType.ERROR);
        }
    }

     */


    public void cargarDatosPieChart() {
        LocalDate fechaHoy = LocalDate.now();
        int habitacionesDisponibles = Inicio.obtenerCantidadHabitaciones("disponible", fechaHoy);
        int habitacionesReservadas = Inicio.obtenerCantidadHabitaciones("reservada", fechaHoy);
        int habitacionesOcupadas = Inicio.obtenerCantidadHabitaciones("ocupada", fechaHoy);

        ObservableList<PieChart.Data> datosPie = FXCollections.observableArrayList();

        if (habitacionesDisponibles > 0) {
            datosPie.add(new PieChart.Data("Disponibles", habitacionesDisponibles));
        }
        if (habitacionesReservadas > 0) {
            datosPie.add(new PieChart.Data("Reservadas", habitacionesReservadas));
        }
        if (habitacionesOcupadas > 0) {
            datosPie.add(new PieChart.Data("Ocupadas", habitacionesOcupadas));
        }

        // Si todas las categorías son cero, muestra un mensaje neutro
        if (datosPie.isEmpty()) {
            datosPie.add(new PieChart.Data("No hay datos disponibles", 1));
        }

        pieChartHabitaciones.setData(datosPie);

        // Añade etiquetas de valores en cada segmento y evita cambio de color
        datosPie.forEach(data -> {
            String label = String.format("%.0f", data.getPieValue());
            Tooltip tooltip = new Tooltip(label); // Muestra el valor al pasar el cursor
            Tooltip.install(data.getNode(), tooltip);

            // Evita cambio de estilo al pasar el mouse sobre el piechart
            data.getNode().setOnMouseEntered(event -> {
                data.getNode().setEffect(null);
            });

            data.getNode().setOnMouseExited(event -> {
                data.getNode().setEffect(null);
            });
        });

        // Asigna colores personalizados a cada categoría
        for (PieChart.Data d : pieChartHabitaciones.getData()) {
            String nombre = d.getName();
            if (nombre.equalsIgnoreCase("Disponibles")) {
                d.getNode().setStyle("-fx-pie-color: green;");
            } else if (nombre.equalsIgnoreCase("Ocupadas")) {
                d.getNode().setStyle("-fx-pie-color: red;");
            } else if (nombre.equalsIgnoreCase("Reservadas")) {
                d.getNode().setStyle("-fx-pie-color: orange;");
            }
        }

        pieChartHabitaciones.setLegendVisible(false);
    }


    // Método para actualizar el gráfico
    public void actualizarPieChart() {
        cargarDatosPieChart();
    }

    @FXML
    private void generarInforme() {
        String mesSeleccionado = cbxMesInforme.getValue();
        String anioSeleccionado = cbxAnioInforme.getValue();
        String tipoHabitacion = cbxTipoHabitacionInforme.getValue();

        if (mesSeleccionado == null || anioSeleccionado == null || tipoHabitacion == null) {
            mostrarAlerta("Error", "Por favor, seleccione el mes, el año y el tipo de habitación.", Alert.AlertType.ERROR);
            return;
        }



            try {

                InformeHabitaciones informe = new InformeHabitaciones();
                List<InformeHabitaciones> habitacionesMasSolicitadas = informe.obtenerHabitacionesMasSolicitadas(mesSeleccionado, Integer.parseInt(anioSeleccionado), tipoHabitacion);
                if (habitacionesMasSolicitadas.isEmpty()) {
                    mostrarAlerta("Informe", "No hay reservas registradas para el mes seleccionado.", Alert.AlertType.ERROR);
                } else {
                    informe.generarInformeHabitacionesMasSolicitadas(habitacionesMasSolicitadas, mesSeleccionado, Integer.parseInt(anioSeleccionado), tipoHabitacion);
                    mostrarAlerta("Éxito", "El informe se ha generado correctamente.", Alert.AlertType.INFORMATION);
                }


            } catch (Exception e) {
                mostrarAlerta("Error", "Hubo un error al generar el informe: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }

        }




    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

