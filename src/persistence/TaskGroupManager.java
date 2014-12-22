package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.FieldNames;
import database.Queries;
import entities.Task;
import entities.TaskGroup;

public class TaskGroupManager {

	public static TaskGroup add(TaskGroup taskGroup) {
		String table = FieldNames.TASKGROUPS;
		String values = "'" + taskGroup.getName() + "', '"
				+ taskGroup.getDescription() + "', "
				+ taskGroup.getTeam().getId();
		long id;
		try {
			id = Queries.insertQuery(table, "name, beschreibung, team", values);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		if (id == -1) {
			return null;
		} else {
			return get(id);
		}
	}

	/**
	 * Die Daten werden auf die des übergebenen Objekts geupdated.
	 * 
	 * @param taskGroup
	 *            die aktualisiert werden soll.
	 * @return Aufgabengruppe so wie sie in der Datenbank steht
	 */
	public static TaskGroup edit(TaskGroup taskGroup) {
		// Aktualisieren des Aufgabengruppe
		String table = FieldNames.TASKGROUPS;
		String updateString = "name = '" + taskGroup.getName()
				+ "', beschreibung = '" + taskGroup.getDescription()
				+ "', team = " + taskGroup.getTeam().getId();
		String where = "aufgabengruppeID = " + taskGroup.getId();

		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				return get(taskGroup.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Löscht die übergebene Aufgabengruppe aus der Datenbank
	 * 
	 * @param aufgabengruppe
	 *            die gelöscht werden soll
	 * @return boolean ob gelöscht oder nicht
	 */
	public static boolean remove(TaskGroup aufgabengruppe) {
		// erst müssen alle untergeordneten Aufgaben gelöscht werden
		ArrayList<Task> a = TaskManager.getListeVonGruppe(aufgabengruppe
				.getId());
		int l = a.size();
		for (int i = 0; i < l; i++) {
			TaskManager.loeschen(a.get(i));
		}

		String table = FieldNames.TASKGROUPS;
		String where = "aufgabengruppeID = " + aufgabengruppe.getId();
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Überprüft, ob eine AufgabenGruppen-ID in der Datenbank vorhanden ist
	 * 
	 * @param id
	 *            die auf vorhandensein geprüft werden soll
	 * @return boolean ob sie vorhanden ist oder nicht
	 */
	public static boolean exists(long id) {
		return TaskGroupManager.get(id) != null;
	}

	/**
	 * Überprüft, ob ein AufgabenGruppen-Name in der Datenbank vorhanden ist
	 * 
	 * @param name
	 *            der auf vorhandensein geprüft werden soll
	 * @return boolean ob er vorhanden ist oder nicht
	 */
	public static boolean exists(String name) {
		return TaskGroupManager.get(name) != null;
	}

	/**
	 * Gibt die AufgabenGruppen mit der angegebenen AufgabenGruppen-ID aus der
	 * Datenbank zurück
	 * 
	 * @param id
	 *            der Aufgabengruppe nach der gesucht werden soll
	 * @return Aufgabengruppe, nach der gesucht wurde
	 */
	public static TaskGroup get(long id) {
		// Suchen der Aufgabe anhand der ID
		try {
			ResultSet rs = Queries.rowQuery("*", FieldNames.TASKGROUPS,
					"aufgabengruppeID = " + id);
			if (rs.isBeforeFirst()) {
				rs.next();
				return createTaskGroupByRow(rs);
			}
		} catch (SQLException e) {
			System.err
					.println("AufgbengruppenVerwaltung.get(long) - SQL ERROR");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gibt die AufgabenGruppen mit dem angegebenen AufgabenGruppen-Name aus der
	 * Datenbank zurück
	 * 
	 * @param name
	 *            der Aufgabengruppe nach der gesucht werden soll
	 * @return Aufgabengruppe, nach der gesucht wurde
	 */
	public static TaskGroup get(String name) {
		// Suchen der Aufgabe anhand der Name
		TaskGroup aufgabengruppe = null;
		try {
			ResultSet rs = Queries.rowQuery("*", FieldNames.TASKGROUPS,
					"name = " + name);
			if (rs.isBeforeFirst()) {
				rs.next();
				return createTaskGroupByRow(rs);
			}
		} catch (SQLException e) {
			System.err
					.println("AufgabenGruppenVerwaltung.get(String) - SQL ERROR");
			e.printStackTrace();
		}
		return aufgabengruppe;
	}

	/**
	 * Gibt alle AufgabeGruppen die in der Datenbank existieren zurück
	 * 
	 * @return Alle AufgabeGruppen die in der Datenbank existieren
	 */
	public static ArrayList<TaskGroup> getList() {
		// returnd eine ArrayListe aller Aufgaben
		ArrayList<TaskGroup> list = new ArrayList<TaskGroup>();
		try {
			ResultSet rs = Queries
					.rowQuery("SELECT * FROM " + FieldNames.TASKS);
			// FIXME shouldn't this be TASK_GROUPS?
			while (rs.next()) {
				// add every result in resultset to ArrayList
				TaskGroup taskGroup = new TaskGroup(
						rs.getLong("aufgabengruppeID"), rs.getString("name"),
						rs.getString("beschreibung"), TeamManager.get(rs
								.getLong("team")));
				list.add(taskGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * Gibt alle Aufgabengruppen eines Teams zurück
	 * 
	 * @param teamID
	 *            nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgabengruppe für die gesuchte ID
	 */
	public static ArrayList<TaskGroup> getList(long teamID) {
		String sql = "SELECT * FROM `" + FieldNames.TASKGROUPS
				+ "` WHERE `team` = " + teamID + " group by `aufgabengruppeID`";
		ArrayList<TaskGroup> list = new ArrayList<TaskGroup>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				TaskGroup a = TaskGroupManager.get(rs
						.getLong("aufgabengruppeID"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * Hilfsfunktion zum einfach erstellen einer AufgabenGruppe aus einem
	 * ResultSet
	 * 
	 * @param rs
	 *            aus dem die AufgabenGruppe erstellt werden soll
	 * @return Aufgabe
	 */
	private static TaskGroup createTaskGroupByRow(ResultSet rs) {
		try {
			TaskGroup taskGroup = new TaskGroup(rs.getLong("aufgabenGruppeID"),
					rs.getString("name"), rs.getString("beschreibung"),
					TeamManager.get(rs.getLong("team")));
			return taskGroup;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
