package POJO;

public class Aufgabe {
	private int id;
	private int teamID;
	private int aufgGrID;
	private int erstellerID;
	private String titel;
	private String beschreibung;
	private int status;
	private int deadline;
	
	// Konstruktor
	public Aufgabe(){
	}

	//Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public int getAufgGrID() {
		return aufgGrID;
	}

	public void setAufgGrID(int aufgGrID) {
		this.aufgGrID = aufgGrID;
	}

	public int getErstellerID() {
		return erstellerID;
	}

	public void setErstellerID(int erstellerID) {
		this.erstellerID = erstellerID;
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

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	

}
