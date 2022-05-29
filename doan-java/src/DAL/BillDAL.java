package DAL;

import common.AppConnection;
import DTO.HoaDonDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class BillDAL {
	private AppConnection appConnection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public BillDAL() {
		appConnection = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<HoaDonDTO> getAllBill() {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();
		Connection conn = appConnection.getConnection();
		String sqlQuery = "select * from HoaDon";
		
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				HoaDonDTO hoaDonDTO = new HoaDonDTO();
				hoaDonDTO.setMaHoaDon(resultSet.getString(1));
				hoaDonDTO.setNgayNhapThongTin(resultSet.getString(2));
				hoaDonDTO.setTongTien(resultSet.getInt(3));
				hoaDonDTO.setMaNhanVien(resultSet.getString(4));

				String maKhachHang = resultSet.getString(5);
				if(resultSet.wasNull()) {
					hoaDonDTO.setMaKhachHang("NULL");
				}
				else {
					hoaDonDTO.setMaKhachHang(maKhachHang);
				}

				result.add(hoaDonDTO);
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

	public void InsertBill(HoaDonDTO hoaDon) {
		String sqlQuery = "insert into HoaDon values (?, ?, ?, ?, ?)";
		Connection conn = appConnection.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			preparedStatement.setString(1, hoaDon.getMaHoaDon());
			preparedStatement.setString(2, hoaDon.getNgayNhapThongTin());
			preparedStatement.setInt(3, hoaDon.getTongTien());
			preparedStatement.setString(4, hoaDon.getMaNhanVien());

			if(hoaDon.getMaKhachHang().equals("NULL")) {
				preparedStatement.setString(5, null);
			}
			else {
				preparedStatement.setString(5, hoaDon.getMaKhachHang());
			}
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
