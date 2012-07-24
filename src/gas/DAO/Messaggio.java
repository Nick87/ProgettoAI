package gas.DAO;

import gas.Exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Messaggio
{	
	private int ID_Messaggio;
	private int ID_Membro;
	private Date data;
	private String testo;
	private boolean letto;
	private boolean confermato;
		
	public Messaggio() {}
	
	public static void inserisciMessaggio(int id_destinatario, int idordinescelto, String testo) throws DBException, SQLException
	{
		Connection conn = null;
		String query = "";
		PreparedStatement ps;
		try
		{
			conn = DBConnection.getDBConnection();
			conn.setAutoCommit(false);
			java.util.Date now = new java.util.Date();
	    	java.sql.Date date = new java.sql.Date(now.getTime());
			query = "INSERT INTO messaggio(ID_Membro, data, testo, letto, confermato) VALUES(?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id_destinatario);
			ps.setDate(2, date);
			ps.setString(3, testo);
			ps.setInt(4, 0);
			ps.setInt(5, 0);
			ps.executeUpdate();
			// Adesso possiamo inserire la nuova scheda di acquisto
			conn.commit();
			conn.setAutoCommit(true);
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	public int getID_Messaggio() {
		return ID_Messaggio;
	}
	public void setID_Messaggio(int iD_Messaggio) {
		ID_Messaggio = iD_Messaggio;
	}
	public int getID_Membro() {
		return ID_Membro;
	}
	public void setID_Membro(int iD_Membro) {
		ID_Membro = iD_Membro;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public boolean isLetto() {
		return letto;
	}
	public void setLetto(boolean letto) {
		this.letto = letto;
	}
	public boolean isConfermato() {
		return confermato;
	}
	public void setConfermato(boolean confermato) {
		this.confermato = confermato;
	}
}
