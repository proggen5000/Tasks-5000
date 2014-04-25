package administration;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.Queries;
import entities.Mitglied;
import entities.Team;

public class TeamVerwaltung {

	/**
	 * Fuegt Werte eines Teams in die DB ein (ohne ID),
	 * liefert ein Team mit den eben eingefuegten Werten zurueck (inkl. ID)
	 * @param team Werte werden in die DB eingefuegt
	 * @return testteam mit den Werten aus der Datenbank
	 */
	public static Team neu (Team team){
		
		//aktuelles Datum beziehen
		Calendar cal = Calendar.getInstance();
		long gruendungsdatum= cal.getTimeInMillis();
		
		//Einfügen der Werte (ohne ID)
		String table= "teams";
		String columns= "teamid, teamname, gruendungsdatum, slogan, gruppenfuehrerid";
		String values= "NULL, "+team.getTeamname()+", "+gruendungsdatum+", "
				+team.getSlogan()+", "+team.getGruppenfuehrer().getId();
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
			//Erstellen eines Teams mit den übernommenen Werten (mit ID)
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
	
	/**
	 * Aktualisiert Werte eines Teams in der DB,
	 * liefert ein Team mit aktualisierten Werten zurueck
	 * ID und Gruendungsdatum koennen nicht geaendert werden
	 * @param team mit aktualiserten Werten wird eingefuegt
	 * @return testteam mit den aktualisierten Werten aus der DB
	 */
	public static Team bearbeiten (Team team){
		
		//Aktualisieren des Teams
		String table= "teams";
		String updateString= "teamname="+team.getTeamname()+", slogan="+team.getSlogan()
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
	
	/**
	 * Loescht ein Team komplett aus der DB
	 * @param team
	 * @return boolean
	 */
	public static boolean loeschen (long teamid){
		
		//Team anhand der ID löschen
		String table= "teams";
		String where= "teamid="+teamid;
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Sucht ein Team anhand der ID in der Datenbank
	 * liefert ein Team mit den gefundenen Werten zurueck
	 * @param teamid
	 * @return testteam
	 */
	public static Team getTeamWithId(long teamid){
		
		//Suchen des Teams anhand der ID
		String sql= "SELECT * FROM teams WHERE teamid="+teamid;
		Team testteam= new Team();
		try {
			testteam=(Team)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testteam;
	}
	
	/**
	 * Sucht ein Team anhand des Teamnamens in der Datenbank
	 * liefert ein Team mit den gefundenen Werten zurueck
	 * @param teamname
	 * @return testteam
	 */
	public static Team getTeamWithName(String teamname){
		
		//Suchen des Mitglieds anhand des usernamens
		String sql= "SELECT * FROM mitglieder WHERE username="+teamname;
		Team testteam= new Team();
		try {
			testteam=(Team)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testteam;
	}
	
	/**
	 * Sucht ein Team anhand einer ID
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param teamid ID des gesuchten Teams
	 * @return boolean
	 */
	public static boolean vorhanden(long teamid){
		
		String sql= "SELECT * FROM teams WHERE teamid= "+teamid;
		Team testteam= new Team();
		try{
			testteam=(Team)Queries.scalarQuery(sql);
			if (testteam.getId()!= -1){
				return true;
			}
			else{
				return false;
			}
		}catch (SQLException e){
			return false;
		}
	}
	
	/**
	 * Sucht ein Mitglied anhand eines Usernamens
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param teamname Username des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean vorhanden(String teamname){
		
		String sql= "SELECT * FROM teams WHERE teamname= "+teamname;
		Team testteam= new Team();
		try{
			testteam=(Team)Queries.scalarQuery(sql);
			if (testteam.getId()!= -1){
				return true;
			}
			else{
				return false;
			}
		}catch (SQLException e){
			return false;
		}
	}
	
	/**
	 * liefert eine Liste aller Teams
	 * @return al ArrayList mit allen Teams
	 */
	public static ArrayList<Team> getListe(){

		String sql = "SELECT * FROM teams";
		ArrayList<Team> al = new ArrayList<Team>();
		
		try {
			ResultSet rs = Queries.rowQuery(sql);
			while(rs.next()){
				Team t= new Team(rs.getLong("teamid"), rs.getString("teamname"),
						rs.getLong("gruendungsdatum"), rs.getString("slogan"),
						(Mitglied)rs.getObject("gruppenfuehrer"));
				al.add(t);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public static ArrayList<Team> getListeVonMitglied(long mitgliedID){
		
		String sql = "SELECT * FROM mitglieder JOIN aufgaben_mitglieder "
				+"ON mitglieder.mitgliedid= aufgaben_mitglieder.mitglieder_mitgliedid "
				+"JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_mitglieder.aufgaben_aufgabeid "
				+"WHERE aufgaben.aufgabeid= " + aufgID;
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
	
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Mitglied a= 
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
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
