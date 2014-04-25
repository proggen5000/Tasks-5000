package entities;
import java.io.Serializable;
import java.util.ArrayList;

import administration.AufgabenVerwaltung;

public class Aufgabengruppe implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String beschreibung;
	private Team team;
	
	public Aufgabengruppe(){}
	
	public Aufgabengruppe(long id, String name, String beschreibung, Team team){
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
		this.team = team;
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
	

	// TODO
	public ArrayList<Aufgabe> getAufgaben(){
		return AufgabenVerwaltung.getListeVonGruppe(id);
	}
}
