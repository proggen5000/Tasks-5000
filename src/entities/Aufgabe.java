package entities;
import java.io.Serializable;

import administration.DateiVerwaltung;

public class Aufgabe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Team team; // vermutlich unnötig, da Team über Aufgabengruppe ermittelt werden kann!! 
	private Aufgabengruppe gruppe;
	private Mitglied ersteller;
	private String titel;
	private String beschreibung;
	private int status;
	private long date; // ergänzt!!
	private long deadline;
	// Wem ist Aufgabe zugeteilt? Sollte das hier gespeichert werden? -> Extra Bean!
	
	public Aufgabe(){
	}
	public Aufgabe(long id, Team team, Aufgabengruppe gruppe, Mitglied ersteller, String titel, String beschreibung, int status, long date, long deadline){
		this.id = id;
		this.team = team;
		this.gruppe = gruppe;
		this.ersteller = ersteller;
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.status = status;
		this.date = date;
		this.deadline = deadline;
	}
	
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

	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	
	
	public int getFilecount(){
		return 3;
		//return DateiVerwaltung.getListeVonAufgabe(this.getId()).size();
	}

}
