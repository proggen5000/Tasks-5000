package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Task;

public class TaskManager {
	/**
	 * Erstellt eine neue Aufgabe in der Datenbank
	 * 
	 * @param task
	 *            Aufgabe, die gespeichert werden soll
	 * @return Aufgabe, so wie sie in der Datenbank gespeichert wurde
	 */
	public static Task add(Task task) {
		// returns null if error else returns inserted obj with ID
		String values = task.getTaskGroup().getId() + ", "
				+ task.getAuthor().getId() + ", '" + task.getName() + "', '"
				+ task.getDescription() + "', " + task.getStatus() + ", "
				+ task.getDate() + ", " + task.getDeadline();
		long id = -1;
		try {
			id = Queries
					.insertQuery(
							"aufgaben",
							"aufgabengruppeID, erstellerID, name, beschreibung, status, erstellungsdatum, deadline",
							values);
		} catch (SQLException e1) {
			e1.printStackTrace();
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
	 * @param aufgabe
	 *            die aktualisiert werden soll.
	 * @return Aufgabe so wie sie in der Datenbank steht
	 */
	public static Task bearbeiten(Task aufgabe) {
		// Aktualisieren der Aufgabe
		String table = "aufgaben";
		// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status,
		// Erstellungsdatum, Deadline
		String updateString = "aufgabengruppeID = "
				+ aufgabe.getTaskGroup().getId() + ", erstellerID = "
				+ aufgabe.getAuthor().getId() + ", name = '"
				+ aufgabe.getName() + "', beschreibung = '"
				+ aufgabe.getDescription() + "', status = "
				+ aufgabe.getStatus() + ", deadline = " + aufgabe.getDeadline();
		String where = "aufgabeID = " + aufgabe.getId();

		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				return get(aufgabe.getId());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * Löscht die übergebene Aufgabe aus der Datenbank
	 * 
	 * @param aufgabe
	 *            die gelöscht werden soll
	 * @return boolean ob gelöscht oder nicht
	 */
	public static boolean loeschen(Task aufgabe) {
		String table = "aufgaben";
		String where = "aufgabeID = " + aufgabe.getId();
		try {
			Queries.deleteQuery("aufgaben_dateien",
					"aufgabeID = " + aufgabe.getId());
			Queries.deleteQuery("aufgaben_mitglieder",
					"aufgabeID = " + aufgabe.getId());
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Überprüft, ob eine Aufgabe-ID in der Datenbank vorhanden ist
	 * 
	 * @param id
	 *            die auf vorhandensein geprüft werden soll
	 * @return boolean ob sie vorhanden ist oder nicht
	 */
	public static boolean vorhanden(long id) {
		return TaskManager.get(id) != null;
	}

	/**
	 * Gibt die Aufgabe mit der angegebenen Aufgabe-ID aus der Datenbank zurück
	 * 
	 * @param id
	 *            der Aufgabe nach der gesucht werden soll
	 * @return Aufgabe, nach der gesucht wurde
	 */
	public static Task get(long id) {
		// Suchen der Aufgabe anhand der ID
		try {
			ResultSet rs = Queries.rowQuery("*", "aufgaben", "aufgabeID = "
					+ id);
			if (rs.isBeforeFirst()) {
				rs.next();
				return createAufgabeByRow(rs);
			}
		} catch (SQLException e) {
			System.err.println("AufgabenVerwaltung.get(long) - SQL ERROR");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Erstellt eine Liste mit allen Aufgaben
	 * 
	 * @return Aufgaben-Liste
	 */
	public static ArrayList<Task> getListe() {
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM aufgaben";
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status,
				// Erstellungsdatum, Deadline
				while (rs.next()) {
					// add every result in resultset to ArrayList
					al.add(createAufgabeByRow(rs));
				}
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
	 * Gibt alle Aufgaben einer Datei zurück
	 * 
	 * @param dateiID
	 *            nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgaben für die gesuchte ID
	 */
	public static ArrayList<Task> getListeVonDatei(long dateiID) {
		// returnd eine ArrayListe aller Aufgaben, die zu einer bestimmten datei
		// gehören
		String sql = "SELECT * FROM aufgaben JOIN aufgaben_dateien ON aufgaben.aufgabeID = aufgaben_dateien.aufgabeID JOIN dateien ON dateien.dateiID = aufgaben_dateien.dateiID WHERE dateien.dateiID = "
				+ dateiID;
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt, soll eine lehere Liste zurückgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Gibt alle Aufgaben des angegebenen Teams zurück, welche der angegebenen
	 * Datei noch nicht zugeordnet sind.
	 * 
	 * @param teamID
	 * @param dateiID
	 * @return ArrayListe mit den gesuchten Aufgabeobjekten
	 */
	public static ArrayList<Task> getListeVonDateiRest(long teamID, long dateiID) {
		ArrayList<Task> aufgabenTeam = getListeVonTeam(teamID);
		ArrayList<Task> aufgabenDatei = getListeVonDatei(dateiID);

		for (Task a : aufgabenDatei) {
			if (aufgabenTeam.contains(a)) {
				aufgabenTeam.remove(a);
			}
		}
		return aufgabenTeam;
	}

	/**
	 * Gibt alle Aufgaben einer Gruppe zurück
	 * 
	 * @param gruppenID
	 *            nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgaben für die gesuchte ID
	 */
	public static ArrayList<Task> getListeVonGruppe(long gruppenID) {
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM aufgaben WHERE aufgabengruppeID = "
				+ gruppenID;
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Findet alle Aufgaben eines bestimmten Mitglieds (sowohl erstellt, als
	 * auch zugewiesen)
	 * 
	 * @param mitgliedID
	 *            ID des Mitglieds
	 * @return Liste mit Aufgaben des angegebenen Mitglieds
	 */
	public static ArrayList<Task> getListeVonMitglied(long mitgliedID) {
		String sql = "SELECT * FROM  `aufgaben_mitglieder` INNER JOIN  `aufgaben` USING ( aufgabeID ) WHERE mitgliedID = "
				+ mitgliedID
				+ " OR erstellerID = "
				+ mitgliedID
				+ " GROUP BY aufgabeID";
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Findet alle Aufgaben eines bestimmten Teams
	 * 
	 * @param teamID
	 *            ID des Teams
	 * @return Liste mit Aufgaben des angegebenen Teams
	 */
	public static ArrayList<Task> getListeVonTeam(long teamID) {
		String sql = "SELECT * FROM aufgaben INNER JOIN aufgabengruppen USING(aufgabengruppeID) WHERE team = "
				+ teamID + " GROUP BY aufgabeID";
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while (rs.next()) {
				// add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Findet alle Aufgaben unter Angabe eines Mitglieds (sowohl erstellt, als
	 * auch zugewiesen) und eines Teams
	 * 
	 * @param mitgliedID
	 * @param teamID
	 * @return Liste mit Aufgaben des Mitglieds und Teams
	 */
	public static ArrayList<Task> getListeVonMitgliedVonTeam(long mitgliedID,
			long teamID) {
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM  `aufgaben_mitglieder` INNER JOIN  `aufgaben` USING ( aufgabeID ) INNER JOIN aufgabengruppen USING(aufgabengruppeID) WHERE (mitgliedID = "
				+ mitgliedID
				+ " OR erstellerID = "
				+ mitgliedID
				+ ") AND team = " + teamID + " GROUP BY aufgabeID";
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				// add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Hilfsfunktion zum einfachen Erstellen einer Aufgabe aus einem ResultSet.
	 * Kann innerhalb des gesamten Packages verwendet werden.
	 * 
	 * @param rs
	 *            aus dem die Aufgabe erstellt werden soll
	 * @return Aufgabe
	 */
	static Task createAufgabeByRow(ResultSet rs) {
		try {
			Task a = new Task(rs.getLong("aufgabeID"), TaskGroupManager.get(rs
					.getLong("aufgabengruppeID")), UserManager.get(rs
					.getLong("erstellerID")), rs.getString("name"),
					rs.getString("beschreibung"), rs.getInt("status"),
					rs.getLong("erstellungsdatum"), rs.getLong("deadline"));
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
