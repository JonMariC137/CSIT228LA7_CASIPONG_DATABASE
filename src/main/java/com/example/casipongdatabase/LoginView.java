package com.example.casipongdatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class LoginView {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public TextField tfName;

    @FXML
    private Pane pHome;

    @FXML
    private void goToHR(ActionEvent actionEvent) throws IOException {
        switchScene("hello-view.fxml", "HR");
    }

    @FXML
    private void goToEmployee(ActionEvent actionEvent) throws IOException {
        boolean exists = false;
        String name = tfName.getText();
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("Select name FROM company1 WHERE name = ?")) {

            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                System.out.println("Successful!");
            }else{
                System.out.println("Doesn't exist!");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        switchScene("employee-view.fxml", "Employee");
    }

    private void switchScene(String fxmlFile, String title) throws IOException {
        Stage stage = (Stage) pHome.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 320, 740);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
