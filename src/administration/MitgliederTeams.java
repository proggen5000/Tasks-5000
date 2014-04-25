package administration;

import java.sql.SQLException;
import java.util.Calendar;
import database.Queries;

public class MitgliederTeams {

	/**
	 * Schreibt eine neue Verbindung zwischen einem Mitglied und einem Team in die DB
	 * liefert true bei Erfolg und false bei Misserfolg zurueck
	 * @param mitgliedid ID des Mitglieds
	 * @param teamid ID des Teams
	 * @param berechtigung Berechtigung des Mitglieds (z.Bsp. admin, Mitglied, etc)
	 * @return boolean
	 */
	public static boolean beitreten(long mitgliedid, long teamid, String berechtigung){
		
		//aktuelles Datum beziehen
		Calendar cal = Calendar.getInstance();
		long beitrittsdatum= cal.getTimeInMillis();
		
		//Parameter vorbereiten
		String table= "mitglieder_teams";
		String columns= "mitglieder_mitgliedid, teams_teamid, berechtigung, beitrittsdatum";
		if (berechtigung==null){
			berechtigung="mitglied";
		}
		String values= mitgliedid+", "+teamid+", "+berechtigung+", "+beitrittsdatum;
		int testID;
		
		//SQL mit Parametern ausführen
		try {
			testID = Queries.insertQuery(table, columns, values);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			testID= -1;
		}
		
		if (testID!= -1){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Ändert die Berechtigung eines Mitglieds in einem Team
	 * liefert true bei Aktualisierung, false bei Fehlern
	 * @param mitgliedid ID des Mitglieds
	 * @param teamid ID des Teams
	 * @param berechtigung neue Berechtigung des Mitglieds
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
	 * löscht die Verbindung zwischen Mitglied und Team in der DB
	 * liefert true bei Erfolg, false bei Misserfolg zurueck
	 * @param mitgliedid ID des Mitglieds
	 * @param teamid ID des Teams
	 * @return boolean
	 */
	public static boolean austreten(long mitgliedid, long teamid){
		
		String table= "mitglieder_teams";
		String where= "mitglieder_mitgliedid="+mitgliedid+" AND teams_teamid="+teamid;
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
		
}
