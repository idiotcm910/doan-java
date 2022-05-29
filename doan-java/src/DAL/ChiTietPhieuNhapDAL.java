package DAL;

import common.AppConnection;
import DTO.ChiTietPhieuNhapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAL {
	private AppConnection appConnection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public ChiTietPhieuNhapDAL() {
		appConnection = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() {
		ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<ChiTietPhieuNhapDTO>();
		Connection conn = appConnection.getConnection();
		String sqlQuery = "select * from CHITIETPHIEUNHAPHANG";
		
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ChiTietPhieuNhapDTO phieuNhapDTO = new ChiTietPhieuNhapDTO();
				phieuNhapDTO.setMaPhieu(resultSet.getString(1));
				phieuNhapDTO.setMaSanPham(resultSet.getString(2));
				phieuNhapDTO.setSoLuong(resultSet.getInt(3));
				phieuNhapDTO.setDonGia(resultSet.getInt(4));
				phieuNhapDTO.setThanhTien(resultSet.getInt(5));
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

	public boolean insertChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) {
		boolean isSuccess = false;
		Connection conn = appConnection.getConnection();
		String sqlString = "insert into CHITIETPHIEUNHAPHANG values (?, ?, ?, ?, ?)"; 

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, ctpn.getMaPhieu());
			preparedStatement.setString(2, ctpn.getMaSanPham());
			preparedStatement.setInt(3, ctpn.getSoLuong());
			preparedStatement.setInt(4, ctpn.getDonGia());
			preparedStatement.setInt(5, ctpn.getThanhTien());

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
