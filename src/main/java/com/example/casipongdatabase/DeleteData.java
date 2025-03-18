package com.example.casipongdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            int id = 1;
            statement.setInt(1,id);
            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Deleted successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
