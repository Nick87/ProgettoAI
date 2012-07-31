package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Log
{
	private int ID_Operazione;
	private String content;
	private Timestamp timestamp;
	private static int numero_righe_per_pagina = 5;
	
	public Log() {}
	
	public static List<Log> getLogContent() throws SQLException, DBException, InvalidOperationException
	{
		ArrayList<Log> lista = new ArrayList<Log>();
		//Seleziona tutti i membri partecipanti allo specifico ordine, tranne naturalmente chi vuole delegare
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM log";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			Log l;
			while(rs.next()) {
				l = new Log();
				l.setID_Operazione(rs.getInt("ID_Operazione"));
				l.setContent(rs.getString("content"));
				l.setTimestamp(rs.getTimestamp("timestamp"));
				lista.add(l);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return lista;
	}
	
	public static List<Log> getLogContentforPage(int num_pagina) throws InvalidOperationException, DBException, SQLException
	{				
		ArrayList<Log> lista = new ArrayList<Log>();
		//Seleziona tutti i membri partecipanti allo specifico ordine, tranne naturalmente chi vuole delegare
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();			
			String query = "SELECT * FROM log ORDER BY timestamp DESC LIMIT " + num_pagina*numero_righe_per_pagina + ", " + numero_righe_per_pagina;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			Log l;
			while(rs.next()) {
				l = new Log();
				l.setID_Operazione(rs.getInt("ID_Operazione"));
				l.setContent(rs.getString("content"));
				l.setTimestamp(rs.getTimestamp("timestamp"));
				lista.add(l);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return lista;
	}
	
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

	public int getID_Operazione() {
		return ID_Operazione;
	}
	public void setID_Operazione(int iD_Operazione) {
		ID_Operazione = iD_Operazione;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public static int getNumero_righe_per_pagina() {
		return numero_righe_per_pagina;
	}

	public static void setNumero_righe_per_pagina(int numero_righe_per_pagina) {
		Log.numero_righe_per_pagina = numero_righe_per_pagina;
	}

	
}
