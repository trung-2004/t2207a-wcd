package com.dao;

import java.sql.*;

public class Database {
    private final String connectionString = "jdbc:mysql://localhost:3306/t2207a_wcd";
    private final String user = "root";
    private final String password = "";
    private Connection conn;
    private static Database instance;
    private Database() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(connectionString, user, password);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Statement getStatement() throws SQLException {
        return conn.createStatement();
    }
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public static Database getInstance() throws Exception{
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

}
