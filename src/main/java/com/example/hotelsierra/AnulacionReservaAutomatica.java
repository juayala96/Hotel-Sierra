package com.example.hotelsierra;

import com.example.hotelsierra.modelo.Reserva;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AnulacionReservaAutomatica {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void iniciarAnulacionAutomatica() {
        Runnable tarea = () -> {
            System.out.println("Ejecutando anulación automática de reservas pendientes - " + LocalDateTime.now());

            try {
                anularReservasPendientes();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        // Programa la tarea para que se ejecute una vez al día a las 3 de la mañana
        //long initialDelay = calcularDelayParaLaSiguienteEjecucion();
        //Ejecuta
        //scheduler.scheduleAtFixedRate(tarea, initialDelay, 24, TimeUnit.HOURS);

        //Se ejecuta una vez cuando inicia la aplicacion y luego a las 24 hs.
        scheduler.scheduleAtFixedRate(tarea, 0, 24, TimeUnit.HOURS);

        //Codigo para probar funcionamiento

        //long initialDelay = 0; // Iniciar inmediatamente para pruebas
        //scheduler.scheduleAtFixedRate(tarea, 0, 10, TimeUnit.SECONDS); // Ejecutar cada 10 segundos





    }

    private long calcularDelayParaLaSiguienteEjecucion() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime proximaEjecucion = ahora.withHour(3).withMinute(0).withSecond(0);
        if (ahora.isAfter(proximaEjecucion)) {
            proximaEjecucion = proximaEjecucion.plusDays(1);
        }
        return ChronoUnit.SECONDS.between(ahora, proximaEjecucion);
    }

    private void anularReservasPendientes() throws SQLException {
        String consulta = "SELECT idReserva, fechaCreacion FROM reservas WHERE estado = (SELECT idEstadoReserva FROM estadoReserva WHERE descripcion = 'Pendiente')";

        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consulta);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                int idReserva = rs.getInt("idReserva");
                LocalDateTime fechaCreacion = rs.getTimestamp("fechaCreacion").toLocalDateTime();
                LocalDateTime ahora = LocalDateTime.now();


                System.out.println("Intentando anular reserva con ID " + idReserva);
                // Verificar si han pasado más de 24 horas desde la creación de la reserva
                if (ChronoUnit.HOURS.between(fechaCreacion, ahora) >= 24) {
                    Reserva reserva = new Reserva();
                    boolean exito = reserva.anularReserva(idReserva);
                    if (exito) {
                        System.out.println("Reserva con ID " + idReserva + " anulada automáticamente por falta de abono.");
                    } else {
                    System.out.println("Falló la anulación de la reserva con ID " + idReserva);
                }
                }





                /*
                //Codigo para probar funcionamiento sin que hayan pasado 24 hs
                System.out.println("Intentando anular reserva con ID " + idReserva);

                Reserva reserva = new Reserva();
                boolean exito = reserva.anularReserva(idReserva);
                if (exito) {
                    System.out.println("Reserva con ID " + idReserva + " anulada automáticamente por falta de abono.");
                } else {
                    System.out.println("Falló la anulación de la reserva con ID " + idReserva);
                }

                 */



            }
        } catch (SQLException e) {
            System.err.println("Error al anular reservas pendientes: " + e.getMessage());
        }
    }

    public void detenerAnulacionAutomatica() {
        scheduler.shutdown(); // Intenta un apagado ordenado
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) { // Espera hasta 5 segundos
                scheduler.shutdownNow(); // Fuerza la terminación si no responde
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow(); // Si ocurre una interrupción, fuerza la terminación
        }
    }


}

