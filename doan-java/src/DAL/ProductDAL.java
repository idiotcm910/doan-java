package DAL;

import common.AppConnection;
import DTO.ProductDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ProductDAL {
	private AppConnection appConnection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public ProductDAL() {
		appConnection = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}

	public ArrayList<ProductDTO> getAllProduct() {
		ArrayList<ProductDTO> result = new ArrayList<ProductDTO>();
		Connection conn = appConnection.getConnection();
		String sqlQuery = "select * from SANPHAM";
		
		try {
			preparedStatement = conn.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ProductDTO productDTO = new ProductDTO();
				productDTO.setMaSanPham(resultSet.getString(1));
				productDTO.setTenSanPham(resultSet.getString(2));
				productDTO.setSoLuong(resultSet.getInt(3));
				productDTO.setDonGiaSanPham(resultSet.getInt(4));
				productDTO.setLoaiSanPham(resultSet.getString(5));
				productDTO.setNgaySanXuat(resultSet.getString(6));
				productDTO.setHanSuDung(resultSet.getString(7));
				productDTO.setMaNhaSanXuat(resultSet.getString(8));
				productDTO.setImageUrl(resultSet.getString(9));
				if(resultSet.wasNull()) {
					productDTO.setImageUrl("NULL");
				}

				result.add(productDTO);
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

	public boolean updateProductQuantity(String maSanPham, int quantity) {
		boolean isSuccess = false;
		Connection conn = appConnection.getConnection();
		String sqlString = "update sanpham set soluong = ? where masanpham=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setInt(1, quantity);
			preparedStatement.setString(2, maSanPham.trim());

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

	public void refreshImageUrlProduct(String maSanPham) {
		Connection conn = appConnection.getConnection();
		String sqlString = "update sanpham set imageurl = NULL where maSanPham=?";
		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, maSanPham);

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

	public boolean updateProduct(String maSanPham, ProductDTO productData) {
		boolean isSuccess = false;
		Connection conn = appConnection.getConnection();
		String sqlString = "update sanpham set tensanpham = ?, soluong = ?, dongiasanpham = ?, loaisanpham = ?, ngaysanxuat = ?, hansudung = ?, manhasanxuat = ?, imageurl = ? where masanpham=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, productData.getTenSanPham());
			preparedStatement.setInt(2, productData.getSoLuong());
			preparedStatement.setInt(3, productData.getDonGiaSanPham());
			preparedStatement.setString(4, productData.getLoaiSanPham());
			preparedStatement.setString(5, productData.getNgaySanXuat());
			preparedStatement.setString(6, productData.getHanSuDung());
			preparedStatement.setString(7, productData.getMaNhaSanXuat());
			preparedStatement.setString(8, productData.getImageUrl());
			preparedStatement.setString(9, maSanPham);

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

	public boolean deleteProduct(String maSanPham) {
		boolean isSuccess = false;
		Connection conn = appConnection.getConnection();
		String sqlString = "delete from sanpham where masanpham=?";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, maSanPham);

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

	public boolean insertOneProduct(ProductDTO productData) {
		boolean isSuccess = false;
		Connection conn = appConnection.getConnection();
		String sqlString = "insert into sanpham values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStatement = conn.prepareStatement(sqlString);

			preparedStatement.setString(1, productData.getMaSanPham());
			preparedStatement.setString(2, productData.getTenSanPham());
			preparedStatement.setInt(3, productData.getSoLuong());
			preparedStatement.setInt(4, productData.getDonGiaSanPham());
			preparedStatement.setString(5, productData.getLoaiSanPham());
			preparedStatement.setString(6, productData.getNgaySanXuat());
			preparedStatement.setString(7, productData.getHanSuDung());
			preparedStatement.setString(8, productData.getMaNhaSanXuat());
			preparedStatement.setString(9, productData.getImageUrl());

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
