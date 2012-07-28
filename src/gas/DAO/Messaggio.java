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

public class Messaggio
{	
	private int ID_Messaggio;
	private int ID_Membro;
	private Date data;
	private String testo;
	private boolean letto;
	private boolean confermato;
	private boolean eliminato;
	public enum TipoMessaggio { ANY, LETTO, NON_LETTO, LETTO_NON_CONFERMATO, LETTO_CONFERMATO };
	
	public Messaggio() {}
	
	public static void inserisciMessaggio(int id_MembroDest, String testo) throws DBException, SQLException
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
			ps.setInt(1, id_MembroDest);
			ps.setDate(2, date);
			ps.setString(3, testo);
			ps.setInt(4, 0);
			ps.setInt(5, 0);
			ps.executeUpdate();
			// Adesso possiamo inserire il nuovo messaggio
			conn.commit();
			conn.setAutoCommit(true);
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	public static List<Messaggio> getListaMessaggiFromIdMembro(TipoMessaggio tipoMessaggio, int idMembro) throws DBException, SQLException
	{
		Connection conn = null;
		List<Messaggio> list = new ArrayList<Messaggio>();
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM messaggio WHERE ID_Membro = ? AND eliminato = ?";
			
			if(tipoMessaggio == TipoMessaggio.NON_LETTO)
				query += " AND letto = 0";
			else if(tipoMessaggio == TipoMessaggio.LETTO_NON_CONFERMATO)
				query += " AND letto = 1 AND confermato = 0";
			else if(tipoMessaggio == TipoMessaggio.LETTO_CONFERMATO)
				query += " AND letto = 1 AND confermato = 1";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembro);
			ps.setInt(2, 0);
			ResultSet rs = ps.executeQuery();
			Messaggio m;
			while(rs.next())
			{
				m = new Messaggio();
				m.setID_Messaggio(rs.getInt("ID_Messaggio"));
				m.setID_Membro(rs.getInt("ID_Membro"));
				m.setData(rs.getDate("data"));
				m.setTesto(rs.getString("testo"));
				boolean val = rs.getInt("letto") == 1 ? true : false;
				m.setLetto(val);
				val = rs.getInt("confermato") == 1 ? true : false;
				m.setConfermato(val);
				val = rs.getInt("eliminato") == 1 ? true : false;
				m.setEliminato(val);
				list.add(m);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static int getNumeroMessaggiFromIdMembro(TipoMessaggio tipoMessaggio, int idMembro) throws DBException, SQLException
	{
		Connection conn = null;
		int n;
		try 
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT COUNT(*) as totale FROM messaggio WHERE ID_Membro = ? AND eliminato = ?";
			
			if(tipoMessaggio == TipoMessaggio.NON_LETTO)
				query += " AND letto = 0";
			else if(tipoMessaggio == TipoMessaggio.LETTO_NON_CONFERMATO)
				query += " AND letto = 1 AND confermato = 0";
			else if(tipoMessaggio == TipoMessaggio.LETTO_CONFERMATO)
				query += " AND letto = 1 AND confermato = 1";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembro);
			ps.setInt(2, 0);
			ResultSet rs = ps.executeQuery();			
			rs.next();
			n = rs.getInt("totale");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return n;
	}
	
	public static void setLettoNonLetto(TipoMessaggio tipoMessaggio, int idMessaggio) throws DBException, SQLException, ItemNotFoundException
	{
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "UPDATE messaggio SET letto = ? WHERE ID_Messaggio = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			if(tipoMessaggio == TipoMessaggio.LETTO)
				ps.setInt(1, 1);
			else
				ps.setInt(1, 0);
			ps.setInt(2, idMessaggio);
			if(ps.executeUpdate() != 1)
				throw new ItemNotFoundException("Messaggio con id " + idMessaggio + " non trovato");
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	public static void eliminaMessaggio(int idMessaggio) throws SQLException, DBException
	{
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "UPDATE messaggio SET eliminato = ? WHERE ID_Messaggio = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, 1);
			ps.setInt(2, idMessaggio);
			if(ps.executeUpdate() != 1)
				System.out.println("Errore eliminazione messaggio");
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	public static void confermaMessaggio(int idMessaggio) throws DBException, SQLException
	{

		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "UPDATE messaggio SET confermato = ? WHERE ID_Messaggio = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, 1);
			ps.setInt(2, idMessaggio);
			if(ps.executeUpdate() != 1)
				System.out.println("Errore conferma messaggio");
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
	public boolean isEliminato() {
		return eliminato;
	}
	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
}
