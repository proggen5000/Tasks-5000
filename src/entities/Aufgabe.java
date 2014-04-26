package entities;
import java.io.Serializable;
import java.util.Date;

public class Aufgabe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id; 
	private Aufgabengruppe gruppe;
	private Mitglied ersteller;
	private String name;
	private String beschreibung;
	private int status;
	private long erstellungsdatum;
	private long deadline;
	
	public Aufgabe(){
	}
	
	public Aufgabe(long id, Aufgabengruppe gruppe, Mitglied ersteller, String name, String beschreibung, int status, long erstellungsdatum, long deadline){
		this.id = id;
		this.gruppe = gruppe;
		this.ersteller = ersteller;
		this.name = name;
		this.beschreibung = beschreibung;
		this.status = status;
		this.erstellungsdatum = erstellungsdatum;
		this.deadline = deadline;
	}
	
	//Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setErsteller(Mitglied ersteller) {
		this.ersteller = ersteller;
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

	public long getErstellungsdatum() {
		return erstellungsdatum;
	}
	public void setErstellungsdatum(long erstellungsdatum) {
		this.erstellungsdatum = erstellungsdatum;
	}
	
	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	
	
	public int getAnzahlDateien(){
		return 3;
		//TODO return DateiVerwaltung.getListeVonAufgabe(this.getId()).size();
	}
	
	/**
	 * Liefert das Erstellungsdatum als Dateobjekt
	 */
	public Date getDateAsDate(){
		return new java.util.Date(erstellungsdatum);
	}
	
	/**
	 * Liefert die Deadline als Dateobjekt
	 */
	public Date getDeadlineAsDate(){
		return new java.util.Date(deadline);
	}

}
