package DTO;

public class NhaSanXuatDTO extends BaseDTO {
	private String maNhaSanXuat;
	private String ten;
	private String diaChi;

	public NhaSanXuatDTO() {
		maNhaSanXuat = "";
		ten = "";
		diaChi = "";
	}

	public void setMaNhaSanXuat(String maNhaSanXuat) {
		this.maNhaSanXuat = maNhaSanXuat;
	}

	public String getMaNhaSanXuat() {
		return maNhaSanXuat;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getDiaChi() {
		return diaChi;
	}

	@Override
	public String[] toArrayString() {
		return new String[] {maNhaSanXuat, ten, diaChi};
	}
}
