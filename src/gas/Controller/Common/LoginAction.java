package gas.Controller.Common;

import gas.DAO.Log;
import gas.DAO.Membro;
import gas.DAO.Messaggio;
import gas.DAO.Messaggio.TipoMessaggio;
import gas.DAO.Notifica;
import gas.DAO.Notifica.TipoNotifica;
import gas.Exception.DBException;
import gas.Exception.LoginException;

import java.sql.SQLException;
import java.util.Map;

import util.LoginStatus;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport
{	
	private String username;
	private String password;
	private String erroreDBMessage;
	private int numeroNotificheNonLette;
	private int numeroMessaggiNonLetti;
	private boolean erroreDB;
	
	public String execute()
	{
		this.erroreDBMessage = "";
		this.erroreDB = false;
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		if(sessionMap.containsKey("user") && sessionMap.containsKey("status"))
			return Action.SUCCESS;

		LoginStatus status;
		try
		{
			status = Membro.checkPassword(username, password);			
			sessionMap.put("user", status.getUsername());
			sessionMap.put("status", status);
			numeroNotificheNonLette = Notifica.getNumeroNotificheFromIdMembro(TipoNotifica.NON_LETTA, status.getID_Membro());
			numeroMessaggiNonLetti = Messaggio.getNumeroMessaggiFromIdMembro(TipoMessaggio.NON_LETTO, status.getID_Membro());
			Log.addLog("Utente " + username + " fa login");
		} catch (LoginException e) {
			addActionError(getText("error.login"));
			Log.addLog("Login errato. Username: " + username + ". Password: " + password);
			return Action.ERROR;
		} catch (DBException e) {
			this.erroreDBMessage = getText("error.DB");
			this.erroreDB = true;
			addActionError(getText("error.DB"));
			return "ErroreDB";
		} catch (SQLException e) {
			this.erroreDBMessage = "Errore query SQL";
			this.erroreDB = true;
			addActionError("Errore query SQL");
			return "ErroreDB";
		}
		return Action.SUCCESS;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getErroreDBMessage() {
		return erroreDBMessage;
	}
	public void setErroreDBMessage(String erroreDBMessage) {
		this.erroreDBMessage = erroreDBMessage;
	}
	public boolean isErroreDB() {
		return erroreDB;
	}
	public void setErroreDB(boolean erroreDB) {
		this.erroreDB = erroreDB;
	}
	public int getNumeroNotificheNonLette() {
		return numeroNotificheNonLette;
	}
	public void setNumeroNotificheNonLette(int numeroNotificheNonLette) {
		this.numeroNotificheNonLette = numeroNotificheNonLette;
	}
	public int getNumeroMessaggiNonLetti() {
		return numeroMessaggiNonLetti;
	}
	public void setNumeroMessaggiNonLetti(int numeroMessaggiNonLetti) {
		this.numeroMessaggiNonLetti = numeroMessaggiNonLetti;
	}
}
