package gas.Controller.Utente;

import gas.DAO.Info_Ordine;
import gas.DAO.Log;
import gas.DAO.Membro;
import gas.DAO.Messaggio;
import gas.DAO.SchedaAcquisto;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class RimuoviDelega extends ActionSupport
{	
	private int idOrdine;
	private int ID_Membro_che_ritira;
	private int idMembro;
	private List<SchedaAcquisto> lista_dati_deleghe = new ArrayList<SchedaAcquisto>();
	private Map<Integer, String> dettagliDelegato;
	
	public String execute()
	{
		try
		{			
			SchedaAcquisto.RimuoviDelega(idMembro,idOrdine);
			int id_responsabile = Info_Ordine.getIdResponsabilefromIdOrdine(idOrdine);
			//Dopo che l'utente rimuove una delega, viene inviato un messaggio al responsabile dell'ordine
			Messaggio.inserisciMessaggio(id_responsabile, "L'utente " + Membro.getUsernameFromId(idMembro) + " ha rimosso la delega per il membro " + Membro.getUsernameFromId(ID_Membro_che_ritira) + " per il ritiro dell'ordine " + idOrdine);
			Log.addLog("Inviato messaggio al membro " + id_responsabile);
			//Viene inviato un messaggio al delegato 
			Messaggio.inserisciMessaggio(ID_Membro_che_ritira, "L'utente " + Membro.getUsernameFromId(idMembro) + " ha rimosso la tua delega per il ritiro dell'ordine " + idOrdine);
			Log.addLog("Inviato messaggio al membro " + ID_Membro_che_ritira);
			lista_dati_deleghe = SchedaAcquisto.DeleghePerUtente(idMembro);
			if(dettagliDelegato == null)
				dettagliDelegato = new HashMap<Integer, String>();
			dettagliDelegato.clear();
			for(SchedaAcquisto s : lista_dati_deleghe) {
				dettagliDelegato.put(s.getID_Membro_che_ritira(),
									  Membro.getNomeCognomeFromId(s.getID_Membro_che_ritira()));
			}
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS; 
	}
	
	public List<SchedaAcquisto> getLista_dati_deleghe() {
		return lista_dati_deleghe;
	}
	public void setLista_dati_deleghe(List<SchedaAcquisto> lista_dati_deleghe) {
		this.lista_dati_deleghe = lista_dati_deleghe;
	}
	public int getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}
	public Map<Integer, String> getDettagliDelegato() {
		return dettagliDelegato;
	}
	public void setDettagliDelegato(Map<Integer, String> dettagliDelegato) {
		this.dettagliDelegato = dettagliDelegato;
	}

	public int getID_Membro_che_ritira() {
		return ID_Membro_che_ritira;
	}

	public void setID_Membro_che_ritira(int iD_Membro_che_ritira) {
		ID_Membro_che_ritira = iD_Membro_che_ritira;
	}

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	
}

