package id.ac.unpas.tubes.view;

import id.ac.unpas.tubes.controller.BarangController;
import id.ac.unpas.tubes.model.Barang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BarangForm extends JFrame {
    private JTextField txtId, txtNama, txtKategori, txtStok, txtLokasi;
    private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnExportPDF;
    private JTable tableBarang;
    private BarangController controller;

    public BarangForm() {
        controller = new BarangController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Kelola Data Barang - Gudang Makmur Jaya");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblId = new JLabel("ID Barang:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 20, 200, 25);
        add(txtId);

        JLabel lblNama = new JLabel("Nama Barang:");
        lblNama.setBounds(20, 60, 100, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(120, 60, 200, 25);
        add(txtNama);

        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setBounds(20, 100, 100, 25);
        add(lblKategori);

        txtKategori = new JTextField();
        txtKategori.setBounds(120, 100, 200, 25);
        add(txtKategori);

        JLabel lblStok = new JLabel("Stok:");
        lblStok.setBounds(20, 140, 100, 25);
        add(lblStok);

        txtStok = new JTextField();
        txtStok.setBounds(120, 140, 200, 25);
        add(txtStok);

        JLabel lblLokasi = new JLabel("Lokasi Rak:");
        lblLokasi.setBounds(20, 180, 100, 25);
        add(lblLokasi);

        txtLokasi = new JTextField();
        txtLokasi.setBounds(120, 180, 200, 25);
        add(txtLokasi);

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

        tableBarang = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableBarang);
        scrollPane.setBounds(400, 20, 450, 500);
        add(scrollPane);

        btnSimpan.addActionListener(e -> {
            if (validasiInput()) {
                controller.insertBarang(
                        txtId.getText(), txtNama.getText(), txtKategori.getText(),
                        Integer.parseInt(txtStok.getText()), txtLokasi.getText()
                );
                loadData();
                resetForm();
            }
        });

        btnUbah.addActionListener(e -> {
            if (validasiInput()) {
                controller.updateBarang(
                        txtId.getText(), txtNama.getText(), txtKategori.getText(),
                        Integer.parseInt(txtStok.getText()), txtLokasi.getText()
                );
                loadData();
                resetForm();
            }
        });

        btnHapus.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteBarang(txtId.getText());
                    loadData();
                    resetForm();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel atau isi ID dulu!");
            }
        });

        btnReset.addActionListener(e -> resetForm());

        btnExportPDF.addActionListener(e -> exportToPdf());

        tableBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableBarang.getSelectedRow();
                txtId.setText(tableBarang.getValueAt(row, 0).toString());
                txtNama.setText(tableBarang.getValueAt(row, 1).toString());
                txtKategori.setText(tableBarang.getValueAt(row, 2).toString());
                txtStok.setText(tableBarang.getValueAt(row, 3).toString());
                txtLokasi.setText(tableBarang.getValueAt(row, 4).toString());
                txtId.setEditable(false); // ID tidak boleh diubah saat mode Edit
            }
        });
    }

    private void loadData() {
        DefaultTableModel model = controller.getAllBarang();
        tableBarang.setModel(model);
    }

    private void resetForm() {
        txtId.setText("");
        txtNama.setText("");
        txtKategori.setText("");
        txtStok.setText("");
        txtLokasi.setText("");
        txtId.setEditable(true);
    }

    private boolean validasiInput() {
        // Cek Kosong
        if (txtId.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty() ||
                txtKategori.getText().trim().isEmpty() || txtStok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return false;
        }

        try {
            int stok = Integer.parseInt(txtStok.getText());
            if (stok < 0) {
                JOptionPane.showMessageDialog(this, "Stok tidak boleh negatif!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
            return false;
        }

        return true;
    }

    private void exportToPdf() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Simpan PDF");
            fileChooser.setSelectedFile(new java.io.File("Data_Barang_" + 
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
                Paragraph title = new Paragraph("LAPORAN DATA BARANG\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                
                // Info tanggal
                Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
                Paragraph date = new Paragraph("Tanggal: " + new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new Date()) + "\n\n", dateFont);
                document.add(date);
                
                // Tabel
                PdfPTable pdfTable = new PdfPTable(5);
                pdfTable.setWidthPercentage(100);
                pdfTable.setWidths(new float[]{1.5f, 3f, 2f, 1.5f, 2f});
                
                // Header
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                String[] headers = {"ID", "Nama Barang", "Kategori", "Stok", "Lokasi"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    pdfTable.addCell(cell);
                }
                
                // Data
                DefaultTableModel model = (DefaultTableModel) tableBarang.getModel();
                Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        PdfPCell cell = new PdfPCell(new Phrase(model.getValueAt(i, j).toString(), dataFont));
                        cell.setPadding(5);
                        if (j == 3) { // Stok - align right
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