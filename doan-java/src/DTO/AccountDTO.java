package DTO;

public class AccountDTO extends BaseDTO {
	private String maTaiKhoan;
	private String tenTaiKhoan;
	private String matKhau;
	private String email;
	private String soDienThoai;
	private String maNhanVien;

	public AccountDTO() {
		maTaiKhoan = "";
		tenTaiKhoan = "";
		matKhau = "";
		email = "";
		soDienThoai = "";
		maNhanVien = "";
	}

	public AccountDTO(String matk, String tentk, String mk, String email, String sdt, String manv) {
		maTaiKhoan = matk;
		tenTaiKhoan = tentk;
		matKhau = mk;
		this.email = email;
		soDienThoai = sdt;
		maNhanVien = manv;
	}

	public void setMaTaiKhoan(String maTaiKhoan) {
		this.maTaiKhoan = maTaiKhoan;
	}

	public String getMaTaiKhoan() {
		return maTaiKhoan;
	}

	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	@Override
	public String[] toArrayString() {
		return new String[] {maTaiKhoan, tenTaiKhoan, "*********", email, soDienThoai, maNhanVien};
	}

	public AccountDTO clone() {
		return new AccountDTO(maTaiKhoan, tenTaiKhoan, matKhau, email, soDienThoai, maNhanVien);
	}
}
