package persistence;

import java.sql.SQLException;

import database.FieldNames;
import database.Queries;
import entities.Task;
import entities.User;

public class TasksMembers {

	public static boolean link(User user, Task task) {
		try {
			return Queries
					.insertQuery(FieldNames.TASKS_USERS,
							"aufgabeID, mitgliedID",
							task.getId() + ", " + user.getId()) >= 0;
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean unlink(User user, Task task) {
		try {
			return Queries.deleteQuery("aufgaben_mitglieder", "aufgabeID = "
					+ task.getId() + " AND mitgliedID = " + user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean unlinkAll(Task task) {
		try {
			return Queries.deleteQuery(FieldNames.TASKS_USERS, "aufgabeID="
					+ task.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
