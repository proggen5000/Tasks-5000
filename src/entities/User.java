package entities;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String password;
	private String email;
	private String firstName;
	private String secondName;
	private long date;

	public User() {
	}

	public User(long id, String name, String password, String email,
			String firstName, String secondName, long date) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.secondName = secondName;
		this.date = date;
	}

	// Getter & Setter
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

	public String getEmail() {
		return email.replace("'", "");
	}

	public void setEmail(String email) {
		this.email = email.replace("'", "");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public Date getDateObject() {
		return new java.util.Date(date);
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() == this.getClass()) {
			User m = (User) o;
			return m.getId() == this.id;
		} else if (o.getClass() == Long.class) {
			Long l = (Long) o;
			return l == this.getId();
		}
		return false;
	}
}