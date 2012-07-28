package gas.Controller.Common;

import gas.DAO.Messaggio;
import gas.DAO.Messaggio.TipoMessaggio;
import gas.Exception.DBException;
import gas.Exception.ItemNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class MarkMessaggioAsReadUnread extends ActionSupport
{
	private InputStream inputStream;
	private int idMessaggio;
	private int idMembro;
	private String readUnread;
	
	public String execute()
	{
		int numeroMessaggiNonLetti = 0;
		String response = null;
		try {
			if(readUnread.equals("READ"))
				Messaggio.setLettoNonLetto(TipoMessaggio.LETTO, idMessaggio);
			else
				Messaggio.setLettoNonLetto(TipoMessaggio.NON_LETTO, idMessaggio);
			numeroMessaggiNonLetti = Messaggio.getNumeroMessaggiFromIdMembro(TipoMessaggio.NON_LETTO, idMembro);
			response = "{\"numeroMessaggiNonLetti\":" + numeroMessaggiNonLetti + "}";
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
	public int getIdMessaggio() {
		return idMessaggio;
	}
	public void setIdMessaggio(int idMessaggio) {
		this.idMessaggio = idMessaggio;
	}
	public int getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	public String getReadUnread() {
		return readUnread;
	}
	public void setReadUnread(String readUnread) {
		this.readUnread = readUnread;
	}
}
