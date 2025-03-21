package com.example.casipongdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Database connection successful");
            String query = "CREATE TABLE `csit228f1`.`company1` (`id` INT NOT NULL AUTO_INCREMENT , `name` VARCHAR(50) NOT NULL , `hours` INT NOT NULL , `wage` INT NOT NULL , `salary` INT NOT NULL , PRIMARY KEY (`id`)) ";
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Table created successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Connection closed");
    }
}
