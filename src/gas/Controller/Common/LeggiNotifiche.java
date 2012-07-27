package gas.Controller.Common;

import gas.DAO.Notifica;
import gas.DAO.Notifica.TipoNotifica;
import gas.Exception.DBException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LeggiNotifiche extends ActionSupport
{
	private int idMembro;
	private List<Notifica> listaNotifiche;
	private Map<Integer, Integer> idNotificheNonLette;
	private int numeroNotificheNonLette;
	
	public String execute()
	{
		try
		{
			listaNotifiche = Notifica.getListaNotificheFromIdMembro(TipoNotifica.ANY, idMembro);
			idNotificheNonLette = new HashMap<Integer, Integer>();
			for(Notifica n : listaNotifiche)
				if(!n.isLetto())
					idNotificheNonLette.put(n.getID_Notifica(), n.getID_Notifica());
			numeroNotificheNonLette = Notifica.getNumeroNotificheFromIdMembro(TipoNotifica.NON_LETTA, idMembro);
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
	public List<Notifica> getListaNotifiche() {
		return listaNotifiche;
	}
	public void setListaNotifiche(List<Notifica> listaNotifiche) {
		this.listaNotifiche = listaNotifiche;
	}
	public int getNumeroNotificheNonLette() {
		return numeroNotificheNonLette;
	}
	public void setNumeroNotificheNonLette(int numeroNotificheNonLette) {
		this.numeroNotificheNonLette = numeroNotificheNonLette;
	}
	public Map<Integer, Integer> getIdNotificheNonLette() {
		return idNotificheNonLette;
	}
	public void setIdNotificheNonLette(HashMap<Integer, Integer> idNotificheNonLette) {
		this.idNotificheNonLette = idNotificheNonLette;
	}
}
