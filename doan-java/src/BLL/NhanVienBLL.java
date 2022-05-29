package BLL;

import DAL.NhanVienDAL;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBLL {
	private static NhanVienBLL instance = null;
	private NhanVienDAL nhanVienDAL;
	private ArrayList<NhanVienDTO> nhanViens;

	private NhanVienBLL() {
		this.nhanVienDAL = new NhanVienDAL();
		nhanViens = nhanVienDAL.getAllNhanVien();
	}
	public static NhanVienBLL getInstance() {
		if(instance == null) {
			instance = new NhanVienBLL();
		}
		return instance;
	}

	public ArrayList<NhanVienDTO> getAllNhanVien() {
		return nhanViens;
	}

	public NhanVienDTO getOneNhanVien(String maNhanVien) {
		NhanVienDTO result = null;

		for(NhanVienDTO nhanVien : nhanViens) {
			if(nhanVien.getMaNhanVien().equals(maNhanVien)) {
				result = nhanVien;
				break;
			}
		}

		return result;
	}

	public boolean ExistsNhanVien(String maNhanVien) {
		boolean isExists = false;	

		for(NhanVienDTO nhanVien : nhanViens) {
			if(nhanVien.getMaNhanVien().equals(maNhanVien)) {
				isExists = true;
				break;
			}
		}

		return isExists;
	}

	public boolean addNhanVien(NhanVienDTO nhanVien) {
		boolean isSuccess = false;

		if(nhanVien != null) {
			NhanVienDTO data = nhanVien.clone();
			nhanViens.add(data);
			isSuccess = nhanVienDAL.insertOneNhanVien(data);
		}	

		return isSuccess;
	}

	public boolean updateNhanVien(NhanVienDTO data) {
		boolean isSuccess = false;

		for(int i = 0; i < nhanViens.size(); ++i) {
			if(nhanViens.get(i).getMaNhanVien().equals(data.getMaNhanVien())) {
				NhanVienDTO nhanVienMoi = data.clone();
				// cap nhat lai tren bll
				nhanViens.set(i, nhanVienMoi);
				nhanVienDAL.updateNhanVien(nhanVienMoi.getMaNhanVien(), nhanVienMoi);
				isSuccess = true;
				break;
			}
		}

		return isSuccess;
	}

	public boolean deleteNhanVien(String maNhanVien) {
		boolean isSuccess = false;

		for(int i = 0; i < nhanViens.size(); ++i) {
			if(nhanViens.get(i).getMaNhanVien().equals(maNhanVien)) {
				nhanViens.remove(i);
				nhanVienDAL.deleteNhanVien(maNhanVien);
				// xoa luon tai khoan
				AccountBLL accountBLL = AccountBLL.getInstance();
				accountBLL.deleteAccount(accountBLL.getOneAccountWithMaNhanVien(maNhanVien).getMaTaiKhoan());
				isSuccess = true;
				break;
			}
		}
		return isSuccess;
	}

	public ArrayList<NhanVienDTO> getAllNhanVienNotAccount() {
		ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
		AccountBLL accBLL = AccountBLL.getInstance();

		for(NhanVienDTO nhanVien : nhanViens) {
			if(accBLL.ExistsAccount(nhanVien.getMaNhanVien()) == false) {
				result.add(nhanVien);
			}
		}

		return result;
	}
}
