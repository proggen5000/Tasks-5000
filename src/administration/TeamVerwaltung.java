package administration;

import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Team;

public class TeamVerwaltung {

	public Team neu (Team team){
		
		//Einfügen der Werte (ohne ID)
		String table= "teams";
		String columns= "teamid, teamname, gründungsdatum, slogan, gruppenführerid";
		String values= "NULL, "+team.getName()+", "+team.getGruendungsdatum()+", "
				+team.getSlogan()+", "+team.getGruppenfuehrer().getId();
		int testID= Queries.insertQuery(table, columns, values);
		
		if (testID== -1){
			return null;
		}
		else{
			//Erstellen eines Teams mit den übernommenen Werten (mit ID)
			Team testteam= new Team();
			String sql= "SELECT * FROM team WHERE testid="+testID;
			try {
				testteam= (Team)Queries.scalarQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return testteam;
		}
	}
	
	public Team bearbeiten (Team team){
		
		//Aktualisieren des Teams
		String table= "teams";
		String updateString= "teamname="+team.getName()+", slogan="+team.getSlogan()
				+", gruppenführerid="+team.getGruppenfuehrer().getId();
		String where= "teamid="+team.getId();
		Team testteam= new Team();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				//erstellen eines Teams mit aktualisierten Daten
				String sql= "SELECT * FROM teams WHERE teamid="+team.getId();
				try {
					testteam= (Team)Queries.scalarQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return testteam;
	}
	
	public boolean loeschen (Team team){
		
		//Team anhand der ID löschen
		String table= "teams";
		String where= "teamid="+team.getId();
		return Queries.deleteQuery(table, where);
	}
	
	public static Team vorhanden (int id){
		
		//Suchen des Mitglieds anhand der ID
		String sql= "SELECT * FROM teams WHERE teamid="+id;
		Team testteam= new Team();
		try {
			testteam=(Team)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testteam;
	}
	
	public static Team vorhanden (String name){
		
		//Suchen des Teams anhand des Namens
		String sql= "SELECT * FROM teams WHERE teamname="+name;
		Team testteam= new Team();
		try {
			testteam=(Team)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testteam;
	}
	
	public ArrayList<Team> getListe(){
		return null;
	}
	
	public ArrayList<Team> getListeVonMitglied(int mitgliedID){
		return null;
	}
	
	public boolean neuesTeammitglied(int teamID, int mitgliedID){
		return false;
	}
	
	public boolean loescheTeammitglied(int teamID, int mitgliedID){
		return false;
	}

	public static Team get(int int1) {
		// TODO Auto-generated method stub
		return null;
	}
}
