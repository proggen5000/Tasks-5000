package entities;

import java.io.Serializable;
import java.util.ArrayList;

import persistence.TaskManager;

public class TaskGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private Team team;

	public TaskGroup() {
	}

	public TaskGroup(long id, String name, String description, Team team) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.team = team;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public ArrayList<Task> getTasks() {
		return TaskManager.getListeVonGruppe(id);
	}
}
