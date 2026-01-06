package id.ac.unpas.tubes.view;

import id.ac.unpas.tubes.controller.SupplierController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class SupplierForm extends JFrame {
	private JTextField txtId, txtNama, txtAlamat, txtNoTelepon;
	private JButton btnSimpan, btnUbah, btnHapus, btnReset;
	private JTable tableSupplier;
	private SupplierController controller;

	public SupplierForm() {
		controller = new SupplierController();
		initComponents();
		loadData();
	}

	private void initComponents() {
		setTitle("Kelola Data Supplier");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);

		JLabel lblId = new JLabel("ID Supplier:");
		lblId.setBounds(20, 20, 100, 25);
		add(lblId);

		txtId = new JTextField();
		txtId.setBounds(120, 20, 200, 25);
		add(txtId);

		JLabel lblNama = new JLabel("Nama Supplier:");
		lblNama.setBounds(20, 60, 100, 25);
		add(lblNama);

		txtNama = new JTextField();
		txtNama.setBounds(120, 60, 200, 25);
		add(txtNama);

		JLabel lblAlamat = new JLabel("Alamat:");
		lblAlamat.setBounds(20, 100, 100, 25);
		add(lblAlamat);

		txtAlamat = new JTextField();
		txtAlamat.setBounds(120, 100, 200, 25);
		add(txtAlamat);

		JLabel lblNoTelepon = new JLabel("No Telepon:");
		lblNoTelepon.setBounds(20, 140, 100, 25);
		add(lblNoTelepon);

		txtNoTelepon = new JTextField();
		txtNoTelepon.setBounds(120, 140, 200, 25);
		add(txtNoTelepon);

		btnSimpan = new JButton("Simpan");
		btnSimpan.setBounds(20, 190, 80, 30);
		add(btnSimpan);

		btnUbah = new JButton("Ubah");
		btnUbah.setBounds(110, 190, 80, 30);
		add(btnUbah);

		btnHapus = new JButton("Hapus");
		btnHapus.setBounds(200, 190, 80, 30);
		add(btnHapus);

		btnReset = new JButton("Reset");
		btnReset.setBounds(290, 190, 80, 30);
		add(btnReset);

		tableSupplier = new JTable();
		JScrollPane scrollPane = new JScrollPane(tableSupplier);
		scrollPane.setBounds(350, 20, 400, 500);
		add(scrollPane);

		btnSimpan.addActionListener(e -> {
			if (validasiInput()) {
				controller.insertSupplier(
					txtId.getText(), txtNama.getText(), txtAlamat.getText(), txtNoTelepon.getText()
				);
				loadData();
				resetForm();
			}
		});

		btnUbah.addActionListener(e -> {
			if (validasiInput()) {
				controller.updateSupplier(
					txtId.getText(), txtNama.getText(), txtAlamat.getText(), txtNoTelepon.getText()
				);
				loadData();
				resetForm();
			}
		});

		btnHapus.addActionListener(e -> {
			if (!txtId.getText().isEmpty()) {
				int confirm = JOptionPane.showConfirmDialog(null, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					controller.deleteSupplier(txtId.getText());
					loadData();
					resetForm();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Pilih data di tabel atau isi ID dulu!");
			}
		});

		btnReset.addActionListener(e -> resetForm());

		tableSupplier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableSupplier.getSelectedRow();
				txtId.setText(tableSupplier.getValueAt(row, 0).toString());
				txtNama.setText(tableSupplier.getValueAt(row, 1).toString());
				txtAlamat.setText(tableSupplier.getValueAt(row, 2).toString());
				txtNoTelepon.setText(tableSupplier.getValueAt(row, 3).toString());
				txtId.setEditable(false);
			}
		});
	}

	private void loadData() {
		DefaultTableModel model = controller.getAllSupplier();
		tableSupplier.setModel(model);
	}

	private void resetForm() {
		txtId.setText("");
		txtNama.setText("");
		txtAlamat.setText("");
		txtNoTelepon.setText("");
		txtId.setEditable(true);
	}

	private boolean validasiInput() {
		if (txtId.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty() ||
			txtAlamat.getText().trim().isEmpty() || txtNoTelepon.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
			return false;
		}
		if (!txtNoTelepon.getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(this, "No Telepon harus berupa angka!");
			return false;
		}
		return true;
	}
}
