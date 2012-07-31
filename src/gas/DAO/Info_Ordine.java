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

public class Info_Ordine
{
	private int ID_Ordine;
	private int ID_Responsabile;
	private int ID_Fornitore;
	private Date data_apertura;
	private Date data_chiusura;
	private boolean notificato;
	private boolean successo;
	
	public enum TipoOrdine { ANY, APERTO, CHIUSO, NOTIFICATO, NON_NOTIFICATO, CHIUSO_NOTIFICATO, CHIUSO_NON_NOTIFICATO, SUCCESSO, NON_SUCCESSO };
	
	public Info_Ordine() {}
	
	public Info_Ordine(int iD_Ordine, int iD_Responsabile, int iD_Fornitore,
			Date data_apertura, Date data_chiusura, boolean notificato, boolean successo) {
		this.ID_Ordine = iD_Ordine;
		this.ID_Responsabile = iD_Responsabile;
		this.ID_Fornitore = iD_Fornitore;
		this.data_apertura = data_apertura;
		this.data_chiusura = data_chiusura;
		this.notificato = notificato;
		this.successo = successo;
	}
	
	public static Info_Ordine getInfoOrdineFromIdOrdine(int idOrdine) throws SQLException, DBException, ItemNotFoundException
	{
		Connection conn = null;
		Info_Ordine infoOrdine = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM info_ordine WHERE ID_Ordine = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				throw new ItemNotFoundException("Ordine con id " + idOrdine + " inesistente");
			infoOrdine = new Info_Ordine();
			infoOrdine.setID_Ordine(rs.getInt("ID_Ordine"));
			infoOrdine.setID_Responsabile(rs.getInt("ID_Responsabile"));
			infoOrdine.setID_Fornitore(rs.getInt("ID_Fornitore"));
			infoOrdine.setData_apertura(rs.getDate("data_apertura"));
			infoOrdine.setData_chiusura(rs.getDate("data_chiusura"));
			boolean val = (rs.getInt("notificato") == 1) ? true : false;
			infoOrdine.setNotificato(val);
			val = (rs.getInt("successo") == 1) ? true : false;
			infoOrdine.setSuccesso(val);
		} finally {
			DBConnection.closeConnection(conn);
		}
		return infoOrdine;
	}
	
	public static List<Info_Ordine> getListaInfoOrdineFromIdResponsabile(TipoOrdine tipoOrdine, int idResponsabile) throws DBException, SQLException
	{
		Connection conn = null;
		List<Info_Ordine> list = new ArrayList<Info_Ordine>();
		Info_Ordine infoOrdine;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM info_ordine WHERE ID_Responsabile = ?";
			
			if(tipoOrdine == TipoOrdine.APERTO)
				query += " AND data_chiusura > ?";
			else if(tipoOrdine == TipoOrdine.CHIUSO)
				query += " AND data_chiusura <= ?";
			else if(tipoOrdine == TipoOrdine.NOTIFICATO)
				query += " AND notificato = 1";
			else if(tipoOrdine == TipoOrdine.NON_NOTIFICATO)
				query += " AND notificato = 0";
			else if(tipoOrdine == TipoOrdine.CHIUSO_NOTIFICATO)
				query += " AND data_chiusura <= ? AND notificato = 1";
			else if(tipoOrdine == TipoOrdine.CHIUSO_NON_NOTIFICATO)
				query += " AND data_chiusura <= ? AND notificato = 0";
			else if(tipoOrdine == TipoOrdine.SUCCESSO)
				query += " AND successo = 1";
			else if(tipoOrdine == TipoOrdine.NON_SUCCESSO)
				query += " AND successo = 0";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idResponsabile);
			if(tipoOrdine != TipoOrdine.ANY){
				java.util.Date now = new java.util.Date();
		    	java.sql.Date date = new java.sql.Date(now.getTime());
		    	ps.setDate(2, date);
			}
			ResultSet rs = ps.executeQuery();
			boolean val;
			while(rs.next())
			{
				infoOrdine = new Info_Ordine();
				infoOrdine.setID_Ordine(rs.getInt("ID_Ordine"));
				infoOrdine.setID_Responsabile(rs.getInt("ID_Responsabile"));
				infoOrdine.setID_Fornitore(rs.getInt("ID_Fornitore"));
				infoOrdine.setData_apertura(rs.getDate("data_apertura"));
				infoOrdine.setData_chiusura(rs.getDate("data_chiusura"));
				val = (rs.getInt("notificato") == 1) ? true : false;
				infoOrdine.setNotificato(val);
				val = (rs.getInt("successo") == 1) ? true : false;
				infoOrdine.setSuccesso(val);
				list.add(infoOrdine);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static List<Info_Ordine> getListaOrdiniFromIdMembroCheAcquista(TipoOrdine tipoOrdine, int idMembro, int flag) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		ArrayList<Info_Ordine> list = new ArrayList<Info_Ordine>();
		String query = "SELECT DISTINCT I.ID_Ordine, I.ID_Responsabile, I.ID_Fornitore, I.data_apertura, I.data_chiusura, I.concluso, I.notificato, I.successo " +
				       "FROM info_ordine I";
		
		if(idMembro > 0 && flag == 1)
			query += ", scheda_di_acquisto S";
		
		if(tipoOrdine == TipoOrdine.APERTO)
			query += " AND data_chiusura > ?";
		else if(tipoOrdine == TipoOrdine.CHIUSO)
			query += " AND data_chiusura <= ?";
		else if(tipoOrdine == TipoOrdine.NOTIFICATO)
			query += " AND notificato = 1";
		else if(tipoOrdine == TipoOrdine.NON_NOTIFICATO)
			query += " AND notificato = 0";
		else if(tipoOrdine == TipoOrdine.CHIUSO_NOTIFICATO)
			query += " AND data_chiusura <= ? AND notificato = 1";
		else if(tipoOrdine == TipoOrdine.CHIUSO_NON_NOTIFICATO)
			query += " AND data_chiusura <= ? AND notificato = 0";
		else if(tipoOrdine == TipoOrdine.SUCCESSO)
			query += " AND successo = 1";
		else if(tipoOrdine == TipoOrdine.NON_SUCCESSO)
			query += " AND successo = 0";
		
		if(idMembro > 0 && flag == 1)
			query += " AND I.ID_Ordine = S.ID_Ordine AND S.ID_membro_che_acquista = ?";
		if(flag == -1)
			query+="AND I.ID_Ordine NOT IN(SELECT DISTINCT ID_Ordine from scheda_di_acquisto WHERE ID_membro_che_acquista = ?)";
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			if(tipoOrdine != TipoOrdine.ANY){
				java.util.Date now = new java.util.Date();
		    	java.sql.Date date = new java.sql.Date(now.getTime());
		    	ps.setDate(1, date);
			}
			if(idMembro > 0 && flag == 1){
				if(tipoOrdine == TipoOrdine.ANY)
					ps.setInt(1, idMembro);
				else
					ps.setInt(2, idMembro);
			}
			if(flag == -1)
				ps.setInt(2, idMembro);
			ResultSet rs = ps.executeQuery();
			Info_Ordine infoOrdine;
	    	while(rs.next())
	    	{
	    		infoOrdine = new Info_Ordine();
	    		infoOrdine.setID_Ordine(rs.getInt("ID_Ordine"));
	    		infoOrdine.setID_Responsabile(rs.getInt("ID_Responsabile"));
	    		infoOrdine.setID_Fornitore(rs.getInt("ID_Fornitore"));
	    		infoOrdine.setData_apertura(rs.getDate("data_apertura"));
	    		infoOrdine.setData_chiusura( rs.getDate("data_chiusura"));
				boolean val = (rs.getInt("notificato") == 1) ? true : false;
				infoOrdine.setNotificato(val);
				val = (rs.getInt("successo") == 1) ? true : false;
				infoOrdine.setSuccesso(val);
	    		list.add(infoOrdine);
	    	}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static boolean isOrdineChiuso(int idOrdine) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		String query = "SELECT * FROM info_ordine " +
				       "WHERE ID_Ordine = ? AND data_chiusura <= ?";
		boolean chiuso;
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idOrdine);
			java.util.Date now = new java.util.Date();
	    	java.sql.Date date = new java.sql.Date(now.getTime());
	    	ps.setDate(2, date);
			ResultSet rs = ps.executeQuery();
			chiuso = (rs.next()) ? true : false;
		} finally {
			DBConnection.closeConnection(conn);
		}
		return chiuso;
	}
	
	public static int getIdResponsabilefromIdOrdine(int idOrdine) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		String query = "SELECT ID_Responsabile FROM info_ordine " +
				       "WHERE ID_Ordine = ?";
		int id_responsabile = 0;
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				id_responsabile=rs.getInt("ID_Responsabile");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return id_responsabile;
	}
	
	public static int getIdFornitorefromIdOrdine(int idOrdine) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		String query = "SELECT ID_Fornitore FROM info_ordine " +
				       "WHERE ID_Ordine = ?";
		int id_responsabile = 0;
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				id_responsabile=rs.getInt("ID_Fornitore");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return id_responsabile;
	}
	
	public static void setNotificato(int idOrdine) throws DBException, SQLException, ItemNotFoundException
	{
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "UPDATE info_ordine SET notificato = 1 WHERE ID_Ordine = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idOrdine);
			if(ps.executeUpdate() != 1)
				throw new ItemNotFoundException("Notifica con id " + idOrdine + " non trovata");
		} finally {
			DBConnection.closeConnection(conn);
		}
	}

	public int getID_Ordine() {
		return ID_Ordine;
	}
	public void setID_Ordine(int iD_Ordine) {
		ID_Ordine = iD_Ordine;
	}
	public int getID_Responsabile() {
		return ID_Responsabile;
	}
	public void setID_Responsabile(int iD_Responsabile) {
		ID_Responsabile = iD_Responsabile;
	}
	public int getID_Fornitore() {
		return ID_Fornitore;
	}
	public void setID_Fornitore(int iD_Fornitore) {
		ID_Fornitore = iD_Fornitore;
	}
	public Date getData_apertura() {
		return data_apertura;
	}
	public void setData_apertura(Date data_apertura) {
		this.data_apertura = data_apertura;
	}
	public Date getData_chiusura() {
		return data_chiusura;
	}
	public void setData_chiusura(Date data_chiusura) {
		this.data_chiusura = data_chiusura;
	}
	public boolean isNotificato() {
		return notificato;
	}
	public void setNotificato(boolean notificato) {
		this.notificato = notificato;
	}
	public boolean isSuccesso() {
		return successo;
	}
	public void setSuccesso(boolean successo) {
		this.successo = successo;
	}
}
