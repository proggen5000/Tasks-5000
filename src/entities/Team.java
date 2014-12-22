package entities;

import java.io.Serializable;
import java.util.Date;

import persistence.TaskManager;
import persistence.UploadManager;
import persistence.UserManager;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private long date;
	private User manager;

	public Team() {
	}

	public Team(long id, String name, long date, String description,
			User manager) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.date = date;
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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Date getDateObject() {
		return new java.util.Date(date);
	}

	public int getTasksCount() {
		return TaskManager.getListeVonTeam(id).size();
	}

	public int getTasksCount(long userId) {
		return TaskManager.getListeVonMitgliedVonTeam(userId, id).size();
	}

	public int getUsersCount() {
		int count = 0;
		if (UserManager.getListOfTeam(id) != null) {
			count = UserManager.getListOfTeam(id).size();
		}
		return count;
	}

	public int getFilesCount() {
		return UploadManager.getListOfTeam(id).size();
	}
}
