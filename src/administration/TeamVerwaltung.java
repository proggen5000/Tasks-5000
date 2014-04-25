package administration;


import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Mitglied;
import entities.Team;

public class TeamVerwaltung {

	public static Team neu (Team team){
		
		//Einf�gen der Werte (ohne ID)
		String table= "teams";
		String columns= "teamid, teamname, gruendungsdatum, slogan, gruppenfuehrerid";
		String values= "NULL, "+team.getTeamname()+", "+team.getGruendungsdatum()+", "
				+team.getBeschreibung()+", "+team.getGruppenfuehrer().getId();
		int testID;
		try {
			testID = Queries.insertQuery(table, columns, values);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			testID= -1;
		}
		
		if (testID== -1){
			return null;
		}
		else{
			//Erstellen eines Teams mit den �bernommenen Werten (mit ID)
			Team testteam= new Team();
			String sql= "SELECT * FROM teams WHERE testid="+testID;
			try {
				testteam= (Team)Queries.scalarQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return testteam;
		}
	}
	
	public static Team bearbeiten (Team team){
		
		//Aktualisieren des Teams
		String table= "teams";
		String updateString= "teamname="+team.getTeamname()+", slogan="+team.getBeschreibung()
				+", gruppenfuehrerid="+team.getGruppenfuehrer().getId();
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
	
	public static boolean loeschen (Team team){
		
		//Team anhand der ID l�schen
		String table= "teams";
		String where= "teamid="+team.getId();
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean vorhanden (long id){
		return false;
	}
	
	public static boolean vorhanden (String name){
		return false;
	}
	
	public static ArrayList<Team> getListe(){
		return null;
	}
	
	public static ArrayList<Team> getListeVonMitglied(long mitgliedID){
		return null;
	}
	
	public static ArrayList<Team> getListeVonMitgliedDummy(long mitgliedID){ // TODO
		Mitglied dummyMitglied = new Mitglied(mitgliedID, "Dummy-Mitglied", "qwertz", "test@mail.de", "Dummy", "User", 23453467);
		Team dummyTeam1 = new Team(1337, "Dummy-Team 1", 214325, "Wir rulen.", dummyMitglied);
		Team dummyTeam2 = new Team(12, "Dummy-Team Zwei", 214325, "Wir rulen.", dummyMitglied);
		Team dummyTeam3 = new Team(1987, "Dummy-Team III", 214325, "Wir rulen.", dummyMitglied);
		ArrayList<Team> liste = new ArrayList<Team>();
		liste.add(dummyTeam1);
		liste.add(dummyTeam2);
		liste.add(dummyTeam3);
		return liste;
	}
	
	public static boolean neuesTeammitglied(long teamID, long mitgliedID){
		return false;
	}
	
	public static boolean loescheTeammitglied(long teamID, long mitgliedID){
		return false;
	}

	public static Team get(long teamID) {
		//Suchen des Teams anhand der ID
		String sql= "SELECT * FROM teams WHERE teamid="+teamID;
		Team testteam= new Team();
		try {
			testteam=(Team)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testteam;
	}
	
	public static Team getDummy(long teamID){ // TODO
		Mitglied dummyMitglied = new Mitglied(14, "Dummy-Mitglied", "qwertz", "test@mail.de", "Dummy", "User", 23453467);
		return new Team(teamID, "Dummy-Team", 214325, "Wir rulen. Das ist unser Slogan.", dummyMitglied);
	}
	
	public static Team get(String name){
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
}
