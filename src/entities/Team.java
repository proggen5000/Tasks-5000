package entities;
import java.io.Serializable;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String slogan;
	private Mitglied gruppenfuehrer;
	private long gruendungsdatum;
	
	public Team(){
	}

	public Team(long id, String name, String slogan,
			Mitglied gruppenfuehrer, long gruendungsdatum) {
		super();
		this.id = id;
		this.name = name;
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
