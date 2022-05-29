package DTO;


public class NhaCungCapDTO extends BaseDTO {
	private String maNhaCungCap;
	private String ten;
	private String diaChi;
		
	public NhaCungCapDTO() {
		maNhaCungCap = "";
		ten = "";
		diaChi = "";
	}

	public void setMaNhaCungCap(String maNhaCungCap) {
		this.maNhaCungCap = maNhaCungCap;
	}

	public String getMaNhaCungCap() {
		return maNhaCungCap;
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
		return new String[] {maNhaCungCap, ten, diaChi};
	}
}
