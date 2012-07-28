package gas.Controller.Common;

import gas.DAO.Messaggio;
import gas.DAO.Messaggio.TipoMessaggio;
import gas.Exception.DBException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class EliminaMessaggio extends ActionSupport
{
	private InputStream inputStream;
	private int numeroMessaggiNonLetti;
	private int idMessaggio;
	private int idMembro;
	
	public String execute()
	{
		String response = "";
		try {
			Messaggio.eliminaMessaggio(idMessaggio);
			numeroMessaggiNonLetti = Messaggio.getNumeroMessaggiFromIdMembro(TipoMessaggio.NON_LETTO, idMembro);
			response = "{\"result\":\"ok\", \"numeroMessaggiNonLetti\":\"" + numeroMessaggiNonLetti + "\"}";
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
	public int getNumeroMessaggiNonLetti() {
		return numeroMessaggiNonLetti;
	}
	public void setNumeroMessaggiNonLetti(int numeroMessaggiNonLetti) {
		this.numeroMessaggiNonLetti = numeroMessaggiNonLetti;
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
}
