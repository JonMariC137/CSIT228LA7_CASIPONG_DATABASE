package com.example.casipongdatabase;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.Objects;

public class HelloController {
    public TextField tfWage;
    public TextField tfWorked;
    public TextField tfName;
    public ListView<Employee> lvList;
    public Label lblSalary;
    private Employee curr;
    public double salary;
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public boolean loaded = false;
    public int currId = 0;
    @FXML
    public void initialize(){
        lvList.getFocusModel().focusedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee employee, Employee t1) {
                tfName.setText(t1.name);
                tfWorked.setText(String.valueOf(t1.hours));
                tfWage.setText(String.valueOf(t1.wage));
                lblSalary.setText("Salary: $" + String.format("%.2f",t1.Salary()));
                curr = t1;
                try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                    Statement statement = connection.createStatement()){
                    String query = "Select * FROM company1";
                    ResultSet resultSet = statement.executeQuery(query);
                    int i = 1;
                    while(resultSet.next()){
                        String name = resultSet.getString("name");
                        int hoursworked  = Integer.parseInt(resultSet.getString("hours"));
                        if(Objects.equals(curr.name, name) && curr.hours == hoursworked){
                            break;
                        }
                        i++;
                    }
                    int x = 0;
                    do{
                        x++;
                        if(x==i){
                            currId = resultSet.getInt("id");
                            break;
                        }
                    }while(true);
                    System.out.println(currId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void onSaveClicked(ActionEvent actionEvent) {
        double wage = Double.parseDouble(tfWage.getText());
        double hours = Double.parseDouble(tfWorked.getText());
        String name = tfName.getText();
        if(curr == null){
            Employee e = new Employee(name,hours,wage);
            lvList.getItems().add(e);
            try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO company1 (name,hours,wage,salary) VALUES (?,?,?,?)")) {

                statement.setString(1,name);
                statement.setString(2,String.valueOf(hours));
                statement.setString(3,String.valueOf(wage));
                statement.setString(4,String.valueOf(salary));

                int rowsAffected = statement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("Inserted successfully");
                }
            } catch (SQLException E) {
                throw new RuntimeException(E);
            }
        }else{
            curr.name = name;
            curr.wage = wage;
            curr.hours = hours;
            try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                PreparedStatement statement = connection.prepareStatement("UPDATE company1 SET name=?, hours=?, wage=?, salary=? WHERE id=?")) {

                statement.setString(1,curr.name);
                statement.setString(2,String.valueOf(curr.hours));
                statement.setString(3,String.valueOf(curr.wage));
                statement.setString(4,String.valueOf(curr.wage*curr.hours));
                statement.setInt(5,currId);
                statement.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Updated successfully!");
        }
    }

    @FXML
    public void onClearClicked(ActionEvent actionEvent) {
        tfWorked.setText("");
        tfName.setText("");
        tfWage.setText("");
        lblSalary.setVisible(false);
        curr = null;
    }

    @FXML
    public void onCalculateButtonClicked(ActionEvent actionEvent) {
        double wage = Double.parseDouble(tfWage.getText());
        double hours = Double.parseDouble(tfWorked.getText());
        String name = tfName.getText();
        salary = wage * hours;
        lblSalary.setText("Salary: $" + String.format("%.2f",salary));
        lblSalary.setVisible(true);
    }

    public void onLoadClicked(ActionEvent actionEvent) {
        if(loaded == true){
            System.out.println("Already loaded!");
            return;
        }
        loaded = true;
        lvList.getItems().clear();
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement statement = connection.createStatement()){
            String query = "Select * FROM company1";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                int hoursworked  = Integer.parseInt(resultSet.getString("hours"));
                int wage  = Integer.parseInt(resultSet.getString("wage"));
                int salary = hoursworked * wage;
                System.out.println("[" + id + "] " + name + " | Hours worked: " + hoursworked + " | Wage: " + wage + "| Salary: " + salary);
                Employee e = new Employee(name, hoursworked, wage);
                lvList.getItems().add(e);
            }
        } catch (SQLException e) {
            System.out.println("Problem");
            throw new RuntimeException(e);
        }
    }

    public void onDeleteClicked(ActionEvent actionEvent) {
        lvList.getItems().remove(curr);
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM company1 WHERE id=?")) {
            statement.setInt(1,currId+1);
            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Deleted successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}