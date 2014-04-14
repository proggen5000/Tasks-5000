package entities;
import java.io.Serializable;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private long gruendungsdatum;
	private String slogan;
	private Mitglied gruppenfuehrer;
	
	public Team(){
		this.id = 4;
		this.name = "Team";
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

}
