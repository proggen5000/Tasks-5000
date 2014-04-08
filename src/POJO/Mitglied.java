package POJO;

public class Mitglied {
	private int id;
	private String username;
	private String email;
	private String password;
	private String vorname;
	private String nachname;
	private int reg_datum;
	
	//Kosntruktor
	public Mitglied(){
	}
	
	
	//Getter & Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getReg_datum() {
		return reg_datum;
	}
	public void setReg_datum(int reg_datum) {
		this.reg_datum = reg_datum;
	}	
}
