package gas.Controller.Common;

import gas.DAO.Notifica;
import gas.DAO.Notifica.TipoNotifica;
import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class MarkAsReadUnread extends ActionSupport
{
	private InputStream inputStream;
	private int idNotifica;
	private int idMembro;
	private String readUnread;
	
	public String execute()
	{
		int numeroNotificheNonLette = 0;
		String response = null;
		try {
			if(readUnread.equals("READ"))
				Notifica.setLettaNonLetta(TipoNotifica.LETTA, idNotifica);
			else
				Notifica.setLettaNonLetta(TipoNotifica.NON_LETTA, idNotifica);
			numeroNotificheNonLette = Notifica.getNumeroNotificheFromIdMembro(TipoNotifica.NON_LETTA, idMembro);
			response = "{\"numeroNotificheNonLette\":" + numeroNotificheNonLette + "}";
			this.inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (UnsupportedEncodingException e) {
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
	public String getReadUnread() {
		return readUnread;
	}
	public void setReadUnread(String readUnread) {
		this.readUnread = readUnread;
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
