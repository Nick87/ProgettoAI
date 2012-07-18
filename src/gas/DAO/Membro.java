package gas.DAO;

import gas.Exception.*;

import java.sql.*;

import util.LoginStatus;

public class Membro
{
	public Membro() {}
	
	public static LoginStatus checkPassword(String username, String password) throws DBException, LoginException
	{
		Connection conn = DBConnection.getDBConnection();
		LoginStatus status = null;
		int ID_Membro;
		String tipo_membro;
		String query = "SELECT ID_Membro, tipo_membro, password FROM membro WHERE username = ?;";
		
	    try {
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				throw new LoginException("Username inesistente");
			
			String passwordToCheck = rs.getString("password");
			ID_Membro = rs.getInt("ID_Membro");
			tipo_membro = rs.getString("tipo_membro");
			
			if(passwordToCheck.equals(password))
				status = new LoginStatus(tipo_membro, username, ID_Membro);
			else
				throw new LoginException("Password errata");
	    } catch (SQLException ex) {
	    	throw new DBException(ex.getMessage());
	    } finally {
			DBConnection.closeConnection(conn);
		}
		return status;
	}
}
