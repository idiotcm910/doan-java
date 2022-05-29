package DAL;

import common.AppConnection;
import DTO.BillDetailsDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class BillDetailsDAL {
	private AppConnection appConnection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public BillDetailsDAL() {
		appConnection = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<BillDetailsDTO> getAllBillDetails() {
		ArrayList<BillDetailsDTO> result = new ArrayList<BillDetailsDTO>();
		Connection conn = appConnection.getConnection();
		String sqlQuery = "select * from ChiTietHoaDon";
		
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				BillDetailsDTO billDetails = new BillDetailsDTO();
				billDetails.setMaHoaDon(resultSet.getString(1));
				billDetails.setMaSanPham(resultSet.getString(2));

				String maKhuyenMai = resultSet.getString(3);
				if(resultSet.wasNull()) {
					billDetails.setMaKhuyenMai("NULL");
				}
				else {
					billDetails.setMaKhuyenMai(maKhuyenMai);
				}

				billDetails.setSoLuong(resultSet.getInt(4));
				billDetails.setDonGia(resultSet.getInt(5));
				billDetails.setThanhTien(resultSet.getInt(6));

				result.add(billDetails);
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

	public void insertBillDetails(BillDetailsDTO chiTietHoaDon) {
		String sqlQuery = "insert into CHITIETHOADON values (?, ?, ?, ?, ?, ?)";
		Connection conn = appConnection.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			preparedStatement.setString(1, chiTietHoaDon.getMaHoaDon());
			preparedStatement.setString(2, chiTietHoaDon.getMaSanPham());
			
			if(chiTietHoaDon.getMaKhuyenMai().equals("NULL")) {
				preparedStatement.setString(3, null);
			}
			else {
				preparedStatement.setString(3, chiTietHoaDon.getMaKhuyenMai());
			}

			preparedStatement.setInt(4, chiTietHoaDon.getSoLuong());
			preparedStatement.setInt(5, chiTietHoaDon.getDonGia());
			preparedStatement.setInt(6, chiTietHoaDon.getThanhTien());
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
