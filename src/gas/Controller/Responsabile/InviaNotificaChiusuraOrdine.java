package gas.Controller.Responsabile;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class InviaNotificaChiusuraOrdine extends ActionSupport
{
	private int idOrdine;
	
	public String execute()
	{
		return Action.SUCCESS;
	}

	public int getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}
}
