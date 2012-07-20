package gas.Controller;

import gas.DAO.Discussione;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SommarioDiscussioni extends ActionSupport
{
	private Map<Integer, String> sommarioDiscussioni;
	private int idMembro;
	private String tipoMembro;
	private String username;
	
	public String execute()
	{
		try {
			sommarioDiscussioni = Discussione.getSommarioDiscussioniFromIdMembro(idMembro);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	public Map<Integer, String> getSommarioDiscussioni() {
		return sommarioDiscussioni;
	}

	public void setSommarioDiscussioni(Map<Integer, String> sommarioDiscussioni) {
		this.sommarioDiscussioni = sommarioDiscussioni;
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
