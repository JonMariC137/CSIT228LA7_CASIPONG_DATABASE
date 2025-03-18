package com.example.casipongdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO company (name,hoursworked,wage,salary) VALUES (?,?,?,?)")) {

            statement.setString(1,"JM");
            statement.setString(2,String.valueOf(5));
            statement.setString(3,String.valueOf(4));
            statement.setString(4,String.valueOf(20));

            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Inserted successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

