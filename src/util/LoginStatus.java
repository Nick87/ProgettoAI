package util;

public class LoginStatus
{
	private int ID_Membro;
	private String tipoMembro;
	private String username;
	
	public LoginStatus(String tipoMembro, String username, int idMembro)
	{
		this.tipoMembro = tipoMembro;
		this.username = username;
		this.setID_Membro(idMembro);
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
	public int getID_Membro() {
		return ID_Membro;
	}
	public void setID_Membro(int iD_Membro) {
		this.ID_Membro = iD_Membro;
	}
}