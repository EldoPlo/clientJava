module com.example.clientjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clientjava to javafx.fxml;
    exports com.example.clientjava;
}