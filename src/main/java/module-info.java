module com.example.hotelsierra {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires layout;
    requires kernel;
    requires java.desktop;

    opens com.example.hotelsierra to javafx.fxml;
    exports com.example.hotelsierra;

    opens com.example.hotelsierra.controlador to javafx.fxml;
    exports com.example.hotelsierra.controlador;

    exports com.example.hotelsierra.modelo;
    opens com.example.hotelsierra.modelo to javafx.fxml;


}