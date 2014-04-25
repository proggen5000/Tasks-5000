package entities;
import java.io.Serializable;
import java.util.Date;

import administration.AufgabenVerwaltung;
import administration.MitgliederVerwaltung;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private long gruendungsdatum;
	private String beschreibung;
	private Mitglied gruppenfuehrer;
	
	public Team(){
	}

	public Team(long id, String name, long gruendungsdatum, String beschreibung,
			Mitglied gruppenfuehrer) {
		super();
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getGruendungsdatum() {
		return gruendungsdatum;
	}

	public void setGruendungsdatum(long gruendungsdatum) {
		this.gruendungsdatum = gruendungsdatum;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
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
