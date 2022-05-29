package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import components.ReadSettings;

public class AppConnection {
	private static AppConnection instance;

	private String hostName;
	private String portNumber;
	private String databaseName;
	private String userName;
	private String password;

	public static AppConnection getInstance() {
		if(instance == null) {
			instance = new AppConnection();
		}
		
		return instance;
	}
	
	private AppConnection() {
		getInfoFromFileSettings();
	}

	private void getInfoFromFileSettings() {
		ReadSettings read = ReadSettings.getInstance();

		this.hostName = read.getConfig("hostName");
		this.databaseName = read.getConfig("databaseName");
		this.portNumber = read.getConfig("portNumber");
		this.userName = read.getConfig("userName");
		this.password = read.getConfig("password");
	}

	public Connection getConnection() {
		Connection conn = null;

		String format = "jdbc:sqlserver://%s:%s;database=%s;user=%s;password=%s;";
		String connectUrl = String.format(format, hostName, portNumber, databaseName, userName, password);
		try {
			conn = DriverManager.getConnection(connectUrl);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}

		return conn;
	}

	public ResultSet getAllValueInTable(String tableName) {
		ResultSet result = null;
		String sqlQuery = String.format("select * from %s", tableName);

		try {
			Connection conn = this.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sqlQuery);
			result = pStatement.executeQuery();	
			conn.close();
			pStatement.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}

		return result;
	}

}
