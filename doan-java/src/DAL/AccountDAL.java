package DAL;

import common.AppConnection;
import DTO.AccountDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class AccountDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public AccountDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<AccountDTO> getAllAccount() {
		ArrayList<AccountDTO> result = new ArrayList<AccountDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from Taikhoan";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				AccountDTO acc = new AccountDTO();
				acc.setMaTaiKhoan(resultSet.getString(1));
				acc.setTenTaiKhoan(resultSet.getString(2));
				acc.setMatKhau(resultSet.getString(3));
				acc.setEmail(resultSet.getString(4));
				acc.setSoDienThoai(resultSet.getString(5));
				acc.setMaNhanVien(resultSet.getString(6));
				result.add(acc);
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				conn.close();
				preparedStatement.close();
				resultSet.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public boolean updatePassword(String maTaiKhoan, String matKhau) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "update taikhoan set matkhau = ? where mataikhoan=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, matKhau);
			preparedStatement.setString(2, maTaiKhoan);

			preparedStatement.executeUpdate();

			isSuccess = true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				conn.close();
				preparedStatement.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}

		return isSuccess;
	}

	public boolean insertAccount(AccountDTO account) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "insert into taikhoan values (?, ?, ?, ?, ?, ?)";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, account.getMaTaiKhoan());
			preparedStatement.setString(2, account.getTenTaiKhoan());
			preparedStatement.setString(3, account.getMatKhau());
			preparedStatement.setString(4, account.getEmail());
			preparedStatement.setString(5, account.getSoDienThoai());
			preparedStatement.setString(6, account.getMaNhanVien());

			preparedStatement.executeUpdate();

			isSuccess = true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				conn.close();
				preparedStatement.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}

		return isSuccess;
	}

	public boolean deleteAccount(String maTaiKhoan) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "delete from taikhoan where mataikhoan=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);
			
			preparedStatement.setString(1, maTaiKhoan);

			preparedStatement.executeUpdate();

			isSuccess = true;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				conn.close();
				preparedStatement.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}

		return isSuccess;
	}
}
