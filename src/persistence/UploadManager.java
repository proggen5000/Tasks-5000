package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Team;
import entities.Upload;
import entities.User;

public class UploadManager {

	/**
	 * Schreibt eine Datei in die DB liefert eine Datei mit den gespeicherten
	 * Daten zurueck
	 * 
	 * @param upload
	 *            mit einzuschreibenden Daten
	 * @return testdatei mit gespeicherten Daten
	 */
	public static Upload add(Upload upload) {

		// Einfuegen der Werte (ohne ID)
		String table = "dateien";
		String columns = "dateiid, name, beschreibung, pfad, teamid, erstellerid";
		String values = "NULL, '" + upload.getName() + "', '"
				+ upload.getDescription() + "', '" + upload.getPath() + "', "
				+ upload.getTeam().getId() + ", " + upload.getAuthor().getId();
		int testID;

		try {
			testID = Queries.insertQuery(table, columns, values);
		} catch (SQLException e1) {
			e1.printStackTrace();
			testID = -1;
		}

		if (testID == -1) {
			return null;
		} else {
			Upload testdatei = get(testID);
			return testdatei;
		}
	}

	/**
	 * aktualisiert Werte einer Datei in der DB
	 * 
	 * @param upload
	 *            mit zu aktualisierenden Werten
	 * @return testdatei aus der DB mit den aktualisierten Werten
	 */
	public static Upload edit(Upload upload) {

		// Aktualisieren der Dateibeschreibung
		String table = "dateien";
		String updateString = "name='" + upload.getName() + "', beschreibung='"
				+ upload.getDescription() + "', pfad='" + upload.getPath()
				+ "', teamid=" + upload.getTeam().getId();
		String where = "dateiid=" + upload.getId();

		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				Upload testdatei = get(upload.getId());
				return testdatei;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * löscht Datei anhand der ID löscht außerdem alle Verbindungen zu Aufgaben
	 * 
	 * @param uploadId
	 *            der zu löschenden Datei
	 * @return boolean
	 */
	public static boolean remove(long uploadId) {
		// Datei anhand der ID löschen
		String table = "dateien";
		String where = "dateiid=" + uploadId;
		String aufgabensql = "SELECT aufgabeid FROM aufgaben_dateien WHERE dateiid= "
				+ uploadId;

		try {
			// löschen aller Verbindungen zu Aufgaben
			ResultSet rs = Queries.rowQuery(aufgabensql);
			if (rs != null) {
				while (rs.next()) {
					TasksUploads.unlink(get(uploadId),
							TaskManager.get(rs.getLong("aufgabeid")));
				}
			}

			// löschen der Datei
			return Queries.deleteQuery(table, where);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * liefert eine Datei aus der DB anhand einer Dateiid
	 * 
	 * @param id
	 *            der zurückzugebenden Datei
	 * @return testdatei mit Werten der Datei
	 */
	public static Upload get(long id) {

		// Suchen der Datei anhand der ID
		String sql = "SELECT * FROM dateien WHERE dateiid=" + id;

		try {
			ResultSet rs = Queries.rowQuery(sql);
			if (rs.isBeforeFirst()) {
				rs.next();
				Upload testdatei = createUploadbyRow(rs);
				return testdatei;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * prüft ob eine Datei in der DB vorhanden ist
	 * 
	 * @param id
	 *            der zu suchenden Datei
	 * @return boolean
	 */
	public static boolean exists(long id) {
		return get(id) != null;
	}

	/**
	 * Gibt eine Liste aller Dateien zurück, die einer Aufgabe zugewiesen sind
	 * 
	 * @param taskId
	 *            Aufgabe, zu dem die Dateien gelistet werden sollen
	 * @return Datei-Liste
	 */
	public static ArrayList<Upload> getListOfTask(long taskId) {

		String sql = "SELECT dateien.dateiid FROM dateien JOIN aufgaben_dateien "
				+ "ON dateien.dateiid= aufgaben_dateien.dateiid "
				+ "JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_dateien.aufgabeid "
				+ "WHERE aufgaben.aufgabeid= " + taskId;
		ArrayList<Upload> al = new ArrayList<Upload>();

		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				Upload d = get(rs.getLong("dateiid"));
				al.add(d);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben
			// werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Liefert eine ArrayList mit allen Dateien eines Teams.
	 * 
	 * @param teamId
	 *            des Teams
	 * @return al ArrayList mit Dateien
	 */
	public static ArrayList<Upload> getListOfTeam(long teamId) {

		String sql = "SELECT dateiid FROM dateien WHERE teamid= " + teamId;
		ArrayList<Upload> al = new ArrayList<Upload>();

		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				Upload d = get(rs.getLong("dateiid"));
				al.add(d);
			}
			return al;
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben
			// werden
			e.printStackTrace();
			return null;
		}
	}

	private static Upload createUploadbyRow(ResultSet rs) {
		try {
			Team team = TeamManager.get(rs.getLong("teamid"));
			User ersteller = UserManager
					.get(rs.getLong("erstellerid"));
			Upload d = new Upload(rs.getLong("dateiid"), rs.getString("name"),
					rs.getString("beschreibung"), rs.getString("pfad"), team,
					ersteller);
			return d;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
