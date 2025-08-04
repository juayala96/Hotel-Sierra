package com.example.hotelsierra.controlador;

import com.example.hotelsierra.modelo.Cliente;
import com.example.hotelsierra.modelo.Habitacion;
import com.example.hotelsierra.modelo.Reserva;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ControladorFormularioPago {

    @FXML
    private TextField campoMontoPago;

    @FXML
    private Button btnAceptarPago;

    private Reserva reservaActual;

    public void setReservaActual(Reserva reserva) {
        this.reservaActual = reserva;
    }

    @FXML
    private void agregarPago() {
        if (reservaActual == null) {
            mostrarAlerta("Error", "No se seleccionó ninguna reserva.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Obtener el monto ingresado por el usuario desde el campoMontoPago
            double montoPago = Double.parseDouble(campoMontoPago.getText().replace(",", "."));

            if (montoPago <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a cero.", Alert.AlertType.ERROR);
                return;
            }

            // Obtener los valores de la reserva actual
            double montoReserva = reservaActual.getMontoReserva();
            double montoAbonado = reservaActual.getMontoAbonado() + montoPago;
            double costoTotal = reservaActual.getCostoTotal();

            // Validar que el monto abonado no sea superior al costo total de la reserva
            if (montoAbonado > costoTotal) {
                mostrarAlerta("Error", "El monto abonado no puede ser superior al costo total de la reserva.", Alert.AlertType.ERROR);
                return;
            }

            String estadoReserva;
            String abonoReserva;

            if (montoAbonado >= montoReserva) {
                // Caso en que el monto abonado cubre o supera el monto de la reserva
                abonoReserva = "Sí";
                estadoReserva = "Confirmada";
            } else {
                // Caso en que aún falta abonar parte de la reserva
                abonoReserva = "No";
                estadoReserva = "Pendiente";
            }

            // Obtener los demás datos de la reserva
            Cliente cliente = reservaActual.getCliente(); // Obtener el cliente asociado
            List<Habitacion> habitacionesList = Reserva.obtenerHabitacionesPorReserva(reservaActual.getIdReserva()); // Obtener habitaciones asociadas a la reserva desde la base de datos
            List<Integer> habitaciones = habitacionesList.stream()
                    .map(Habitacion::getIdHabitacion)
                    .collect(Collectors.toList());

            // Utilizar el método modificarReserva para actualizar la información de la reserva
            boolean exito = reservaActual.modificarReserva(
                    reservaActual.getIdReserva(),
                    cliente.getIdCliente(),
                    habitaciones.size(),
                    reservaActual.getCantidadHuespedes(),
                    reservaActual.getFechaLlegada(),
                    reservaActual.getFechaSalida(),
                    reservaActual.getCostoNoche(),
                    costoTotal,
                    montoReserva,
                    montoAbonado,
                    abonoReserva,
                    estadoReserva,
                    habitaciones
            );

            if (exito) {
                mostrarAlerta("Éxito", "El pago se ha registrado correctamente.", Alert.AlertType.INFORMATION);
                // Cerrar la ventana de agregar pago si existe
                Stage stage = (Stage) btnAceptarPago.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo registrar el pago.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese un valor numérico válido.", Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

