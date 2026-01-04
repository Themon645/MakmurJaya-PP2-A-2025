package id.ac.unpas.tubes.model;

public class Barang {
    private String id;
    private String nama;
    private String kategori;
    private int stok;
    private String lokasiRak;

    public Barang(String id, String nama, String kategori, int stok, String lokasiRak) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.stok = stok;
        this.lokasiRak = lokasiRak;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
}
