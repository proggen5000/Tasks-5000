package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.FieldNames;
import database.Queries;
import entities.Team;
import entities.Upload;
import entities.User;

public class UploadManager {

	/**
	 * Schreibt eine Datei in die DB liefert eine Datei mit den gespeicherten
	 * Daten zurück.
	 * 
	 * @param upload
	 *            mit einzuschreibenden Daten
	 * @return testdatei mit gespeicherten Daten
	 */
	public static Upload add(Upload upload) {
		String table = FieldNames.FILES;
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
			return get(testID);
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
		String table = FieldNames.FILES;
		String updateString = "name='" + upload.getName() + "', beschreibung='"
				+ upload.getDescription() + "', pfad='" + upload.getPath()
				+ "', teamid=" + upload.getTeam().getId();
		String where = "dateiid=" + upload.getId();

		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				return get(upload.getId());
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
		String table = FieldNames.FILES;
		String where = "dateiid=" + uploadId;
		// String aufgabensql =
		// "SELECT aufgabeid FROM aufgaben_dateien WHERE dateiid= "
		// + uploadId;

		try {
			// löschen aller Verbindungen zu Aufgaben
			TasksUploads.unlinkAll(get(uploadId));
			/*
			 * ResultSet rs = Queries.rowQuery(aufgabensql); if (rs != null) {
			 * while (rs.next()) { TasksUploads.unlink(get(uploadId),
			 * TaskManager.get(rs.getLong("aufgabeid"))); } }
			 */
			return Queries.deleteQuery(table, where);

		} catch (SQLException e) {
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
		String sql = "SELECT * FROM " + FieldNames.FILES + " WHERE dateiid="
				+ id;

		try {
			ResultSet rs = Queries.rowQuery(sql);
			if (rs.isBeforeFirst()) {
				rs.next();
				return createUploadbyRow(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
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
		String sql = "SELECT dateien.dateiid FROM " + FieldNames.FILES
				+ " JOIN " + FieldNames.TASKS_FILES
				+ " ON dateien.dateiid= aufgaben_dateien.dateiid " + "JOIN "
				+ FieldNames.TASKS
				+ " ON aufgaben.aufgabeid = aufgaben_dateien.aufgabeid "
				+ "WHERE aufgaben.aufgabeid= " + taskId;
		ArrayList<Upload> list = new ArrayList<Upload>();

		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				list.add(get(rs.getLong("dateiid")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * Liefert eine ArrayList mit allen Dateien eines Teams.
	 * 
	 * @param teamId
	 *            des Teams
	 * @return al ArrayList mit Dateien
	 */
	public static ArrayList<Upload> getListOfTeam(long teamId) {
		String sql = "SELECT dateiid FROM " + FieldNames.FILES
				+ " WHERE teamid= " + teamId;
		ArrayList<Upload> list = new ArrayList<Upload>();

		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				list.add(get(rs.getLong("dateiid")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Upload createUploadbyRow(ResultSet rs) {
		try {
			Team team = TeamManager.get(rs.getLong("teamid"));
			User ersteller = UserManager.get(rs.getLong("erstellerid"));
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
