module com.example.casipongdatabase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.casipongdatabase to javafx.fxml;
    exports com.example.casipongdatabase;
}