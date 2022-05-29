package DAL;


import common.AppConnection;
import DTO.NhaCungCapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class NhaCungCapDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public NhaCungCapDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<NhaCungCapDTO> getAllNhaCungCap() {
		ArrayList<NhaCungCapDTO> result = new ArrayList<NhaCungCapDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from NHACUNGCAP";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				NhaCungCapDTO nsx = new NhaCungCapDTO();
				nsx.setMaNhaCungCap(resultSet.getString(1));
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
