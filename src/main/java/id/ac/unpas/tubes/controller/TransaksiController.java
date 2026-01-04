package id.ac.unpas.tubes.controller;

import id.ac.unpas.tubes.model.DatabaseConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class TransaksiController {
	// READ
	public DefaultTableModel getAllTransaksi() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Tanggal");
		model.addColumn("Jenis");
		model.addColumn("Jumlah");
		model.addColumn("ID Barang");
		try {
			String sql = "SELECT * FROM transaksi";
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				model.addRow(new Object[]{
					rs.getString("id"),
					rs.getString("tanggal"),
					rs.getString("jenis"),
					rs.getInt("jumlah"),
					rs.getString("id_barang")
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}

	// CREATE
	public void insertTransaksi(String id, String tanggal, String jenis, int jumlah, String idBarang) {
		if (id.isEmpty() || tanggal.isEmpty() || jenis.isEmpty() || idBarang.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(null, "Semua field wajib diisi!");
			return;
		}
		try {
			String sql = "INSERT INTO transaksi VALUES (?, ?, ?, ?, ?)";
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, tanggal);
			pstmt.setString(3, jenis);
			pstmt.setInt(4, jumlah);
			pstmt.setString(5, idBarang);
			pstmt.executeUpdate();
			javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// UPDATE
	public void updateTransaksi(String id, String tanggal, String jenis, int jumlah, String idBarang) {
		try {
			String sql = "UPDATE transaksi SET tanggal=?, jenis=?, jumlah=?, id_barang=? WHERE id=?";
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tanggal);
			pstmt.setString(2, jenis);
			pstmt.setInt(3, jumlah);
			pstmt.setString(4, idBarang);
			pstmt.setString(5, id);
			pstmt.executeUpdate();
			javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DELETE
	public void deleteTransaksi(String id) {
		try {
			String sql = "DELETE FROM transaksi WHERE id=?";
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
