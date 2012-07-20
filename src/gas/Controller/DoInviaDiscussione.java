package gas.Controller;
import com.google.gson.*;

import gas.DAO.Discussione;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DoInviaDiscussione extends ActionSupport
{
	private int idMittente;
	private int idDestinatario;
	private String messageContent;
	private InputStream inputStream;
    
	public String execute()
	{
		Gson gson = new Gson();
		String json;
		Map<String, String> message = new HashMap<String, String>();
		try
		{
			Discussione.addDiscussione(idMittente, idDestinatario, messageContent);
			message.put("result", "success");
			message.put("message", "Messaggio inviato correttamente");
			json = gson.toJson(message);
			setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			message.put("result", "error");
			message.put("message", e.getMessage());
			json = gson.toJson(message);
			try {
				setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e1) {
				System.out.println(e1.getMessage());
			}
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			message.put("result", "error");
			message.put("message", e.getMessage());
			json = gson.toJson(message);
			try {
				setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e1) {
				System.out.println(e1.getMessage());
			}
			return Action.ERROR;
		} catch (InvalidOperationException e) {
			System.out.println(e.getMessage());
			message.put("result", "error");
			message.put("message", e.getMessage());
			json = gson.toJson(message);
			try {
				setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e1) {
				System.out.println(e1.getMessage());
			}
			return Action.ERROR;
		} catch (DBException e) {
			System.out.println(e.getMessage());
			message.put("result", "error");
			message.put("message", e.getMessage());
			json = gson.toJson(message);
			try {
				setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e1) {
				System.out.println(e1.getMessage());
			}
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	public int getIdMittente() {
		return idMittente;
	}

	public void setIdMittente(int idMittente) {
		this.idMittente = idMittente;
	}

	public int getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(int idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
