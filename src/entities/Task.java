package entities;

import java.io.Serializable;
import java.util.Date;

import persistence.UploadManager;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private TaskGroup taskGroup;
	private User author;
	private String name;
	private String description;
	private int status;
	private long date;
	private long deadline;

	public Task() {
	}

	public Task(long id, TaskGroup taskGroup, User author, String name,
			String description, int status, long date, long deadline) {
		this.id = id;
		this.taskGroup = taskGroup;
		this.author = author;
		this.name = name;
		this.description = description;
		this.status = status;
		this.date = date;
		this.deadline = deadline;
	}

	// Getters & Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TaskGroup getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(TaskGroup taskGroup) {
		this.taskGroup = taskGroup;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public int getFilesCount() {
		return UploadManager.getListOfTask(id).size();
	}

	/**
	 * Returns date as date object (instead of long value).
	 */
	public Date getDateObject() {
		return new java.util.Date(date);
	}

	/**
	 * Returns deadline as date object.
	 */
	public Date getDeadlineObject() {
		return new java.util.Date(deadline);
	}

}
