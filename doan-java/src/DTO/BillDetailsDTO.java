package DTO;
import common.Util;

public class BillDetailsDTO {
	private String maHoaDon;
	private String maSanPham;
	private String maKhuyenMai;
	private int soLuong;
	private int donGia;
	private int thanhTien;

	public BillDetailsDTO(String maHD, String maSP, String maKM, int sl, int donGia, int ThanhTien) {
		this.maHoaDon = maHD;
		this.maSanPham = maSP;
		this.maKhuyenMai = maKM;
		this.soLuong = sl;
		this.donGia = donGia;
		this.thanhTien = ThanhTien;
	}

	public BillDetailsDTO() {
		this.maHoaDon = "";
		this.maSanPham = "";
		this.maKhuyenMai = "";
		this.soLuong = 0;
		this.donGia = 0;
		this.thanhTien = 0;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}

	public String getMaKhuyenMai() {
		return maKhuyenMai;
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

	public boolean equals(BillDetailsDTO another) {
		if(!maHoaDon.equals(another.getMaHoaDon())) {
			return false;
		}

		if(!maSanPham.equals(another.getMaSanPham())) {
			return false;
		}

		if(!maKhuyenMai.equals(another.getMaKhuyenMai())) {
			return false;
		}

		if(soLuong != another.getSoLuong()) {
			return false;	
		}

		if(donGia != another.getDonGia()) {
			return false;
		}

		if(thanhTien != another.getThanhTien()) {
			return false;
		}

		return true;
	}

	public BillDetailsDTO clone() {
		return new BillDetailsDTO(maHoaDon, maSanPham, maKhuyenMai, soLuong, donGia, thanhTien);
	}

	public String[] toArray() {
		String maKhuyenMaiString = (this.maKhuyenMai.equals("NULL"))? "Không có" : this.maKhuyenMai;
		return new String[] {this.maHoaDon, this.maSanPham, maKhuyenMaiString, Integer.toString(this.soLuong), Util.convertMoneyToString(this.donGia), Util.convertMoneyToString(this.thanhTien)};
	}
}
