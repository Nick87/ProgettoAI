package gas.Controller.Amministratore;

import gas.DAO.Log;
import gas.Exception.DBException;
import gas.Exception.InvalidOperationException;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ConsultaLog extends ActionSupport
{
	private int idMembro;
	private String tipoMembro;
	private String username;
	private String num_pag;
	private int num_pagina_int;
	private List<Log> lista_log;
	
	public String execute()
	{
		try {						
			num_pagina_int=Integer.parseInt(num_pag);
			lista_log = Log.getLogContentforPage(num_pagina_int);
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
	public String getNum_pag() {
		return num_pag;
	}
	public void setNum_pag(String num_pag) {
		this.num_pag = num_pag;
	}
	public List<Log> getLista_log() {
		return lista_log;
	}
	public void setLista_log(List<Log> lista_log) {
		this.lista_log = lista_log;
	}
	public int getNum_pagina_int() {
		return num_pagina_int;
	}
	public void setNum_pagina_int(int num_pagina_int) {
		this.num_pagina_int = num_pagina_int;
	}
}