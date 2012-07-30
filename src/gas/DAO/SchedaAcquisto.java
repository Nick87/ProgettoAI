package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import gas.Exception.ItemNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchedaAcquisto
{
	private int ID_Scheda;
	private int ID_Ordine;
	private int ID_Prodotto;
	private int ID_Membro_che_acquista;
	private int ID_Membro_che_ritira;
	private int quantita;
	private boolean ritirato;
	
	public SchedaAcquisto(int iD_Scheda, int iD_Ordine, int iD_Prodotto,
			int iD_Membro_che_acquista, int iD_Membro_che_ritira, int quantita,
			boolean ritirato) {
		this.ID_Scheda = iD_Scheda;
		this.ID_Ordine = iD_Ordine;
		this.ID_Prodotto = iD_Prodotto;
		this.ID_Membro_che_acquista = iD_Membro_che_acquista;
		this.ID_Membro_che_ritira = iD_Membro_che_ritira;
		this.quantita = quantita;
		this.ritirato = ritirato;
	}
	
	public static void RimuoviDelega(int iD_membro_che_acquista, int idOrdine) throws DBException, SQLException
	{
		Connection conn = null;
		String query = "";
		PreparedStatement ps;
		try
		{
			conn = DBConnection.getDBConnection();
			conn.setAutoCommit(false);
			query = "UPDATE scheda_di_acquisto " +
				"SET ID_membro_che_ritira = ? " +
				"WHERE ID_Ordine = ? AND ID_membro_che_acquista = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, -1);
			ps.setInt(2, idOrdine);
			ps.setInt(3, iD_membro_che_acquista);
			ps.executeUpdate();
			// Adesso possiamo inserire la nuova scheda di acquisto
			conn.commit();
			conn.setAutoCommit(true);
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	public static void Aggiungi_Delega(int idMembroCompratore,int idordinescelto,int idutentescelto) throws InvalidOperationException, DBException, SQLException, ItemNotFoundException
	{
		Connection conn = null;
		String query = "";
		PreparedStatement ps;
		try
		{
			conn = DBConnection.getDBConnection();
			conn.setAutoCommit(false);
			query = "UPDATE scheda_di_acquisto " +
				"SET ID_membro_che_ritira = ? " +
				"WHERE ID_Ordine = ? AND ID_membro_che_acquista = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, idutentescelto);
			ps.setInt(2, idordinescelto);
			ps.setInt(3, idMembroCompratore);
			ps.executeUpdate();
			// Adesso possiamo inserire la nuova scheda di acquisto
			conn.commit();
			conn.setAutoCommit(true);
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	public static void Aggiorna_Crea_Scheda(int idOrdine, int idMembro, Map<Integer, Integer> newQuantita, String operation) throws InvalidOperationException, DBException, SQLException, ItemNotFoundException
	{
		Connection conn = null;
		int idProdotto, newAmount, old_quantita_richiesta, disponibilita_attuale, diff;
		Prodotto p;
		String query = "";
		PreparedStatement ps;
		try
		{
			for(Integer key : newQuantita.keySet())
			{				
				idProdotto = key.intValue();
				newAmount = newQuantita.get(key);
				p = Prodotto.getProdotto(idProdotto);
				if(newAmount % p.getStep() != 0)
					throw new InvalidOperationException("Quantita' non e' multiplo dello step (" + p.getStep() + ")");
				if(newAmount < p.getPezzatura_min_utente())
					throw new InvalidOperationException("Quantita' e' minore della pezzatura minima utente (" + p.getPezzatura_min_utente() + ")");
				java.util.Date now = new java.util.Date();
		    	java.sql.Date date = new java.sql.Date(now.getTime());
		    	if(p.getFine_disponibilita().before(date))
					throw new InvalidOperationException("Prodotto non disponibile");
		    	if(!p.isAcquistabile())
					throw new InvalidOperationException("Prodotto non acquistabile");
		    	
		    	if(operation.equals("AGGIORNA"))
		    	{
			    	// Puo' darsi che questa richiesta non pu� essere soddisfatta perch�
			    	// un aumento di prodotti richiesti superi la quantita massima messa a disposizione
			    	// dal fornitore		    	
			    	old_quantita_richiesta = Prodotto.getQuantitaRichiesta(idOrdine, idMembro, idProdotto);
			    	disponibilita_attuale = Prodotto.getDisponibilitaProdotto(idProdotto);
			    	diff = newAmount - old_quantita_richiesta;
			    	if(diff > disponibilita_attuale)
			    		throw new InvalidOperationException("Quantita massima superata per il prodotto (" + disponibilita_attuale + ")");
		    	}
		    	else
		    	{
		    		//Controllo se la nuova quantita richiesta non superi quella disponibile
		    		disponibilita_attuale = Prodotto.getDisponibilitaProdotto(idProdotto);
		    		if(newAmount > disponibilita_attuale)
		    			throw new InvalidOperationException("Quantita massima superata per il prodotto (" + disponibilita_attuale + ")");
		    	}
			}
			
			if(operation.equals("AGGIORNA"))
			{
				conn = DBConnection.getDBConnection();
				conn.setAutoCommit(false);
				query = "UPDATE scheda_di_acquisto " +
					"SET quantita = ? " +
					"WHERE ID_Prodotto = ? AND ID_Ordine = ? AND ID_membro_che_acquista = ?";
				for(Integer key : newQuantita.keySet())
				{
					idProdotto = key.intValue();
					newAmount = newQuantita.get(key);
					ps = conn.prepareStatement(query);
					ps.setInt(1, newAmount);
					ps.setInt(2, idProdotto);
					ps.setInt(3, idOrdine);
					ps.setInt(4, idMembro);
					if(ps.executeUpdate() != 1)
						throw new InvalidOperationException("Errore update scheda di acquisto");
				}
			}
			else
			{				
				int id_scheda=get_max_ID();
				id_scheda++;
				conn = DBConnection.getDBConnection();
				conn.setAutoCommit(false);
				//ID_Scheda;ID_Ordine;ID_Prodotto;ID_membro_che_acquista;quantita;ritirato;ID_membro_che_ritira
				query = "INSERT INTO scheda_di_acquisto VALUES(?,?,?,?,?,?,?)";
				for(Integer key : newQuantita.keySet())
				{
					idProdotto = key.intValue();
					newAmount = newQuantita.get(key);
					ps = conn.prepareStatement(query);
					ps.setInt(1, id_scheda);
					ps.setInt(2, idOrdine);
					ps.setInt(3, idProdotto);
					ps.setInt(4, idMembro);
					ps.setInt(5, newAmount);
					ps.setInt(6, 0);
					ps.setInt(7, -1);
					if(ps.executeUpdate() != 1)
						throw new InvalidOperationException("Errore inserimento scheda di acquisto");
				}
			}
	    	// Adesso possiamo inserire la nuova scheda di acquisto
			conn.commit();
			conn.setAutoCommit(true);
		} catch (ItemNotFoundException e) {
			if(conn != null) conn.rollback();
			throw e;
		} catch (InvalidOperationException e) {
			if(conn != null) conn.rollback();
			throw e;
		} finally {
			DBConnection.closeConnection(conn);
		}
	}
	
	private static int get_max_ID() throws DBException, SQLException
	{
		//Cerco il prossimo id_scheda prendendo il massimo dalla tabella scheda_acquisto
		Connection conn = DBConnection.getDBConnection();
		int id=0;
		String query="SELECT max( ID_Scheda )FROM scheda_di_acquisto";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				id=rs.getInt(1);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return id;
	}
	
	public static List<SchedaAcquisto> DeleghePerUtente(int id_membro) throws DBException, SQLException
	{
		ArrayList<SchedaAcquisto> list = new ArrayList<SchedaAcquisto>();		
		//Cerco il prossimo id_scheda prendendo il massimo dalla tabella scheda_acquisto
		Connection conn = DBConnection.getDBConnection();
		String query="SELECT * FROM scheda_di_acquisto WHERE ID_membro_che_acquista = ? AND ID_membro_che_ritira != ? GROUP BY ID_Ordine";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id_membro);
			ps.setInt(2, -1);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				list.add(new SchedaAcquisto(rs.getInt("ID_Scheda"), rs.getInt("ID_Ordine"), rs.getInt("ID_Prodotto"),rs.getInt("ID_Membro_che_acquista"),rs.getInt("ID_Membro_che_ritira"),rs.getInt("quantita"),rs.getBoolean("ritirato")));
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public int getID_Scheda() {
		return ID_Scheda;
	}
	public void setID_Scheda(int iD_Scheda) {
		ID_Scheda = iD_Scheda;
	}
	public int getID_Ordine() {
		return ID_Ordine;
	}
	public void setID_Ordine(int iD_Ordine) {
		ID_Ordine = iD_Ordine;
	}
	public int getID_Prodotto() {
		return ID_Prodotto;
	}
	public void setID_Prodotto(int iD_Prodotto) {
		ID_Prodotto = iD_Prodotto;
	}
	public int getID_Membro_che_acquista() {
		return ID_Membro_che_acquista;
	}
	public void setID_Membro_che_acquista(int iD_Membro_che_acquista) {
		ID_Membro_che_acquista = iD_Membro_che_acquista;
	}
	public int getID_Membro_che_ritira() {
		return ID_Membro_che_ritira;
	}
	public void setID_Membro_che_ritira(int iD_Membro_che_ritira) {
		ID_Membro_che_ritira = iD_Membro_che_ritira;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public boolean isRitirato() {
		return ritirato;
	}
	public void setRitirato(boolean ritirato) {
		this.ritirato = ritirato;
	}
}
