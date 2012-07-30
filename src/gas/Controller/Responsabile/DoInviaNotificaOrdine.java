package gas.Controller.Responsabile;

import gas.DAO.Info_Ordine;
import gas.DAO.Info_Ordine.TipoOrdine;
import gas.DAO.Membro;
import gas.DAO.Notifica;
import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DoInviaNotificaOrdine extends ActionSupport
{
	private int idOrdine;
	private int idMembro;
	private boolean successo;
	private List<Info_Ordine> lista_ordini;
	
	public String execute()
	{
		try
		{
			List<Integer> membriDaNotificare = Membro.getIdMembriFromOrdine(idOrdine); // Notifichiamo utenti e fornitore
			String content = "Ordine " + idOrdine + " chiuso ";
			content += (successo) ? "con successo" : "senza successo";
			for(Integer idMembro : membriDaNotificare)
				Notifica.addNotifica(content, idMembro);
			Info_Ordine.setNotificato(idOrdine);
			//Siccome redirigiamo nella stessa pagina, cosi' il reponsabile visualizza la lista aggiornata
			lista_ordini = Info_Ordine.getListaInfoOrdineFromIdResponsabile(TipoOrdine.CHIUSO_NON_NOTIFICATO, idMembro);
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
	public List<Info_Ordine> getLista_ordini() {
		return lista_ordini;
	}
	public void setLista_ordini(List<Info_Ordine> lista_ordini) {
		this.lista_ordini = lista_ordini;
	}
	public boolean isSuccesso() {
		return successo;
	}
	public void setSuccesso(boolean successo) {
		this.successo = successo;
	}
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
}
