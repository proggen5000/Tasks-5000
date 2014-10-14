package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Task;
import entities.Upload;

public class TasksUploads {

	/**
	 * Weist einer Datei eine Aufgabe zu
	 * 
	 * @param upload
	 *            Datei, welche die Aufgabe zugewiesen wird
	 * @param task
	 *            Aufgabe, die der Datei zugewiesen werden soll
	 * @return Zuweisung erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean link(Upload upload, Task task) {
		try {
			return Queries.insertQuery("aufgaben_dateien",
					"aufgabeID, dateiID", task.getId() + ", " + upload.getId()) >= 0;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Entfernt die Zuweisung einer Aufgabe
	 * 
	 * @param d
	 *            Datei, bei dem die Zuweisung gelöscht werden soll
	 * @param a
	 *            Aufgabe, bei dem die Zuweisung gelöscht werden soll
	 * @return Löschen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean unlink(Upload d, Task a) {
		try {
			return Queries.deleteQuery("aufgaben_dateien",
					"dateiID = " + d.getId() + " AND aufgabeID = " + a.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Erstellt eine Liste von Aufgaben, die einer Datei zugewiesen sind
	 * 
	 * @param uploadId
	 *            ID einer Datei, für die die Liste zurückgegeben werden soll
	 * @return Aufgaben-Liste
	 */
	public static ArrayList<Task> getList(long uploadId) {
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM aufgaben INNER JOIN aufgaben_dateien ON aufgaben.aufgabeID = aufgaben_dateien.aufgabeID WHERE dateiID = "
				+ uploadId;
		ArrayList<Task> al = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				// add every result in resultset to ArrayList
				al.add(TaskManager.createAufgabeByRow(rs));
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
	 * Entfernt alle Aufgabenzuordnungen der angegebenen Datei
	 * 
	 * @param d
	 *            Datei, zu dem die Zuweisungen gelöscht werden soll
	 * @return Löschen der Zuweisungen erfolgreich (true) / nicht erfolgreich
	 *         (false)
	 */
	public static boolean unlinkAll(Upload d) {
		try {
			return Queries.deleteQuery("aufgaben_dateien",
					"dateiID=" + d.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
