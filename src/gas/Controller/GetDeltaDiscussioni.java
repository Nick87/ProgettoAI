package gas.Controller;

import gas.DAO.Discussione;
import gas.DAO.Membro;
import gas.Exception.DBException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		try
		{
			Map<String, Object> map;
			Map<Integer, String> mappaIdUsername;
			List<Discussione> deltaDiscussioni = Discussione.getDeltaDiscussioni(idDiscussione, lastIdMessaggioDiscussione);
			String json = "";
			if(deltaDiscussioni.size() > 0){
				mappaIdUsername = new HashMap<Integer, String>();
				Discussione d = deltaDiscussioni.get(0);
				mappaIdUsername.put(d.getID_Membro_Mittente(), Membro.getUsernameFromId(d.getID_Membro_Mittente()));
				mappaIdUsername.put(d.getID_Membro_Destinatario(), Membro.getUsernameFromId(d.getID_Membro_Destinatario()));
				map = new HashMap<String, Object>();
				map.put("mappaIdUsername", mappaIdUsername);
				map.put("deltaDiscussioni", deltaDiscussioni);
				Gson gson = new Gson();
				json = gson.toJson(map);
			}
			this.inputStream = new ByteArrayInputStream(json.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
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
