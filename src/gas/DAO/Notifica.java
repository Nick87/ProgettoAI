package gas.DAO;

import gas.Exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Notifica
{
	public static void addNotifica(String content, int idMembroDestinatario)
	{
		Connection conn = null;
		try {
			conn = DBConnection.getDBConnection();
			String query = "INSERT INTO `notifica`(`ID_Membro`, `data`, `testo`, `letta`) VALUES (?, ?, ?, 0)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembroDestinatario);
			java.util.Date now = new java.util.Date();
	    	java.sql.Date date = new java.sql.Date(now.getTime());
			ps.setDate(2, date);
	    	ps.setString(3, content);
			if(ps.executeUpdate() != 1)
				System.out.println("Errore scrittura notifica");
		} catch (DBException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
}
