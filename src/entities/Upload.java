package entities;

import java.io.Serializable;

import persistence.TasksUploads;

public class Upload implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String description;
	private String path;
	private Team team;
	private User author;

	// Konstruktor
	public Upload() {
	}

	public Upload(long id, String name, String description, String path,
			Team team, User author) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.path = path;
		this.team = team;
		this.author = author;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * Returns the file size of the upload.
	 * 
	 * @return File size in KB.
	 */
	public int getSize() {
		return 0;
		// TODO fileItem.getSize();
	}

	/**
	 * Detemines whether the file is linked to a task already.
	 * 
	 * @return true if file is linked to one or more tasks.
	 */
	public boolean isLinked() {
		return (TasksUploads.getList(id).size() > 0);
	}
}