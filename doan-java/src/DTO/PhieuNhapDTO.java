package DTO;

import common.Util;

public class PhieuNhapDTO extends BaseDTO {
	private String maPhieu;
	private String maNhaCungCap;
	private String maNhanVien;
	private int tongTien;
	private String ngayNhap;

	public PhieuNhapDTO() {
		maPhieu = "";
		maNhaCungCap = "";
		maNhanVien = "";
		tongTien = 0;
		ngayNhap = "";
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaNhaCungCap(String maNhaCungCap) {
		this.maNhaCungCap = maNhaCungCap;
	}

	public String getMaNhaCungCap() {
		return maNhaCungCap;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setTongTien(int tongTien) {
		this.tongTien = tongTien;
	}

	public int getTongTien() {
		return tongTien;
	}

	public void setNgayNhap(String ngayNhap) {
		this.ngayNhap = ngayNhap;
	}

	public String getNgayNhap() {
		return ngayNhap;
	}

	@Override 
	public String[]	toArrayString() {
		return new String[] {maPhieu, maNhaCungCap, maNhanVien, Util.convertMoneyToString(tongTien), ngayNhap};
	}
}
