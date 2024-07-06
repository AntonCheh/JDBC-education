package com.example;

import java.sql.*;

public class TransactionExamples {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String userName = "myuser";
    private static final String password = "mysecretpassword";

    public static void main(String[] args) {
        Connection connection = null;
        try  {
            connection = DriverManager.getConnection(URL, userName, password);
            connection.setAutoCommit(false);
            String createTableSQL = "CREATE TABLE pupils (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL)";
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);
            String insert1 = "INSERT INTO pupils (name) VALUES ('ALICE')";
            String insert2 = "INSERT INTO pupils (name) VALUES ('BOB')";

            statement.executeUpdate(insert1);
            statement.executeUpdate(insert2);
            statement.executeUpdate(insert1);
            connection.commit();
            System.out.println("Transaction is successfully committed");

        } catch (SQLException e) {
            System.out.println("got SQL exception: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("SQL exception in closing: " + e.getMessage());
            }
        }
    }
}
