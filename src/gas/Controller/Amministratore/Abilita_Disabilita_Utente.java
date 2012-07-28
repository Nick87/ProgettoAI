package gas.Controller.Amministratore;

import gas.DAO.Membro;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class Abilita_Disabilita_Utente extends ActionSupport
{	
	private List<Membro> lista_utenti;
	private int idMembro;
	private int id_membro_selezionato;
	private String tipoMembro;
	private String operazione;
	public String execute()
	{
		try
		{
			/*if(!Membro.modificaAbilitazioneUtente(id_membro_selezionato, tipoMembro, operazione))
				return Action.ERROR;*/
			Membro.modificaAbilitazioneUtente(id_membro_selezionato, tipoMembro, operazione);
			/*if(Membro.getListaUtentiFromTipoMembro(tipoMembro) == null)
				return Action.ERROR;*/
			lista_utenti = Membro.getListaUtentiFromTipoMembro(tipoMembro);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (InvalidOperationException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	public List<Membro> getLista_utenti() {
		return lista_utenti;
	}
	public void setLista_utenti(List<Membro> lista_utenti) {
		this.lista_utenti = lista_utenti;
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
	public String getOperazione() {
		return operazione;
	}
	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	public int getId_membro_selezionato() {
		return id_membro_selezionato;
	}
	public void setId_membro_selezionato(int id_membro_selezionato) {
		this.id_membro_selezionato = id_membro_selezionato;
	}
}
