package gas.Controller.Utente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gas.DAO.Info_Ordine;
import gas.DAO.Membro;
import gas.Exception.DBException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class GetMembriDelegabili extends ActionSupport
{
	private int idMembro;
	private int ordine_scelto;
	private List<Info_Ordine> listaOrdiniCompleta;
	private List<Integer> listaOrdini = new ArrayList<Integer>();
	private List<Membro> listaUtentiCompleta;
	Map<Integer, String> listaUtentiDelegabili = new HashMap<Integer, String>();
	
	public String execute()
	{
		try {
			listaOrdiniCompleta = Info_Ordine.getOrdini(Info_Ordine.TipoOrdine.CHIUSO, idMembro, 1);
			for(Info_Ordine i : listaOrdiniCompleta)
				listaOrdini.add(i.getID_Ordine());
			listaUtentiCompleta = Membro.getMembriDelegabili(idMembro,ordine_scelto);
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
	public int getOrdine_scelto() {
		return ordine_scelto;
	}
	public void setOrdine_scelto(int ordine_scelto) {
		this.ordine_scelto = ordine_scelto;
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
	public List<Info_Ordine> getListaOrdiniCompleta() {
		return listaOrdiniCompleta;
	}
	public void setListaOrdiniCompleta(List<Info_Ordine> listaOrdiniCompleta) {
		this.listaOrdiniCompleta = listaOrdiniCompleta;
	}
	public List<Integer> getListaOrdini() {
		return listaOrdini;
	}
	public void setListaOrdini(List<Integer> listaOrdini) {
		this.listaOrdini = listaOrdini;
	}
}
