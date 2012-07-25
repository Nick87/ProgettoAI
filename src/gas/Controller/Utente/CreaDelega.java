package gas.Controller.Utente;

import gas.DAO.Log;
import gas.DAO.Membro;
import gas.DAO.Messaggio;
import gas.DAO.SchedaAcquisto;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import gas.Exception.ItemNotFoundException;

import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class CreaDelega extends ActionSupport
{
	private int idMembro;
	private String ordine_scelto;
	private String utente_scelto;
	
	public String execute()
	{
		int idordinescelto = Integer.parseInt(ordine_scelto);
		int idutentescelto = Integer.parseInt(utente_scelto);
		try
		{
			SchedaAcquisto.Aggiungi_Delega(idMembro,idordinescelto,idutentescelto);
			Log.addLog("Aggiunta delega del membro "+idutentescelto+" da parte del membro "+idMembro+" per l'ordine "+idordinescelto);
			//Dopo che l'utente crea una delega, viene inviato un messaggio al responsabile dell'ordine
			int id_responsabile = 5;
			Messaggio.inserisciMessaggio(id_responsabile, "L'utente " + Membro.getUsernameFromId(idMembro) + " ha delegato il membro " + Membro.getUsernameFromId(idutentescelto) + " al ritiro dell'ordine " + idordinescelto);
			Log.addLog("Inviato messaggio al membro " + id_responsabile);
			//Viene inviato un messaggio al delegato 
			Messaggio.inserisciMessaggio(idutentescelto, "L'utente " + Membro.getUsernameFromId(idMembro) + " ti ha delegato al ritiro dell'ordine " + idordinescelto);
			Log.addLog("Inviato messaggio al membro "+idutentescelto);
		} catch (InvalidOperationException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (DBException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			addActionError(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	public String getOrdine_scelto() {
		return ordine_scelto;
	}
	public void setOrdine_scelto(String ordine_scelto) {
		this.ordine_scelto = ordine_scelto;
	}
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	public String getUtente_scelto() {
		return utente_scelto;
	}
	public void setUtente_scelto(String utente_scelto) {
		this.utente_scelto = utente_scelto;
	}
}
