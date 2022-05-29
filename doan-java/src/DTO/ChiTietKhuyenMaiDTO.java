package DTO;


public class ChiTietKhuyenMaiDTO extends BaseDTO {
	private String maKhuyenMai;
	private int phanTramKhuyenMai;
	private String maSanPham;

	public ChiTietKhuyenMaiDTO() {
		maKhuyenMai = "";
		phanTramKhuyenMai = 0;
		maSanPham = "";
	}

	public ChiTietKhuyenMaiDTO(String maKm, int phanTramKM, String maSP) {
		maKhuyenMai = maKm;
		phanTramKhuyenMai = phanTramKM;
		maSanPham = maSP;
	}

	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}

	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}

	public void setPhanTramKhuyenMai(int phanTramKhuyenMai) {
		this.phanTramKhuyenMai = phanTramKhuyenMai;
	}

	public int getPhanTramKhuyenMai() {
		return phanTramKhuyenMai;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	@Override 
	public String[] toArrayString() {
		return new String[] {maKhuyenMai, phanTramKhuyenMai + "%", maSanPham};
	}

	public ChiTietKhuyenMaiDTO clone() {
		return new ChiTietKhuyenMaiDTO(maKhuyenMai, phanTramKhuyenMai, maSanPham);
	}
}
