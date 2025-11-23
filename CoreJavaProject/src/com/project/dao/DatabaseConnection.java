package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Oracle Database connection parameters
    private static final String URL = "jdbc:oracle:thin:@Admin:1521:xe";
    private static final String USERNAME = "System";
    private static final String PASSWORD = "tiger";
    
    
    
    static {
        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Oracle JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Oracle Database connection established successfully!");
            System.out.println("Database Product: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Database Version: " + conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.out.println("Failed to connect to Oracle database: " + e.getMessage());
            System.out.println("URL: " + URL);
            System.out.println("Please check:");
            System.out.println("1. Oracle Database is running");
            System.out.println("2. Connection parameters are correct");
            System.out.println("3. Oracle JDBC driver is in classpath");
        }
    }
    
    // Method to get connection with custom parameters
    public static Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}