package DAL;

import common.AppConnection;
import DTO.KhuyenMaiDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class KhuyenMaiDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public KhuyenMaiDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<KhuyenMaiDTO> getAllKhuyenMai() {
		ArrayList<KhuyenMaiDTO> result = new ArrayList<KhuyenMaiDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from khuyenmai";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				KhuyenMaiDTO km = new KhuyenMaiDTO();
				km.setMaKhuyenMai(resultSet.getString(1));
				km.setTenKhuyenMai(resultSet.getString(2));
				km.setNgayBatDau(resultSet.getString(3));
				km.setNgayKetThuc(resultSet.getString(4));
				result.add(km);
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

	public boolean insertKhuyenMai(KhuyenMaiDTO km) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "insert into khuyenmai values (?, ?, ?, ?)"; 

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, km.getMaKhuyenMai());
			preparedStatement.setString(2, km.getTenKhuyenMai());
			preparedStatement.setString(3, km.getNgayBatDau());
			preparedStatement.setString(4, km.getNgayKetThuc());

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
