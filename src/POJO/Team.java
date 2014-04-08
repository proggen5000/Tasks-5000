package POJO;

public class Team {
	int id;
	String name;
	int gruendungsdatum;
	String slogan;
	int gruppenfuehrerID;
	
	//Kosntruktor
	public Team(){
	}

	//Getters & Setters
	public int getId() {
		return id;
		//
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGruendungsdatum() {
		return gruendungsdatum;
	}

	public void setGruendungsdatum(int gruendungsdatum) {
		this.gruendungsdatum = gruendungsdatum;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public int getGruppenfuehrerID() {
		return gruppenfuehrerID;
	}

	public void setGruppenfuehrerID(int gruppenfuehrerID) {
		this.gruppenfuehrerID = gruppenfuehrerID;
	}

}
