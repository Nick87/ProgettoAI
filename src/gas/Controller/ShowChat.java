package gas.Controller;

import gas.DAO.Discussione;
import gas.DAO.Membro;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ShowChat extends ActionSupport
{
	private int idDiscussione;
	private int idMittente; // passato come parametro da sommarioDiscussioni.jsp
	private int idDestinatario;
	private String usernameMittente;
	private String usernameDestinatario;
	private Map<Integer, String> mapIdMembroUsername;
	private List<Discussione> chat;
	
	public String execute()
	{
		try {
			chat = Discussione.getDiscussioneFromId(idDiscussione);
			Discussione d = chat.get(0);
			mapIdMembroUsername = new HashMap<Integer, String>();
			mapIdMembroUsername.put(d.getID_Membro_Destinatario(), Membro.getUsernameFromId(d.getID_Membro_Destinatario()));
			mapIdMembroUsername.put(d.getID_Membro_Mittente(), Membro.getUsernameFromId(d.getID_Membro_Mittente()));
			if(d.getID_Membro_Mittente() == idMittente)
				idDestinatario = d.getID_Membro_Destinatario();
			else
				idDestinatario = d.getID_Membro_Mittente();
			this.usernameMittente = Membro.getUsernameFromId(idMittente);
			this.usernameDestinatario = Membro.getUsernameFromId(idDestinatario);
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

	public Map<Integer, String> getMapIdMembroUsername() {
		return mapIdMembroUsername;
	}

	public void setMapIdMembroUsername(Map<Integer, String> mapIdMembroUsername) {
		this.mapIdMembroUsername = mapIdMembroUsername;
	}

	public int getIdMittente() {
		return idMittente;
	}

	public void setIdMittente(int idMittente) {
		this.idMittente = idMittente;
	}

	public int getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(int idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getUsernameMittente() {
		return usernameMittente;
	}

	public void setUsernameMittente(String usernameMittente) {
		this.usernameMittente = usernameMittente;
	}

	public String getUsernameDestinatario() {
		return usernameDestinatario;
	}

	public void setUsernameDestinatario(String usernameDestinatario) {
		this.usernameDestinatario = usernameDestinatario;
	}
}
