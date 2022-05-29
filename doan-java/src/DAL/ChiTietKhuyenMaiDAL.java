package DAL;

import common.AppConnection;
import DTO.ChiTietKhuyenMaiDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ChiTietKhuyenMaiDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public ChiTietKhuyenMaiDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<ChiTietKhuyenMaiDTO> getAllChiTietKhuyenMai() {
		ArrayList<ChiTietKhuyenMaiDTO> result = new ArrayList<ChiTietKhuyenMaiDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from chitietkhuyenmai";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ChiTietKhuyenMaiDTO ctkm = new ChiTietKhuyenMaiDTO();
				ctkm.setMaKhuyenMai(resultSet.getString(1));
				ctkm.setPhanTramKhuyenMai(resultSet.getInt(2));
				ctkm.setMaSanPham(resultSet.getString(3));
				result.add(ctkm);
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

	public boolean insertChiTietKhuyenMai(ChiTietKhuyenMaiDTO ctkm) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "insert into chitietkhuyenmai values (?, ?, ?)"; 

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, ctkm.getMaKhuyenMai());
			preparedStatement.setInt(2, ctkm.getPhanTramKhuyenMai());
			preparedStatement.setString(3, ctkm.getMaSanPham());

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
