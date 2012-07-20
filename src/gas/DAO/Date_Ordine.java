package gas.DAO;

import gas.Exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Date_Ordine
{
	private int ID_Ordine;
	private Date data_apertura;
	private Date data_chiusura;
	public enum TipoOrdine { APERTO, CHIUSO, ANY };
	
	public Date_Ordine() {}
	
	public Date_Ordine(int ID_Ordine, Date data_apertura, Date data_chiusura)
	{
		this.ID_Ordine = ID_Ordine;
		this.data_apertura = data_apertura;
		this.data_chiusura = data_chiusura;
	}

	public static List<Date_Ordine> getOrdini(TipoOrdine tipoOrdine, int idMembro) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		ArrayList<Date_Ordine> list = new ArrayList<Date_Ordine>();
		String query = "SELECT DISTINCT D.ID_Ordine, D.data_apertura, D.data_chiusura " +
				       "FROM date_ordine D";
		
		if(idMembro > 0)
			query += ", scheda_di_acquisto S";
		
		if(tipoOrdine == TipoOrdine.APERTO)
			query += " WHERE D.data_chiusura > ?";
		else if(tipoOrdine == TipoOrdine.CHIUSO)
			query += " WHERE D.data_chiusura <= ?";
		
		if(idMembro > 0)
			query += " AND D.ID_Ordine = S.ID_Ordine AND S.ID_membro_che_acquista = ?";
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			if(tipoOrdine != TipoOrdine.ANY){
				java.util.Date now = new java.util.Date();
		    	java.sql.Date date = new java.sql.Date(now.getTime());
		    	ps.setDate(1, date);
			}
			if(idMembro > 0){
				if(tipoOrdine == TipoOrdine.ANY)
					ps.setInt(1, idMembro);
				else
					ps.setInt(2, idMembro);
			}
			ResultSet rs = ps.executeQuery();
	    	while(rs.next()){	    		
	    		list.add(new Date_Ordine(rs.getInt("ID_Ordine"), rs.getDate("data_apertura"), rs.getDate("data_chiusura")));
	    	}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static boolean isOrdineChiuso(int idOrdine) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		String query = "SELECT * FROM date_ordine D " +
				       "WHERE D.ID_Ordine = ? AND D.data_chiusura <= ?";
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
