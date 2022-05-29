package components;

import DTO.NhanVienDTO;
import BLL.NhanVienBLL;

public class UserToken {
	private static UserToken instance = null;
	private NhanVienDTO nhanVien;
	// phan quyen cho tai khoan duoc phep truy cap nhung menu nao`
	// phan quyen gom 9 menu
	// 0 - trang chu
	// 1 - san pham
	// 2 - thong ke
	// 3 - hoa don
	// 4 - khach hang
	// 5 - tai khoan
	// 6 - nhan su
	// 7 - khuyen mai
	// 8 - chi tiet phieu nhap
	private boolean[] phanQuyen;
	private NhanVienBLL nhanVienBLL;

	public static void khoiTaoUserToken(String maNhanVien) {
		if(instance == null) {
			instance = new UserToken(maNhanVien);	
		}
	}	

	public static UserToken getInstance() {
		return instance;
	}	

	public static void refreshToken() {
		instance = null;
	}

	private UserToken() {
		initVariable();
	}

	private void initVariable() {
		phanQuyen = new boolean[9];
		nhanVienBLL = NhanVienBLL.getInstance();
	}

	private UserToken(String maNhanVien) {
		this();
		nhanVien = nhanVienBLL.getOneNhanVien(maNhanVien);
		phanQuyen();
	}

	private void phanQuyen() {
		switch(nhanVien.getViTri()) {
			case "bán hàng":
				phanQuyen[0] = true;
				phanQuyen[2] = true;
				phanQuyen[3] = true;
				phanQuyen[8] = true;
				break;
			case "quản lí":
				for(int i = 0; i < phanQuyen.length; ++i) {
					if(i != 3) {
						phanQuyen[i] = true;
					}
				}
				break;
		}
	}

	public boolean isPermissed(int indexBoard) {
		return phanQuyen[indexBoard];
	}
	
	public NhanVienDTO getNhanVien() {
		return nhanVien;
	}


}
