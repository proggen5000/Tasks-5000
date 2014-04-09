package entities;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Dateien")
public class Datei implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="DateID")
	private long id;
	
	@NotNull
	@Column(name="Titel")
	private String titel;
	
	@Column(name="Beschreibung")
	private String beschreibung;
	
	@NotNull
	@Column(name="Pfad")
	private String pfad;
	
	@NotNull
	@Column(name="Version")
	private String version;
	
	@OneToOne
	@JoinColumn(name="TeamID")
	private Team team;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="Ersteller")
	private Mitglied ersteller;

	//Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Mitglied getErsteller() {
		return ersteller;
	}

	public void setErsteller(Mitglied ersteller) {
		this.ersteller = ersteller;
	}

	public String getPfad() {
		return pfad;
	}

	public void setPfad(String pfad) {
		this.pfad = pfad;
	}
	

}
