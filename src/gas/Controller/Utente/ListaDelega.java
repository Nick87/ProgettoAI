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

public class ListaDelega extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private List<SchedaAcquisto> lista_dati_deleghe = new ArrayList<SchedaAcquisto>();
	private Map<Integer, String> dettagliDelegato;
	
	public String execute()
	{
		try
		{
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
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	public String getTipoMembro() {
		return tipoMembro;
	}
	public void setTipoMembro(String tipoMembro) {
		this.tipoMembro = tipoMembro;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Map<Integer, String> getDettagliDelegato() {
		return dettagliDelegato;
	}
	public void setDettagliDelegato(Map<Integer, String> dettagliDelegato) {
		this.dettagliDelegato = dettagliDelegato;
	}
}

