# Sistem Pengelolaan Gudang (Java Swing MVC)

Aplikasi desktop untuk mengelola data gudang (barang, supplier, transaksi) berbasis Java Swing dengan pola MVC. Data disimpan di MySQL, laporan dapat diekspor ke PDF.

## Fitur Utama
- CRUD master barang (dengan lokasi rak) + ekspor PDF.
- CRUD master supplier + ekspor PDF.
- Pencatatan transaksi MASUK/KELUAR terkait barang + ekspor PDF.
- Validasi input dasar (angka, tanggal, pilihan MASUK/KELUAR).

## Struktur Singkat
- Entrypoint: [src/main/java/id/ac/unpas/tubes/App.java](src/main/java/id/ac/unpas/tubes/App.java)
- Main window & menu: [src/main/java/id/ac/unpas/tubes/view/MainFrame.java](src/main/java/id/ac/unpas/tubes/view/MainFrame.java)
- View forms: [src/main/java/id/ac/unpas/tubes/view/BarangForm.java](src/main/java/id/ac/unpas/tubes/view/BarangForm.java), [src/main/java/id/ac/unpas/tubes/view/SupplierForm.java](src/main/java/id/ac/unpas/tubes/view/SupplierForm.java), [src/main/java/id/ac/unpas/tubes/view/TransaksiForm.java](src/main/java/id/ac/unpas/tubes/view/TransaksiForm.java)
- Controllers: [src/main/java/id/ac/unpas/tubes/controller/BarangController.java](src/main/java/id/ac/unpas/tubes/controller/BarangController.java), [src/main/java/id/ac/unpas/tubes/controller/SupplierController.java](src/main/java/id/ac/unpas/tubes/controller/SupplierController.java), [src/main/java/id/ac/unpas/tubes/controller/TransaksiController.java](src/main/java/id/ac/unpas/tubes/controller/TransaksiController.java)
- Koneksi DB: [src/main/java/id/ac/unpas/tubes/model/DatabaseConnection.java](src/main/java/id/ac/unpas/tubes/model/DatabaseConnection.java)

## Prasyarat
- JDK 24 (sesuai `maven.compiler.release` di pom.xml).
- Maven 3.x.
- MySQL Server (mis. XAMPP) berjalan di `localhost:3306`.
- Akses internet sekali untuk mengunduh dependensi Maven.

## Setup Database
Jalankan SQL berikut di MySQL:
```sql
CREATE DATABASE db_gudang_makmur;
USE db_gudang_makmur;

CREATE TABLE barang (
    id VARCHAR(10) PRIMARY KEY,
    nama VARCHAR(100),
    kategori VARCHAR(50),
    stok INT,
    lokasi_rak VARCHAR(20)
);

CREATE TABLE supplier (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_supplier VARCHAR(100),
    no_telp VARCHAR(15),
    alamat TEXT
);

CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tanggal DATE,
    jenis_transaksi ENUM('Masuk', 'Keluar'),
    jumlah INT,
    id_barang VARCHAR(10),
    FOREIGN KEY (id_barang) REFERENCES barang(id)
);
```

## Konfigurasi Koneksi
- Kredensial default ada di [src/main/java/id/ac/unpas/tubes/model/DatabaseConnection.java](src/main/java/id/ac/unpas/tubes/model/DatabaseConnection.java):
  - URL: `jdbc:mysql://localhost:3306/db_gudang_makmur`
  - User: `root`
  - Password: kosong
- Sesuaikan jika username/password atau host berbeda.

## Build & Jalankan
1) Pastikan MySQL aktif dan DB sudah dibuat.
2) Unduh dependensi dan kompilasi:
```powershell
mvn clean compile
```
3) Jalankan aplikasi (Windows, classpath memakai repo Maven lokal di `%USERPROFILE%/.m2`):
```powershell
java -cp "target/classes;%USERPROFILE%\.m2\repository\com\mysql\mysql-connector-j\9.5.0\mysql-connector-j-9.5.0.jar;%USERPROFILE%\.m2\repository\com\itextpdf\itextpdf\5.5.13.3\itextpdf-5.5.13.3.jar" id.ac.unpas.tubes.App
```
   Linux/macOS (gunakan pemisah `:`):
```bash
java -cp "target/classes:$HOME/.m2/repository/com/mysql/mysql-connector-j/9.5.0/mysql-connector-j-9.5.0.jar:$HOME/.m2/repository/com/itextpdf/itextpdf/5.5.13.3/itextpdf-5.5.13.3.jar" id.ac.unpas.tubes.App
```
Catatan: `mvn exec:java` belum dikonfigurasi di pom. Jika ingin, set `exec.mainClass` ke `id.ac.unpas.tubes.App` atau gunakan plugin exec.

## Cara Menggunakan Aplikasi
1) Buka aplikasi; menu utama ada di jendela awal.
2) **Kelola Supplier** (Menu Data Master → Supplier)
   - Isi ID, Nama, Alamat, No Telepon → Simpan.
   - Klik baris tabel untuk edit (Ubah) atau Hapus.
   - Export PDF untuk laporan supplier.
3) **Kelola Barang** (Menu Data Master → Barang)
   - Isi ID Barang, Nama, Kategori, Stok, Lokasi Rak → Simpan.
   - Edit/Hapus melalui tabel; Export PDF untuk laporan barang.
4) **Catat Transaksi** (Menu Transaksi → Transaksi)
   - Isi ID Transaksi, Tanggal (YYYY-MM-DD), Jenis (MASUK/KELUAR), Jumlah, ID Barang (harus sudah ada).
   - Simpan untuk mencatat; Ubah/Hapus lewat tabel; Export PDF untuk laporan transaksi.

## Catatan Penting
- Di skema SQL, `id` untuk supplier dan transaksi bersifat AUTO_INCREMENT, tetapi form meminta ID. Konsistenkan: isi ID manual sesuai kebutuhan atau sesuaikan skema/SQL insert agar otomatis.
- Stok barang tidak otomatis berubah saat transaksi dicatat; sesuaikan secara manual di form Barang jika diperlukan.
- Pastikan MySQL driver dan iText sudah tersedia di classpath saat menjalankan aplikasi.
