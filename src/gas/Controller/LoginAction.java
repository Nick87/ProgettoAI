package gas.Controller;

import gas.DAO.Log;
import gas.DAO.Membro;
import gas.Exception.DBException;
import gas.Exception.LoginException;

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
	private boolean erroreDB;
	
	public String execute()
	{
		this.erroreDBMessage = "";
		this.erroreDB = false;
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		if(sessionMap.containsKey("user") && sessionMap.containsKey("status"))
			return Action.SUCCESS;

		LoginStatus status;
		try {
			status = Membro.checkPassword(username, password);
		} catch (LoginException e) {
			addActionError(getText("error.login"));
			Log.addLog("Login errato. Username: " + username + ". Password: " + password);
			return Action.ERROR;
		} catch (DBException e) {
			this.erroreDBMessage = getText("error.DB");
			this.erroreDB = true;
			addActionError(getText("error.DB"));
			return "ErroreDB";
		}
		
		sessionMap.put("user", status.getUsername());
		sessionMap.put("status", status);
		Log.addLog("Utente " + username + " fa login");
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
}
