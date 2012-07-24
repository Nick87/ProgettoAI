package gas.Controller;

import gas.DAO.Date_Ordine;
import gas.DAO.Membro;
import gas.Exception.DBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class Delega extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private List<Date_Ordine> listaOrdiniCompleta;
	private List<Integer> listaOrdini = new ArrayList<Integer>();
	private List<Membro> listaUtentiCompleta;
	private Map<Integer, String> listaUtentiDelegabili = new HashMap<Integer, String>();
	
	public String execute()
	{
		try
		{
			listaOrdiniCompleta = Date_Ordine.getOrdini(Date_Ordine.TipoOrdine.CHIUSO, idMembro, 1);
			listaUtentiCompleta = Membro.getMembriDelegabili(idMembro);
			for(Date_Ordine d : listaOrdiniCompleta)
				listaOrdini.add(d.getID_Ordine());
			for(Membro m: listaUtentiCompleta)
					listaUtentiDelegabili.put(m.getID_Membro(), m.getNome()+" "+m.getCognome());
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS; 
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
	public List<Date_Ordine> getListaOrdiniCompleta() {
		return listaOrdiniCompleta;
	}
	public void setListaOrdiniCompleta(List<Date_Ordine> listaOrdiniCompleta) {
		this.listaOrdiniCompleta = listaOrdiniCompleta;
	}
	public List<Integer> getListaOrdini() {
		return listaOrdini;
	}
	public void setListaOrdini(List<Integer> listaOrdini) {
		this.listaOrdini = listaOrdini;
	}
	public List<Membro> getListaUtentiCompleta() {
		return listaUtentiCompleta;
	}
	public void setListaUtentiCompleta(List<Membro> listaUtentiCompleta) {
		this.listaUtentiCompleta = listaUtentiCompleta;
	}
	public Map<Integer, String> getListaUtentiDelegabili() {
		return listaUtentiDelegabili;
	}
	public void setListaUtentiDelegabili(Map<Integer, String> listaUtentiDelegabili) {
		this.listaUtentiDelegabili = listaUtentiDelegabili;
	}
}
