package DAL;


import common.AppConnection;
import DTO.NhaSanXuatDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class NhaSanXuatDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public NhaSanXuatDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<NhaSanXuatDTO> getAllNhaSanXuat() {
		ArrayList<NhaSanXuatDTO> result = new ArrayList<NhaSanXuatDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from NHASANXUAT";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				NhaSanXuatDTO nsx = new NhaSanXuatDTO();
				nsx.setMaNhaSanXuat(resultSet.getString(1));
				nsx.setTen(resultSet.getString(2));
				nsx.setDiaChi(resultSet.getString(3));
				result.add(nsx);
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
}
