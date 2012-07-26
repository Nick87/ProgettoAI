package gas.DAO;

import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import gas.Exception.ItemNotFoundException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Prodotto
{
	private int ID_Prodotto;
	private int ID_Fornitore;
	private String nome;
	private String descrizione;
	private String categoria;
	private int quantita;
	private String unita_di_misura;
	private double costo_unitario;
	private int dimensione;
	private double costo_trasporto;
	private int pezzatura_min_fornitore;
	private int pezzatura_min_utente;
	private int step;
	private Date inizio_disponibilita;
	private Date fine_disponibilita;
	private boolean acquistabile;
	
	public Prodotto() {}
	
	public Prodotto(int ID_Prodotto, int ID_Fornitore, String nome,
			String descrizione, String categoria, int quantita,
			String unita_di_misura, double costo_unitario, int dimensione,
			double costo_trasporto, int pezzatura_min_fornitore,
			int pezzatura_min_utente, int step, Date inizio_disponibilita,
			Date fine_disponibilita, boolean acquistabile)
	{
		this.ID_Prodotto = ID_Prodotto;
		this.ID_Fornitore = ID_Fornitore;
		this.nome = nome;
		this.descrizione = descrizione;
		this.categoria = categoria;
		this.quantita = quantita;
		this.unita_di_misura = unita_di_misura;
		this.costo_unitario = costo_unitario;
		this.dimensione = dimensione;
		this.costo_trasporto = costo_trasporto;
		this.pezzatura_min_fornitore = pezzatura_min_fornitore;
		this.pezzatura_min_utente = pezzatura_min_utente;
		this.step = step;
		this.inizio_disponibilita = inizio_disponibilita;
		this.fine_disponibilita = fine_disponibilita;
		this.acquistabile = acquistabile;
	}
	
	public static Prodotto getProdotto(int idProdotto) throws DBException, ItemNotFoundException, SQLException
	{
		Prodotto p = new Prodotto();
		Connection conn = DBConnection.getDBConnection();
		String query = "SELECT * FROM prodotto WHERE ID_Prodotto = ?";
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idProdotto);
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				throw new ItemNotFoundException("ID prodotto " + idProdotto + " inesistente");
			p.setID_Prodotto(rs.getInt("ID_Prodotto"));
			p.setID_Fornitore(rs.getInt("ID_Fornitore"));
			p.setNome(rs.getString("nome"));
			p.setDescrizione(rs.getString("descrizione"));
			p.setCategoria(rs.getString("categoria"));
			p.setQuantita(rs.getInt("quantita"));
			p.setUnita_di_misura(rs.getString("unita_di_misura"));
			p.setCosto_unitario(rs.getDouble("costo_unitario"));
			p.setDimensione(rs.getInt("dimensione"));
			p.setCosto_trasporto(rs.getDouble("costo_trasporto"));
			p.setPezzatura_min_fornitore(rs.getInt("pezzatura_min_fornitore"));
			p.setPezzatura_min_utente(rs.getInt("pezzatura_min_utente"));
			p.setStep(rs.getInt("step"));
			p.setInizio_disponibilita(rs.getDate("inizio_disponibilita"));
			p.setFine_disponibilita(rs.getDate("fine_disponibilita"));
			boolean acquistabile = (rs.getInt("acquistabile") == 1) ? true : false;
			p.setAcquistabile(acquistabile);
		} finally {
			DBConnection.closeConnection(conn);
		}
		return p;
	}
	
	public static List<Prodotto> getListaProdottiFromOrdine(int idOrdine) throws DBException, SQLException
	{
		ArrayList<Prodotto> lista = new ArrayList<Prodotto>();
		Connection conn = DBConnection.getDBConnection();
		
		String query = "SELECT P.ID_Prodotto," +
					   "       P.ID_Fornitore, " +
					   "       P.nome," +
					   "       P.descrizione," +
					   "       P.categoria," +
					   "       P.quantita," +
					   "       P.unita_di_misura," +
					   "       P.costo_unitario," +
					   "       P.dimensione," +
					   "       P.costo_trasporto," +
					   "       P.pezzatura_min_fornitore," +
					   "       P.pezzatura_min_utente," +
					   "       P.step," +
					   "       P.inizio_disponibilita," +
					   "       P.fine_disponibilita," +
					   "       P.acquistabile " +
				       "FROM prodotto P, ordine O " +
				       "WHERE O.ID_Prodotto = P.ID_Prodotto AND P.acquistabile='1'" +
				       " AND O.ID_Ordine = ? AND P.inizio_disponibilita <= ?";

	    try
	    {
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setInt(1, idOrdine);
	    	//Controllo se il prodotto e' disponibile nella data attuale
	    	java.util.Date now = new java.util.Date();
	    	java.sql.Date date = new java.sql.Date(now.getTime());
	    	ps.setDate(2, date);
			ResultSet rs = ps.executeQuery();
			Prodotto p;
			while(rs.next()){
				p = new Prodotto();
				p.setID_Prodotto(rs.getInt("ID_Prodotto"));
				p.setID_Fornitore(rs.getInt("ID_Fornitore"));
				p.setNome(rs.getString("nome"));
				p.setDescrizione(rs.getString("descrizione"));
				p.setCategoria(rs.getString("categoria"));
				p.setQuantita(rs.getInt("quantita"));
				p.setUnita_di_misura(rs.getString("unita_di_misura"));
				p.setCosto_unitario(rs.getDouble("costo_unitario"));
				p.setDimensione(rs.getInt("dimensione"));
				p.setCosto_trasporto(rs.getDouble("costo_trasporto"));
				p.setPezzatura_min_fornitore(rs.getInt("pezzatura_min_fornitore"));
				p.setPezzatura_min_utente(rs.getInt("pezzatura_min_utente"));
				p.setStep(rs.getInt("step"));
				p.setInizio_disponibilita(rs.getDate("inizio_disponibilita"));
				p.setFine_disponibilita(rs.getDate("fine_disponibilita"));
				boolean val = rs.getInt("acquistabile") == 0 ? false : true;
				p.setAcquistabile(val);
				lista.add(p);
			}
	    } finally {
			DBConnection.closeConnection(conn);
		}
		return lista;
	}
	
	public static List<Prodotto> getListaProdottiFromSchedaAcquisto(int idOrdine, int idMembro) throws DBException, SQLException
	{
		ArrayList<Prodotto> lista = new ArrayList<Prodotto>();
		Connection conn = DBConnection.getDBConnection();
		
		String query = "SELECT P.ID_Prodotto," +
					   "       P.ID_Fornitore, " +
					   "       P.nome," +
					   "       P.descrizione," +
					   "       P.categoria," +
					   "       S.quantita," +
					   "       P.unita_di_misura," +
					   "       P.costo_unitario," +
					   "       P.dimensione," +
					   "       P.costo_trasporto," +
					   "       P.pezzatura_min_fornitore," +
					   "       P.pezzatura_min_utente," +
					   "       P.step," +
					   "       P.inizio_disponibilita," +
					   "       P.fine_disponibilita," +
					   "       P.acquistabile " +
				       "FROM prodotto P, scheda_di_acquisto S " +
				       "WHERE S.ID_Prodotto = P.ID_Prodotto AND S.ID_Ordine = ? AND S.ID_membro_che_acquista = ?";

	    try
	    {
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setInt(1, idOrdine);
	    	ps.setInt(2, idMembro);
			ResultSet rs = ps.executeQuery();
			Prodotto p;
			while(rs.next()){
				p = new Prodotto();
				p.setID_Prodotto(rs.getInt("ID_Prodotto"));
				p.setID_Fornitore(rs.getInt("ID_Fornitore"));
				p.setNome(rs.getString("nome"));
				p.setDescrizione(rs.getString("descrizione"));
				p.setCategoria(rs.getString("categoria"));
				p.setQuantita(rs.getInt("quantita"));
				p.setUnita_di_misura(rs.getString("unita_di_misura"));
				p.setCosto_unitario(rs.getDouble("costo_unitario"));
				p.setDimensione(rs.getInt("dimensione"));
				p.setCosto_trasporto(rs.getDouble("costo_trasporto"));
				p.setPezzatura_min_fornitore(rs.getInt("pezzatura_min_fornitore"));
				p.setPezzatura_min_utente(rs.getInt("pezzatura_min_utente"));
				p.setStep(rs.getInt("step"));
				p.setInizio_disponibilita(rs.getDate("inizio_disponibilita"));
				p.setFine_disponibilita(rs.getDate("fine_disponibilita"));
				boolean val = rs.getInt("acquistabile") == 0 ? false : true;
				p.setAcquistabile(val);
				lista.add(p);
			}
	    } finally {
			DBConnection.closeConnection(conn);
		}
		return lista;
	}
	
	public static int getDisponibilitaProdotto(int idProdotto) throws DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		int totale = 0, prenotati = 0;
		String query = "";
		try
		{
			query = "SELECT P.quantita as totale " +
					"FROM prodotto P " +
					"WHERE P.ID_Prodotto = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idProdotto);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totale = rs.getInt("totale");
			query = "SELECT SUM(S.quantita) as prenotati " +
					"FROM scheda_di_acquisto S, info_ordine I " +
					"WHERE S.ID_Ordine = I.ID_Ordine AND S.ID_Prodotto = ? AND I.data_chiusura > ? " +
					"GROUP BY S.ID_Prodotto";
			ps = conn.prepareStatement(query);
			java.util.Date now = new java.util.Date();
	    	java.sql.Date date = new java.sql.Date(now.getTime());
	    	ps.setInt(1, idProdotto);
	    	ps.setDate(2, date);
			rs = ps.executeQuery();
			rs.next();
			prenotati = rs.getInt("prenotati");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return totale-prenotati;
	}
	
	public static int getQuantitaRichiesta(int idOrdine, int idMembro, int idProdotto) throws InvalidOperationException, DBException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		int quantita;
		String query = "";
		PreparedStatement ps;
		ResultSet rs;
		
		try
		{
			query = "SELECT S.quantita as old_quantita FROM scheda_di_acquisto S, info_ordine I " +
	    			"WHERE I.ID_Ordine = S.ID_Ordine AND " +
	    			"S.ID_Ordine = ? AND S.ID_Prodotto = ? AND S.ID_Membro_che_acquista = ? AND " +
	    			"I.data_chiusura > ?";
	    	ps = conn.prepareStatement(query);
	    	java.util.Date now = new java.util.Date();
	    	java.sql.Date date = new java.sql.Date(now.getTime());
	    	ps.setInt(1, idOrdine);
	    	ps.setInt(2, idProdotto);
	    	ps.setInt(3, idMembro);
	    	ps.setDate(4, date);
	    	rs = ps.executeQuery();
	    	if(!rs.next())
	    		throw new InvalidOperationException("Errore inaspettato");
	    	quantita = rs.getInt("old_quantita");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return quantita;
	}
	
	public int getID_Fornitore() {
		return ID_Fornitore;
	}

	public void setID_Fornitore(int iD_Fornitore) {
		ID_Fornitore = iD_Fornitore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getUnita_di_misura() {
		return unita_di_misura;
	}

	public void setUnita_di_misura(String unita_di_misura) {
		this.unita_di_misura = unita_di_misura;
	}

	public double getCosto_unitario() {
		return costo_unitario;
	}

	public void setCosto_unitario(double costo_unitario) {
		this.costo_unitario = costo_unitario;
	}

	public int getDimensione() {
		return dimensione;
	}

	public void setDimensione(int dimensione) {
		this.dimensione = dimensione;
	}

	public double getCosto_trasporto() {
		return costo_trasporto;
	}

	public void setCosto_trasporto(double costo_trasporto) {
		this.costo_trasporto = costo_trasporto;
	}

	public int getPezzatura_min_fornitore() {
		return pezzatura_min_fornitore;
	}

	public void setPezzatura_min_fornitore(int pezzatura_min_fornitore) {
		this.pezzatura_min_fornitore = pezzatura_min_fornitore;
	}

	public int getPezzatura_min_utente() {
		return pezzatura_min_utente;
	}

	public void setPezzatura_min_utente(int pezzatura_min_utente) {
		this.pezzatura_min_utente = pezzatura_min_utente;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Date getInizio_disponibilita() {
		return inizio_disponibilita;
	}

	public void setInizio_disponibilita(Date inizio_disponibilita) {
		this.inizio_disponibilita = inizio_disponibilita;
	}

	public Date getFine_disponibilita() {
		return fine_disponibilita;
	}

	public void setFine_disponibilita(Date fine_disponibilita) {
		this.fine_disponibilita = fine_disponibilita;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getID_Prodotto() {
		return ID_Prodotto;
	}
	
	public void setID_Prodotto(int iD_Prodotto) {
		ID_Prodotto = iD_Prodotto;
	}

	public boolean isAcquistabile() {
		return acquistabile;
	}

	public void setAcquistabile(boolean acquistabile) {
		this.acquistabile = acquistabile;
	}
}
