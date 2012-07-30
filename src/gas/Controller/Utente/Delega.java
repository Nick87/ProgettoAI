package gas.Controller.Utente;

import gas.DAO.Info_Ordine;
import gas.Exception.DBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class Delega extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private List<Info_Ordine> listaOrdiniCompleta;
	private List<Integer> listaOrdini;
	
	public String execute()
	{
		try
		{
			listaOrdini = new ArrayList<Integer>();
			listaOrdiniCompleta = Info_Ordine.getListaOrdiniFromIdMembroCheAcquista(Info_Ordine.TipoOrdine.CHIUSO, idMembro, 1);
			for(Info_Ordine i : listaOrdiniCompleta)
				listaOrdini.add(i.getID_Ordine());
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
	public List<Info_Ordine> getListaOrdiniCompleta() {
		return listaOrdiniCompleta;
	}
	public void setListaOrdiniCompleta(List<Info_Ordine> listaOrdiniCompleta) {
		this.listaOrdiniCompleta = listaOrdiniCompleta;
	}
	public List<Integer> getListaOrdini() {
		return listaOrdini;
	}
	public void setListaOrdini(List<Integer> listaOrdini) {
		this.listaOrdini = listaOrdini;
	}
}
