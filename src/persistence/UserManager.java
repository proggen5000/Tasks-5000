package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.FieldNames;
import database.Queries;
import entities.User;

public class UserManager {

	/**
	 * Fuegt Werte eines Mitglieds in die Datenbank ein, liefert ein Mitglied
	 * mit den eben eingefuegten Werten zurueck (inkl. ID)
	 * 
	 * @param user
	 *            Werte werden ein die DB eingefuegt
	 * @return testmitglied mit den Werten aus der Datenbank
	 */
	public static User add(User user) {
		Calendar cal = Calendar.getInstance();
		long regdatum = cal.getTimeInMillis();

		String table = FieldNames.USERS;
		String columns = "mitgliedid, username, pw, email, vorname, nachname, "
				+ "regdatum";
		String values = "NULL, '" + user.getName() + "', PASSWORD('"
				+ user.getPassword() + "'), '" + user.getEmail() + "', '"
				+ user.getFirstName() + "', '" + user.getSecondName() + "', "
				+ regdatum;
		long testId;
		try {
			testId = Queries.insertQuery(table, columns, values);
		} catch (SQLException e) {
			e.printStackTrace();
			testId = -1;
		}

		if (testId == -1) {
			return null;
		} else {
			return get(testId);
		}
	}

	/**
	 * Aktualisiert Werte eines Mitglieds in der Datenbank, liefert ein Mitglied
	 * mit aktualisierten Werten zurueck ID und Registrierungsdatum koennen
	 * nicht geaendert werden
	 * 
	 * @param user
	 *            mit aktualiserten Werten wird eingefuegt
	 * @param passwordChange
	 *            Angabe, ob das Passwort geändert wurde
	 * @return testmitglied mit den aktualisierten Werten aus der DB
	 */
	public static User edit(User user, boolean passwordChange) {
		String table = FieldNames.USERS;
		String updateString;
		if (passwordChange) {
			updateString = "username='" + user.getName() + "', pw=PASSWORD('"
					+ user.getPassword() + "'), email='" + user.getEmail()
					+ "', vorname='" + user.getFirstName() + "', nachname='"
					+ user.getSecondName() + "'";
		} else {
			updateString = "username='" + user.getName() + "', pw='"
					+ user.getPassword() + "', email='" + user.getEmail()
					+ "', vorname='" + user.getFirstName() + "', nachname='"
					+ user.getSecondName() + "'";
		}
		String where = "mitgliedid=" + user.getId();

		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				return get(user.getId());
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean remove(long userId) {
		String table = FieldNames.USERS;
		String where = "mitgliedid=" + userId;
		String gruppenfuehrersql = "SELECT teamid FROM " + FieldNames.TEAMS
				+ " WHERE gruppenfuehrerid= " + userId;
		String teamsql = "SELECT teamid FROM " + FieldNames.USERS_TEAMS
				+ " WHERE mitgliedid= " + userId;
		String aufgabensql = "SELECT aufgabeid FROM "
				+ FieldNames.TASKS_USERS + " WHERE mitgliedid= " + userId;

		try {
			// löscht Teams wo das Mitglied gruppenfuehrer war
			ResultSet rs = Queries.rowQuery(gruppenfuehrersql);
			if (rs != null) {
				while (rs.next()) {
					TeamManager.loeschen(rs.getLong("teamid"));
				}
			}
			// löscht Verbindungen zu bestehenden Teams
			rs = Queries.rowQuery(teamsql);
			if (rs != null) {
				while (rs.next()) {
					UsersTeams.unlink(userId, rs.getLong("teamid"));
				}
			}
			// löscht zugeordnete Aufgaben
			rs = Queries.rowQuery(aufgabensql);
			if (rs != null) {
				while (rs.next()) {
					TasksMembers.unlink(UserManager.get(userId),
							TaskManager.get(rs.getLong("aufgabenid")));
				}
			}
			// löscht das Mitglied
			return Queries.deleteQuery(table, where);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Sucht ein Mitglied anhand der ID in der Datenbank liefert ein Mitglied
	 * mit den gefundenen Werten zurueck
	 * 
	 * @param id
	 * @return testmitglied
	 */
	public static User get(long id) {
		try {
			ResultSet rs = Queries.rowQuery("*", "mitglieder", "mitgliedId = "
					+ id);
			if (rs.isBeforeFirst()) {
				rs.next();
				return createUserByRow(rs);
			}
		} catch (SQLException e) {
			System.err.println("UserManager.get(long) - SQL ERROR");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sucht ein Mitglied anhand des Usernamens in der Datenbank liefert ein
	 * Mitglied mit den gefundenen Werten zurueck
	 * 
	 * @param username
	 * @return testmitglied
	 */
	public static User get(String username) {
		try {
			ResultSet rs = Queries.rowQuery("*", FieldNames.USERS,
					"username = '" + username + "'");
			if (rs.isBeforeFirst()) {
				rs.next();
				return createUserByRow(rs);
			}
		} catch (SQLException e) {
			System.err.println("UserManager.get(String) - SQL ERROR");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sucht ein Mitglied anhand einer id liefert true bei Fund, false bei
	 * Nichtexistenz zurueck
	 * 
	 * @param id
	 *            ID des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean exists(long id) {
		return get(id) != null;
	}

	/**
	 * Sucht ein Mitglied anhand eines Usernamens liefert true bei Fund, false
	 * bei Nichtexistenz zurueck
	 * 
	 * @param username
	 *            Username des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean exists(String username) {
		return get(username) != null;
	}

	/**
	 * liefert eine ArrayList aller Mitglieder
	 * 
	 * @return al ArrayList mit Mitgliedern
	 */
	public static ArrayList<User> getList() {
		ArrayList<User> list = new ArrayList<User>();
		try {
			ResultSet rs = Queries.rowQuery("*", FieldNames.USERS,
					"true ORDER BY username ASC");
			while (rs.next()) {
				list.add(createUserByRow(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * liefert eine ArrayList aller Mitglieder, die einer bestimmten Aufgabe
	 * zugeordnet sind
	 * 
	 * @param taskId
	 *            ID der Aufgabe
	 * @return al ArrayList mit Mitgliedern
	 */
	public static ArrayList<User> getListOfTask(long taskId) {
		String sql = "SELECT * FROM "
				+ FieldNames.USERS
				+ " INNER JOIN "
				+ FieldNames.TASKS_USERS
				+ " ON mitglieder.mitgliedid= aufgaben_mitglieder.mitgliedid "
				+ "INNER JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_mitglieder.aufgabeid "
				+ "WHERE aufgaben.aufgabeid= " + taskId
				+ " ORDER BY username ASC";
		ArrayList<User> al = new ArrayList<User>();

		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				al.add(createUserByRow(rs));
			}
			return al;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Liefert alle Mitglieder eines Teams, die der Aufgabe noch nicht
	 * zugeordnet sind
	 * 
	 * @param aufgabenID
	 * @return
	 */
	public static ArrayList<User> getListOfTaskRest(long teamId, long aufgabenId) {
		ArrayList<User> usersTeam = getListOfTeam(teamId);
		ArrayList<User> usersTask = getListOfTask(aufgabenId);

		for (User m : usersTeam) {
			if (usersTask.contains(m)) {
				usersTask.remove(m);
			} else {
				usersTask.add(m);
			}
		}

		return usersTask;
	}

	/**
	 * liefert eine ArrayList aller Mitglieder eines Teams
	 * 
	 * @param teamId
	 * @return al
	 */
	public static ArrayList<User> getListOfTeam(long teamId) {
		String sql = "SELECT * FROM " + FieldNames.USERS + " JOIN "
				+ FieldNames.USERS_TEAMS + " "
				+ "ON mitglieder.mitgliedid= mitglieder_teams.mitgliedid "
				+ "JOIN teams ON teams.teamid = mitglieder_teams.teamid "
				+ "WHERE teams.teamid= " + teamId + " ORDER BY username ASC";

		ArrayList<User> list = new ArrayList<User>();

		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				list.add(createUserByRow(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Liefert alle Benutzer, die dem Team noch nicht zugeordnet sind
	 * 
	 * @param aufgabenID
	 * @return
	 */
	public static ArrayList<User> getListOfTeamRest(long teamId) {
		ArrayList<User> usersAll = getList();
		ArrayList<User> usersTeam = getListOfTeam(teamId);

		for (User u : usersTeam) {
			if (usersAll.contains(u)) {
				usersAll.remove(u);
			}
		}
		return usersAll;
	}

	/**
	 * Prüft, ob ein bestimmtes Mitglied einem bestimmten Team zugeordnet ist
	 * 
	 * @param mitgliedID
	 * @param teamID
	 * @return boolean
	 */
	public static boolean isMemberInTeam(long mitgliedID, long teamID) {
		try {
			ResultSet rs = Queries.rowQuery("*", FieldNames.USERS_TEAMS,
					"mitgliedID = " + mitgliedID + " AND teamID = " + teamID);
			if (rs.isBeforeFirst()) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("isMemberInTeam - SQLERROR");
			e.printStackTrace();
		}
		return false;
	}

	public static boolean checkLogin(String username, String password) {
		try {
			ResultSet rs = Queries.rowQuery("*", FieldNames.USERS,
					"username = '" + username + "' AND pw = PASSWORD('"
							+ password + "')");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private static User createUserByRow(ResultSet rs) {
		try {
			if (!rs.isBeforeFirst() && !rs.isAfterLast()) {
				User u = new User(rs.getLong("mitgliedID"),
						rs.getString("username"), rs.getString("pw"),
						rs.getString("email"), rs.getString("vorname"),
						rs.getString("nachname"), rs.getLong("regdatum"));
				return u;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.err.println("UserManager.createUserByRow - SQL ERROR");
			e.printStackTrace();
			return null;
		}
	}
}
