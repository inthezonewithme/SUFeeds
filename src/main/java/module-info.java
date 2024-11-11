module com.example.kamwaro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kamwaro to javafx.fxml;
    exports com.example.kamwaro;


}