package id.ac.unpas.tubes.view;

import id.ac.unpas.tubes.controller.TransaksiController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class TransaksiForm extends JFrame {
	private JTextField txtId, txtTanggal, txtJenis, txtJumlah, txtIdBarang;
	private JButton btnSimpan, btnUbah, btnHapus, btnReset;
	private JTable tableTransaksi;
	private TransaksiController controller;

	public TransaksiForm() {
		controller = new TransaksiController();
		initComponents();
		loadData();
	}

	private void initComponents() {
		setTitle("Kelola Data Transaksi");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);

		JLabel lblId = new JLabel("ID Transaksi:");
		lblId.setBounds(20, 20, 100, 25);
		add(lblId);

		txtId = new JTextField();
		txtId.setBounds(120, 20, 200, 25);
		add(txtId);

		JLabel lblTanggal = new JLabel("Tanggal (YYYY-MM-DD):");
		lblTanggal.setBounds(20, 60, 150, 25);
		add(lblTanggal);

		txtTanggal = new JTextField();
		txtTanggal.setBounds(170, 60, 150, 25);
		add(txtTanggal);

		JLabel lblJenis = new JLabel("Jenis (MASUK/KELUAR):");
		lblJenis.setBounds(20, 100, 150, 25);
		add(lblJenis);

		txtJenis = new JTextField();
		txtJenis.setBounds(170, 100, 150, 25);
		add(txtJenis);

		JLabel lblJumlah = new JLabel("Jumlah:");
		lblJumlah.setBounds(20, 140, 100, 25);
		add(lblJumlah);

		txtJumlah = new JTextField();
		txtJumlah.setBounds(120, 140, 200, 25);
		add(txtJumlah);

		JLabel lblIdBarang = new JLabel("ID Barang:");
		lblIdBarang.setBounds(20, 180, 100, 25);
		add(lblIdBarang);

		txtIdBarang = new JTextField();
		txtIdBarang.setBounds(120, 180, 200, 25);
		add(txtIdBarang);

		btnSimpan = new JButton("Simpan");
		btnSimpan.setBounds(20, 230, 80, 30);
		add(btnSimpan);

		btnUbah = new JButton("Ubah");
		btnUbah.setBounds(110, 230, 80, 30);
		add(btnUbah);

		btnHapus = new JButton("Hapus");
		btnHapus.setBounds(200, 230, 80, 30);
		add(btnHapus);

		btnReset = new JButton("Reset");
		btnReset.setBounds(290, 230, 80, 30);
		add(btnReset);

		tableTransaksi = new JTable();
		JScrollPane scrollPane = new JScrollPane(tableTransaksi);
		scrollPane.setBounds(400, 20, 450, 500);
		add(scrollPane);

		btnSimpan.addActionListener(e -> {
			if (validasiInput()) {
				controller.insertTransaksi(
					txtId.getText(), txtTanggal.getText(), txtJenis.getText(),
					Integer.parseInt(txtJumlah.getText()), txtIdBarang.getText()
				);
				loadData();
				resetForm();
			}
		});

		btnUbah.addActionListener(e -> {
			if (validasiInput()) {
				controller.updateTransaksi(
					txtId.getText(), txtTanggal.getText(), txtJenis.getText(),
					Integer.parseInt(txtJumlah.getText()), txtIdBarang.getText()
				);
				loadData();
				resetForm();
			}
		});

		btnHapus.addActionListener(e -> {
			if (!txtId.getText().isEmpty()) {
				int confirm = JOptionPane.showConfirmDialog(null, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					controller.deleteTransaksi(txtId.getText());
					loadData();
					resetForm();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Pilih data di tabel atau isi ID dulu!");
			}
		});

		btnReset.addActionListener(e -> resetForm());

		tableTransaksi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableTransaksi.getSelectedRow();
				txtId.setText(tableTransaksi.getValueAt(row, 0).toString());
				txtTanggal.setText(tableTransaksi.getValueAt(row, 1).toString());
				txtJenis.setText(tableTransaksi.getValueAt(row, 2).toString());
				txtJumlah.setText(tableTransaksi.getValueAt(row, 3).toString());
				txtIdBarang.setText(tableTransaksi.getValueAt(row, 4).toString());
				txtId.setEditable(false);
			}
		});
	}

	private void loadData() {
		DefaultTableModel model = controller.getAllTransaksi();
		tableTransaksi.setModel(model);
	}

	private void resetForm() {
		txtId.setText("");
		txtTanggal.setText("");
		txtJenis.setText("");
		txtJumlah.setText("");
		txtIdBarang.setText("");
		txtId.setEditable(true);
	}

	private boolean validasiInput() {
		if (txtId.getText().trim().isEmpty() || txtTanggal.getText().trim().isEmpty() ||
			txtJenis.getText().trim().isEmpty() || txtJumlah.getText().trim().isEmpty() || txtIdBarang.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
			return false;
		}
		if (!txtJumlah.getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
			return false;
		}
		if (!txtJenis.getText().equalsIgnoreCase("MASUK") && !txtJenis.getText().equalsIgnoreCase("KELUAR")) {
			JOptionPane.showMessageDialog(this, "Jenis harus MASUK atau KELUAR!");
			return false;
		}
		// Validasi tanggal sederhana (format YYYY-MM-DD)
		if (!txtTanggal.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
			JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD!");
			return false;
		}
		return true;
	}
}
