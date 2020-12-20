package com.sanitas.clinicapp;

import java.sql.*;

public class Database {

    private final String username;
    private final String password;
    private String url;

    private Connection connection;

    public Database(String host, Integer port, String databaseName, String username, String password) {
        url = "jdbc:mysql://localhost/sakila?user=root&password=DButcnMySQL";

        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
    }

    public PreparedStatement preparedStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public CallableStatement callableStatement(String query) throws SQLException {
        return getConnection().prepareCall(query);
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || !this.connection.isValid(5)) {
            this.connect();
        }
        return this.connection;
    }

}
