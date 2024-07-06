package com.example;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String userName = "myuser";
    private static final String password = "mysecretpassword";


    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("SELECT count(*) FROM information_schema.tables;");
            while (resultSet.next()) {
                int count = resultSet.getInt("count");
                System.out.println("There is " + count + " tables in database");
            }
        }catch (SQLException e) {
            System.out.println("got SQL exception: " + e.getMessage());
        }
    }
}