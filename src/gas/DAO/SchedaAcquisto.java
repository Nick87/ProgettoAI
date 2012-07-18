package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import gas.Exception.ItemNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
	public static void AggiornaScheda(int idOrdine, int idMembro, Map<Integer, Integer> newQuantita) throws InvalidOperationException, DBException, SQLException, ItemNotFoundException
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
					throw new InvalidOperationException("Quantita' non e' multiplo dello step");
				if(newAmount < p.getPezzatura_min_utente())
					throw new InvalidOperationException("Quantita' e' minore della pezzatura minima utente");
				java.util.Date now = new java.util.Date();
		    	java.sql.Date date = new java.sql.Date(now.getTime());
		    	if(p.getFine_disponibilita().before(date))
					throw new InvalidOperationException("Prodotto non disponibile");
		    	if(!p.isAcquistabile())
					throw new InvalidOperationException("Prodotto non acquistabile");
		    	// Può darsi che questa richiesta non può essere soddisfatta perchè
		    	// un aumento di prodotti richiesti superi la quantita massima messa a disposizione
		    	// dal fornitore		    	
		    	old_quantita_richiesta = Prodotto.getQuantitaRichiesta(idOrdine, idMembro, idProdotto);
		    	disponibilita_attuale = Prodotto.getDisponibilitaProdotto(idProdotto);
		    	diff = newAmount - old_quantita_richiesta;
		    	if(diff > disponibilita_attuale)
		    		throw new InvalidOperationException("Quantita massima superata per il prodotto");
			}
			
			conn = DBConnection.getDBConnection();
			conn.setAutoCommit(false);
			query = "UPDATE scheda_di_acquisto " +
					"SET quantita = ? " +
					"WHERE ID_Prodotto = ? AND ID_Ordine = ? AND ID_membro_che_acquista = ?";
			
	    	// Adesso possiamo aggiornare la scheda di acquisto
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
