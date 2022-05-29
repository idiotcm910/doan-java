package DAL;


import common.AppConnection;
import DTO.NhanVienDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class NhanVienDAL {
	private AppConnection ketNoi;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public NhanVienDAL() {
		ketNoi = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<NhanVienDTO> getAllNhanVien() {
		ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
		Connection conn = ketNoi.getConnection();
		String sqlQuery = "select * from nhanvien";
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				NhanVienDTO nhanVien = new NhanVienDTO();
				nhanVien.setMaNhanVien(resultSet.getString(1));
				nhanVien.setTenNhanVien(resultSet.getString(2));
				nhanVien.setNgayVaoLam(resultSet.getString(3));
				nhanVien.setViTri(resultSet.getString(4));
				nhanVien.setLuong(resultSet.getInt(5));
				result.add(nhanVien);
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

	public boolean deleteNhanVien(String maNhanVien) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "delete from nhanvien where manhanvien=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, maNhanVien);

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

	public boolean insertOneNhanVien(NhanVienDTO productData) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "insert into nhanvien values (?, ?, ?, ?, ?)";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, productData.getMaNhanVien());
			preparedStatement.setString(2, productData.getTenNhanVien());
			preparedStatement.setString(3, productData.getNgayVaoLam());
			preparedStatement.setString(4, productData.getViTri());
			preparedStatement.setInt(5, productData.getLuong());

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

	public boolean updateNhanVien(String maNhanVien, NhanVienDTO nhanVien) {
		boolean isSuccess = false;
		Connection conn = ketNoi.getConnection();
		String sqlString = "update nhanVien set tenNhanVien = ?, ngayvaolam = ?, vitri = ?, luong = ? where manhanvien=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, nhanVien.getTenNhanVien());
			preparedStatement.setString(2, nhanVien.getNgayVaoLam());
			preparedStatement.setString(3, nhanVien.getViTri());
			preparedStatement.setInt(4, nhanVien.getLuong());
			preparedStatement.setString(5, maNhanVien);

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
