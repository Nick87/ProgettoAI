package gas.Controller;

import gas.DAO.Date_Ordine;
import gas.DAO.Date_Ordine.TipoOrdine;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ListaOrdiniAperti extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private List<Date_Ordine> ordiniAperti;
	
	public String execute()
	{
		try {
			ordiniAperti = Date_Ordine.getOrdini(TipoOrdine.APERTO, idMembro, -1);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	public List<Date_Ordine> getOrdiniAperti() {
		return ordiniAperti;
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
}
