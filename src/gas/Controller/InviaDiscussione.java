package gas.Controller;

import java.sql.SQLException;
import java.util.List;

import gas.DAO.Membro;
import gas.DAO.Membro.memberType;
import gas.Exception.DBException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class InviaDiscussione extends ActionSupport
{
	private List<Membro> utenti;
	private int idMembro;
	private String tipoMembro;
	private String username;
	
	public String execute()
	{
		try {
			this.utenti = Membro.getMembriFromType(memberType.UTENTE);
			int index = 0;
			for(Membro m : utenti){
				if(m.getID_Membro() == this.idMembro){
					index = utenti.indexOf(m);
					break;
				}
			}
			// Elimino il mittente dalla lista. Non ha senso che mi mandi messaggi da solo
			this.utenti.remove(index);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	public List<Membro> getUtenti() {
		return utenti;
	}
	public void setUtenti(List<Membro> utenti) {
		this.utenti = utenti;
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