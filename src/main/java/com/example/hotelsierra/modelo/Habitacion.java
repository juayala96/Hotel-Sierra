package com.example.hotelsierra.modelo;

import com.example.hotelsierra.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class Habitacion {

    private int idHabitacion;
    private Integer numero;
    private Integer capacidad;
    private Integer cantidadCamas;
    private Integer camasSingle;
    private Integer camasDoble;
    private String categoria;
    private Double precio;
    private String estado;

    public Habitacion(int idHabitacion, Integer numero, Integer capacidad, Integer cantidadCamas, Integer camasSingle, Integer camasDoble, String categoria, Double precio, String estado) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.capacidad = capacidad;
        this.cantidadCamas = cantidadCamas;
        this.camasSingle = camasSingle;
        this.camasDoble = camasDoble;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
    }

    public Habitacion(Integer numero, Integer capacidad, Integer cantidadCamas, Integer camasSingle, Integer camasDoble, String categoria, Double precio, String estado) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.cantidadCamas = cantidadCamas;
        this.camasSingle = camasSingle;
        this.camasDoble = camasDoble;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
    }

    public Habitacion(int idHabitacion, Integer numero, Integer capacidad, Integer cantidadCamas, Integer camasSingle, Integer camasDoble, String categoria, Double precio) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.capacidad = capacidad;
        this.cantidadCamas = cantidadCamas;
        this.camasSingle = camasSingle;
        this.camasDoble = camasDoble;
        this.categoria = categoria;
        this.precio = precio;
    }

    public Habitacion() {
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getCantidadCamas() {
        return cantidadCamas;
    }

    public void setCantidadCamas(Integer cantidadCamas) {
        this.cantidadCamas = cantidadCamas;
    }

    public Integer getCamasSingle() {
        return camasSingle;
    }

    public void setCamasSingle(Integer camasSingle) {
        this.camasSingle = camasSingle;
    }

    public Integer getCamasDoble() {
        return camasDoble;
    }

    public void setCamasDoble(Integer camasDoble) {
        this.camasDoble = camasDoble;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    // Método para obtener la lista de usuarios
    public static ObservableList<Habitacion> obtenerListaHabitaciones() {
        ObservableList<Habitacion> listaHabitaciones = FXCollections.observableArrayList();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Consulta para obtener las habitaciones y su estado actual
            String consulta = "SELECT h.idHabitacion, h.numero, h.capacidad, h.cantidadCamas, h.camasSingle, h.camasDoble, " +
                    "ch.descripcion AS categoria, h.precio, " +
                    "CASE " +
                    "    WHEN EXISTS ( " +
                    "        SELECT 1 " +
                    "        FROM estadoHabitacionHistorial ehh " +
                    "        WHERE ehh.idHabitacion = h.idHabitacion " +
                    "        AND NOW() BETWEEN ehh.fechaInicio AND ehh.fechaFin " +
                    "        AND ehh.idEstadoHabitacion = (SELECT idEstadoHabitacion FROM estadoHabitacion WHERE descripcion = 'Ocupada') " +
                    "        AND fechaCreacion = ( " +
                    "            SELECT MAX(fechaCreacion) " +
                    "            FROM estadoHabitacionHistorial " +
                    "            WHERE idHabitacion = h.idHabitacion AND idReserva = ehh.idReserva" +
                    "        )" +
                    "    ) THEN 'Ocupada' " +
                    "    WHEN EXISTS ( " +
                    "        SELECT 1 " +
                    "        FROM estadoHabitacionHistorial ehh " +
                    "        WHERE ehh.idHabitacion = h.idHabitacion " +
                    "        AND NOW() BETWEEN ehh.fechaInicio AND ehh.fechaFin " +
                    "        AND ehh.idEstadoHabitacion = (SELECT idEstadoHabitacion FROM estadoHabitacion WHERE descripcion = 'Reservada') " +
                    "        AND fechaCreacion = ( " +
                    "            SELECT MAX(fechaCreacion) " +
                    "            FROM estadoHabitacionHistorial " +
                    "            WHERE idHabitacion = h.idHabitacion AND idReserva = ehh.idReserva" +
                    "        )" +
                    "    ) THEN 'Reservada' " +
                    "    ELSE 'Libre' " +
                    "END AS estado " +
                    "FROM habitaciones h " +
                    "INNER JOIN categoriaHabitacion ch ON h.categoria = ch.idCategoriaHabitacion";


            pstm = con.prepareStatement(consulta);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int idHabitacion = rs.getInt("idHabitacion");
                int numero = rs.getInt("numero");
                int capacidad = rs.getInt("capacidad");
                int cantidadCamas = rs.getInt("cantidadCamas");
                int camasSingle = rs.getInt("camasSingle");
                int camasDoble = rs.getInt("camasDoble");
                String categoria = rs.getString("categoria");
                double precio = rs.getDouble("precio");
                String estado = rs.getString("estado");

                // Crea el objeto Habitación con los valores obtenidos de la consulta
                Habitacion habitacion = new Habitacion(idHabitacion, numero, capacidad, cantidadCamas, camasSingle, camasDoble, categoria, precio, estado);
                listaHabitaciones.add(habitacion);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return listaHabitaciones;
    }




    //----Crear habitacion---//

    public boolean crearHabitacion(int numero, int capacidad, int cantidadCamas, int camasSingle, int camasDoble, String categoria, double precio) throws SQLException {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {
            // Obtene el ID de la categoría
            String consultaCategoria = "SELECT idCategoriaHabitacion FROM categoriaHabitacion WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaCategoria);
            pstm.setString(1, categoria);
            ResultSet rsCategoria = pstm.executeQuery();
            int idCategoria = rsCategoria.next() ? rsCategoria.getInt("idCategoriaHabitacion") : 1;
            rsCategoria.close();

            // Inserta la habitación en la base de datos
            String consulta = "INSERT INTO habitaciones (numero, capacidad, cantidadCamas, camasSingle, camasDoble, categoria, precio) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstm = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, numero);
            pstm.setInt(2, capacidad);
            pstm.setInt(3, cantidadCamas);
            pstm.setInt(4, camasSingle);
            pstm.setInt(5, camasDoble);
            pstm.setInt(6, idCategoria);
            pstm.setDouble(7, precio);

            int filasInsertadas = pstm.executeUpdate();
            if (filasInsertadas > 0) {
                // Obtener el ID generado para la habitación
                ResultSet rsGenerado = pstm.getGeneratedKeys();
                if (rsGenerado.next()) {
                    int idHabitacion = rsGenerado.getInt(1);
                    return true; // Retorna true si la habitación se ha creado correctamente
                }
            }

        } finally {
            try {
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    public boolean modificarHabitacion(int idHabitacion, int numero, int capacidad, int cantidadCamas, int camasSingle, int camasDoble, String categoria, double precio) throws SQLException {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {
            // Obtiene el ID de la categoría
            String consultaCategoria = "SELECT idCategoriaHabitacion FROM categoriaHabitacion WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaCategoria);
            pstm.setString(1, categoria);
            ResultSet rsCategoria = pstm.executeQuery();
            int idCategoria = rsCategoria.next() ? rsCategoria.getInt("idCategoriaHabitacion") : 1;
            rsCategoria.close();

            // Actualiza la habitación en la base de datos (sin la columna 'estado')
            String consulta = "UPDATE habitaciones SET numero = ?, capacidad = ?, cantidadCamas = ?, camasSingle = ?, camasDoble = ?, categoria = ?, precio = ? WHERE idHabitacion = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, numero);
            pstm.setInt(2, capacidad);
            pstm.setInt(3, cantidadCamas);
            pstm.setInt(4, camasSingle);
            pstm.setInt(5, camasDoble);
            pstm.setInt(6, idCategoria);
            pstm.setDouble(7, precio);
            pstm.setInt(8, idHabitacion); //id de la habitacion a modificar

            int filasActualizadas = pstm.executeUpdate();
            return filasActualizadas > 0; // Retorna true si la actualización fue exitosa


        } finally {
            try {
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public boolean eliminarHabitacion(int idHabitacion) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {

            String consulta = "DELETE FROM habitaciones WHERE idHabitacion = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idHabitacion);

            int filasEliminadas = pstm.executeUpdate();
            return filasEliminadas > 0; //

        } catch (SQLException e) {
            System.err.println("Error al eliminar la habitación: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Habitacion obtenerHabitacionPorId(int idHabitacion) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Habitacion habitacion = null;

        try {
            String consulta = "SELECT * FROM habitaciones WHERE idHabitacion = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idHabitacion);
            rs = pstm.executeQuery();

            if (rs.next()) {
                habitacion = new Habitacion();
                habitacion.setIdHabitacion(rs.getInt("idHabitacion"));
                habitacion.setNumero(rs.getInt("numero"));
                habitacion.setCapacidad(rs.getInt("capacidad"));
                habitacion.setCamasSingle(rs.getInt("camasSingle"));
                habitacion.setCamasDoble(rs.getInt("camasDoble"));
                habitacion.setCategoria(rs.getString("categoria"));
                habitacion.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la habitación por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return habitacion;
    }


    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", numero=" + numero +
                ", capacidad=" + capacidad +
                ", cantidadCamas=" + cantidadCamas +
                ", camasSingle=" + camasSingle +
                ", camasDoble=" + camasDoble +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                '}';
    }
}
