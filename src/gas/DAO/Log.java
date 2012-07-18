package gas.DAO;

import gas.Exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Log
{
	public static void addLog(String content)
	{
		Connection conn = null;
		try {
			conn = DBConnection.getDBConnection();
			String query = "INSERT INTO `log`(`content`) VALUES (?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, content);
			if(ps.executeUpdate() != 1)
				System.out.println("Errore scrittura log");
		} catch (DBException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
}
