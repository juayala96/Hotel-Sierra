package com.example.hotelsierra.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.hotelsierra.Conexion;
import com.example.hotelsierra.modelo.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reserva {

    private int idReserva;

    private Cliente cliente;

    private Integer cantidadHabitaciones;
    private Integer cantidadHuespedes;
    private LocalDate fechaLlegada;
    private LocalDate fechaSalida;
    private String categoria;
    private Double costoNoche;
    private Double costoTotal;
    private Double montoReserva;
    private Double montoAbonado;
    private String abonoReserva;
    private String estado;

    private String habitaciones;
    private Timestamp fechaCreacion;

    public Reserva(Cliente cliente, Integer cantidadHabitaciones, Integer cantidadHuespedes, LocalDate fechaLlegada, LocalDate fechaSalida, String categoria, Double costoNoche, Double costoTotal, Double montoReserva, Double montoAbonado, String abonoReserva, String estado, String habitaciones, Timestamp fechaCreacion) {
        this.cliente = cliente;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.categoria = categoria;
        this.costoNoche = costoNoche;
        this.costoTotal = costoTotal;
        this.montoReserva = montoReserva;
        this.montoAbonado = montoAbonado;
        this.abonoReserva = abonoReserva;
        this.estado = estado;
        this.habitaciones = habitaciones;
        this.fechaCreacion = fechaCreacion;
    }

    public Reserva(int idReserva, Cliente cliente, Integer cantidadHabitaciones, Integer cantidadHuespedes, LocalDate fechaLlegada, LocalDate fechaSalida, String categoria, Double costoNoche, Double costoTotal, Double montoReserva, Double montoAbonado, String abonoReserva, String estado, String habitaciones, Timestamp fechaCreacion) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.categoria = categoria;
        this.costoNoche = costoNoche;
        this.costoTotal = costoTotal;
        this.montoReserva = montoReserva;
        this.montoAbonado = montoAbonado;
        this.abonoReserva = abonoReserva;
        this.estado = estado;
        this.habitaciones = habitaciones;
        this.fechaCreacion = fechaCreacion;
    }

    public Reserva(int idReserva, Cliente cliente, Integer cantidadHabitaciones, Integer cantidadHuespedes, LocalDate fechaLlegada,
                   LocalDate fechaSalida, Double costoNoche, Double costoTotal, Double montoReserva, Double montoAbonado, String abonoReserva,
                   String estado, String habitaciones) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.costoNoche = costoNoche;
        this.costoTotal = costoTotal;
        this.montoReserva = montoReserva;
        this.montoAbonado = montoAbonado;
        this.abonoReserva = abonoReserva;
        this.estado = estado;
        this.habitaciones = habitaciones;
    }

    public Reserva(Cliente cliente, Integer cantidadHabitaciones, Integer cantidadHuespedes, LocalDate fechaLlegada, LocalDate fechaSalida, Double costoNoche, Double costoTotal, Double montoReserva, Double montoAbonado, String abonoReserva, String estado, String habitaciones) {
        this.cliente = cliente;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.costoNoche = costoNoche;
        this.costoTotal = costoTotal;
        this.montoReserva = montoReserva;
        this.montoAbonado = montoAbonado;
        this.abonoReserva = abonoReserva;
        this.estado = estado;
        this.habitaciones = habitaciones;
    }
    public Reserva(int idReserva, Cliente cliente, int cantidadHabitaciones, int cantidadHuespedes,
                   LocalDate fechaLlegada, LocalDate fechaSalida, double costoNoche, double costoTotal,
                   double montoReserva, double montoAbonado, String abonoReserva, String estado,
                   String habitaciones) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.costoNoche = costoNoche;
        this.costoTotal = costoTotal;
        this.montoReserva = montoReserva;
        this.montoAbonado = montoAbonado;
        this.abonoReserva = abonoReserva;
        this.estado = estado;
        this.habitaciones = habitaciones;
    }

    public Reserva(int idReserva, Cliente cliente, Integer cantidadHabitaciones, Integer cantidadHuespedes, LocalDate fechaLlegada, LocalDate fechaSalida, Double costoNoche, Double costoTotal, Double montoReserva, Double montoAbonado, String abonoReserva, String estado, String habitaciones, Timestamp fechaCreacion) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.costoNoche = costoNoche;
        this.costoTotal = costoTotal;
        this.montoReserva = montoReserva;
        this.montoAbonado = montoAbonado;
        this.abonoReserva = abonoReserva;
        this.estado = estado;
        this.habitaciones = habitaciones;
        this.fechaCreacion = fechaCreacion;
    }

    public Reserva() {
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public Double getCostoNoche() {
        return costoNoche;
    }

    public void setCostoNoche(Double costoNoche) {
        this.costoNoche = costoNoche;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Double getMontoReserva() {
        return montoReserva;
    }

    public void setMontoReserva(Double montoReserva) {
        this.montoReserva = montoReserva;
    }

    public Double getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(Double montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public String getAbonoReserva() {
        return abonoReserva;
    }

    public void setAbonoReserva(String abonoReserva) {
        this.abonoReserva = abonoReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(String habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    //ok
    public static ObservableList<Reserva> obtenerListaReservas() {
        ObservableList<Reserva> listaReservas = FXCollections.observableArrayList();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Consulta para obtener las reservas y datos del cliente
            String consulta = "SELECT r.idReserva, r.cantidadHabitaciones, r.cantidadHuespedes, " +
                    "r.fechaLlegada, r.fechaSalida, r.costoNoche, r.costoTotal, r.montoReserva, r.montoAbonado, " +
                    "ar.descripcion AS abonoReserva, er.descripcion AS estado, " +
                    "c.idCliente, c.nombre, c.apellido, c.documento " +
                    "FROM reservas r " +
                    "INNER JOIN abonoReserva ar ON r.abonoReserva = ar.idAbonoReserva " +
                    "INNER JOIN estadoReserva er ON r.estado = er.idEstadoReserva " +
                    "INNER JOIN clientes c ON r.clienteReserva = c.idCliente";

            pstm = con.prepareStatement(consulta);
            rs = pstm.executeQuery();

            // Lista temporal para almacenar los datos de las reservas
            List<Reserva> reservasTemporales = new ArrayList<>();

            while (rs.next()) {
                int idReserva = rs.getInt("idReserva");
                int cantidadHabitaciones = rs.getInt("cantidadHabitaciones");
                int cantidadHuespedes = rs.getInt("cantidadHuespedes");
                LocalDate fechaLlegada = rs.getObject("fechaLlegada", LocalDate.class);
                LocalDate fechaSalida = rs.getObject("fechaSalida", LocalDate.class);
                double costoNoche = rs.getDouble("costoNoche");
                double costoTotal = rs.getDouble("costoTotal");
                double montoReserva = rs.getDouble("montoReserva");
                double montoAbonado = rs.getDouble("montoAbonado");
                String abonoReserva = rs.getString("abonoReserva");
                String estado = rs.getString("estado");

                // Datos del cliente asociados a la reserva
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String documento = rs.getString("documento");

                // Crea objeto Cliente
                Cliente cliente = new Cliente(idCliente, nombre, apellido, documento);

                // Crea objeto Reserva sin habitaciones (las habitaciones se añadirán después)
                Reserva reserva = new Reserva(idReserva, cliente, cantidadHabitaciones, cantidadHuespedes,
                        fechaLlegada, fechaSalida, costoNoche, costoTotal, montoReserva,
                        montoAbonado, abonoReserva, estado, "");

                reservasTemporales.add(reserva);
            }


            rs.close();
            pstm.close();

            // ¿btenemos las habitaciones para cada reserva
            for (Reserva reserva : reservasTemporales) {
                List<Habitacion> habitaciones = obtenerHabitacionesPorReserva(reserva.getIdReserva());
                String habitacionesStr = habitaciones.stream()
                        .map(habitacion -> "Habitación " + habitacion.getNumero())
                        .collect(Collectors.joining(", "));
                reserva.setHabitaciones(habitacionesStr);


                listaReservas.add(reserva);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return listaReservas;
    }




      //Método para obtener las habitaciones asociadas a una reserva.

    public static List<Habitacion> obtenerHabitacionesPorReserva(int idReserva) {
        List<Habitacion> habitaciones = new ArrayList<>();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            String consulta = "SELECT h.idHabitacion, h.numero, h.capacidad, h.camasSingle, h.camasDoble, h.categoria, h.precio " +
                    "FROM reservaHabitacion rh " +
                    "INNER JOIN habitaciones h ON rh.idHabitacion = h.idHabitacion " +
                    "WHERE rh.idReserva = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idReserva);
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
                habitaciones.add(habitacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones para la reserva: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return habitaciones;
    }

    //sin tipo de habitacion
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaLlegada, LocalDate fechaSalida, int cantidadHuespedes, int cantidadHabitaciones) {
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Calcular la capacidad mínima requerida por habitación
            int capacidadMinima = (int) Math.ceil((double) cantidadHuespedes / cantidadHabitaciones);

            String consulta = "SELECT h.idHabitacion, h.numero, h.capacidad, h.camasSingle, h.camasDoble, ch.descripcion AS categoria, h.precio " +
                    "FROM habitaciones h " +
                    "JOIN categoriaHabitacion ch ON h.categoria = ch.idCategoriaHabitacion " +
                    "WHERE h.capacidad >= ? " +
                    "AND h.idHabitacion NOT IN (" +
                    "    SELECT eh.idHabitacion " +
                    "    FROM estadoHabitacionHistorial eh " +
                    "    WHERE (" +
                    "        (eh.fechaInicio <= ? AND eh.fechaFin >= ?) " + // Conflicto directo con la fecha solicitada
                    "        OR (" +
                    "            eh.fechaInicio <= ? AND eh.fechaFin >= ?) " + // Conflicto si el check-in de otra reserva ocurre el mismo día
                    "        )" +
                    "    AND eh.idEstadoHabitacion IN (" +
                    "        SELECT idEstadoHabitacion FROM estadoHabitacion WHERE descripcion IN ('Reservada', 'Ocupada')" +
                    "    )" +
                    "    AND eh.fechaCreacion = (" +
                    "        SELECT MAX(fechaCreacion) FROM estadoHabitacionHistorial " +
                    "        WHERE idHabitacion = eh.idHabitacion" +
                    "    )" + // Solo considerar el estado más reciente
                    ") " +
                    "ORDER BY h.capacidad ASC";

            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, capacidadMinima);
            pstm.setTimestamp(2, Timestamp.valueOf(fechaSalida.atTime(10, 0))); // Setear la fecha de salida con hora 10:00
            pstm.setTimestamp(3, Timestamp.valueOf(fechaLlegada.atTime(15, 0))); // Setear la fecha de llegada con hora 15:00
            pstm.setTimestamp(4, Timestamp.valueOf(fechaSalida.atTime(10, 0)));
            pstm.setTimestamp(5, Timestamp.valueOf(fechaLlegada.atTime(15, 0)));

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

    //metodo que incluye tipo de habitacion - lo uso en el controladorInicio
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaLlegada, LocalDate fechaSalida, int cantidadHuespedes, int cantidadHabitaciones, String categoria) {
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Calcular la capacidad mínima requerida por habitación
            int capacidadMinima = (int) Math.ceil((double) cantidadHuespedes / cantidadHabitaciones);

            // Consulta para buscar habitaciones disponibles según los filtros de capacidad, fechas y tipo de habitación
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
                    "    )" +
                    ") ";


            if (!categoria.equalsIgnoreCase("Ambas")) {
                consulta += "AND ch.descripcion = ? ";
            }

            consulta += "ORDER BY h.capacidad ASC";

            pstm = con.prepareStatement(consulta);
            int paramIndex = 1;

            // Setea los valores para la capacidad mínima y las fechas
            pstm.setInt(paramIndex++, capacidadMinima);
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaSalida.atTime(10, 0))); // Setea la fecha de salida con hora 10:00
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaLlegada.atTime(15, 0))); // Seteala fecha de llegada con hora 15:00
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaSalida.atTime(10, 0)));
            pstm.setTimestamp(paramIndex++, Timestamp.valueOf(fechaLlegada.atTime(15, 0)));


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



    public boolean registrarReserva(int clienteId, int cantidadHabitaciones, int cantidadHuespedes, LocalDate fechaLlegada,
                                    LocalDate fechaSalida, double costoNoche, double costoTotal, double montoReserva,
                                    double montoAbonado, String abonoReserva, String estado, List<Integer> habitaciones) {
        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
        int idReserva = insertarReserva(clienteId, cantidadHabitaciones, cantidadHuespedes, fechaLlegada, fechaSalida,
                costoNoche, costoTotal, montoReserva, montoAbonado, abonoReserva, estado, fechaCreacion);

        if (idReserva != -1) {
            // Guarda la relación en la tabla intermedia reservaHabitacion y actualizar el estado en estadoHabitacionHistorial
            for (Integer idHabitacion : habitaciones) {
                agregarRelacionReservaHabitacion(idReserva, idHabitacion);
                actualizarEstadoHabitacionHistorial(idHabitacion, "Reservada", fechaLlegada, fechaSalida, idReserva, "crearReserva");
            }
            return true;
        }
        return false;
    }

    // Método para insertar la reserva y devolver el ID generado
    private int insertarReserva(int clienteId, int cantidadHabitaciones, int cantidadHuespedes, LocalDate fechaLlegada,
                                LocalDate fechaSalida, double costoNoche, double costoTotal, double montoReserva,
                                double montoAbonado, String abonoReserva, String estado, Timestamp fechaCreacion) {
        String consultaReserva = "INSERT INTO reservas (clienteReserva, cantidadHabitaciones, cantidadHuespedes, " +
                "fechaLlegada, fechaSalida, costoNoche, costoTotal, montoReserva, montoAbonado, abonoReserva, estado, fechaCreacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consultaReserva, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, clienteId);
            pstm.setInt(2, cantidadHabitaciones);
            pstm.setInt(3, cantidadHuespedes);
            pstm.setDate(4, Date.valueOf(fechaLlegada));
            pstm.setDate(5, Date.valueOf(fechaSalida));
            pstm.setDouble(6, costoNoche);
            pstm.setDouble(7, costoTotal);
            pstm.setDouble(8, montoReserva);
            pstm.setDouble(9, montoAbonado);
            pstm.setInt(10, obtenerIdAbonoReserva(abonoReserva, con));
            pstm.setInt(11, obtenerIdEstadoReserva(estado, con));
            pstm.setTimestamp(12, fechaCreacion);

            int filasInsertadas = pstm.executeUpdate();
            if (filasInsertadas > 0) {
                try (ResultSet rsGenerado = pstm.getGeneratedKeys()) {
                    if (rsGenerado.next()) {
                        return rsGenerado.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar la reserva: " + e.getMessage());
        }
        return -1;
    }







    //modificar no esta cambiando bien la disponibilidad de habitacion, cuando cambio fechas pero elijo la misma habitacion. o sea no esta eliminando la relacion en la tabla estadohabitacionhistorial
    public boolean modificarReserva(int idReserva, int clienteId, int cantidadHabitaciones, int cantidadHuespedes,
                                    LocalDate fechaLlegada, LocalDate fechaSalida, double costoNoche, double costoTotal,
                                    double montoReserva, double montoAbonado, String abonoReserva, String estado,
                                    List<Integer> habitacionesNuevas) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {
            // Actualizar los detalles de la reserva en la base de datos
            String consultaActualizarReserva = "UPDATE reservas SET clienteReserva = ?, cantidadHabitaciones = ?, cantidadHuespedes = ?, " +
                    "fechaLlegada = ?, fechaSalida = ?, costoNoche = ?, costoTotal = ?, montoReserva = ?, " +
                    "montoAbonado = ?, abonoReserva = ?, estado = ? WHERE idReserva = ?";
            pstm = con.prepareStatement(consultaActualizarReserva);
            pstm.setInt(1, clienteId);
            pstm.setInt(2, cantidadHabitaciones);
            pstm.setInt(3, cantidadHuespedes);
            pstm.setDate(4, Date.valueOf(fechaLlegada));
            pstm.setDate(5, Date.valueOf(fechaSalida));
            pstm.setDouble(6, costoNoche);
            pstm.setDouble(7, costoTotal);
            pstm.setDouble(8, montoReserva);
            pstm.setDouble(9, montoAbonado);
            pstm.setInt(10, obtenerIdAbonoReserva(abonoReserva, con));  // Obtener el ID de abono de la reserva
            pstm.setInt(11, obtenerIdEstadoReserva(estado, con));  // Obtener el ID del estado de la reserva
            pstm.setInt(12, idReserva);

            int filasActualizadas = pstm.executeUpdate();
            pstm.close();

            if (filasActualizadas > 0) {
                // Obtener las habitaciones actuales de la reserva
                List<Integer> habitacionesAnteriores = obtenerHabitacionesPorReserva(idReserva)
                        .stream()
                        .map(Habitacion::getIdHabitacion)
                        .collect(Collectors.toList());

                // Identificar habitaciones eliminadas y habitaciones agregadas
                List<Integer> habitacionesEliminadas = new ArrayList<>(habitacionesAnteriores);
                habitacionesEliminadas.removeAll(habitacionesNuevas);

                List<Integer> habitacionesAgregadas = new ArrayList<>(habitacionesNuevas);
                habitacionesAgregadas.removeAll(habitacionesAnteriores);

                // Eliminar las habitaciones eliminadas y actualizar el historial
                for (Integer idHabitacion : habitacionesEliminadas) {
                    eliminarRelacionReservaHabitacion(idReserva, idHabitacion);
                    eliminarRegistroEstadoHabitacionHistorial(idReserva, idHabitacion);
                    System.out.println("Registro eliminado en estadoHabitacionHistorial para habitación con ID " + idHabitacion + " y reserva con ID " + idReserva);
                }

                // Actualizar las fechas de las habitaciones que se mantienen en la reserva
                for (Integer idHabitacion : habitacionesAnteriores) {
                    if (!habitacionesEliminadas.contains(idHabitacion)) {
                        // Actualizar las fechas del estado anterior (en este caso eliminamos el estado anterior y luego creamos uno nuevo)
                        eliminarRegistroEstadoHabitacionHistorial(idReserva, idHabitacion);
                        actualizarEstadoHabitacionHistorial(idHabitacion, "Reservada", fechaLlegada, fechaSalida, idReserva, "modificarReserva");
                        System.out.println("Registro actualizado en estadoHabitacionHistorial para habitación con ID " + idHabitacion);
                    }
                }

                // Agregar las nuevas habitaciones y su estado
                for (Integer idHabitacion : habitacionesAgregadas) {
                    agregarRelacionReservaHabitacion(idReserva, idHabitacion);
                    actualizarEstadoHabitacionHistorial(idHabitacion, "Reservada", fechaLlegada, fechaSalida, idReserva, "modificarReserva");
                    System.out.println("Registro insertado en estadoHabitacionHistorial para habitación con ID " + idHabitacion);
                }

                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar la reserva: " + e.getMessage());
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Retornar false si no se realizaron cambios
    }



    public int obtenerIdEstadoReserva(String estado, Connection con) throws SQLException {
        String consultaEstado = "SELECT idEstadoReserva FROM estadoReserva WHERE descripcion = ?";
        try (PreparedStatement pstm = con.prepareStatement(consultaEstado)) {
            pstm.setString(1, estado);
            try (ResultSet rsEstado = pstm.executeQuery()) {
                if (rsEstado.next()) {
                    return rsEstado.getInt("idEstadoReserva");
                }
            }
        }
        return 1; // Retornar 1 como valor por defecto si no se encuentra el estado
    }


    private int obtenerIdAbonoReserva(String abonoReserva, Connection con) throws SQLException {
        String consultaAbono = "SELECT idAbonoReserva FROM abonoReserva WHERE descripcion = ?";
        try (PreparedStatement pstm = con.prepareStatement(consultaAbono)) {
            pstm.setString(1, abonoReserva);
            try (ResultSet rsAbono = pstm.executeQuery()) {
                if (rsAbono.next()) {
                    return rsAbono.getInt("idAbonoReserva");
                }
            }
        }
        return 1; // Retornar 1 como valor por defecto si no se encuentra el abono
    }
    //para probar check in
    public static String obtenerEstadoReserva(int idReserva) {
        String estado = null;

        String consulta = "SELECT er.descripcion FROM reservas r " +
                "JOIN estadoReserva er ON r.estado = er.idEstadoReserva " +
                "WHERE r.idReserva = ?";

        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consulta)) {

            pstm.setInt(1, idReserva);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    estado = rs.getString("descripcion");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el estado de la reserva: " + e.getMessage());
        }

        return estado;
    }



    private void eliminarRelacionReservaHabitacion(int idReserva, int idHabitacion) {
        String eliminarRelacion = "DELETE FROM reservaHabitacion WHERE idReserva = ? AND idHabitacion = ?";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(eliminarRelacion)) {
            pstm.setInt(1, idReserva);
            pstm.setInt(2, idHabitacion);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar la relación reserva-habitación: " + e.getMessage());
        }
    }





    private void agregarRelacionReservaHabitacion(int idReserva, int idHabitacion) {
        String agregarRelacion = "INSERT INTO reservaHabitacion (idReserva, idHabitacion) VALUES (?, ?)";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(agregarRelacion)) {
            pstm.setInt(1, idReserva);
            pstm.setInt(2, idHabitacion);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar la relación reserva-habitación: " + e.getMessage());
        }
    }






    public boolean anularReserva(int idReserva) {
        System.out.println("Inicio del proceso para anular la reserva con ID: " + idReserva);


        boolean reservaAnulada = anularEstadoReservaEnBaseDeDatos(idReserva);
        if (!reservaAnulada) {
            System.err.println("No se pudo anular la reserva con ID: " + idReserva);
            return false;
        }


        List<Integer> habitaciones = obtenerHabitacionesPorReserva(idReserva)
                .stream()
                .map(Habitacion::getIdHabitacion)
                .collect(Collectors.toList());
        System.out.println("Habitaciones asociadas a la reserva: " + habitaciones);


        for (Integer idHabitacion : habitaciones) {
            eliminarRegistroEstadoHabitacionHistorial(idReserva, idHabitacion);
        }


        eliminarRelacionesReservaHabitacion(idReserva);

        System.out.println("Reserva con ID " + idReserva + " anulada correctamente.");
        return true;
    }


    private boolean anularEstadoReservaEnBaseDeDatos(int idReserva) {
        String consultaAnularReserva = "UPDATE reservas SET estado = (SELECT idEstadoReserva FROM estadoReserva WHERE descripcion = 'Anulada') WHERE idReserva = ?";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consultaAnularReserva)) {
            pstm.setInt(1, idReserva);
            int filasActualizadas = pstm.executeUpdate();
            System.out.println("Filas actualizadas en reservas (anular): " + filasActualizadas);
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al anular la reserva en la base de datos: " + e.getMessage());
            return false;
        }
    }





    public boolean eliminarReserva(int idReserva) {
        List<Integer> habitaciones = obtenerHabitacionesPorReserva(idReserva)
                .stream()
                .map(Habitacion::getIdHabitacion)
                .collect(Collectors.toList());


        for (Integer idHabitacion : habitaciones) {
            eliminarRegistroEstadoHabitacionHistorial(idReserva, idHabitacion);
        }


        eliminarRelacionesReservaHabitacion(idReserva);


        return eliminarReservaEnBaseDeDatos(idReserva);
    }

    // Método para eliminar la reserva en la tabla reservas
    private boolean eliminarReservaEnBaseDeDatos(int idReserva) {
        String consultaReserva = "DELETE FROM reservas WHERE idReserva = ?";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consultaReserva)) {
            pstm.setInt(1, idReserva);
            int filasEliminadas = pstm.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la reserva: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar las relaciones en la tabla reservaHabitacion
    private void eliminarRelacionesReservaHabitacion(int idReserva) {
        String consultaRelacion = "DELETE FROM reservaHabitacion WHERE idReserva = ?";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consultaRelacion)) {
            pstm.setInt(1, idReserva);
            pstm.executeUpdate();
            System.out.println("Relaciones eliminadas para la reserva con ID: " + idReserva);
        } catch (SQLException e) {
            System.err.println("Error al eliminar relaciones de reservaHabitacion para la reserva con ID " + idReserva + ": " + e.getMessage());
        }
    }

    // Método auxiliar para actualizar el estado de la habitación en el historial 22/11/2024 funciona
    /*
    public void actualizarEstadoHabitacionHistorial(int idHabitacion, String nuevoEstado, LocalDate fechaInicio, LocalDate fechaFin, int idReserva) {
        try (Connection con = Conexion.leerConexion()) {
            // Obtener el ID del estado
            String consultaEstado = "SELECT idEstadoHabitacion FROM estadoHabitacion WHERE descripcion = ?";
            int idEstadoHabitacion;
            try (PreparedStatement pstm = con.prepareStatement(consultaEstado)) {
                pstm.setString(1, nuevoEstado);
                try (ResultSet rsEstado = pstm.executeQuery()) {
                    idEstadoHabitacion = rsEstado.next() ? rsEstado.getInt("idEstadoHabitacion") : 1;
                }
            }

            // Insertar un nuevo registro en estadoHabitacionHistorial
            String insertarHistorial = "INSERT INTO estadoHabitacionHistorial (idHabitacion, idEstadoHabitacion, fechaInicio, fechaFin, idReserva) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmInsertar = con.prepareStatement(insertarHistorial)) {
                pstmInsertar.setInt(1, idHabitacion);
                pstmInsertar.setInt(2, idEstadoHabitacion);
                pstmInsertar.setDate(3, Date.valueOf(fechaInicio));
                pstmInsertar.setDate(4, Date.valueOf(fechaFin));
                pstmInsertar.setInt(5, idReserva);
                pstmInsertar.executeUpdate();
                System.out.println("Registro insertado en estadoHabitacionHistorial para habitación con ID " + idHabitacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la habitación con ID " + idHabitacion + ": " + e.getMessage());
        }
    }

     */
    public void actualizarEstadoHabitacionHistorial(int idHabitacion, String nuevoEstado, LocalDate fechaInicio, LocalDate fechaFin, int idReserva, String tipoOperacion) {
        try (Connection con = Conexion.leerConexion()) {
            //Obtiene ID del estado
            String consultaEstado = "SELECT idEstadoHabitacion FROM estadoHabitacion WHERE descripcion = ?";
            int idEstadoHabitacion;
            try (PreparedStatement pstm = con.prepareStatement(consultaEstado)) {
                pstm.setString(1, nuevoEstado);
                try (ResultSet rsEstado = pstm.executeQuery()) {
                    idEstadoHabitacion = rsEstado.next() ? rsEstado.getInt("idEstadoHabitacion") : 1;
                }
            }

            // Ajusta fechaInicio y fechaFin según el tipo de operación
            LocalDateTime fechaInicioConHora;
            LocalDateTime fechaFinConHora;

            switch (tipoOperacion) {
                case "crearReserva", "modificarReserva" -> {
                    // Establece las horas por defecto (15:00:00 para inicio, 10:00:00 para fin)
                    fechaInicioConHora = fechaInicio.atTime(15, 0, 0);
                    fechaFinConHora = fechaFin.atTime(10, 0, 0);
                }
                case "checkIn" -> {
                    // La fecha y hora actuales como fecha de inicio
                    fechaInicioConHora = LocalDateTime.now();
                    fechaFinConHora = fechaFin.atTime(10, 0, 0);
                }
                /*case "checkOut" -> {
                    // Ajustar la fecha de fin al momento de hacer check-out
                    fechaInicioConHora = fechaInicio.atTime(15, 0, 0);
                    fechaFinConHora = LocalDateTime.now(); // Hora actual como fecha de fin
                }

                 */
                default -> throw new IllegalArgumentException("Tipo de operación no válido: " + tipoOperacion);
            }

            // Insertar un nuevo registro en estadoHabitacionHistorial
            String insertarHistorial = "INSERT INTO estadoHabitacionHistorial (idHabitacion, idEstadoHabitacion, fechaInicio, fechaFin, idReserva) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmInsertar = con.prepareStatement(insertarHistorial)) {
                pstmInsertar.setInt(1, idHabitacion);
                pstmInsertar.setInt(2, idEstadoHabitacion);
                pstmInsertar.setTimestamp(3, Timestamp.valueOf(fechaInicioConHora));
                pstmInsertar.setTimestamp(4, Timestamp.valueOf(fechaFinConHora));
                pstmInsertar.setInt(5, idReserva);
                pstmInsertar.executeUpdate();
                System.out.println("Registro insertado en estadoHabitacionHistorial para habitación con ID " + idHabitacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la habitación con ID " + idHabitacion + ": " + e.getMessage());
        }
    }

    public void actualizarFechaFinEstadoHabitacionHistorial(int idHabitacion, int idReserva, LocalDateTime fechaFin) {
        try (Connection con = Conexion.leerConexion()) {


            String actualizarFechaFin = "UPDATE estadoHabitacionHistorial SET fechaFin = ? " +
                    "WHERE idHabitacion = ? AND idReserva = ? AND idEstadoHabitacion = 3";

            try (PreparedStatement pstmActualizar = con.prepareStatement(actualizarFechaFin)) {
                pstmActualizar.setTimestamp(1, Timestamp.valueOf(fechaFin));
                pstmActualizar.setInt(2, idHabitacion);
                pstmActualizar.setInt(3, idReserva);
                int filasActualizadas = pstmActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Registro actualizado en estadoHabitacionHistorial para habitación con ID " + idHabitacion);
                } else {
                    System.err.println("No se encontró un registro para actualizar en estadoHabitacionHistorial para habitación con ID " + idHabitacion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la fecha de fin en estadoHabitacionHistorial para la habitación con ID " + idHabitacion + ": " + e.getMessage());
        }
    }



    /*

    private void eliminarRegistroEstadoHabitacionHistorial(int idReserva, int idHabitacion) {
        String eliminarRegistro = "DELETE FROM estadoHabitacionHistorial WHERE idHabitacion = ? AND idReserva = ?";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(eliminarRegistro)) {
            pstm.setInt(1, idHabitacion);
            pstm.setInt(2, idReserva);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el registro del estado de la habitación: " + e.getMessage());
        }
    }

     */

    private void eliminarRegistroEstadoHabitacionHistorial(int idReserva, int idHabitacion) {

        String eliminarRegistro = "DELETE FROM estadoHabitacionHistorial WHERE idHabitacion = ? AND idReserva = ?";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(eliminarRegistro)) {
            pstm.setInt(1, idHabitacion);
            pstm.setInt(2, idReserva);
            int filasEliminadas = pstm.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Registro eliminado en estadoHabitacionHistorial para habitación con ID " + idHabitacion + " y reserva con ID " + idReserva);
            } else {
                System.err.println("No se encontró ningún registro para eliminar en estadoHabitacionHistorial con habitación ID " + idHabitacion + " y reserva ID " + idReserva);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el registro del estado de la habitación: " + e.getMessage());
        }
    }
    /*
    // Método estático en la clase Reserva para verificar si existe un check-in previo
    public static boolean verificarCheckIn(int idReserva) {
        // Lógica para consultar la base de datos y verificar si existe un registro de check-in
        // Retornar true si existe, false de lo contrario
        String consulta = "SELECT COUNT(*) FROM estadoHabitacionHistorial WHERE idReserva = ? AND estado = 3";
        try (Connection con = Conexion.leerConexion();
             PreparedStatement ps = con.prepareStatement(consulta)) {
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     */


    // Método para buscar reservas basado en el filtro seleccionado
    public List<Reserva> buscarReservas(String filtro, String valor) {
        List<Reserva> reservasEncontradas = new ArrayList<>();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {

            String consulta = "SELECT r.idReserva, r.cantidadHabitaciones, r.cantidadHuespedes, " +
                    "r.fechaLlegada, r.fechaSalida, r.costoNoche, r.costoTotal, r.montoReserva, r.montoAbonado, " +
                    "ar.descripcion AS abonoReserva, er.descripcion AS estado, " +
                    "c.idCliente, c.nombre, c.apellido, c.documento, " +
                    "GROUP_CONCAT(h.numero SEPARATOR ', ') AS habitaciones " +
                    "FROM reservas r " +
                    "INNER JOIN clientes c ON r.clienteReserva = c.idCliente " +
                    "INNER JOIN abonoReserva ar ON r.abonoReserva = ar.idAbonoReserva " +
                    "INNER JOIN estadoReserva er ON r.estado = er.idEstadoReserva " +
                    "LEFT JOIN reservaHabitacion rh ON r.idReserva = rh.idReserva " +
                    "LEFT JOIN habitaciones h ON rh.idHabitacion = h.idHabitacion " +
                    "WHERE ";

            // Añadir la condición segun el filtro seleccionado
            switch (filtro) {
                case "Documento":
                    consulta += "c.documento LIKE ?";
                    break;
                case "Nombre":
                    consulta += "c.nombre LIKE ?";
                    break;
                case "Apellido":
                    consulta += "c.apellido LIKE ?";
                    break;
                default:
                    throw new IllegalArgumentException("Filtro desconocido: " + filtro);
            }


            consulta += " GROUP BY r.idReserva";

            pstm = con.prepareStatement(consulta);
            pstm.setString(1, "%" + valor + "%");
            rs = pstm.executeQuery();


            while (rs.next()) {

                Cliente cliente = new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("documento"));


                String habitaciones = rs.getString("habitaciones");


                Reserva reserva = new Reserva(
                        rs.getInt("idReserva"), cliente, rs.getInt("cantidadHabitaciones"), rs.getInt("cantidadHuespedes"),
                        rs.getObject("fechaLlegada", LocalDate.class), rs.getObject("fechaSalida", LocalDate.class),
                        rs.getDouble("costoNoche"), rs.getDouble("costoTotal"), rs.getDouble("montoReserva"),
                        rs.getDouble("montoAbonado"), rs.getString("abonoReserva"), rs.getString("estado"), habitaciones
                );
                reservasEncontradas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar reservas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reservasEncontradas;
    }


    public static boolean habitacionOcupada(int idHabitacion, int idReserva) {
        boolean ocupada = false;

        String consulta = "SELECT 1 FROM estadoHabitacionHistorial ehh " +
                "JOIN estadoHabitacion eh ON ehh.idEstadoHabitacion = eh.idEstadoHabitacion " +
                "WHERE ehh.idHabitacion = ? AND ehh.idReserva = ? AND eh.descripcion = 'Ocupada' " +
                "AND ehh.fechaCreacion = (" +
                "    SELECT MAX(fechaCreacion) " +
                "    FROM estadoHabitacionHistorial " +
                "    WHERE idHabitacion = ehh.idHabitacion AND idReserva = ehh.idReserva" +
                ")";

        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consulta)) {

            pstm.setInt(1, idHabitacion);
            pstm.setInt(2, idReserva);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    ocupada = true;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar si la habitación está ocupada: " + e.getMessage());
        }

        return ocupada;
    }





    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", cantidadHabitaciones=" + cantidadHabitaciones +
                ", cantidadHuespedes=" + cantidadHuespedes +
                ", fechaLlegada=" + fechaLlegada +
                ", fechaSalida=" + fechaSalida +
                ", costoNoche=" + costoNoche +
                ", costoTotal=" + costoTotal +
                ", montoReserva=" + montoReserva +
                ", montoAbonado=" + montoAbonado +
                ", abonoReserva='" + abonoReserva + '\'' +
                ", estado='" + estado + '\'' +
                ", cliente=" + cliente +
                ", habitaciones='" + habitaciones + '\'' +
                '}';
    }



}
