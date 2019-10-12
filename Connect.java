package org.openjfx.mavenproject3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mario
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect {
    Connection con = null;

    public static Connection conDB() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/autoskola", "root", "");
            return con;

            /* Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from kandidat");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }

            con.close(); */
            
        } catch (Exception e) {
            System.err.println("Connect : " + e.getMessage());
            return null;
        }
    
}
}
