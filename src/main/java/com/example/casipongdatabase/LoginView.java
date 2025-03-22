package com.example.casipongdatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginView {

    @FXML
    private Pane pHome; // Ensure it's correctly linked in FXML

    @FXML
    private void goToHR(ActionEvent actionEvent) throws IOException {
        switchScene("hello-view.fxml", "HR");
    }

    @FXML
    private void goToEmployee(ActionEvent actionEvent) throws IOException {
        switchScene("employee-view.fxml", "Employee");
    }

    private void switchScene(String fxmlFile, String title) throws IOException {
        Stage stage = (Stage) pHome.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 320, 740);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show(); // Ensure scene is shown properly
    }
}
