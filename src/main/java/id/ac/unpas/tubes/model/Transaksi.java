package id.ac.unpas.tubes.model;

public class Transaksi {
	private String id;
	private String tanggal;
	private String jenis;
	private int jumlah;
	private String idBarang;

	public Transaksi(String id, String tanggal, String jenis, int jumlah, String idBarang) {
		this.id = id;
		this.tanggal = tanggal;
		this.jenis = jenis;
		this.jumlah = jumlah;
		this.idBarang = idBarang;
	}

	public String getId() { return id; }
	public String getTanggal() { return tanggal; }
	public String getJenis() { return jenis; }
	public int getJumlah() { return jumlah; }
	public String getIdBarang() { return idBarang; }
	public void setId(String id) { this.id = id; }
	public void setTanggal(String tanggal) { this.tanggal = tanggal; }
	public void setJenis(String jenis) { this.jenis = jenis; }
	public void setJumlah(int jumlah) { this.jumlah = jumlah; }
	public void setIdBarang(String idBarang) { this.idBarang = idBarang; }
}
