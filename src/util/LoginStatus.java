package util;

public class LoginStatus
{
	private String tipoMembro;
	private String username;
	private int ID;
	
	public LoginStatus(String tipoMembro, String username, int iD)
	{
		this.setTipoMembro(tipoMembro);
		this.username = username;
		this.ID = iD;
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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}