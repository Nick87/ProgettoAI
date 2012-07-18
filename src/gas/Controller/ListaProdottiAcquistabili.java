package gas.Controller;

import gas.DAO.Prodotto;
import gas.Exception.DBException;

import java.util.ArrayList;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ListaProdottiAcquistabili extends ActionSupport
{	
	private int idMembro;
	private int idOrdine;
	ArrayList<Prodotto>listaProdotti;
	
	public String execute()
	{
		try {
			listaProdotti = (ArrayList<Prodotto>) Prodotto.getListaProdottiFromOrdine(idOrdine);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	public ArrayList<Prodotto> getListaProdotti() {
		return listaProdotti;
	}

	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}
}
