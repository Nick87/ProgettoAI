package gas.Controller.Amministratore;

import gas.DAO.Log;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ScaricaLog extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private List<Log> lista_log;
	private InputStream fileInputStream;
	
	public String execute()
	{
		try
		{						
			lista_log = Log.getLogContent(tipoMembro);
			FileOutputStream prova = new FileOutputStream("out.log");
	        PrintStream scrivi = new PrintStream(prova);
	        for(Log l : lista_log)
	        	scrivi.println(l.getID_Operazione() + " " + l.getContent() + " " + l.getTimestamp().toString());
	        scrivi.flush();
	        scrivi.close();
			File f = new File("out.log");
			fileInputStream = new FileInputStream(f);
			f.delete();
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (InvalidOperationException e) {
			System.out.println(e.getMessage());
			return Action.ERROR;
		} catch (IOException e) {
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
	public String getTipoMembro() {
		return tipoMembro;
	}
	public void setTipoMembro(String tipoMembro) {
		this.tipoMembro = tipoMembro;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Log> getLista_log() {
		return lista_log;
	}
	public void setLista_log(List<Log> lista_log) {
		this.lista_log = lista_log;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
}
