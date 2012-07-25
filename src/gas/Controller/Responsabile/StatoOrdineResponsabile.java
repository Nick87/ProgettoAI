package gas.Controller.Responsabile;

import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import gas.DAO.Info_Ordine;
import gas.DAO.Info_Ordine.TipoOrdine;
import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

public class StatoOrdineResponsabile extends ActionSupport
{
	private Info_Ordine ordine;
	private int idMembro;
	private int idOrdine;

	public String execute()
	{
		try {
			ordine = Info_Ordine.getInfoOrdineFromIdResponsabile(TipoOrdine.CHIUSO, idMembro);
			idOrdine = ordine.getID_Ordine();
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

	public Info_Ordine getOrdine() {
		return ordine;
	}
	public void setOrdine(Info_Ordine ordine) {
		this.ordine = ordine;
	}
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	public int getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}
}
