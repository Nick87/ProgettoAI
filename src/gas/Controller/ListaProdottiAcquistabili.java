package gas.Controller;

import gas.DAO.Prodotto;
import gas.Exception.DBException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ListaProdottiAcquistabili extends ActionSupport
{	
	private int idMembro;
	private int idOrdine;
	private List<Prodotto>listaProdotti;
	private Map<Integer, Integer> disponibilitaProdotti;
	
	public String execute()
	{
		try {
			listaProdotti = Prodotto.getListaProdottiFromOrdine(idOrdine);
			
			if(disponibilitaProdotti == null)
				disponibilitaProdotti = new HashMap<Integer, Integer>();
			disponibilitaProdotti.clear();
			for(Prodotto p : listaProdotti)
				disponibilitaProdotti.put(p.getID_Prodotto(),
										  Prodotto.getDisponibilitaProdotto(p.getID_Prodotto()));
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	public Map<Integer, Integer> getDisponibilitaProdotti() {
		return disponibilitaProdotti;
	}

	public void setDisponibilitaProdotti(Map<Integer, Integer> disponibilitaProdotti) {
		this.disponibilitaProdotti = disponibilitaProdotti;
	}

	public void setListaProdotti(List<Prodotto> listaProdotti) {
		this.listaProdotti = listaProdotti;
	}
	
	public List<Prodotto> getListaProdotti() {
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
