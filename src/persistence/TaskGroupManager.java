package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Task;
import entities.TaskGroup;

public class TaskGroupManager {

	/**
	 * Ersellt eine neue AufgabenGruppe in der Datenbank
	 * 
	 * @param taskGroup
	 *            die Aufgabengruppe die gespeichert werden soll
	 * @return Aufgabengruppe, so wie sie in der Datenbank gespeichert wurde
	 */
	public static TaskGroup add(TaskGroup taskGroup) {

		String values = "'" + taskGroup.getName() + "', '"
				+ taskGroup.getBeschreibung() + "', "
				+ taskGroup.getTeam().getId();
		long id;
		try {
			id = Queries.insertQuery("aufgabengruppen",
					"name, beschreibung, team", values);
		} catch (SQLException e1) {
			e1.printStackTrace();
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
	 * @param aufgabengruppe
	 *            die aktualisiert werden soll.
	 * @return Aufgabengruppe so wie sie in der Datenbank steht
	 */
	public static TaskGroup edit(TaskGroup aufgabengruppe) {
		// Aktualisieren des Aufgabengruppe
		String table = "aufgabengruppen";
		String updateString = "name = '" + aufgabengruppe.getName()
				+ "', beschreibung = '" + aufgabengruppe.getBeschreibung()
				+ "', team = " + aufgabengruppe.getTeam().getId();
		String where = "aufgabengruppeID = " + aufgabengruppe.getId();

		TaskGroup aufgabengruppe_neu = null;
		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				return get(aufgabengruppe.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		return aufgabengruppe_neu;
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

		String table = "aufgabengruppen";
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
			ResultSet rs = Queries.rowQuery("*", "aufgabengruppen",
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
			ResultSet rs = Queries.rowQuery("*", "aufgabengruppen", "name = "
					+ name);
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
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM aufgaben";
		ArrayList<TaskGroup> al = new ArrayList<TaskGroup>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				TaskGroup a = new TaskGroup(rs.getLong("aufgabengruppeID"),
						rs.getString("name"), rs.getString("beschreibung"),
						TeamManager.get(rs.getLong("team")));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Gibt alle Aufgabengruppen eines Teams zurück
	 * 
	 * @param teamID
	 *            nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgabengruppe für die gesuchte ID
	 */
	public static ArrayList<TaskGroup> getList(long teamID) {
		String sql = "SELECT * FROM `aufgabengruppen` WHERE `team` = " + teamID
				+ " group by `aufgabengruppeID`";
		ArrayList<TaskGroup> al = new ArrayList<TaskGroup>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				TaskGroup a = TaskGroupManager.get(rs
						.getLong("aufgabengruppeID"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
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
