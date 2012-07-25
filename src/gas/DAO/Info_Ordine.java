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
	public enum TipoOrdine { APERTO, CHIUSO, ANY };
	private boolean concluso;
	
	public Info_Ordine() {}
	
	public Info_Ordine(int iD_Ordine, int iD_Responsabile, int iD_Fornitore,
			Date data_apertura, Date data_chiusura, boolean concluso) {
		this.ID_Ordine = iD_Ordine;
		this.ID_Responsabile = iD_Responsabile;
		this.ID_Fornitore = iD_Fornitore;
		this.data_apertura = data_apertura;
		this.data_chiusura = data_chiusura;
		this.concluso = concluso;
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
			boolean concluso = (rs.getInt("concluso") == 1) ? true : false;
			infoOrdine.setConcluso(concluso);
		} finally {
			DBConnection.closeConnection(conn);
		}
		return infoOrdine;
	}
	
	public static Info_Ordine getInfoOrdineFromIdResponsabile(TipoOrdine tipoOrdine, int idResponsabile) throws DBException, SQLException, ItemNotFoundException
	{
		Connection conn = null;
		Info_Ordine infoOrdine = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM info_ordine WHERE ID_Responsabile = ?";
			
			if(tipoOrdine == TipoOrdine.APERTO)
				query += " AND data_chiusura > ?";
			else if(tipoOrdine == TipoOrdine.CHIUSO)
				query += " AND data_chiusura <= ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idResponsabile);
			if(tipoOrdine != TipoOrdine.ANY){
				java.util.Date now = new java.util.Date();
		    	java.sql.Date date = new java.sql.Date(now.getTime());
		    	ps.setDate(2, date);
			}
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				throw new ItemNotFoundException("Ordine con id Responsabile " + idResponsabile + " inesistente");
			infoOrdine = new Info_Ordine();
			infoOrdine.setID_Ordine(rs.getInt("ID_Ordine"));
			infoOrdine.setID_Responsabile(rs.getInt("ID_Responsabile"));
			infoOrdine.setID_Fornitore(rs.getInt("ID_Fornitore"));
			infoOrdine.setData_apertura(rs.getDate("data_apertura"));
			infoOrdine.setData_chiusura(rs.getDate("data_chiusura"));
			boolean concluso = (rs.getInt("concluso") == 1) ? true : false;
			infoOrdine.setConcluso(concluso);
		} finally {
			DBConnection.closeConnection(conn);
		}
		return infoOrdine;
	}
	
	public static List<Info_Ordine> getOrdini(TipoOrdine tipoOrdine, int idMembro, int flag) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		ArrayList<Info_Ordine> list = new ArrayList<Info_Ordine>();
		String query = "SELECT DISTINCT I.ID_Ordine, I.data_apertura, I.data_chiusura " +
				       "FROM info_ordine I";
		
		if(idMembro > 0 && flag == 1)
			query += ", scheda_di_acquisto S";
		
		if(tipoOrdine == TipoOrdine.APERTO)
			query += " WHERE I.data_chiusura > ?";
		else if(tipoOrdine == TipoOrdine.CHIUSO)
			query += " WHERE I.data_chiusura <= ?";
		
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
			Info_Ordine i;
	    	while(rs.next()){
	    		i = new Info_Ordine();
	    		i.setID_Ordine(rs.getInt("ID_Ordine"));
	    		//i.setID_Responsabile(rs.getInt("ID_Responsabile"));
	    		//i.setID_Fornitore(rs.getInt("ID_Fornitore"));
	    		i.setData_apertura(rs.getDate("data_apertura"));
	    		i.setData_chiusura( rs.getDate("data_chiusura"));
	    		//boolean concluso = (rs.getInt("concluso") == 1) ? true : false;
	    		//i.setConcluso(concluso);
	    		list.add(i);
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
	public boolean isConcluso() {
		return concluso;
	}
	public void setConcluso(boolean concluso) {
		this.concluso = concluso;
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
}
