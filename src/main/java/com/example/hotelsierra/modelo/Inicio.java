package com.example.hotelsierra.modelo;

import com.example.hotelsierra.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inicio {

    private Integer cantidadHabitaciones;
    private Integer cantidadHuespedes;
    private LocalDate fechaLlegada;
    private LocalDate fechaSalida;

    private String categoria;

    public Inicio(Integer cantidadHabitaciones, Integer cantidadHuespedes, LocalDate fechaLlegada, LocalDate fechaSalida, String categoria) {
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.categoria = categoria;
    }

    public Inicio() {
    }

    public Integer getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(Integer cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public Integer getCantidadHuespedes() {
        return cantidadHuespedes;
    }

    public void setCantidadHuespedes(Integer cantidadHuespedes) {
        this.cantidadHuespedes = cantidadHuespedes;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaLlegada, LocalDate fechaSalida, int cantidadHuespedes, int cantidadHabitaciones, String categoria) {
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Calcula la capacidad mínima requerida por habitación
            int capacidadMinima = (int) Math.ceil((double) cantidadHuespedes / cantidadHabitaciones);

            // Consulta para buscar habitaciones disponibles según capacidad, fechas y tipo de habitación
            String consulta = "SELECT h.idHabitacion, h.numero, h.capacidad, h.camasSingle, h.camasDoble, ch.descripcion AS categoria, h.precio " +
                    "FROM habitaciones h " +
                    "JOIN categoriaHabitacion ch ON h.categoria = ch.idCategoriaHabitacion " +
                    "WHERE h.capacidad >= ? " +
                    "AND h.idHabitacion NOT IN (" +
                    "    SELECT eh.idHabitacion " +
                    "    FROM estadoHabitacionHistorial eh " +
                    "    WHERE (" +
                    "        (eh.fechaInicio <= ? AND eh.fechaFin >= ?) " +
                    "        OR (" +
                    "            eh.fechaInicio <= ? AND eh.fechaFin >= ?) " +
                    "        )" +
                    "    AND eh.idEstadoHabitacion IN (" +
                    "        SELECT idEstadoHabitacion FROM estadoHabitacion WHERE descripcion IN ('Reservada', 'Ocupada')" +
                    "    )" +
                    "    AND eh.fechaCreacion = (" +
                    "        SELECT MAX(fechaCreacion) FROM estadoHabitacionHistorial " +
                    "        WHERE idHabitacion = eh.idHabitacion" +
                    "    )" + // Solo considerar el estado más reciente
                    ") ";

            // Si el tipo de habitación no es "Ambas", agrega el filtro de tipo de habitación
            if (!categoria.equalsIgnoreCase("Ambas")) {
                consulta += "AND ch.descripcion = ? ";
            }

            consulta += "ORDER BY h.capacidad ASC";

            pstm = con.prepareStatement(consulta);
            int paramIndex = 1;

            // Setea los valores para la capacidad mínima y las fechas
            pstm.setInt(paramIndex++, capacidadMinima);
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaSalida.atTime(10, 0))); // Setea la fecha de salida con hora 10:00
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaLlegada.atTime(15, 0))); // Setea la fecha de llegada con hora 15:00
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaSalida.atTime(10, 0)));
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaLlegada.atTime(15, 0)));

            // Solo establece el valor del tipo de habitación si no es "Ambas"
            if (!categoria.equalsIgnoreCase("Ambas")) {
                pstm.setString(paramIndex++, categoria);
            }

            rs = pstm.executeQuery();


            while (rs.next()) {
                Habitacion habitacion = new Habitacion();
                habitacion.setIdHabitacion(rs.getInt("idHabitacion"));
                habitacion.setNumero(rs.getInt("numero"));
                habitacion.setCapacidad(rs.getInt("capacidad"));
                habitacion.setCamasSingle(rs.getInt("camasSingle"));
                habitacion.setCamasDoble(rs.getInt("camasDoble"));
                habitacion.setCategoria(rs.getString("categoria"));
                habitacion.setPrecio(rs.getDouble("precio"));
                habitacionesDisponibles.add(habitacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones disponibles: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(habitacionesDisponibles);

        return habitacionesDisponibles;
    }




    public static int obtenerCantidadHabitaciones(String estado, LocalDate fecha) {
        int cantidad = 0;
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            String consulta = "";

            if (estado.equalsIgnoreCase("disponible")) {
                // Habitaciones que están libres para la fecha y hora actuales
                consulta = "SELECT COUNT(*) AS cantidad FROM habitaciones h " +
                        "WHERE h.idHabitacion NOT IN (" +
                        "SELECT ehh.idHabitacion FROM estadoHabitacionHistorial ehh " +
                        "JOIN estadoHabitacion eh ON ehh.idEstadoHabitacion = eh.idEstadoHabitacion " +
                        "WHERE NOW() BETWEEN ehh.fechaInicio AND ehh.fechaFin " +
                        "AND (eh.descripcion = 'Reservada' OR eh.descripcion = 'Ocupada') " +
                        "AND ehh.fechaCreacion = ( " +
                        "    SELECT MAX(ehh2.fechaCreacion) " +
                        "    FROM estadoHabitacionHistorial ehh2 " +
                        "    WHERE ehh2.idHabitacion = ehh.idHabitacion AND ehh2.idReserva = ehh.idReserva " +
                        "))";
                pstm = con.prepareStatement(consulta);

            } else if (estado.equalsIgnoreCase("reservada")) {
                // Habitaciones que están reservadas para la fecha y hora actuales
                consulta = "SELECT COUNT(*) AS cantidad FROM habitaciones h " +
                        "JOIN estadoHabitacionHistorial ehh ON h.idHabitacion = ehh.idHabitacion " +
                        "JOIN estadoHabitacion eh ON ehh.idEstadoHabitacion = eh.idEstadoHabitacion " +
                        "WHERE eh.descripcion = 'Reservada' AND NOW() BETWEEN ehh.fechaInicio AND ehh.fechaFin " +
                        "AND ehh.fechaCreacion = ( " +
                        "    SELECT MAX(ehh2.fechaCreacion) " +
                        "    FROM estadoHabitacionHistorial ehh2 " +
                        "    WHERE ehh2.idHabitacion = ehh.idHabitacion AND ehh2.idReserva = ehh.idReserva " +
                        ")";
                pstm = con.prepareStatement(consulta);

            } else if (estado.equalsIgnoreCase("ocupada")) {
                // Habitaciones que están ocupadas para la fecha y hora actuales
                consulta = "SELECT COUNT(*) AS cantidad FROM habitaciones h " +
                        "JOIN estadoHabitacionHistorial ehh ON h.idHabitacion = ehh.idHabitacion " +
                        "JOIN estadoHabitacion eh ON ehh.idEstadoHabitacion = eh.idEstadoHabitacion " +
                        "WHERE eh.descripcion = 'Ocupada' AND NOW() BETWEEN ehh.fechaInicio AND ehh.fechaFin " +
                        "AND ehh.fechaCreacion = ( " +
                        "    SELECT MAX(ehh2.fechaCreacion) " +
                        "    FROM estadoHabitacionHistorial ehh2 " +
                        "    WHERE ehh2.idHabitacion = ehh.idHabitacion AND ehh2.idReserva = ehh.idReserva " +
                        ")";
                pstm = con.prepareStatement(consulta);

            } else {
                throw new IllegalArgumentException("Estado no válido: " + estado);
            }

            rs = pstm.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la cantidad de habitaciones: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cantidad;
    }






}

