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
	private String timestamp;
	private InputStream inputStream;
	
	public String execute()
	{
		Gson gson = new Gson();
		try
		{
			/*SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - kk:mm:ss");
			Date parsedDate = dateFormat.parse(timestamp);
			Timestamp ts = new Timestamp(parsedDate.getTime());
			List<Discussione> deltaDiscussioni = Discussione.getDeltaDiscussioni(idDiscussione, ts);*/
			
			
			this.inputStream = new ByteArrayInputStream("CIAO".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} /*catch (ParseException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		}*/
		return Action.SUCCESS;
	}

	public int getIdDiscussione() {
		return idDiscussione;
	}

	public void setIdDiscussione(int idDiscussione) {
		this.idDiscussione = idDiscussione;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
