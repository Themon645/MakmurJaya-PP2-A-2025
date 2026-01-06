package id.ac.unpas.tubes.view;

import id.ac.unpas.tubes.controller.SupplierController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SupplierForm extends JFrame {
	private JTextField txtId, txtNama, txtAlamat, txtNoTelepon;
	private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnExportPDF;
	private JTable tableSupplier;
	private SupplierController controller;

	public SupplierForm() {
		controller = new SupplierController();
		initComponents();
		loadData();
	}

	private void initComponents() {
		setTitle("Kelola Data Supplier");
		setSize(900, 600);
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

		btnExportPDF = new JButton("Export PDF");
		btnExportPDF.setBounds(20, 270, 120, 30);
		add(btnExportPDF);

		tableSupplier = new JTable();
		JScrollPane scrollPane = new JScrollPane(tableSupplier);
		scrollPane.setBounds(400, 20, 450, 500);
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

		btnExportPDF.addActionListener(e -> exportToPdf());

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

	private void exportToPdf() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Simpan PDF");
			fileChooser.setSelectedFile(new java.io.File("Data_Supplier_" + 
				new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf"));
			
			int userSelection = fileChooser.showSaveDialog(this);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filePath.toLowerCase().endsWith(".pdf")) {
					filePath += ".pdf";
				}
				
				Document document = new Document(PageSize.A4);
				PdfWriter.getInstance(document, new FileOutputStream(filePath));
				document.open();
				
				// Judul
				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
				Paragraph title = new Paragraph("LAPORAN DATA SUPPLIER\n\n", titleFont);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
				
				// Info tanggal
				Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
				Paragraph date = new Paragraph("Tanggal: " + new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new Date()) + "\n\n", dateFont);
				document.add(date);
				
				// Tabel
				PdfPTable pdfTable = new PdfPTable(4);
				pdfTable.setWidthPercentage(100);
				pdfTable.setWidths(new float[]{1.5f, 3f, 4f, 2f});
				
				// Header
				Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
				String[] headers = {"ID", "Nama Supplier", "Alamat", "No Telepon"};
				for (String header : headers) {
					PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(5);
					pdfTable.addCell(cell);
				}
				
				// Data
				DefaultTableModel model = (DefaultTableModel) tableSupplier.getModel();
				Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
				for (int i = 0; i < model.getRowCount(); i++) {
					for (int j = 0; j < model.getColumnCount(); j++) {
						PdfPCell cell = new PdfPCell(new Phrase(model.getValueAt(i, j).toString(), dataFont));
						cell.setPadding(5);
						pdfTable.addCell(cell);
					}
				}
				
				document.add(pdfTable);
				document.close();
				
				JOptionPane.showMessageDialog(this, "PDF berhasil diekspor ke: " + filePath);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error saat export PDF: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
