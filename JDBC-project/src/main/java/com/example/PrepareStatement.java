package com.example;

import java.sql.*;

public class PrepareStatement {

    private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String userName = "myuser";
    private static final String password = "mysecretpassword";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            createTable(connection);
            insertRecords(connection);
            ResultSet resultSet = retrieveRecords(connection);
            printRecord(resultSet);
        } catch (SQLException e) {
            System.out.println("got SQL exception: " + e.getMessage());
        }
    }

    private static void printRecord(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println("ID: " + id + " , Name: " + name + " , age: " + age);
        }
    }

    private static ResultSet retrieveRecords(Connection connection) throws SQLException {
        String retrieveStudentsSQL = "SELECT * FROM students";
        Statement statement = connection.createStatement();
        return statement.executeQuery(retrieveStudentsSQL);
    }

    private static void insertRecords(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO students (name, age) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
        preparedStatement.setString(1, "Ivan");
        preparedStatement.setInt(2, 20);
        preparedStatement.executeUpdate();
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255)," +
                "age INT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
    }
}

