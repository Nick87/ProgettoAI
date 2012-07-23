package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Discussione
{
	private int ID_Discussione;
	private int ID_Messaggio_Discussione;
	private int ID_Membro_Mittente;
	private int ID_Membro_Destinatario;
	private Timestamp timestamp;
	private String testo;
	private boolean letta;
	
	public Discussione() {}
	
	public Discussione(int iD_Discussione, int iD_Messaggio_Discussione,
			int iD_Membro_Mittente, int iD_Membro_Destinatario, Timestamp timestamp,
			String testo, boolean letta) {
		this.ID_Discussione = iD_Discussione;
		this.ID_Messaggio_Discussione = iD_Messaggio_Discussione;
		this.ID_Membro_Mittente = iD_Membro_Mittente;
		this.ID_Membro_Destinatario = iD_Membro_Destinatario;
		this.setTimestamp(timestamp);
		this.testo = testo;
		this.letta = letta;
	}
	
	public static Discussione addDiscussione(int idMittente, int idDestinatario, String testo) throws SQLException, InvalidOperationException, DBException
	{
		Connection conn = null;
		String query;
		PreparedStatement ps;
		ResultSet rs;
		Discussione discussione = null;
		try
		{
			conn = DBConnection.getDBConnection();
			query = "SELECT ID_Discussione, MAX(ID_Messaggio_Discussione) as maximum FROM discussione " + 
				   	"WHERE ID_Membro_Mittente = ? OR ID_Membro_Destinatario = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, idMittente);
			ps.setInt(2, idMittente);
			rs = ps.executeQuery();
			int idDiscussione, idMessaggioDiscussione;
			
			if(!rs.next())
				throw new InvalidOperationException("Errore select discussione");
			
			idDiscussione = rs.getInt("ID_Discussione") == 0 ? 1 : rs.getInt("ID_Discussione");
			idMessaggioDiscussione = rs.getInt("maximum") + 1;
			
			query = "INSERT INTO `discussione`(`ID_Discussione`, `ID_Messaggio_Discussione`, `ID_Membro_Mittente`, `ID_Membro_Destinatario`, `timestamp`, `testo`, `letta`) " +
					"VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";

			ps = conn.prepareStatement(query);
			ps.setInt(1, idDiscussione);
			ps.setInt(2, idMessaggioDiscussione);
			ps.setInt(3, idMittente);
			ps.setInt(4, idDestinatario);
		    ps.setString(5, testo);
		    ps.setInt(6, 0);
		    if(ps.executeUpdate() != 1)
		    	throw new InvalidOperationException("Errore insert discussione");
		    query = "SELECT * FROM discussione " +
		    		"WHERE ID_Discussione = ? AND ID_Messaggio_Discussione = ?";
		    ps = conn.prepareStatement(query);
		    ps.setInt(1, idDiscussione);
		    ps.setInt(2, idMessaggioDiscussione);
		    rs = ps.executeQuery();
		    rs.next();
		    discussione = new Discussione();
		    discussione.setID_Discussione(rs.getInt("ID_Discussione"));
		    discussione.setID_Messaggio_Discussione(rs.getInt("ID_Messaggio_Discussione"));
		    discussione.setID_Membro_Mittente(rs.getInt("ID_Membro_Mittente"));
		    discussione.setID_Membro_Destinatario(rs.getInt("ID_Membro_Destinatario"));
		    discussione.setTimestamp(rs.getTimestamp("timestamp"));
		    discussione.setTesto(rs.getString("testo"));
		} finally {
			DBConnection.closeConnection(conn);
		}
		return discussione;
	}
	
	public static Map<Integer, String> getSommarioDiscussioniFromIdMembro(int idMembro) throws DBException, SQLException
	{
		Connection conn = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		String query_1, query_2, query_3;
		PreparedStatement ps_1, ps_2, ps_3;
		ResultSet rs_1, rs_2, rs_3;
		try
		{
			conn = DBConnection.getDBConnection();
			query_1 = "SELECT  DISTINCT ID_Discussione FROM discussione " +
					  "WHERE ID_Membro_Mittente = ? OR ID_Membro_Destinatario = ?";
			ps_1 = conn.prepareStatement(query_1);
			ps_1.setInt(1, idMembro);
			ps_1.setInt(2, idMembro);
			rs_1 = ps_1.executeQuery();
			
			query_2 = "SELECT ID_Membro_Mittente as mitt, ID_Membro_Destinatario as dest " +
					  "FROM discussione " +
					  "WHERE ID_Discussione = ? " +
					  "LIMIT 0, 1";
			ps_2 = conn.prepareStatement(query_2);
			query_3 = "SELECT username FROM membro WHERE ID_Membro = ?";
			ps_3 = conn.prepareStatement(query_3);
			while(rs_1.next())
			{
				ps_2.setInt(1, rs_1.getInt("ID_Discussione"));
				rs_2 = ps_2.executeQuery();
				rs_2.next();
				if(rs_2.getInt("mitt") == idMembro) {
					ps_3.setInt(1, rs_2.getInt("dest"));
					rs_3 = ps_3.executeQuery();
					rs_3.next();
					map.put(rs_1.getInt("ID_Discussione"), rs_3.getString("username"));
				} else {
					ps_3.setInt(1, rs_2.getInt("mitt"));
					rs_3 = ps_3.executeQuery();
					rs_3.next();
					map.put(rs_1.getInt("ID_Discussione"), rs_3.getString("username"));
				}
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return map;
	}
	
	public static List<Discussione> getDiscussioneFromId(int idDiscussione) throws SQLException, DBException
	{
		Connection conn = null;
		List<Discussione> list = new ArrayList<Discussione>();
		String query;
		PreparedStatement ps;
		ResultSet rs;
		Discussione d;
		try
		{
			conn = DBConnection.getDBConnection();
			query = "SELECT * FROM discussione WHERE ID_Discussione = ? ORDER BY timestamp";
			ps = conn.prepareStatement(query);
			ps.setInt(1, idDiscussione);
			rs = ps.executeQuery();
			while(rs.next())
			{
				d = new Discussione();
				d.setID_Discussione(rs.getInt("ID_Discussione"));
				d.setID_Messaggio_Discussione(rs.getInt("ID_Messaggio_Discussione"));
				d.setID_Membro_Mittente(rs.getInt("ID_Membro_Mittente"));
				d.setID_Membro_Destinatario(rs.getInt("ID_Membro_Destinatario"));
				d.setTimestamp(rs.getTimestamp("timestamp"));
				d.setTesto(rs.getString("testo"));
				boolean letta = rs.getInt("letta") == 1 ? true : false;
				d.setLetta(letta);
				list.add(d);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static List<Discussione> getDiscussioniFromIdMembro(int idMembro) throws DBException, SQLException
	{
		Connection conn = null;
		List<Discussione> list = new ArrayList<Discussione>();
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM discussione " + 
						   "WHERE ID_Membro_Mittente = ? OR ID_Membro_Destinatario = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembro);
			ps.setInt(2, idMembro);
			ResultSet rs = ps.executeQuery();
			Discussione d;
			while(rs.next())
			{
				d = new Discussione();
				d.setID_Discussione(rs.getInt("ID_Discussione"));
				d.setID_Messaggio_Discussione(rs.getInt("ID_Messaggio_Discussione"));
				d.setID_Membro_Mittente(rs.getInt("ID_Membro_Mittente"));
				d.setID_Membro_Destinatario(rs.getInt("ID_Membro_Destinatario"));
				d.setTimestamp(rs.getTimestamp("timestamp"));
				d.setTesto(rs.getString("testo"));
				boolean letta = rs.getInt("letta") == 1 ? true : false;
				d.setLetta(letta);
				list.add(d);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static List<Discussione> getDeltaDiscussioni(int idDiscussione, int idMessaggioDiscussione) throws DBException, SQLException
	{
		List<Discussione> list = new ArrayList<Discussione>();
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM discussione " +
						   "WHERE ID_Discussione = ? AND ID_Messaggio_Discussione > ? " +
						   "ORDER BY ID_Messaggio_Discussione";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idDiscussione);
			ps.setInt(2, idMessaggioDiscussione);
			ResultSet rs = ps.executeQuery();
			Discussione d;
			while(rs.next())
			{
				d = new Discussione();
				d.setID_Discussione(rs.getInt("ID_Discussione"));
				d.setID_Messaggio_Discussione(rs.getInt("ID_Messaggio_Discussione"));
				d.setID_Membro_Mittente(rs.getInt("ID_Membro_Mittente"));
				d.setID_Membro_Destinatario(rs.getInt("ID_Membro_Destinatario"));
				d.setTimestamp(rs.getTimestamp("timestamp"));
				d.setTesto(rs.getString("testo"));
				boolean letta = rs.getInt("letta") == 1 ? true : false;
				d.setLetta(letta);
				list.add(d);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public int getID_Discussione() {
		return ID_Discussione;
	}
	public void setID_Discussione(int iD_Discussione) {
		ID_Discussione = iD_Discussione;
	}
	public int getID_Messaggio_Discussione() {
		return ID_Messaggio_Discussione;
	}
	public void setID_Messaggio_Discussione(int iD_Messaggio_Discussione) {
		ID_Messaggio_Discussione = iD_Messaggio_Discussione;
	}
	public int getID_Membro_Mittente() {
		return ID_Membro_Mittente;
	}
	public void setID_Membro_Mittente(int iD_Membro_Mittente) {
		ID_Membro_Mittente = iD_Membro_Mittente;
	}
	public int getID_Membro_Destinatario() {
		return ID_Membro_Destinatario;
	}
	public void setID_Membro_Destinatario(int iD_Membro_Destinatario) {
		ID_Membro_Destinatario = iD_Membro_Destinatario;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public boolean isLetta() {
		return letta;
	}
	public void setLetta(boolean letta) {
		this.letta = letta;
	}
}
