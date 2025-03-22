package com.example.casipongdatabase;

import com.mysql.cj.protocol.Resultset;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class EmployeeView {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    @FXML
    private ResultSet RS; // Corrected to java.sql.ResultSet
    @FXML
    private Label lblName;
    @FXML
    private TextField hours; // Ensure this is linked in FXML

    @FXML
    private Pane pHome;

    private int ID;
    private int Wage;
    private int hoursVal;

    public void onSaveClicked(ActionEvent actionEvent) {
        // Implement save functionality
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE company1 SET hours=?, salary=? WHERE id=?")) {


            statement.setString(1,hours.getText());
            hoursVal = Integer.parseInt(hours.getText());
            statement.setString(2, String.valueOf(hoursVal * Wage));
            statement.setString(3, String.valueOf(ID));
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Updated successfully!");
    }

    public void setData(int id, String name, String hoursValue, int wage) {
        hoursVal = Integer.parseInt(hoursValue);
        hours.setText(String.valueOf(hoursVal));
        lblName.setText(name);
        ID = id;
        Wage = wage;
    }

    public void onBackButttonClicked(ActionEvent actionEvent) throws IOException {
        switchScene("login-view.fxml","Login");
    }

    private void switchScene(String fxmlFile, String title) throws IOException {
        Stage stage = (Stage) pHome.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 320, 350);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show(); // Ensure scene is shown properly
    }
}
