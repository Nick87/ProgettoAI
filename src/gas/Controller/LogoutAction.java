package gas.Controller;

import gas.DAO.Log;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport
{	
	public String execute()
	{
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		if(sessionMap.containsKey("user")){
			Log.addLog("Utente " + sessionMap.get("user") + " fa logout");
			sessionMap.remove("user");
			sessionMap.remove("status");
		}
		return Action.SUCCESS;
	}
}
