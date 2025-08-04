package com.example.hotelsierra.modelo;

import com.example.hotelsierra.Conexion;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUsuario {
    public LoginUsuario() {}

    // ------------------------------------------- Login Usuario -------------------------------------------------
    public Integer loginUsuario(int dni, String contrasenia) {
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Integer userId = null;

        try {
            String consulta = "SELECT idUsuario FROM usuarios WHERE dni = ? AND contrasenia = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, dni); // Cambiado a setInt para DNI
            pstm.setString(2, contrasenia);
            rs = pstm.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("idUsuario");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error - Iniciar sesión");
                alerta.setContentText("Error en la base de datos.");
                alerta.showAndWait();
            }
        }
        return userId;
    }
    public String obtenerNombreUsuario(int idUsuario) {
        String nombre = null;
        String apellido = null;
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            String consulta = "SELECT nombre, apellido FROM usuarios WHERE idUsuario = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idUsuario);
            rs = pstm.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre del usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (nombre + " " + apellido);

    }


    public String obtenerRolDelUsuario(int idUsuario) {
        String rol = null;
        Connection con = Conexion.leerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            String consulta = "SELECT r.descripcion FROM roles r " +
                    "INNER JOIN usuarios u ON r.idRol = u.rol " +
                    "WHERE u.idUsuario = ?";
            pstm = con.prepareStatement(consulta);
            pstm.setInt(1, idUsuario);
            rs = pstm.executeQuery();

            if (rs.next()) {
                rol = rs.getString("descripcion");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el rol del usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rol;
    }


}
