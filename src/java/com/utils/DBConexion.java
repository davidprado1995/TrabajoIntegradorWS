/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class DBConexion {
    
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/arqsw?user=root&password=";

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("Verifica tu driver en el classpath");
        } catch (SQLException e) {
            System.out.println("Verifica tus parametros de conexion");
        
        }
        return con;
    }
}
