package entities;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="TeamID")
	private long id;
	
	@NotNull
	@Column(name="Teamname")
	private String name;
	
	@Column(name="Gr??ndungsdatum")
	private long gruendungsdatum;
	
	@Column(name="Slogan")
	private String slogan;
	
	@OneToOne
	@JoinColumn(name="Gruppenf??hrerID")
	private Mitglied gruppenfuehrer;

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
