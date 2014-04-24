package administration;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import database.Queries;
import entities.Mitglied;
import entities.Team;

public class MitgliederTeams {

	//Verknüpfer
	public static boolean neu(Mitglied mitglied, Team team, String berechtigung){
		
		//aktuelles Datum beziehen
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
		String date = dateFormatter.format(cal.getTime());
		
		//Parameter vorbereiten
		String table= "mitglieder_teams";
		String columns= "mitglieder_mitgliedid, teams_teamid, berechtigung, beitrittsdatum";
		if (berechtigung==null){
			berechtigung="mitglied";
		}
		String values= mitglied.getId()+", "+team.getId()+", "+berechtigung+", "+date;
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
	
	public static boolean bearbeiten(Mitglied mitglied, Team team, String berechtigung){
		
		//Vorbereiten der Parameter
		String table= "mitglieder_teams";
		String updateString= "berechtigung="+berechtigung;
		String where= "mitglieder_mitgliedid="+mitglied.getId()+" AND teams_teamid="+team.getId();
		
		//SQL mit Parametern ausführen
		try {
			return Queries.updateQuery(table, updateString, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
		
	public static boolean loeschen(Mitglied mitglied, Team team){
		
		//
		String table= "mitglieder_teams";
		String where= "mitglieder_mitgliedid="+mitglied.getId()+" AND teams_teamid="+team.getId();
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
		
}
