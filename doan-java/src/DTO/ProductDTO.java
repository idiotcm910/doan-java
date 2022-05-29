package DTO;

import java.util.ArrayList;
import common.Util;

public class ProductDTO extends BaseDTO{
	private String maSanPham;
	private String tenSanPham;
	private Integer soLuong;
	private Integer donGiaSanPham;
	private String loaiSanPham;
	private String ngaySanXuat;
	private String hanSuDung;
	private String maNhaSanXuat;
	private String imageUrl;

	public ProductDTO() {
		this.maSanPham = "";
		this.tenSanPham = "";
		this.soLuong = 0;
		this.donGiaSanPham = 0;
		this.loaiSanPham = "";
		this.ngaySanXuat = "";
		this.hanSuDung = "";
		this.maSanPham = "";
		this.imageUrl = "";
	}

	public ProductDTO(String maSanPham, String tenSanPham, Integer soLuong, Integer donGiaSanPham, String loaiSanPham, String ngaySanXuat, String hanSuDung, String maNhaSanXuat, String imageUrl) {
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.soLuong = soLuong;
		this.donGiaSanPham = donGiaSanPham;
		this.loaiSanPham = loaiSanPham;
		this.ngaySanXuat = ngaySanXuat;
		this.hanSuDung = hanSuDung;
		this.maNhaSanXuat = maNhaSanXuat;
		this.imageUrl = imageUrl;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}

	public Integer getSoLuong() {
		return soLuong;
	}

	public void setDonGiaSanPham(Integer donGiaSanPham) {
		this.donGiaSanPham = donGiaSanPham;
	}

	public Integer getDonGiaSanPham() {
		return donGiaSanPham;
	}

	public void setLoaiSanPham(String loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

	public String getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setNgaySanXuat(String ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}

	public String getNgaySanXuat() {
		return ngaySanXuat;
	}

	public void setHanSuDung(String hanSuDung) {
		this.hanSuDung = hanSuDung;
	}

	public String getHanSuDung() {
		return hanSuDung;
	}

	public void setMaNhaSanXuat(String maNhaSanXuat) {
		this.maNhaSanXuat = maNhaSanXuat;
	}

	public String getMaNhaSanXuat() {
		return maNhaSanXuat;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public String[] toArrayString() {
		return new String[] {this.maSanPham, this.tenSanPham, this.soLuong.toString(), Util.convertMoneyToString(this.donGiaSanPham), this.loaiSanPham, this.ngaySanXuat, this.hanSuDung, this.maNhaSanXuat}; 
	}

	/** ko kiem tra  */
	public boolean equals(ProductDTO product) {
		if(!this.maSanPham.equals(product.getMaSanPham())) {
			return false;
		}

		if(!this.tenSanPham.equals(product.getTenSanPham())) {
			return false;
		}

		if(this.soLuong != product.getSoLuong()) {
			return false;
		}

		if(this.donGiaSanPham != product.getDonGiaSanPham()) {
			return false;
		}

		if(!this.loaiSanPham.equals(product.getLoaiSanPham())) {
			return false;
		}

		if(!this.ngaySanXuat.equals(product.getNgaySanXuat())) {
			return false;
		}

		if(!this.hanSuDung.equals(product.getHanSuDung())) {
			return false;
		}

		if(!this.maNhaSanXuat.equals(product.getMaNhaSanXuat())) {
			return false;
		}

		if(!this.imageUrl.equals(product.getImageUrl())) {
			return false;
		}

		return true;
	}

	public ProductDTO clone() {
		ProductDTO product = new ProductDTO();
		product.setMaSanPham(this.maSanPham);
		product.setTenSanPham(this.tenSanPham);
		product.setSoLuong(this.soLuong);
		product.setDonGiaSanPham(this.donGiaSanPham);
		product.setLoaiSanPham(this.loaiSanPham);
		product.setNgaySanXuat(this.ngaySanXuat);
		product.setHanSuDung(this.hanSuDung);
		product.setMaNhaSanXuat(this.maNhaSanXuat);
		product.setImageUrl(this.imageUrl);
		return product;
	}
}
