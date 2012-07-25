package gas.Controller.Utente;

import gas.DAO.Info_Ordine;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.List;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class MieiOrdini extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private String tipoOrdini;
	private List<Info_Ordine> listaOrdini;
	
	public String execute()
	{
		try {
			if(tipoOrdini.equals("aperti"))
				listaOrdini = Info_Ordine.getOrdini(Info_Ordine.TipoOrdine.APERTO, idMembro, 1);
			else
				listaOrdini = Info_Ordine.getOrdini(Info_Ordine.TipoOrdine.CHIUSO, idMembro, 1);
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
	public String getTipoOrdini() {
		return tipoOrdini;
	}
	public void setTipoOrdini(String tipoOrdini) {
		this.tipoOrdini = tipoOrdini;
	}
	public List<Info_Ordine> getListaOrdini() {
		return listaOrdini;
	}
	public void setListaOrdini(List<Info_Ordine> listaOrdini) {
		this.listaOrdini = listaOrdini;
	}
}