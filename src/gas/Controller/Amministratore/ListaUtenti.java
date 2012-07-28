package gas.Controller.Amministratore;

import gas.DAO.Membro;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;

import java.sql.SQLException;
import java.util.List;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ListaUtenti extends ActionSupport
{	
	private int idMembro;
	private String tipoMembro;
	private String username;
	private List<Membro> lista_utenti;
	public String execute()
	{
		try
		{			
			/*if(Membro.getListaUtentiFromTipoMembro(tipoMembro) == null)
				return Action.ERROR;*/
			lista_utenti = Membro.getListaUtentiFromTipoMembro(tipoMembro);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (InvalidOperationException e) {
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
	public List<Membro> getLista_utenti() {
		return lista_utenti;
	}
	public void setLista_utenti(List<Membro> lista_utenti) {
		this.lista_utenti = lista_utenti;
	}
	
	
	
}
