package entities;
import java.io.Serializable;

import administration.AufgabenDateien;

public class Datei implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String beschreibung;
	private String pfad;
	private Team team;
	private Mitglied ersteller;
	
	// Konstruktor
	public Datei(){
	}
	
	public Datei(long id, String name, String beschreibung, String pfad, Team team,
			Mitglied ersteller){
		super();
		this.id= id;
		this.name= name;
		this.beschreibung= beschreibung;
		this.pfad= pfad;
		this.team= team;
		this.ersteller= ersteller;
	}
	
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
	
	/**
	 * Liefert die Groesse der Datei in KB
	 * @return Dateigroesse in KB
	 */
	public int getSize(){
		return 0;
		// TODO fileItem.getSize();
	}
	
	/**
	 * Zeigt an, ob die Datei mit mindestens einer Aufgabe verknüpft ist.
	 * @return true, falls Datei mit einer oder mehr Aufgaben verknüpft ist
	 */
	public boolean getVerknuepft(){
		return (AufgabenDateien.getListVonDatei(id).size() > 0);
	}
}