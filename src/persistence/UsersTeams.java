package persistence;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;

import database.FieldNames;
import database.Queries;
import entities.Team;

public class UsersTeams {

	final static String DEFAULT_ACCESS = "Member";

	public static boolean link(long userId, long teamId, String access) {
		Calendar cal = Calendar.getInstance();
		String table = FieldNames.USERS_TEAMS;
		String columns = "mitgliedID, teamID, berechtigung, beitrittsdatum";
		if (access == null) {
			access = DEFAULT_ACCESS;
		}
		String values = userId + ", " + teamId + ", '" + access + "', "
				+ cal.getTimeInMillis();
		int testId;

		try {
			testId = Queries.insertQuery(table, columns, values);
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			testId = 0;
		} catch (SQLException e) {
			e.printStackTrace();
			testId = -1;
		}
		return testId >= 0;
	}

	/**
	 * Ändert die Berechtigung eines Mitglieds in einem Team. Liefert true bei
	 * Aktualisierung, false bei Fehlern.
	 * 
	 * @param mitgliedid
	 *            ID des Mitglieds
	 * @param teamid
	 *            ID des Teams
	 * @param berechtigung
	 *            neue Berechtigung des Mitglieds
	 * @return boolean
	 */
	public static boolean edit(long mitgliedid, long teamid, String berechtigung) {
		String table = FieldNames.USERS_TEAMS;
		String updateString = "berechtigung=" + berechtigung;
		String where = "mitglieder_mitgliedid=" + mitgliedid
				+ " AND teams_teamid=" + teamid;

		try {
			return Queries.updateQuery(table, updateString, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean unlink(long mitgliedid, long teamid) {
		String table = FieldNames.USERS_TEAMS;
		String where = "mitgliedid=" + mitgliedid + " AND teamid=" + teamid;
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean unlinkAll(Team team) {
		try {
			return Queries.deleteQuery(FieldNames.USERS_TEAMS,
					"teamID=" + team.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
