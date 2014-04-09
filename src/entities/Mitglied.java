package entities;
import java.io.Serializable;

public class Mitglied implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String email;
	private String password;
	private String vorname;
	private String nachname;
	private long reg_datum;
		
	//Getter & Setter
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public long getReg_datum() {
		return reg_datum;
	}
	public void setReg_datum(long reg_datum) {
		this.reg_datum = reg_datum;
	}	
}
