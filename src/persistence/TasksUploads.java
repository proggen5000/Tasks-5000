package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.FieldNames;
import database.Queries;
import entities.Task;
import entities.Upload;

public class TasksUploads {

	public static boolean link(Upload upload, Task task) {
		try {
			return Queries.insertQuery(FieldNames.TASKS_FILES,
					"aufgabeID, dateiID", task.getId() + ", " + upload.getId()) >= 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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
		String sql = "SELECT * FROM "
				+ FieldNames.TASKS
				+ " INNER JOIN "
				+ FieldNames.TASKS_FILES
				+ " ON aufgaben.aufgabeID = aufgaben_dateien.aufgabeID WHERE dateiID = "
				+ uploadId;
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			while (rs.next()) {
				list.add(TaskManager.createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

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
