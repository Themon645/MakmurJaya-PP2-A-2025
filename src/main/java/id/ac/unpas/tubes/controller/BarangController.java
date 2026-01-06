package id.ac.unpas.tubes.controller;

import id.ac.unpas.tubes.model.DatabaseConnection;
import id.ac.unpas.tubes.model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class BarangController {
    public DefaultTableModel getAllBarang() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Lokasi");

        try {
            String sql = "SELECT * FROM barang";
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getInt("stok"),
                        rs.getString("lokasi_rak")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public void insertBarang(String id, String nama, String kategori, int stok, String lokasi) {
        // Validasi sederhana
        if (id.isEmpty() || nama.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "ID dan Nama tidak boleh kosong!");
            return;
        }

        try {
            String sql = "INSERT INTO barang VALUES (?, ?, ?, ?, ?)";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, nama);
            pstmt.setString(3, kategori);
            pstmt.setInt(4, stok);
            pstmt.setString(5, lokasi);

            pstmt.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBarang(String id, String nama, String kategori, int stok, String lokasi) {
        try {
            String sql = "UPDATE barang SET nama=?, kategori=?, stok=?, lokasi_rak=? WHERE id=?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nama);
            pstmt.setString(2, kategori);
            pstmt.setInt(3, stok);
            pstmt.setString(4, lokasi);
            pstmt.setString(5, id); // Where clause

            pstmt.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBarang(String id) {
        try {
            String sql = "DELETE FROM barang WHERE id=?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            pstmt.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
