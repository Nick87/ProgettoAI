package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Notifica
{
	private int ID_Notifica;
	private int ID_Membro;
	private Date data;
	private String testo;
	private boolean letto;
	public enum TipoNotifica { ANY, LETTA, NON_LETTA }; 
	
	public static void addNotifica(String content, int idMembroDestinatario) throws DBException, SQLException
	{
		Connection conn = null;
		try
		{
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
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	public static int getNumeroNotificheFromIdMembro(TipoNotifica tipoNotifica, int idMembro) throws DBException, SQLException
	{
		Connection conn = null;
		int n;
		try 
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT COUNT(*) as totale FROM notifica WHERE ID_Membro = ?";
			
			if(tipoNotifica == TipoNotifica.LETTA || tipoNotifica == TipoNotifica.NON_LETTA)
				query += " AND letta = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembro);
			if(tipoNotifica == TipoNotifica.LETTA)
				ps.setInt(2, 1);
			else if(tipoNotifica == TipoNotifica.NON_LETTA)
				ps.setInt(2, 0);
			ResultSet rs = ps.executeQuery();			
			rs.next();
			n = rs.getInt("totale");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return n;
	}

	public static List<Notifica> getListaNotificheFromIdMembro(TipoNotifica tipoNotifica, int idMembro) throws SQLException, DBException
	{
		Connection conn = null;
		List<Notifica> list = new ArrayList<Notifica>();
		try 
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM notifica WHERE ID_Membro = ?";
			
			if(tipoNotifica == TipoNotifica.LETTA || tipoNotifica == TipoNotifica.NON_LETTA)
				query += " AND letta = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembro);
			if(tipoNotifica == TipoNotifica.LETTA)
				ps.setInt(2, 1);
			else if(tipoNotifica == TipoNotifica.NON_LETTA)
				ps.setInt(2, 0);
			ResultSet rs = ps.executeQuery();
			Notifica n;
			while(rs.next())
			{
				n = new Notifica();
				n.setID_Notifica(rs.getInt("ID_Notifica"));
				n.setID_Membro(rs.getInt("ID_Membro"));
				n.setData(rs.getDate("data"));
				n.setTesto(rs.getString("testo"));
				boolean letto = (rs.getInt("letta") == 1) ? true : false;
				n.setLetto(letto);
				list.add(n);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static void setLettaNonLetta(TipoNotifica tipoNotifica, int idNotifica) throws DBException, SQLException, ItemNotFoundException
	{
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "UPDATE notifica SET letta = ? WHERE ID_Notifica = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			if(tipoNotifica == TipoNotifica.LETTA)
				ps.setInt(1, 1);
			else
				ps.setInt(1, 0);
			ps.setInt(2, idNotifica);
			if(ps.executeUpdate() != 1)
				throw new ItemNotFoundException("Notifica con id " + idNotifica + " non trovata");
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	public int getID_Notifica() {
		return ID_Notifica;
	}
	public void setID_Notifica(int iD_Notifica) {
		ID_Notifica = iD_Notifica;
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
}
