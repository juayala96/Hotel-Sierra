package com.example.hotelsierra.modelo;

import com.example.hotelsierra.Conexion;
//import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


import java.io.FileNotFoundException;


public class InformeHabitaciones {
    private int numeroHabitacion;
    private int totalReservas;
    private String tipoHabitacion;

    public InformeHabitaciones(int numeroHabitacion, int totalReservas, String tipoHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
        this.totalReservas = totalReservas;
        this.tipoHabitacion = tipoHabitacion;
    }

    public InformeHabitaciones() {
    }

    // Getters
    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public int getTotalReservas() {
        return totalReservas;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }


    // Método para obtener las habitaciones más solicitadas por mes, año y tipo de habitación
    public List<InformeHabitaciones> obtenerHabitacionesMasSolicitadas(String mes, int anio, String tipoHabitacion) {
        List<InformeHabitaciones> listaHabitaciones = new ArrayList<>();
        String consulta = "SELECT h.numero, ch.descripcion, COUNT(rh.idReserva) AS cantidadReservas " +
                "FROM habitaciones h " +
                "JOIN categoriaHabitacion ch ON h.categoria = ch.idCategoriaHabitacion " +
                "JOIN reservaHabitacion rh ON h.idHabitacion = rh.idHabitacion " +
                "JOIN reservas r ON rh.idReserva = r.idReserva " +
                "WHERE MONTH(r.fechaLlegada) = ? AND YEAR(r.fechaLlegada) = ? ";

        if (!tipoHabitacion.equals("Ambas")) {
            consulta += "AND ch.descripcion = ? ";
        }

        consulta += "GROUP BY h.idHabitacion " +
                "ORDER BY cantidadReservas DESC " +
                "LIMIT 5"
        ;

        try (Connection con = Conexion.leerConexion();
             PreparedStatement pstm = con.prepareStatement(consulta)) {

            // Convertir el mes en el número correspondiente
            int numeroMes = convertirMesANumero(mes);
            pstm.setInt(1, numeroMes);
            pstm.setInt(2, anio);

            if (!tipoHabitacion.equals("Ambas")) {
                pstm.setString(3, tipoHabitacion);
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int numeroHabitacion = rs.getInt("numero");
                    String tipoCategoria = rs.getString("descripcion");
                    int cantidadReservas = rs.getInt("cantidadReservas");

                    listaHabitaciones.add(new InformeHabitaciones(numeroHabitacion, cantidadReservas, tipoCategoria));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las habitaciones más solicitadas: " + e.getMessage());
        }

        return listaHabitaciones;
    }

    // Método para generar el informe en PDF
    public void generarInformeHabitacionesMasSolicitadas(List<InformeHabitaciones> habitaciones, String mes, int anio, String tipoHabitacion) {
        String nombreArchivo = "Informe_Habitaciones_" + mes + "_" + anio + "_" + tipoHabitacion + ".pdf";

        try {
            // Crea el PdfWriter y el PdfDocument
            PdfWriter writer = new PdfWriter(new FileOutputStream(nombreArchivo));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Título del documento
            document.add(new Paragraph("Informe de Habitaciones Más Solicitadas"));
            document.add(new Paragraph("Mes: " + mes + " | Año: " + anio + " | Tipo de Habitación: " + tipoHabitacion));
            document.add(new Paragraph(" "));

            // Crea la tabla con 3 columnas
            float[] columnWidths = {200f, 200f, 200f};
            Table tabla = new Table(columnWidths);

            // Agregar los encabezados de la tabla
            tabla.addHeaderCell(new Cell().add(new Paragraph("Número de Habitación")));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Tipo de Habitación")));
            tabla.addHeaderCell(new Cell().add(new Paragraph("Cantidad de Reservas")));

            // Rellenar la tabla con los datos de las habitaciones
            for (InformeHabitaciones habitacion : habitaciones) {
                tabla.addCell(new Cell().add(new Paragraph(String.valueOf(habitacion.getNumeroHabitacion()))));
                tabla.addCell(new Cell().add(new Paragraph(habitacion.getTipoHabitacion())));
                tabla.addCell(new Cell().add(new Paragraph(String.valueOf(habitacion.getTotalReservas()))));
            }

            // Agregar la tabla al documento
            document.add(tabla);

            // Cerrar el documento
            document.close();
            System.out.println("Informe generado exitosamente: " + nombreArchivo);

            // Abrir el archivo PDF automáticamente después de generarlo
            if (Desktop.isDesktopSupported()) {
                try {
                    File archivoPdf = new File(nombreArchivo);
                    Desktop.getDesktop().open(archivoPdf);
                } catch (IOException e) {
                    System.err.println("Error al intentar abrir el archivo PDF: " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error al generar el informe PDF: El archivo no fue encontrado.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error al generar el informe PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Método para convertir el nombre del mes a número
    private int convertirMesANumero(String mes) {
        return switch (mes) {
            case "Enero" -> 1;
            case "Febrero" -> 2;
            case "Marzo" -> 3;
            case "Abril" -> 4;
            case "Mayo" -> 5;
            case "Junio" -> 6;
            case "Julio" -> 7;
            case "Agosto" -> 8;
            case "Septiembre" -> 9;
            case "Octubre" -> 10;
            case "Noviembre" -> 11;
            case "Diciembre" -> 12;
            default -> 0;
        };
    }
}