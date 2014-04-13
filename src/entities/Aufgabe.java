package entities;
import java.io.Serializable;

public class Aufgabe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Team team; 
	private Aufgabengruppe gruppe;
	private Mitglied ersteller;
	private String titel;
	private String beschreibung;
	private int status;
	private long deadline;

	//Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Aufgabengruppe getGruppe() {
		return gruppe;
	}

	public void setGruppe(Aufgabengruppe gruppe) {
		this.gruppe = gruppe;
	}

	public Mitglied getErsteller() {
		return ersteller;
	}

	public void setErsteller(Mitglied ersteller) {
		this.ersteller = ersteller;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	

}
