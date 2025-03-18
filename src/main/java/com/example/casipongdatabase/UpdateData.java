package com.example.casipongdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET name=?, email=? WHERE id=?")) {
            String name = "Mark James Casipong";
            String email = "markjames.casipong@cit.edu";
            int id = 3;
            statement.setString(1,name);
            statement.setString(2,email);
            statement.setInt(3,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
