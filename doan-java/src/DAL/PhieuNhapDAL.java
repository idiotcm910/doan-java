package DAL;

import common.AppConnection;
import DTO.PhieuNhapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class PhieuNhapDAL {
	private AppConnection appConnection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public PhieuNhapDAL() {
		appConnection = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<PhieuNhapDTO> getAllPhieuNhap() {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();
		Connection conn = appConnection.getConnection();
		String sqlQuery = "select * from PHIEUNHAPHANG";
		
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				PhieuNhapDTO phieuNhapDTO = new PhieuNhapDTO();
				phieuNhapDTO.setMaPhieu(resultSet.getString(1));
				phieuNhapDTO.setMaNhaCungCap(resultSet.getString(2));
				phieuNhapDTO.setMaNhanVien(resultSet.getString(3));
				phieuNhapDTO.setTongTien(resultSet.getInt(4));
				phieuNhapDTO.setNgayNhap(resultSet.getString(5));
				result.add(phieuNhapDTO);
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

	public void InsertPhieuNhap(PhieuNhapDTO pn) {
		String sqlQuery = "insert into PhieuNhapHang values (?, ?, ?, ?, ?)";
		Connection conn = appConnection.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			preparedStatement.setString(1, pn.getMaPhieu());
			preparedStatement.setString(2, pn.getMaNhaCungCap());
			preparedStatement.setString(3, pn.getMaNhanVien());
			preparedStatement.setInt(4, pn.getTongTien());
			preparedStatement.setString(5, pn.getNgayNhap());

			preparedStatement.executeUpdate();
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
	}
}
