package entities;
import java.io.Serializable;
import java.util.Date;

public class Mitglied implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String pw;
	private String email;
	private String vorname;
	private String nachname;
	private long regdatum;
	
	// Konstruktor
	public Mitglied(){
	}
	
	public Mitglied(long id, String username, String pw, String email, 
			String vorname, String nachname, long regdatum) {
		super();
		this.id = id;
		this.username = username;
		this.pw = pw;
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
	public String getPw() {
		return pw;
	}
	public void setPw(String password) {
		this.pw = password;
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
	
	/**
	 * Überschreibt Vergleichsmethode für Mitgliedobjekte
	 * @param o Zu vergleichendes Objekt
	 * @return Vegleich erfolgreich (true) / nicht erfolgreich (false)
	 */
	@Override
	public boolean equals(Object o){
		if(o.getClass() == this.getClass()){
			Mitglied m = (Mitglied)o;
			return m.getId()==this.id;
		}else if(o.getClass() == Long.class){
			Long l = (Long)o;
			return l == this.getId();
		}
		return false;
	}
}