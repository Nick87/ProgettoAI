package gas.Controller;

import gas.DAO.Discussione;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ShowChat extends ActionSupport
{
	private int idDiscussione;
	private List<Discussione> chat;
	
	public String execute()
	{
		try {
			setChat(Discussione.getDiscussioneFromId(idDiscussione));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	public int getIdDiscussione() {
		return idDiscussione;
	}

	public void setIdDiscussione(int idDiscussione) {
		this.idDiscussione = idDiscussione;
	}

	public List<Discussione> getChat() {
		return chat;
	}

	public void setChat(List<Discussione> chat) {
		this.chat = chat;
	}
}
