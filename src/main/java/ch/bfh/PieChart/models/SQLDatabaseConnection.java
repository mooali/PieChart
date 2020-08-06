package ch.bfh.PieChart.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    String url = "jdbc:sqlserver://corpus.bfh.ch:55783;Database=pt2_2020";
    String user ="Group33";
    String pass ="Pt2020$589255";
    Connection conn;



    public SQLDatabaseConnection(){
        try {
            this.connect(url,user,pass);
            System.out.println("ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Connection connect(String url, String username, String password) throws SQLException {
        System.out.println("\n------------------------------------------");
        System.out.print("connecting to '" + url + "' ...  ");
        if (username == null) {
            conn = DriverManager.getConnection(url);
        } else {
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            conn = DriverManager.getConnection(url, props);
        }
        conn.getCatalog();
        return conn;
    }

    public Connection getConn(){
        return this.conn;
    }



}