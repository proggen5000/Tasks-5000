package persistence;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;

import database.Queries;
import entities.Team;

public class UsersTeams {

	/**
	 * Schreibt eine neue Verbindung zwischen einem Mitglied und einem Team in
	 * die DB. Liefert true bei Erfolg und false bei Misserfolg.
	 * 
	 * @param userId
	 *            ID des Mitglieds
	 * @param teamId
	 *            ID des Teams
	 * @param access
	 *            Berechtigung des Mitglieds (z.Bsp. admin, Mitglied, etc)
	 * @return boolean
	 */
	public static boolean link(long userId, long teamId, String access) {

		// aktuelles Datum beziehen
		Calendar cal = Calendar.getInstance();
		long beitrittsdatum = cal.getTimeInMillis();

		// Parameter vorbereiten
		String table = "mitglieder_teams";
		String columns = "mitgliedID, teamID, berechtigung, beitrittsdatum";
		if (access == null) {
			access = "Mitglied";
		}
		String values = userId + ", " + teamId + ", '" + access + "', "
				+ beitrittsdatum;
		int testId;

		// SQL mit Parametern ausführen
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

		// Vorbereiten der Parameter
		String table = "mitglieder_teams";
		String updateString = "berechtigung=" + berechtigung;
		String where = "mitglieder_mitgliedid=" + mitgliedid
				+ " AND teams_teamid=" + teamid;

		// SQL mit Parametern ausführen
		try {
			return Queries.updateQuery(table, updateString, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Löscht die Verbindung zwischen Mitglied und Team in der DB. Liefert true
	 * bei Erfolg, false bei Misserfolg.
	 * 
	 * @param mitgliedid
	 *            ID des Mitglieds
	 * @param teamid
	 *            ID des Teams
	 * @return boolean
	 */
	public static boolean unlink(long mitgliedid, long teamid) {

		String table = "mitglieder_teams";
		String where = "mitgliedid=" + mitgliedid + " AND teamid=" + teamid;
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Entfernt alle Mitgliederzuordnungen des angegebenen Teams.
	 * 
	 * @param team
	 *            Team, in dem alle Mitglieder(-zuordnungen) gelöscht werden
	 *            sollen
	 * @return Löschvorgang erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean unlinkAll(Team team) {
		try {
			return Queries.deleteQuery("mitglieder_teams",
					"teamID=" + team.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
