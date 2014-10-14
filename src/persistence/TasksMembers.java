package persistence;

import java.sql.SQLException;

import database.Queries;
import entities.Task;
import entities.User;

public class TasksMembers {

	/**
	 * Weist einem Mitglied eine Aufgabe zu
	 * 
	 * @param user
	 *            Mitglied, welchem die Aufgabe zugewiesen wird
	 * @param task
	 *            Aufgabe, die dem Mitglied zugewiesen werden soll
	 * @return Zuweisung erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean link(User user, Task task) {
		try {
			return Queries
					.insertQuery("aufgaben_mitglieder",
							"aufgabeID, mitgliedID",
							task.getId() + ", " + user.getId()) >= 0;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Entfernt die Zuweisung einer Aufgabe
	 * 
	 * @param user
	 *            Mitglied, bei dem die Zuweisung gelöscht werden soll
	 * @param task
	 *            Aufgabe, bei dem die Zuweisung gelöscht werden soll
	 * @return Löschen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean unlink(User user, Task task) {
		try {
			return Queries.deleteQuery("aufgaben_mitglieder", "aufgabeID = "
					+ task.getId() + " AND mitgliedID = " + user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Entfernt alle Zuweisungen einer Aufgabe
	 * 
	 * @param a
	 *            Aufgabe, bei der alle Zuweisungen gelöscht werden sollen
	 * @return Löschen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean unlinkAll(Task a) {
		try {
			return Queries.deleteQuery("aufgaben_mitglieder",
					"aufgabeID=" + a.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
