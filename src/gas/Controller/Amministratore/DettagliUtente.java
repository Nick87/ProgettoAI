package gas.Controller.Amministratore;

import gas.DAO.Membro;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;
import java.sql.SQLException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DettagliUtente extends ActionSupport
{
	private int idMembro;
	private int id_membro_dettaglio;
	private String tipoMembro;
	private Membro info_membro;
	
	public String execute()
	{
		try {
			info_membro = Membro.getDettaglioUtente(id_membro_dettaglio, tipoMembro);
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
	
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	public int getId_membro_dettaglio() {
		return id_membro_dettaglio;
	}
	public void setId_membro_dettaglio(int id_membro_dettaglio) {
		this.id_membro_dettaglio = id_membro_dettaglio;
	}
	public String getTipoMembro() {
		return tipoMembro;
	}
	public void setTipoMembro(String tipoMembro) {
		this.tipoMembro = tipoMembro;
	}
	public Membro getInfo_membro() {
		return info_membro;
	}
	public void setInfo_membro(Membro info_membro) {
		this.info_membro = info_membro;
	}
}
