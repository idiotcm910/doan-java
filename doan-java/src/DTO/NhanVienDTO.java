package DTO;

import common.Util;

public class NhanVienDTO extends BaseDTO {
	private String maNhanVien;
	private String tenNhanVien;
	private String ngayVaoLam;
	private String viTri;
	private Integer luong;

	public NhanVienDTO() {
		maNhanVien = "";
		tenNhanVien = "";
		ngayVaoLam = "";
		viTri = "";
		luong = 0;
	}

	public NhanVienDTO(String maNhanVien, String ten, String ngayVaoLam, String viTri, int luong) {
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = ten;
		this.ngayVaoLam = ngayVaoLam;
		this.viTri = viTri;
		this.luong = luong;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setNgayVaoLam(String ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public String getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setViTri(String viTri) {
		this.viTri = viTri;
	}

	public String getViTri() {
		return viTri;
	}

	public void setLuong(Integer luong) {
		this.luong = luong;
	}

	public Integer getLuong() {
		return luong;
	}

	@Override 
	public String[] toArrayString() {
		return new String[] {maNhanVien, tenNhanVien, ngayVaoLam, viTri, Util.convertMoneyToString(luong)};
	}

	public NhanVienDTO clone() {
		return new NhanVienDTO(maNhanVien, tenNhanVien, ngayVaoLam, viTri, luong);	
	}
}
