package com.example.kamwaro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
   static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String dbUrl = "jdbc:mysql://localhost/db_jesse_kamwaro_189730";
   static final String dbUser = "root";
   static final String dbPassword = "Passwords$uck0";

    public static Connection getConnection()throws SQLException{
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            throw new SQLException("Database driver not found");
        }
        return DriverManager.getConnection(dbUrl,dbUser,dbPassword);
    }
}
