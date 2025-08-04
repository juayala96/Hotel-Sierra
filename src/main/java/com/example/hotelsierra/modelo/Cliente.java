package com.example.hotelsierra.modelo;

import com.example.hotelsierra.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String documento;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String direccion;
    private String localidad;
    private String provincia;
    private String pais;
    private String codigoPostal;
    private String email;
    private String telefono;
    private String tipoCliente;

    public Cliente(int idCliente, String nombre, String apellido, String tipoDocumento, String documento, LocalDate fechaNacimiento, String nacionalidad, String direccion, String localidad, String provincia, String codigoPostal, String email, String telefono, String tipoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.email = email;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
    }

    public Cliente(int idCliente, String nombre, String apellido, String tipoDocumento, String documento, LocalDate fechaNacimiento, String nacionalidad, String direccion, String localidad, String provincia, String pais, String codigoPostal, String email, String telefono, String tipoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.email = email;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
    }

    public Cliente(String nombre, String apellido, String tipoDocumento, String documento, LocalDate fechaNacimiento, String nacionalidad, String direccion, String localidad, String provincia, String pais, String codigoPostal, String email, String telefono, String tipoCliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.email = email;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
    }

    public Cliente(String nombre, String apellido, String tipoDocumento, String documento, LocalDate fechaNacimiento, String nacionalidad, String direccion, String localidad, String provincia, String codigoPostal, String email, String telefono, String tipoCliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.email = email;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
    }

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String apellido, String documento) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    // Método para obtener la lista de clientes
    public static ObservableList<Cliente> obtenerListaClientes() {
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Consulta para unir las tablas clientes, tipo de Documento y tipo de Cliente
            String consulta = "SELECT c.idCliente, c.nombre, c.apellido, td.descripcion AS tipoDocumento, c.documento, c.fechaNacimiento, " +
                    "c.nacionalidad, c.direccion, c.localidad, c.provincia, c.pais, c.codigoPostal, c.email, c.telefono, " +
                    "tc.descripcion AS tipoCliente " +
                    "FROM clientes c " +
                    "INNER JOIN tipoDocumento td ON c.tipoDocumento = td.idTipoDocumento " +
                    "INNER JOIN tipoCliente tc ON c.tipoCliente = tc.idTipoCliente";

            pstm = con.prepareStatement(consulta);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String tipoDocumento = rs.getString("tipoDocumento"); // Alias usado para td.descripcion
                String documento = rs.getString("documento");
                LocalDate fechaNacimiento = rs.getObject("fechaNacimiento", LocalDate.class); // Usar LocalDate
                String nacionalidad = rs.getString("nacionalidad");
                String direccion = rs.getString("direccion");
                String localidad = rs.getString("localidad");
                String provincia = rs.getString("provincia");
                String pais = rs.getString("pais");
                String codigoPostal = rs.getString("codigoPostal");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String tipoCliente = rs.getString("tipoCliente"); // Alias usado para tc.descripcion



                Cliente cliente = new Cliente(idCliente, nombre, apellido, tipoDocumento, documento, fechaNacimiento, nacionalidad, direccion,
                        localidad, provincia, pais, codigoPostal, email, telefono, tipoCliente);
                listaClientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return listaClientes;
    }



    //----Crear usuario---//

    public boolean crearCliente(String nombre, String apellido, String tipoDocumento, String documento, LocalDate fechaNacimiento, String nacionalidad, String direccion, String localidad, String provincia, String codigoPostal, String email, String telefono, String tipoCliente, String pais) throws SQLException {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {
            // Obtiene el id del tipo de doc que seleccionó
            String consultaTipoDocumento = "SELECT idTipoDocumento FROM tipoDocumento WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaTipoDocumento);
            pstm.setString(1, tipoDocumento);
            ResultSet rsTipoDocumento = pstm.executeQuery();

            int idTipoDocumento = 1; // Valor por defecto
            if (rsTipoDocumento.next()) {
                idTipoDocumento = rsTipoDocumento.getInt("idTipoDocumento");
            }

            // Obtiene el id del tipo de cliente que seleccionó
            String consultaTipoCliente = "SELECT idTipoCliente FROM tipoCliente WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaTipoCliente);
            pstm.setString(1, tipoCliente);
            ResultSet rsTipoCliente = pstm.executeQuery();

            int idTipoCliente = 1; // Valor por defecto
            if (rsTipoCliente.next()) {
                idTipoCliente = rsTipoCliente.getInt("idTipoCliente");
            }

            // Inserta el nuevo cliente
            String consulta = "INSERT INTO clientes (nombre, apellido, tipoDocumento, documento, fechaNacimiento, nacionalidad, direccion, localidad, provincia, codigoPostal, email, telefono, tipoCliente, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstm = con.prepareStatement(consulta);
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);
            pstm.setInt(3, idTipoDocumento);
            pstm.setString(4, documento);
            pstm.setDate(5, java.sql.Date.valueOf(fechaNacimiento)); // Convierte LocalDate a Date
            pstm.setString(6, nacionalidad);
            pstm.setString(7, direccion);
            pstm.setString(8, localidad);
            pstm.setString(9, provincia);
            pstm.setString(10, codigoPostal);
            pstm.setString(11, email);
            pstm.setString(12, telefono);
            pstm.setInt(13, idTipoCliente);
            pstm.setString(14, pais);

            int filasInsertadas = pstm.executeUpdate();
            return filasInsertadas > 0; // Retorna true si la inserción fue exitosa


        } finally {
            try {
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean modificarCliente(int idCliente, String nombre, String apellido, String tipoDocumento, String documento, LocalDate fechaNacimiento, String nacionalidad, String direccion, String localidad, String provincia, String codigoPostal, String email, String telefono, String tipoCliente, String pais) throws SQLException  {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {

            String consultaTipoDocumento = "SELECT idTipoDocumento FROM tipoDocumento WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaTipoDocumento);
            pstm.setString(1, tipoDocumento);
            ResultSet rsTipoDocumento = pstm.executeQuery();

            int idTipoDocumento = 1; // Valor por defecto
            if (rsTipoDocumento.next()) {
                idTipoDocumento = rsTipoDocumento.getInt("idTipoDocumento");
            }


            String consultaTipoCliente = "SELECT idTipoCliente FROM tipoCliente WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaTipoCliente);
            pstm.setString(1, tipoCliente);
            ResultSet rsTipoCliente = pstm.executeQuery();

            int idTipoCliente = 1;
            if (rsTipoCliente.next()) {
                idTipoCliente = rsTipoCliente.getInt("idTipoCliente");
            }


            String consulta = "UPDATE clientes SET nombre = ?, apellido = ?, tipoDocumento = ?, documento = ?, fechaNacimiento = ?, nacionalidad = ?, direccion = ?, localidad = ?, provincia = ?, codigoPostal = ?, email = ?, telefono = ?, tipoCliente = ?, pais = ?WHERE idCliente = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);
            pstm.setInt(3, idTipoDocumento);
            pstm.setString(4, documento);
            pstm.setDate(5, java.sql.Date.valueOf(fechaNacimiento));
            pstm.setString(6, nacionalidad);
            pstm.setString(7, direccion);
            pstm.setString(8, localidad);
            pstm.setString(9, provincia);
            pstm.setString(10, codigoPostal);
            pstm.setString(11, email);
            pstm.setString(12, telefono);
            pstm.setInt(13, idTipoCliente);
            pstm.setString(14, pais);
            pstm.setInt(15, idCliente); // Id cliente que se va a modificar

            int filasActualizadas = pstm.executeUpdate(); //
            return filasActualizadas > 0;


        } finally {
            try {
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public boolean eliminarCliente(int idCliente) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {

            String consulta = "DELETE FROM clientes WHERE idCliente = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idCliente);

            int filasEliminadas = pstm.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el cliente: " + e.getMessage());
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

    public static Cliente obtenerClientePorDocumento(String documento) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Consulta para buscar al cliente por documento
            String consulta = "SELECT idCliente, nombre, apellido, documento FROM clientes WHERE documento = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setString(1, documento);

            rs = pstm.executeQuery();

            if (rs.next()) {
                // Obtiene los datos del cliente y crea el objeto Cliente
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                return new Cliente(idCliente, nombre, apellido, documento);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        // Devuelve null si no encuentra el cliente
        return null;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
