package DAL;

import common.AppConnection;
import DTO.CustomerDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class CustomerDAL {
	private AppConnection appConnection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public CustomerDAL() {
		appConnection = AppConnection.getInstance();
		preparedStatement = null;
		resultSet = null;
	}


	private ArrayList<CustomerDTO> getCustomerWithinQuery(String query) {
		ArrayList<CustomerDTO> result = new ArrayList<CustomerDTO>();
		Connection conn = appConnection.getConnection();

		try {
			preparedStatement = conn.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setMaKhachHang(resultSet.getString(1));
				customerDTO.setHo(resultSet.getString(2));
				customerDTO.setTen(resultSet.getString(3));
				customerDTO.setHang(resultSet.getString(4));
				result.add(customerDTO);
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

	public ArrayList<CustomerDTO> getAllCustomer() {
		String sqlQuery = "select * from KHACHHANG";
		return getCustomerWithinQuery(sqlQuery);
	}

	public ArrayList<CustomerDTO> getCustomerExactlyCondition(String columnName, String condition) {
		String sqlQuery = "select * from KHACHHANG where " + columnName + " = " + "'" + condition + "'";
		return getCustomerWithinQuery(sqlQuery);
	}

	public ArrayList<CustomerDTO> getCustomerContainCondition(String columnName, String condition) {

		String sqlQuery = "select * from KHACHHANG where " + columnName + " like " + "N'%" + condition + "%'";
		return getCustomerWithinQuery(sqlQuery);
	}
}
