package BLL;

import DAL.AccountDAL;
import DTO.AccountDTO;
import java.util.ArrayList;

public class AccountBLL {
	private static AccountBLL instance = null;
	private AccountDAL accDAL;
	private ArrayList<AccountDTO> accounts;

	private AccountBLL() {
		this.accDAL = new AccountDAL();
		accounts = accDAL.getAllAccount();
	}
	public static AccountBLL getInstance() {
		if(instance == null) {
			instance = new AccountBLL();
		}
		return instance;
	}

	public ArrayList<AccountDTO> getAllAccounts() {
		return accounts;
	}

	public AccountDTO getOneAccountWithMaNhanVien(String maNhanVien) {
		AccountDTO result = null;

		for(AccountDTO acc : accounts) {
			if(acc.getMaNhanVien().equals(maNhanVien)) {
				result = acc;
			}
		}

		return result;
	}

	public boolean ExistsAccount(String maNhanVien) {
		boolean isExist = false;

		for(AccountDTO acc : accounts) {
			if(acc.getMaNhanVien().equals(maNhanVien)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	public String kiemTraDangNhap(String username, String password) {
		String result = "";

		for(AccountDTO acc : accounts) {
			if(acc.getTenTaiKhoan().equals(username) && acc.getMatKhau().equals(password)) {
				result = acc.getMaNhanVien();
				break;
			}
		}

		return result;
	}

	public void capNhatMatKhau(String maTaiKhoan, String matKhau) {
		for(AccountDTO acc : accounts) {
			if(acc.getMaTaiKhoan().trim().equals(maTaiKhoan.trim())) {
				acc.setMatKhau(matKhau);
				accDAL.updatePassword(acc.getMaTaiKhoan(), acc.getMatKhau());
				break;
			}
		}	
	}

	public void addAcount(AccountDTO acc) {
		AccountDTO newAcc = acc.clone();
		accounts.add(newAcc);
		accDAL.insertAccount(newAcc);
	}

	public void deleteAccount(String maTaiKhoan) {
		for(int i = 0; i < accounts.size(); ++i) {
			AccountDTO account = accounts.get(i);
			if(account.getMaTaiKhoan().equals(maTaiKhoan)) {
				String maNhanVien = account.getMaNhanVien();
				accounts.remove(i);	
				accDAL.deleteAccount(maTaiKhoan);
				NhanVienBLL.getInstance().deleteNhanVien(maNhanVien);
				break;
			}
		}	
	}
}
