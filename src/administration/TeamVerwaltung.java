package administration;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.Queries;
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
				Team testteam= get(testID);
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
		String updateString= "name="+team.getName()+", beschreibung="+team.getBeschreibung()
				+", gruppenfuehrerid="+team.getGruppenfuehrer().getId();
		String where= "teamid="+team.getId();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				Team testteam= get(team.getId());
				return testteam;
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
	 * Löscht ein Team komplett aus der DB
	 * Löscht außerdem: Dateien und Aufgabengruppen des Teams, Verbindungen zu Mitgliedern,
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
		String aufgabengruppensql= "SELECT aufgabengruppeid FROM aufgabengruppen "
								+"WHERE teamid= "+teamid;
				
		try {
			//löschen der dazugehörenden Dateien
			ResultSet rs= Queries.rowQuery(dateisql); 
			if (rs!= null){
				while (rs.next()){
					DateiVerwaltung.loeschen(rs.getLong("dateiid"));
				}
			}
			
			//löschen aller Verbindungen zu weiteren Mitgliedern
			rs= Queries.rowQuery(mitgliederteamsql); 
			if (rs!= null){
				while (rs.next()){
					MitgliederTeams.austreten(rs.getLong("mitgliedid"), teamid);
				}
			}
			
			//löschen der dazugehörenden Aufgabengruppen
			rs= Queries.rowQuery(aufgabengruppensql); 
			if (rs!= null){
				while (rs.next()){
					AufgabengruppenVerwaltung.loeschen(AufgabengruppenVerwaltung.get(rs.getLong("aufgabengruppeid")));
				}
			}
			
			//löschen des Teams
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
		try {
			ResultSet rs = Queries.rowQuery("*", "teams", "teamid = "+teamid);
			rs.next();
			return createTeambyRow(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sucht ein Team anhand des Teamnamens in der Datenbank
	 * liefert ein Team mit den gefundenen Werten zurueck
	 * @param teamname
	 * @return testteam
	 */
	public static Team get(String teamname){
		try {
			ResultSet rs = Queries.rowQuery("*", "teams", "teamname = '"+teamname+"'");
			rs.next();
			return createTeambyRow(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sucht ein Team anhand einer ID
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param teamid ID des gesuchten Teams
	 * @return boolean
	 */
	public static boolean vorhanden(long id){
		return get(id) != null;
	}
	
	/**
	 * Sucht ein Mitglied anhand eines Usernamens
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param teamname Username des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean vorhanden(String name){
		return get(name) != null;
	}
	
	/**
	 * liefert eine Liste aller Teams
	 * @return al ArrayList mit allen Teams
	 */
	public static ArrayList<Team> getListe(){
		ArrayList<Team> al = new ArrayList<Team>();
		try{
			ResultSet rs = Queries.rowQuery("*", "teams", "true");
			while(rs.next()){
				al.add(createTeambyRow(rs));
			}
			return al;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * liefert eine Liste mit allen Teams eines Mitglieds
	 * @param mitgliedID ID des Mitglieds
	 * @return al ArrayList mit allen Teams
	 */
	public static ArrayList<Team> getListeVonMitglied(long mitgliedID){
		ArrayList<Team> al = new ArrayList<Team>();
		try{
			String sql = "SELECT teams.* FROM teams JOIN mitglieder_teams "
				+"ON teams.teamid= mitglieder_teams.teamid "
				+"JOIN mitglieder ON mitglieder.mitgliedid = mitglieder_teams.mitgliedid "
				+"WHERE mitglieder.mitgliedid= " + mitgliedID;
			ResultSet rs = Queries.rowQuery(sql);
			while(rs.next()){
				al.add(createTeambyRow(rs));
			}
			return al;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private static Team createTeambyRow(ResultSet rs){
		try {
			Team t= new Team(rs.getLong("teamid"), rs.getString("name"),
					rs.getLong("gruendungsdatum"), rs.getString("beschreibung"),
					MitgliederVerwaltung.get(rs.getLong("gruppenfuehrerid")));
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
