package gas.DAO;

import gas.Exception.DBException;

import java.sql.*;

public class DBConnection
{
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost/gas";
	private static final String DBUser = "root";
	private static final String DBPass = ""; 

	public static Connection getDBConnection() throws DBException
	{
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, DBUser, DBPass);
	    } catch (Exception ex) {
	    	throw new DBException(ex.getMessage());
    	}
		return conn;
	}
	
	public static void closeConnection(Connection c)
	{
		try {
			if(c != null) c.close();
		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}