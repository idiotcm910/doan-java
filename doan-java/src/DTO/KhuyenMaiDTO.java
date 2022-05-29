package DTO;

public class KhuyenMaiDTO extends BaseDTO {
	private String maKhuyenMai;
	private String tenKhuyenMai;
	private String ngayBatDau;
	private String ngayKetThuc;

	public KhuyenMaiDTO() {
		maKhuyenMai = "";
		tenKhuyenMai = "";
		ngayBatDau = "";
		ngayKetThuc = "";
	}

	public KhuyenMaiDTO(String maKM, String tenKM, String ngayBD, String ngayKT) {
		maKhuyenMai = maKM;
		tenKhuyenMai = tenKM;
		ngayBatDau = ngayBD;
		ngayKetThuc = ngayKT;
	}

	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}

	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}

	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}

	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}

	public void setNgayBatDau(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public String getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayKetThuc(String ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public String getNgayKetThuc() {
		return ngayKetThuc;
	}

	@Override
	public String[] toArrayString() {
		return new String[] {maKhuyenMai, tenKhuyenMai, ngayBatDau, ngayKetThuc};
	}
}
