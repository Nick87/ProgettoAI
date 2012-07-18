package gas.Controller;

import gas.DAO.Date_Ordine;
import gas.DAO.Prodotto;
import gas.Exception.DBException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DettaglioOrdine extends ActionSupport
{
	private int idOrdine;
	private int idMembro;
	private List<Prodotto> listaProdotti;
	private Map<Integer, Integer> disponibilitaProdotti;
	private boolean editable;
	
	public DettaglioOrdine() {}
	
	public String execute()
	{
		try {
			if(Date_Ordine.isOrdineChiuso(idOrdine))
				this.editable = false;
			else
				this.editable = true;
			listaProdotti = Prodotto.getListaProdottiFromSchedaAcquisto(idOrdine, idMembro);
			if(disponibilitaProdotti == null)
				disponibilitaProdotti = new HashMap<Integer, Integer>();
			disponibilitaProdotti.clear();
			for(Prodotto p : listaProdotti)
				disponibilitaProdotti.put(p.getID_Prodotto(),
										  Prodotto.getDisponibilitaProdotto(p.getID_Prodotto()));
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}

	public List<Prodotto> getListaProdotti() {
		return listaProdotti;
	}

	public void setListaProdotti(List<Prodotto> listaProdotti) {
		this.listaProdotti = listaProdotti;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Map<Integer, Integer> getDisponibilitaProdotti() {
		return disponibilitaProdotti;
	}

	public void setDisponibilitaProdotti(Map<Integer, Integer> disponibilitaProdotti) {
		this.disponibilitaProdotti = disponibilitaProdotti;
	}
}
