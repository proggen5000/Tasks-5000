package entities;
import java.io.Serializable;

public class Aufgabengruppe implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String beschreibung;
	// Team??
	
	public Aufgabengruppe(){}
	
	public Aufgabengruppe(long id, String name, String beschreibung){
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
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
}
