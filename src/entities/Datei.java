package entities;
import java.io.Serializable;

public class Datei implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String beschreibung;
	private String pfad;
	private Team team;
	private Mitglied ersteller;
	
	// Konstruktor!
	
	//Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
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
