package id.ac.unpas.tubes.model;

public class Supplier {
	private String id;
	private String nama;
	private String alamat;
	private String noTelepon;

	public Supplier(String id, String nama, String alamat, String noTelepon) {
		this.id = id;
		this.nama = nama;
		this.alamat = alamat;
		this.noTelepon = noTelepon;
	}

	public String getId() { return id; }
	public String getNama() { return nama; }
	public String getAlamat() { return alamat; }
	public String getNoTelepon() { return noTelepon; }
	public void setId(String id) { this.id = id; }
	public void setNama(String nama) { this.nama = nama; }
	public void setAlamat(String alamat) { this.alamat = alamat; }
	public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
}
