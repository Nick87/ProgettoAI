package gas.Controller.Utente;


import gas.DAO.Log;
import gas.DAO.Prodotto;
import gas.DAO.SchedaAcquisto;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import gas.Exception.ItemNotFoundException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class AggiornaQuantita extends ActionSupport
{	
	class OldNewQuantita
	{
		private int oldQuantita;
		private int newQuantita;
		public OldNewQuantita(int oldQuantita, int newQuantita) {			
			this.oldQuantita = oldQuantita;
			this.newQuantita = newQuantita;
		}
		public int getOldQuantita() {
			return oldQuantita;
		}
		public void setOldQuantita(int oldQuantita) {
			this.oldQuantita = oldQuantita;
		}
		public int getNewQuantita() {
			return newQuantita;
		}
		public void setNewQuantita(int newQuantita) {
			this.newQuantita = newQuantita;
		}
	}
	
	private Map<String, String> quantita;
	private Map<Integer, Integer> newQuantita;
	private List<Prodotto> listaProdotti;
	private Map<Integer, Integer> disponibilitaProdotti;
	private int idOrdine;
	private int idMembro;
	private boolean input;
	private boolean editable = true;
	
	public String execute()
	{
		this.input = false;
		int newAmount, idProdotto;
		newQuantita = new HashMap<Integer, Integer>();
		for(String key : quantita.keySet())
		{			
			try {
				idProdotto = Integer.parseInt(key);
				newAmount = Integer.parseInt(quantita.get(key));
				newQuantita.put(idProdotto, newAmount);
			} catch(NumberFormatException e) {
				System.out.println(e.getMessage());
				this.input = true;
				return Action.INPUT;
			}
		}
		try {
			SchedaAcquisto.Aggiorna_Crea_Scheda(idOrdine, idMembro, newQuantita, "AGGIORNA");
			addActionMessage("Aggiornamento completato con successo");
			Log.addLog("Aggiornata scheda d'acquisto con idOrdine " + idOrdine + " del membro " + idMembro);
		} catch (InvalidOperationException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (DBException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} finally {
			// Aggiorniamo lista prodotti e loro disponibilita' cosi' l'utente vede i valori aggiornati
			updateProdotti();
		}
		return Action.SUCCESS;
	}
	
	private void updateProdotti()
	{
		try {
			listaProdotti = Prodotto.getListaProdottiFromSchedaAcquisto(idOrdine, idMembro);
			disponibilitaProdotti = new HashMap<Integer, Integer>();
			for(Prodotto p : listaProdotti)
				disponibilitaProdotti.put(p.getID_Prodotto(),
										  Prodotto.getDisponibilitaProdotto(p.getID_Prodotto()));
		} catch (DBException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Map<String, String> getQuantita() {
		return quantita;
	}

	public void setQuantita(Map<String, String> quantita) {
		this.quantita = quantita;
	}

	public boolean isInput() {
		return input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<Prodotto> getListaProdotti() {
		return listaProdotti;
	}

	public void setListaProdotti(List<Prodotto> listaProdotti) {
		this.listaProdotti = listaProdotti;
	}

	public Map<Integer, Integer> getDisponibilitaProdotti() {
		return disponibilitaProdotti;
	}

	public void setDisponibilitaProdotti(Map<Integer, Integer> disponibilitaProdotti) {
		this.disponibilitaProdotti = disponibilitaProdotti;
	}
}
