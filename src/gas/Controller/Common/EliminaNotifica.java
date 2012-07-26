package gas.Controller.Common;

import gas.DAO.Notifica;
import gas.DAO.Notifica.TipoNotifica;
import gas.Exception.DBException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class EliminaNotifica extends ActionSupport
{
	private InputStream inputStream;
	int numeroNotificheNonLette;
	private int idNotifica;
	private int idMembro;
	
	public String execute()
	{
		String response = "";
		try {
			Notifica.eliminaNotifica(idNotifica);
			numeroNotificheNonLette = Notifica.getNumeroNotificheFromIdMembro(TipoNotifica.NON_LETTA, idMembro);
			response = "{\"result\":\"ok\", \"numeroNotificheNonLette\":\"" + numeroNotificheNonLette + "\"}";
			this.inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public int getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(int idNotifica) {
		this.idNotifica = idNotifica;
	}
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
}
