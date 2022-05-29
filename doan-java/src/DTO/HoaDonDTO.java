package DTO;

import common.Util;

public class HoaDonDTO {
	String maHoaDon;
	String ngayNhapThongTin;
	int tongTien;
	String maNhanVien;
	String maKhachHang;

	public HoaDonDTO(String maHD, String time, int tongTien, String maNV, String maKH) {
		this.maHoaDon = maHD;
		this.ngayNhapThongTin = time;
		this.tongTien = tongTien;
		this.maNhanVien = maNV;
		this.maKhachHang = maKH;
	}

	public HoaDonDTO() {
		this.maHoaDon = "";
		this.ngayNhapThongTin = "";
		this.tongTien = 0;
		this.maNhanVien = "";
		this.maKhachHang = "";
	}

	public void setMaHoaDon(String str) {
		this.maHoaDon = str;
	}

	public void setNgayNhapThongTin(String str) {
		this.ngayNhapThongTin = str;
	}

	public void setTongTien(int number) {
		this.tongTien = number;
	}

	public void setMaNhanVien(String str) {
		this.maNhanVien = str;
	}

	public void setMaKhachHang(String str) {
		this.maKhachHang = str;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public String getNgayNhapThongTin() {
		return ngayNhapThongTin;
	}

	public int getTongTien() {
		return tongTien;
	}
	
	public String getMaNhanVien() {
		return maNhanVien;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public boolean equals(HoaDonDTO hoaDon) {
		if(!maHoaDon.equals(hoaDon.getMaHoaDon())) {
			return false;	
		}

		if(!ngayNhapThongTin.equals(hoaDon.getNgayNhapThongTin())) {
			return false;
		}

		if(tongTien != hoaDon.getTongTien()) {
			return false;
		}

		if(!maNhanVien.equals(hoaDon.getMaNhanVien())) {
			return false;
		}

		if(!maKhachHang.equals(hoaDon.getMaKhachHang())) {
			return false;
		}

		return true;
	}

	public String[] toArray() {
		String maKhachHangString = (this.maKhachHang.equals("NULL"))? "Không có" : this.maKhachHang;

		String[] array = {this.maHoaDon, this.ngayNhapThongTin, Util.convertMoneyToString(this.tongTien), this.maNhanVien, maKhachHangString};
		return array;
	}





}
