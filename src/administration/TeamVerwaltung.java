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
		
		//Einfuegen der Werte (ohne ID)
		String table= "teams";
		String columns= "teamid, teamname, gruendungsdatum, beschreibung, gruppenfuehrerid";
		String values= "NULL, "+team.getName()+", "+gruendungsdatum+", "
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
			//Erstellen eines Teams mit den uebernommenen Werten (mit ID)
			String sql= "SELECT * FROM teams WHERE testid="+testID;
			try {
				ResultSet rs= Queries.rowQuery(sql);
				Mitglied gruppenfuehrer= MitgliederVerwaltung.get(rs.getLong("gruppenfuehrerid"));
				Team testteam= new Team(rs.getLong("teamid"), rs.getString("name"),
						rs.getLong("gruendungsdatum"), rs.getString("beschreibung"),
						gruppenfuehrer);
				return testteam;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
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
		String updateString= "name="+team.getName()+", beschreibung="+team.getBeschreibung()
				+", gruppenfuehrerid="+team.getGruppenfuehrer().getId();
		String where= "teamid="+team.getId();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				//erstellen eines Teams mit aktualisierten Daten
				String sql= "SELECT * FROM teams WHERE teamid="+team.getId();
				try {
					ResultSet rs= Queries.rowQuery(sql);
					Mitglied gruppenfuehrer= MitgliederVerwaltung.get(rs.getLong("gruppenfuehrerid"));
					Team testteam= new Team(rs.getLong("teamid"), rs.getString("name"),
							rs.getLong("gruendungsdatum"), rs.getString("beschreibung"),
							gruppenfuehrer);
					return testteam;
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Loescht ein Team komplett aus der DB
	 * Loescht auﬂerdem: Dateien des Teams, Verbindungen zu Mitgliedern
	 * @param teamid
	 * @return boolean
	 */
	public static boolean loeschen (long teamid){
		
		//Team anhand der ID loeschen
		String table= "teams";
		String where= "teamid="+teamid;
		String dateisql= "SELECT dateiid FROM dateien WHERE teamid= "+teamid;
		String mitgliederteamsql= "SELECT mitgliedid FROM "
								+"mitglieder_teams WHERE teamid= "+teamid;
				
		try {
			ResultSet rs= Queries.rowQuery(dateisql); 
			if (rs!= null){
				while (rs.next()){
					DateiVerwaltung.loeschen(rs.getLong("dateiid"));
				}
			}
			
			rs= Queries.rowQuery(mitgliederteamsql); 
			if (rs!= null){
				while (rs.next()){
					MitgliederTeams.austreten(rs.getLong("mitgliedid"), teamid);
				}
			}
			
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
	public static Team get(long teamid){
		
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
	public static Team get(String teamname){
		
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
				Mitglied gruppenfuehrer= MitgliederVerwaltung.get(rs.getLong("gruppenfuehrerid"));
				Team t= new Team(rs.getLong("teamid"), rs.getString("name"),
						rs.getLong("gruendungsdatum"), rs.getString("beschreibung"),
						gruppenfuehrer);
				al.add(t);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	/**
	 * liefert eine Liste mit allen Teams eines Mitglieds
	 * @param mitgliedID ID des Mitglieds
	 * @return al ArrayList mit allen Teams
	 */
	public static ArrayList<Team> getListeVonMitglied(long mitgliedID){
		
		String sql = "SELECT * FROM teams JOIN mitglieder_teams "
				+"ON teams.teamid= mitglieder_teams.teamid "
				+"JOIN mitglieder ON mitglieder.mitgliederid = mitglieder_teams.mitgliedid "
				+"WHERE mitglieder.mitgliedid= " + mitgliedID;
		ArrayList<Team> al = new ArrayList<Team>();
	
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Mitglied gruppenfuehrer= MitgliederVerwaltung.get(rs.getLong("gruppenfuehrerid"));
				Team t= new Team(rs.getLong("id"), rs.getString("name"),
						rs.getLong("gruendungsdatum"), rs.getString("beschreibung"),
						gruppenfuehrer);
				al.add(t);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
}
