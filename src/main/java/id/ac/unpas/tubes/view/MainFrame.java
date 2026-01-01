package id.ac.unpas.tubes.view;

import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Sistem Pengelolaan Gudang");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		JMenu menuData = new JMenu("Data Master");
		JMenu menuTransaksi = new JMenu("Transaksi");

		JMenuItem menuBarang = new JMenuItem("Barang");
		JMenuItem menuSupplier = new JMenuItem("Supplier");
		JMenuItem menuTransaksiItem = new JMenuItem("Transaksi");

		menuBarang.addActionListener(e -> new BarangForm().setVisible(true));
		menuSupplier.addActionListener(e -> new SupplierForm().setVisible(true));
		menuTransaksiItem.addActionListener(e -> new TransaksiForm().setVisible(true));

		menuData.add(menuBarang);
		menuData.add(menuSupplier);
		menuTransaksi.add(menuTransaksiItem);

		menuBar.add(menuData);
		menuBar.add(menuTransaksi);
		setJMenuBar(menuBar);

		JLabel lblWelcome = new JLabel("Selamat datang di Sistem Pengelolaan Gudang", SwingConstants.CENTER);
		add(lblWelcome);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
