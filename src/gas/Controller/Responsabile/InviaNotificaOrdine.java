package gas.Controller.Responsabile;

import gas.DAO.Info_Ordine;
import gas.DAO.Info_Ordine.TipoOrdine;
import gas.Exception.DBException;
import java.sql.SQLException;
import java.util.List;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class InviaNotificaOrdine extends ActionSupport
{
	private int idMembro;
	private List<Info_Ordine> lista_ordini;
	
	public String execute()
	{
		try {
			lista_ordini = Info_Ordine.getListaInfoOrdineFromIdResponsabile(TipoOrdine.CHIUSO_NON_NOTIFICATO, idMembro);
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
	public List<Info_Ordine> getLista_ordini() {
		return lista_ordini;
	}
	public void setLista_ordini(List<Info_Ordine> lista_ordini) {
		this.lista_ordini = lista_ordini;
	}
}
