package gas.Controller.Utente;

import gas.DAO.Membro;
import gas.DAO.SchedaAcquisto;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class RimuoviDelega extends ActionSupport{
	
	private int ID_membro_che_acquista;
	private int idOrdine;
	private List<SchedaAcquisto> lista_dati_deleghe = new ArrayList<SchedaAcquisto>();
	private Map<Integer, String> dettagliDelegato;
	
	public String execute()
	{
		try
		{
			SchedaAcquisto.RimuoviDelega(ID_membro_che_acquista,idOrdine);
			lista_dati_deleghe = SchedaAcquisto.DeleghePerUtente(ID_membro_che_acquista);
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
	
	public int getID_membro_che_acquista() {
		return ID_membro_che_acquista;
	}
	public void setID_membro_che_acquista(int iD_membro_che_acquista) {
		ID_membro_che_acquista = iD_membro_che_acquista;
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
}

