package gas.Controller.Utente;

import gas.DAO.Info_Ordine;
import gas.DAO.Info_Ordine.TipoOrdine;
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
	private List<Info_Ordine> ordiniAperti;
	
	public String execute()
	{
		try {
			setOrdiniAperti(Info_Ordine.getOrdini(TipoOrdine.APERTO, idMembro, -1));
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	public List<Info_Ordine> getOrdiniAperti() {
		return ordiniAperti;
	}
	public void setOrdiniAperti(List<Info_Ordine> ordiniAperti) {
		this.ordiniAperti = ordiniAperti;
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
