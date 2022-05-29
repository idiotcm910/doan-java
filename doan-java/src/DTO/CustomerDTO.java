package DTO;

public class CustomerDTO extends BaseDTO {
	private String maKhachHang;
	private String ten;
	private String ho;
	private String hang;

	public CustomerDTO() {
		maKhachHang = "";
		ten = "";
		ho = "";
		hang = "";
	}

	public CustomerDTO(String maKhachHang, String ten, String ho, String hang) {
		this.maKhachHang = maKhachHang;
		this.ten = ten;
		this.ho = ho;
		this.hang = hang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getHo() {
		return ho;
	}

	public void setHang(String hang) {
		this.hang = hang;
	}

	public String getHang() {
		return hang;
	}

	@Override
	public String[] toArrayString() {
		return new String[] {maKhachHang, ho, ten, hang};
	}
}
