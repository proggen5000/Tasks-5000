package entities;
import java.io.Serializable;
import java.util.Date;

import administration.AufgabenVerwaltung;
import administration.MitgliederVerwaltung;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String teamname;
	private long gruendungsdatum;
	private String slogan;
	private Mitglied gruppenfuehrer;
	
	public Team(){
	}

	public Team(long id, String teamname, long gruendungsdatum, String slogan,
			Mitglied gruppenfuehrer) {
		super();
		this.id = id;
		this.teamname = teamname;
		this.slogan = slogan;
		this.gruppenfuehrer = gruppenfuehrer;
		this.gruendungsdatum = gruendungsdatum;
	}

	//Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String name) {
		this.teamname = name;
	}

	public long getGruendungsdatum() {
		return gruendungsdatum;
	}

	public void setGruendungsdatum(long gruendungsdatum) {
		this.gruendungsdatum = gruendungsdatum;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public Mitglied getGruppenfuehrer() {
		return gruppenfuehrer;
	}

	public void setGruppenfuehrer(Mitglied gruppenfuehrer) {
		this.gruppenfuehrer = gruppenfuehrer;
	}
	
	/**
	 * Liefert das Gruendungsdatum als Dateobjekt
	 */
	public Date getGruendungsdatumAsDate(){
		return new java.util.Date(gruendungsdatum);
	}
	
	public int getAufgabenAnzahl(){
		return AufgabenVerwaltung.getListeVonTeam(id).size();
	}
	
	public int getAufgabenAnzahlVonMitglied(long mitgliedID){
		return AufgabenVerwaltung.getListeVonMitgliedVonTeam(mitgliedID, id).size();
	}
	
	public int getAnzahlMitglieder(){
		return MitgliederVerwaltung.getListeVonTeam(id).size();
	}

}
