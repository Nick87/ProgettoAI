package gas.Controller.Common;

import gas.DAO.Messaggio;
import gas.DAO.Messaggio.TipoMessaggio;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LeggiMessaggi extends ActionSupport
{
	private int idMembro;
	private List<Messaggio> listaMessaggi;
	private Map<Integer, Integer> idMessaggiNonLetti;
	private int numeroMessaggiNonLetti;
	
	public String execute()
	{
		try
		{
			listaMessaggi = Messaggio.getListaMessaggiFromIdMembro(TipoMessaggio.ANY, idMembro);
			idMessaggiNonLetti = new HashMap<Integer, Integer>();
			for(Messaggio m : listaMessaggi)
				if(!m.isLetto())
					idMessaggiNonLetti.put(m.getID_Messaggio(), m.getID_Messaggio());
			numeroMessaggiNonLetti = Messaggio.getNumeroMessaggiFromIdMembro(TipoMessaggio.NON_LETTO, idMembro);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (DBException e) {
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
	public List<Messaggio> getListaMessaggi() {
		return listaMessaggi;
	}
	public void setListaMessaggi(List<Messaggio> listaMessaggi) {
		this.listaMessaggi = listaMessaggi;
	}
	public Map<Integer, Integer> getIdMessaggiNonLetti() {
		return idMessaggiNonLetti;
	}
	public void setIdMessaggiNonLetti(Map<Integer, Integer> idMessaggiNonLetti) {
		this.idMessaggiNonLetti = idMessaggiNonLetti;
	}
	public int getNumeroMessaggiNonLetti() {
		return numeroMessaggiNonLetti;
	}
	public void setNumeroMessaggiNonLetti(int numeroMessaggiNonLetti) {
		this.numeroMessaggiNonLetti = numeroMessaggiNonLetti;
	}
}
