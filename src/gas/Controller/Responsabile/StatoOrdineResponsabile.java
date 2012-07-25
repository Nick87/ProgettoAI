package gas.Controller.Responsabile;

import java.sql.SQLException;

import com.opensymphony.xwork2.Action;

import gas.DAO.Info_Ordine;
import gas.DAO.Info_Ordine.TipoOrdine;
import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

public class StatoOrdineResponsabile
{
	private Info_Ordine ordine;
	private int idMembro;
	private String tipoMembro;
	private String username;
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
	public int getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}
}
