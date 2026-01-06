package id.ac.unpas.tubes.controller;

import id.ac.unpas.tubes.model.DatabaseConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class SupplierController {
	// READ
	public DefaultTableModel getAllSupplier() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Nama");
		model.addColumn("Alamat");
		model.addColumn("No Telepon");
		try {
			String sql = "SELECT * FROM supplier";
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				model.addRow(new Object[]{
					rs.getString("id"),
					rs.getString("nama_supplier"),
					rs.getString("alamat"),
					rs.getString("no_telp")
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}

	// CREATE
	public void insertSupplier(String id, String nama, String alamat, String noTelepon) {
		if (id.isEmpty() || nama.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(null, "ID dan Nama tidak boleh kosong!");
			return;
		}
		try {
			String sql = "INSERT INTO supplier (nama_supplier, alamat, no_telp) VALUES (?, ?, ?)";
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nama);
			pstmt.setString(2, alamat);
			pstmt.setString(3, noTelepon);
			pstmt.executeUpdate();
			javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// UPDATE
	public void updateSupplier(String id, String nama, String alamat, String noTelepon) {
		try {
			String sql = "UPDATE supplier SET nama_supplier=?, alamat=?, no_telp=? WHERE id=?";
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nama);
			pstmt.setString(2, alamat);
			pstmt.setString(3, noTelepon);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
			javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DELETE
	public void deleteSupplier(String id) {
		try {
			String sql = "DELETE FROM supplier WHERE id=?";
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
