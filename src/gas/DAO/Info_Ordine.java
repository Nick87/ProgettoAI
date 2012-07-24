package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Info_Ordine
{
	private int ID_Ordine;
	private int ID_Responsabile;
	private int ID_Fornitore;
	private boolean concluso;
	
	public Info_Ordine() {}
	
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
}
