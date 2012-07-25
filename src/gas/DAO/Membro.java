package gas.DAO;

import gas.Exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.LoginStatus;

public class Membro
{
	private int ID_Membro;
	private String nome;
	private String cognome;
	private int eta;
	private String sesso;
	private Date data_nascita;
	private String luogo_nascita;
	private String indirizzo;
	private String email;
	private String telefono;
	private memberType tipo_membro;
	private String username;
	private String password;
	private boolean abilitato;
	
	public Membro() {}
	public enum memberType { ADMIN, UTENTE, FORNITORE, RESPONSABILE };
	
	public static LoginStatus checkPassword(String username, String password) throws DBException, LoginException, SQLException
	{
		Connection conn = DBConnection.getDBConnection();
		LoginStatus status = null;
		int ID_Membro;
		String tipo_membro;
		String query = "SELECT ID_Membro, tipo_membro, password FROM membro WHERE username = ?;";
		
	    try
	    {
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				throw new LoginException("Username inesistente");
			
			String passwordToCheck = rs.getString("password");
			ID_Membro = rs.getInt("ID_Membro");
			tipo_membro = rs.getString("tipo_membro");
			
			if(passwordToCheck.equals(password))
				status = new LoginStatus(tipo_membro, username, ID_Membro);
			else
				throw new LoginException("Password errata");
	    } finally {
			DBConnection.closeConnection(conn);
		}
		return status;
	}
	
	public static String getUsernameFromId(int idMembro) throws DBException, SQLException
	{
		Connection conn = null;
		String username = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT username FROM membro WHERE ID_Membro = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idMembro);
			ResultSet rs = ps.executeQuery();
			rs.next();
			username = rs.getString("username");
		} finally {
			DBConnection.closeConnection(conn);
		}
		return username;
	}
	
	public static List<Integer> getIdMembriFromOrdine(int idOrdine) throws DBException, SQLException
	{
		Connection conn = null;
		List<Integer> list = new ArrayList<Integer>();
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT DISTINCT ID_Membro_che_acquista as id " +
						   "FROM scheda_di_acquisto " +
						   "WHERE ID_Ordine = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				list.add(rs.getInt("id"));
		} finally {
			DBConnection.closeConnection(conn);
		}
		return list;
	}
	
	public static List<Membro> getMembriDelegabili(int idMembroRichiedente) throws DBException, SQLException
	{
		ArrayList<Membro> lista = new ArrayList<Membro>();
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM membro WHERE";
			query += " tipo_membro = ? AND ID_Membro != ? AND abilitato = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "U");
			ps.setInt(2, idMembroRichiedente);
			ps.setInt(3, 1);
			ResultSet rs = ps.executeQuery();
			Membro m;
			while(rs.next())
			{
				m = new Membro();
				m.setID_Membro(rs.getInt("ID_membro"));
				m.setNome(rs.getString("nome"));
				m.setCognome(rs.getString("cognome"));
				lista.add(m);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return lista;
	}
	public static List<Membro> getMembriFromType(memberType ... tipi) throws DBException, SQLException
	{
		ArrayList<Membro> lista = new ArrayList<Membro>();
		Connection conn = null;
		try
		{
			conn = DBConnection.getDBConnection();
			String query = "SELECT * FROM membro WHERE";
			query += " tipo_membro = ?";
			for(int i = 1; i < tipi.length; i++)
				query += " OR tipo_membro = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			for(int i = 0; i < tipi.length; i++)
				ps.setString(i+1, memberTypeToString(tipi[i]));
			
			ResultSet rs = ps.executeQuery();
			Membro m;
			while(rs.next())
			{
				m = new Membro();
				m.setID_Membro(rs.getInt("ID_membro"));
				m.setNome(rs.getString("nome"));
				m.setCognome(rs.getString("cognome"));
				m.setEta(rs.getInt("eta"));
				m.setSesso(rs.getString("sesso"));
				m.setData_nascita(rs.getDate("data_nascita"));
				m.setLuogo_nascita(rs.getString("luogo_nascita"));
				m.setIndirizzo(rs.getString("indirizzo"));
				m.setEmail(rs.getString("email"));
				m.setTelefono(rs.getString("telefono"));
				m.setTipo_membro(stringToMemberType(rs.getString("tipo_membro")));
				m.setUsername(rs.getString("username"));
				m.setPassword(rs.getString("password"));
				boolean abilitato = rs.getString("abilitato") == "1" ? true : false;
				m.setAbilitato(abilitato);
				lista.add(m);
			}
		} finally {
			DBConnection.closeConnection(conn);
		}
		return lista;
	}
	
	private static String memberTypeToString(memberType type)
	{
		if(type == memberType.ADMIN)
			return "A";
		if(type == memberType.FORNITORE)
			return "F";
		if(type == memberType.UTENTE)
			return "U";
		if(type == memberType.RESPONSABILE)
			return "R";
		return "";
	}
	
	private static memberType stringToMemberType(String type)
	{
		switch(type)
		{
			case "A":
				return memberType.ADMIN;
			case "F":
				return memberType.FORNITORE;
			case "U":
				return memberType.UTENTE;
			case "R":
				return memberType.RESPONSABILE;
		}
		return null;
	}
	
	public int getID_Membro() {
		return ID_Membro;
	}
	public void setID_Membro(int iD_Membro) {
		ID_Membro = iD_Membro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(Date data_nascita) {
		this.data_nascita = data_nascita;
	}
	public String getLuogo_nascita() {
		return luogo_nascita;
	}
	public void setLuogo_nascita(String luogo_nascita) {
		this.luogo_nascita = luogo_nascita;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public memberType getTipo_membro() {
		return tipo_membro;
	}
	public void setTipo_membro(memberType tipo_membro) {
		this.tipo_membro = tipo_membro;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAbilitato() {
		return abilitato;
	}
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
}
