package com.example.hotelsierra;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Hotel extends Application {
    private AnulacionReservaAutomatica reservaAutomatica;

    @Override
    public void start(Stage stage) throws IOException {

        // Inicializar la anulación automática
        reservaAutomatica = new AnulacionReservaAutomatica();
        reservaAutomatica.iniciarAnulacionAutomatica();

        FXMLLoader fxmlLoader = new FXMLLoader(Hotel.class.getResource("/vistas/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hotel Sierra");
        stage.setScene(scene);
        stage.show();



    }

    @Override
    public void stop() throws Exception {
        // Detener la anulación automática al cerrar la aplicación
        if (reservaAutomatica != null) {
            reservaAutomatica.detenerAnulacionAutomatica();
        }
        super.stop();
    }

    public static void main(String[] args) {

        /*
        // Iniciar la anulación automática de reservas pendientes
        AnulacionReservaAutomatica reservaAutomatica = new AnulacionReservaAutomatica();
        reservaAutomatica.iniciarAnulacionAutomatica();
        */
        launch();
    }
}


