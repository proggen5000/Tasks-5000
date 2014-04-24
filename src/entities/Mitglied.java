package entities;
import java.io.Serializable;
import java.util.Date;

public class Mitglied implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String password;
	private String email;
	private String vorname;
	private String nachname;
	private long regdatum;
	
	// Konstruktor
	public Mitglied(){
	}
	
	public Mitglied(long id, String username, String password, String email, 
			String vorname, String nachname, long regdatum) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.vorname = vorname;
		this.nachname = nachname;
		this.regdatum = regdatum;
	}

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
	public long getRegdatum() {
		return regdatum;
	}
	public void setRegdatum(long reg_datum) {
		this.regdatum = reg_datum;
	}	
	
	/**
	 * Liefert das Registrierungsdatum als Dateobjekt
	 */
	public Date getRegdatumAsDate(){
		return new java.util.Date(regdatum);
	}
}
