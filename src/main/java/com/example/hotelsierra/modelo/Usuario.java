package com.example.hotelsierra.modelo;

import com.example.hotelsierra.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private Integer dni;
    private String email;
    private String contrasenia;
    private String rol;

    // Constructor
    public Usuario(int idUsuario, String nombre, String apellido, Integer dni, String email, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario(String nombre, String apellido, Integer dni, String email, String contrasenia, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario() {

    }


    //Getters y Setters


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Método para obtener la lista de usuarios
    public static ObservableList<Usuario> obtenerListaUsuarios() {
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Consulta SQL para unir las tablas usuarios y rol
            String consulta = "SELECT u.idUsuario, u.nombre, u.apellido, u.dni, u.email, u.contrasenia, r.descripcion " +
                    "FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol = r.idRol";
            pstm = con.prepareStatement(consulta);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Integer dni = Integer.valueOf(rs.getString("dni"));
                String email = rs.getString("email");
                String contrasenia = rs.getString("contrasenia");
                String rol = rs.getString("descripcion");

                // Crear un nuevo objeto Usuario con el nombre del rol
                /* listaUsuarios.add(new Usuario(id, nombre, apellido, dni, email, contrasenia, rol)); */

                Usuario usuario = new Usuario(idUsuario, nombre, apellido, dni, email, contrasenia, rol);
                listaUsuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return listaUsuarios;
    }



    //----Crear usuario---//

    public boolean crearUsuario(String nombre, String apellido, int dni, String email, String contrasenia, String rol) throws SQLException {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {
            // Obtiene el id del rol seleccionado en el cbx
            String consultaRol = "SELECT idRol FROM roles WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaRol);
            pstm.setString(1, rol);
            ResultSet rs = pstm.executeQuery();

            int idRol = 1; // Valor por defecto si no se encuentra el rol
            if (rs.next()) {
                idRol = rs.getInt("idRol");
            }

            // Inserta el nuevo usuario en la base de datos
            String consulta = "INSERT INTO usuarios (nombre, apellido, dni, email, contrasenia, rol) VALUES (?, ?, ?, ?, ?, ?)";
            pstm = con.prepareStatement(consulta);
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);
            pstm.setInt(3, dni);
            pstm.setString(4, email);
            pstm.setString(5, contrasenia);
            pstm.setInt(6, idRol);

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

    public boolean modificarUsuario(int idUsuario, String nombre, String apellido, int dni, String email, String contrasenia, String rol) throws SQLException {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {

            String consultaRol = "SELECT idRol FROM roles WHERE descripcion = ?";
            pstm = con.prepareStatement(consultaRol);
            pstm.setString(1, rol);
            ResultSet rs = pstm.executeQuery();

            int idRol = 1;
            if (rs.next()) {
                idRol = rs.getInt("idRol");
            }

            // Actualiza el usuario
            String consulta = "UPDATE usuarios SET nombre = ?, apellido = ?, dni = ?, email = ?, contrasenia = ?, rol = ? WHERE idUsuario = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);
            pstm.setInt(3, dni);
            pstm.setString(4, email);
            pstm.setString(5, contrasenia);
            pstm.setInt(6, idRol);
            pstm.setInt(7, idUsuario); // Idusuario que se va a modificar

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



    public boolean eliminarUsuario(int idUsuario) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;

        try {

            String consulta = "DELETE FROM usuarios WHERE idUsuario = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idUsuario);

            int filasEliminadas = pstm.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
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



}

