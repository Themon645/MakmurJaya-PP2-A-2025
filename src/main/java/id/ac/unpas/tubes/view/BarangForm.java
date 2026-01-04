package id.ac.unpas.tubes.view;

import id.ac.unpas.tubes.controller.BarangController;
import id.ac.unpas.tubes.model.Barang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class BarangForm extends JFrame {
    private JTextField txtId, txtNama, txtKategori, txtStok, txtLokasi;
    private JButton btnSimpan, btnUbah, btnHapus, btnReset;
    private JTable tableBarang;
    private BarangController controller;

    public BarangForm() {
        controller = new BarangController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Kelola Data Barang - Gudang Makmur Jaya");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        tableBarang = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableBarang);
        scrollPane.setBounds(350, 20, 400, 500);
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
}