package gas.Controller;

import gas.DAO.Discussione;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class GetDeltaDiscussioni extends ActionSupport
{
	private int idDiscussione;
	private int lastIdMessaggioDiscussione;
	private InputStream inputStream;
	
	public String execute()
	{
		Gson gson = new Gson();
		try
		{
			List<Discussione> deltaDiscussioni = Discussione.getDeltaDiscussioni(idDiscussione, lastIdMessaggioDiscussione);
			this.inputStream = new ByteArrayInputStream("CIAO".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	public int getIdDiscussione() {
		return idDiscussione;
	}

	public void setIdDiscussione(int idDiscussione) {
		this.idDiscussione = idDiscussione;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int getLastIdMessaggioDiscussione() {
		return lastIdMessaggioDiscussione;
	}

	public void setLastIdMessaggioDiscussione(int lastIdMessaggioDiscussione) {
		this.lastIdMessaggioDiscussione = lastIdMessaggioDiscussione;
	}
}
