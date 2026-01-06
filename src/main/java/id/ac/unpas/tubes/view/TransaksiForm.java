package id.ac.unpas.tubes.view;

import id.ac.unpas.tubes.controller.TransaksiController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransaksiForm extends JFrame {
	private JTextField txtId, txtTanggal, txtJenis, txtJumlah, txtIdBarang;
	private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnExportPDF;
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

		btnExportPDF = new JButton("Export PDF");
		btnExportPDF.setBounds(20, 270, 120, 30);
		add(btnExportPDF);

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

		btnExportPDF.addActionListener(e -> exportToPdf());

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

	private void exportToPdf() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Simpan PDF");
			fileChooser.setSelectedFile(new java.io.File("Data_Transaksi_" + 
				new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf"));
			
			int userSelection = fileChooser.showSaveDialog(this);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filePath.toLowerCase().endsWith(".pdf")) {
					filePath += ".pdf";
				}
				
				Document document = new Document(PageSize.A4.rotate());
				PdfWriter.getInstance(document, new FileOutputStream(filePath));
				document.open();
				
				// Judul
				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
				Paragraph title = new Paragraph("LAPORAN DATA TRANSAKSI\n\n", titleFont);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
				
				// Info tanggal
				Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
				Paragraph date = new Paragraph("Tanggal: " + new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new Date()) + "\n\n", dateFont);
				document.add(date);
				
				// Tabel
				PdfPTable pdfTable = new PdfPTable(5);
				pdfTable.setWidthPercentage(100);
				pdfTable.setWidths(new float[]{2f, 2.5f, 2f, 1.5f, 2f});
				
				// Header
				Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
				String[] headers = {"ID Transaksi", "Tanggal", "Jenis", "Jumlah", "ID Barang"};
				for (String header : headers) {
					PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(5);
					pdfTable.addCell(cell);
				}
				
				// Data
				DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel();
				Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
				for (int i = 0; i < model.getRowCount(); i++) {
					for (int j = 0; j < model.getColumnCount(); j++) {
						PdfPCell cell = new PdfPCell(new Phrase(model.getValueAt(i, j).toString(), dataFont));
						cell.setPadding(5);
						if (j == 2) { // Jenis - center
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else if (j == 3) { // Jumlah - right
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
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
