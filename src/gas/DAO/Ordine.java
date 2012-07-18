package gas.DAO;

import java.util.List;

public class Ordine
{
	private int ID_Ordine;
	private List<Prodotto> prodotti;
	
	public Ordine() {}
	
	public int getID_Ordine() {
		return ID_Ordine;
	}

	public void setID_Ordine(int iD_Ordine) {
		ID_Ordine = iD_Ordine;
	}

	public List<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
}
