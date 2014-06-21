package administration;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;

import database.Queries;
import entities.Team;

public class MitgliederTeams {

	/**
	 * Schreibt eine neue Verbindung zwischen einem Mitglied und einem Team in
	 * die DB. Liefert true bei Erfolg und false bei Misserfolg.
	 * 
	 * @param mitgliedid
	 *            ID des Mitglieds
	 * @param teamid
	 *            ID des Teams
	 * @param berechtigung
	 *            Berechtigung des Mitglieds (z.Bsp. admin, Mitglied, etc)
	 * @return boolean
	 */
	public static boolean beitreten(long mitgliedid, long teamid, String berechtigung){
		
		//aktuelles Datum beziehen
		Calendar cal = Calendar.getInstance();
		long beitrittsdatum= cal.getTimeInMillis();
		
		//Parameter vorbereiten
		String table= "mitglieder_teams";
		String columns= "mitgliedID, teamID, berechtigung, beitrittsdatum";
		if (berechtigung==null){
			berechtigung="Mitglied";
		}
		String values= mitgliedid+", "+teamid+", '"+berechtigung+"', "+beitrittsdatum;
		int testID;
		
		//SQL mit Parametern ausführen
		try {
			testID = Queries.insertQuery(table, columns, values);
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			testID = 0;
		} catch(SQLException e){
			e.printStackTrace();
			testID = -1;
		}
		return testID >= 0;
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
	public static boolean bearbeiten(long mitgliedid, long teamid, String berechtigung){
		
		//Vorbereiten der Parameter
		String table= "mitglieder_teams";
		String updateString= "berechtigung="+berechtigung;
		String where= "mitglieder_mitgliedid="+mitgliedid+" AND teams_teamid="+teamid;
		
		//SQL mit Parametern ausführen
		try {
			return Queries.updateQuery(table, updateString, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public static boolean austreten(long mitgliedid, long teamid){
		
		String table= "mitglieder_teams";
		String where= "mitgliedid="+mitgliedid+" AND teamid="+teamid;
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public static boolean entfernenAlle(Team team){
		try {
			return Queries.deleteQuery("mitglieder_teams", "teamID=" + team.getId());
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
