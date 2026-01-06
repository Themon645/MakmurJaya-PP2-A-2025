package id.ac.unpas.tubes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_gudang_makmur";
                String user = "root"; 
                String password = ""; 

                // Register Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi Berhasil!");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return connection;
    }
}