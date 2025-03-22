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
    public static ResultSet rs;

    @FXML
    private Pane pHome;

    @FXML
    private void goToHR(ActionEvent actionEvent) throws IOException {
        switchScene("hello-view.fxml", "HR");
    }

    @FXML
    private void goToEmployee(ActionEvent actionEvent) throws IOException {
        String name = tfName.getText();
        String hours = null;
        int id, wage;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM company1 WHERE name = ?")) {

            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println("User Found!");
                hours = rs.getString("hours");
                id = Integer.parseInt(rs.getString("id"));
                wage = Integer.parseInt(rs.getString("wage"));
            } else {
                System.out.println("User Doesn't Exist!");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employee-view.fxml"));
        Stage stage = (Stage) pHome.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 320, 350);
        stage.setScene(scene);
        stage.setTitle("Employee");
        stage.show();

        EmployeeView employeeView = fxmlLoader.getController();
        employeeView.setData(id,name,hours,wage);
    }

    private void switchScene(String fxmlFile, String title) throws IOException {
        Stage stage = (Stage) pHome.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = null;
        if(fxmlFile.equals("hello-view.fxml")) {
            scene = new Scene(fxmlLoader.load(), 320, 740);
        }else{
            scene = new Scene(fxmlLoader.load(), 320, 350);
        }
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
