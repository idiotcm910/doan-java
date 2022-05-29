package DAL;


import common.AppConnection;
import DTO.LoaiSanPhamDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class LoaiSanPhamDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public LoaiSanPhamDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<LoaiSanPhamDTO> getAllLoaiSanPham() {
		ArrayList<LoaiSanPhamDTO> result = new ArrayList<LoaiSanPhamDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from LOAISANPHAM";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				LoaiSanPhamDTO loaiSP = new LoaiSanPhamDTO();
				loaiSP.setMaLoai(resultSet.getString(1));
				loaiSP.setTen(resultSet.getString(2));
				result.add(loaiSP);
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
