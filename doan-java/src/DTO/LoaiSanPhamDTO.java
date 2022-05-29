package DTO;

public class LoaiSanPhamDTO extends BaseDTO {
	private String maLoai;
	private String ten;

	public LoaiSanPhamDTO() {
		maLoai = "";
		ten = "";
	}

	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}

	public String getMaLoai() {
		return maLoai;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}

	@Override
	public String[] toArrayString() {
		return new String[] {maLoai, ten};
	}
}
