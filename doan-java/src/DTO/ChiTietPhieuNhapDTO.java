package DTO;

import common.Util;

public class ChiTietPhieuNhapDTO extends BaseDTO {
	private String maPhieu;
	private String maSanPham;
	private int soLuong;
	private int donGia;
	private int thanhTien;
		
	public ChiTietPhieuNhapDTO() {
		maPhieu = "";
		maSanPham = "";
		soLuong = 0;
		donGia = 0;
		thanhTien = 0;
	}

	public ChiTietPhieuNhapDTO(String maPhieu, String maSanPham, int soLuong, int donGia, int thanhTien) {
		this.maPhieu = maPhieu;
		this.maSanPham = maSanPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}

	public int getDonGia() {
		return donGia;
	}

	public void setThanhTien(int thanhTien) {
		this.thanhTien = thanhTien;
	}

	public int getThanhTien() {
		return thanhTien;
	}

	public boolean equals(ChiTietPhieuNhapDTO ctpn) {
		if(maPhieu.equals(ctpn.getMaPhieu())) {
			return false;
		}

		if(maSanPham.equals(ctpn.getMaSanPham())) {
			return false;
		}

		if(donGia != ctpn.getDonGia()) {
			return false;
		}

		if(thanhTien != ctpn.getThanhTien()) {
			return false;
		}

		return true;
	}

	public ChiTietPhieuNhapDTO clone() {
		return new ChiTietPhieuNhapDTO(maPhieu, maSanPham, soLuong, donGia, thanhTien);
	}

	@Override
	public String[] toArrayString() {
		return new String[] {maPhieu, maSanPham, Integer.toString(soLuong), Util.convertMoneyToString(donGia), Util.convertMoneyToString(thanhTien)};
	}
}

