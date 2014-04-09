package entities;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="Mitglieder")
public class Mitglied implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="MitgliedID")
	private long id;
	
	@NotNull
	@Column(name="Username")
	private String username;
	
	@NotNull
	@Email
	@Column(name="Email")
	private String email;
	
	@NotNull
	@Column(name="PW-Hash")
	private String password;
	
	@NotNull
	@Column(name="Vorname")
	private String vorname;
	
	@Column(name="Nachname")
	private String nachname;
	
	@Column(name="Registrierungsdatum")
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
