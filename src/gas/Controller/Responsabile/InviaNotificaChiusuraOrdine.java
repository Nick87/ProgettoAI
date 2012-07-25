package gas.Controller.Responsabile;

import java.sql.SQLException;
import java.util.List;

import gas.DAO.Info_Ordine;
import gas.DAO.Membro;
import gas.DAO.Notifica;
import gas.DAO.Info_Ordine.TipoOrdine;
import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class InviaNotificaChiusuraOrdine extends ActionSupport
{
	private int idOrdine;
	private int idMembro;
	private Info_Ordine ordine;
	private List<Integer> membriDaNotificare;
	
	public String execute()
	{
		try
		{
			// Dato che redirigo alla stessa pagina, il responsabile deve continuare a visualizzare l'ordine
			ordine = Info_Ordine.getInfoOrdineFromIdResponsabile(TipoOrdine.CHIUSO, idMembro);
			membriDaNotificare = Membro.getIdMembriFromOrdine(idOrdine);
			for(Integer idMembro : membriDaNotificare)
				Notifica.addNotifica("Chiuso ordine " + idOrdine, idMembro);
			addActionMessage("Notifica inviata con successo");
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
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
	public Info_Ordine getOrdine() {
		return ordine;
	}
	public void setOrdine(Info_Ordine ordine) {
		this.ordine = ordine;
	}
	public List<Integer> getUtentiDaNotificare() {
		return membriDaNotificare;
	}
	public void setUtentiDaNotificare(List<Integer> utentiDaNotificare) {
		this.membriDaNotificare = utentiDaNotificare;
	}
}
