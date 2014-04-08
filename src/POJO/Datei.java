package POJO;

public class Datei {
	private int id;
	private String titel;
	private String beschreibung;
	private String version;
	private int teamID;
	private int erstellerID;
	private String pfad;
	
	//Konstruktor
	public Datei(){
		
	}

	//Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public int getErstellerID() {
		return erstellerID;
	}

	public void setErstellerID(int erstellerID) {
		this.erstellerID = erstellerID;
	}

	public String getPfad() {
		return pfad;
	}

	public void setPfad(String pfad) {
		this.pfad = pfad;
	}
	

}
