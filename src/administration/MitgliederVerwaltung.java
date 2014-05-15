package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.Queries;
import entities.Mitglied;

public class MitgliederVerwaltung {

	/**
	 * Fuegt Werte eines Mitglieds in die Datenbank ein,
	 * liefert ein Mitglied mit den eben eingefuegten Werten zurueck (inkl. ID)
	 * @param mitglied Werte werden ein die DB eingefuegt
	 * @return testmitglied mit den Werten aus der Datenbank
	 */
	public static Mitglied neu (Mitglied mitglied){
		
		//aktuelles Datum beziehen
		Calendar cal = Calendar.getInstance();
		long regdatum= cal.getTimeInMillis();
		
		//Einfuegen der Werte (ohne ID)
		String table= "mitglieder";
		String columns= "mitgliedid, username, pw, email, vorname, nachname, "
				+"regdatum";
		String values= "NULL, "+mitglied.getUsername()+", "+mitglied.getPw()+", "
				+mitglied.getEmail()+", "+mitglied.getVorname()+", "+mitglied.getNachname()
				+", "+regdatum;
		long testID;
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
			Mitglied testmitglied= get(testID);
				return testmitglied;
		}
	}
	
	/**
	 * Aktualisiert Werte eines Mitglieds in der Datenbank,
	 * liefert ein Mitglied mit aktualisierten Werten zurueck
	 * ID und Registrierungsdatum koennen nicht geaendert werden
	 * @param mitglied mit aktualiserten Werten wird eingefuegt
	 * @return testmitglied mit den aktualisierten Werten aus der DB
	 */
	public static Mitglied bearbeiten (Mitglied mitglied){
		
		//Aktualisieren des Mitglieds
		String table= "mitglieder";
		String updateString= "username="+mitglied.getUsername()+", pw="
				+mitglied.getPw()+", email="+mitglied.getEmail()+", vorname="
				+mitglied.getVorname()+", nachname="+mitglied.getNachname();
		String where= "mitgliedid="+mitglied.getId();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				Mitglied testmitglied= get(mitglied.getId());
					return testmitglied;
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
	 * Loescht ein Mitglied komplett aus der Datenbank
	 * @param mitglied
	 * @return boolean
	 */
	public static boolean loeschen (long id){
		
		//Mitglied anhand der ID loeschen
		String table= "mitglieder";
		String where= "mitgliedid="+id;
		String gruppenfuehrersql= "SELECT teamid FROM teams WHERE gruppenfuehrerid= "+id;
		String teamsql="SELECT teamid FROM mitglieder_teams WHERE mitgliedid= "+id;
		String aufgabensql= "SELECT aufgabenid FROM aufgaben_mitglieder WHERE mitgliedid= "+id;
				
		try {
			//löscht Teams wo das Mitglied gruppenfuehrer war
			ResultSet rs= Queries.rowQuery(gruppenfuehrersql); 
			if (rs!= null){
				while (rs.next()){
					TeamVerwaltung.loeschen(rs.getLong("teamid"));
				}
			}
			
			//löscht Verbindungen zu bestehenden Teams
			rs= Queries.rowQuery(teamsql); 
			if (rs!= null){
				while (rs.next()){
					MitgliederTeams.austreten(id, rs.getLong("teamid"));
				}
			}
			
			//löscht zugeordnete Aufgaben
			rs= Queries.rowQuery(aufgabensql); 
			if (rs!= null){
				while (rs.next()){
					AufgabenMitglieder.entfernen(MitgliederVerwaltung.get(id), AufgabenVerwaltung.get(rs.getLong("aufgabenid")));
				}
			}
			
			//löscht das Mitglied
			return Queries.deleteQuery(table, where);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * Sucht ein Mitglied anhand der ID in der Datenbank
	 * liefert ein Mitglied mit den gefundenen Werten zurueck
	 * @param id
	 * @return testmitglied
	 */
	public static Mitglied get(long id){
		try{
			ResultSet rs = Queries.rowQuery("*", "Mitglieder", "MitgliedId = "+id);
			if(rs.isBeforeFirst()){
				rs.next();
				return createMitgliedbyRow(rs);
			}
		}catch(SQLException e){
			System.err.println("Mitgliederverwaltung.get(long) - SQL ERROR");
		}
		return null;
	}
	
	/**
	 * Sucht ein Mitglied anhand des Usernamens in der Datenbank
	 * liefert ein Mitglied mit den gefundenen Werten zurueck
	 * @param username
	 * @return testmitglied
	 */
	public static Mitglied get(String username){
		
		try{
			ResultSet rs = Queries.rowQuery("*", "Mitglieder", "username = '"+username+"'");
			rs.next();
			return createMitgliedbyRow(rs);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sucht ein Mitglied anhand einer id
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param id ID des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean vorhanden(long id){
		return get(id) != null;
	}
	
	/**
	 * Sucht ein Mitglied anhand eines Usernamens
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param username Username des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean vorhanden(String username){
		return get(username) != null;
	}
	
	/**
	 * liefert eine ArrayList aller Mitglieder
	 * @return al ArrayList mit Mitgliedern
	 */
	public static ArrayList<Mitglied> getListe(){
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
		try {
			ResultSet rs = Queries.rowQuery("*", "Mitglieder", "true ORDER BY username ASC");
			while(rs.next()){
				al.add(createMitgliedbyRow(rs));
			}
			return al;
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * liefert eine ArrayList aller Mitglieder, die einer bestimmten Aufgabe zugeordnet sind
	 * @param aufgID ID der Aufgabe
	 * @return al ArrayList mit Mitgliedern
	 */
	public static ArrayList<Mitglied> getListeVonAufgabe(long aufgID){
		String sql = "SELECT * FROM mitglieder INNER JOIN aufgaben_mitglieder "
					+"ON mitglieder.mitgliedid= aufgaben_mitglieder.mitgliedid "
					+"INNER JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_mitglieder.aufgabeid "
					+"WHERE aufgaben.aufgabeid= " + aufgID +" ORDER BY username ASC";
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
		
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				al.add(createMitgliedbyRow(rs));
			}
			return al;
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * liefert eine ArrayList aller Mitglieder eines Teams
	 * @param teamID
	 * @return al
	 */
	public static ArrayList<Mitglied> getListeVonTeam(long teamID){
		String sql = "SELECT * FROM mitglieder JOIN mitglieder_teams "
					+"ON mitglieder.mitgliedid= mitglieder_teams.mitgliedid "
					+"JOIN teams ON teams.teamid = mitglieder_teams.teamid "
					+"WHERE teams.teamid= " + teamID + " ORDER BY username ASC";
		
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
		
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				al.add(createMitgliedbyRow(rs));
			}
			return al;
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Pr�ft, ob ein bestimmtes Mitglied einem bestimmten Team zugeordnet ist
	 * @param mitgliedID
	 * @param teamID
	 * @return boolean
	 */
	public static boolean istMitgliedInTeam(long mitgliedID, long teamID){
		try {
			ResultSet rs = Queries.rowQuery("*", "mitglieder_teams", "MitgliedID = " + mitgliedID + " AND TeamID = " + teamID);
			if (rs.isBeforeFirst()){
				return true;
			}
		} catch (SQLException e) {
			System.err.println("istMitgliedInTeam - SQLERROR");
		}
		return false;
	}
	
	/**
	 * Prueft, ob Username und Password uebereinstimmen
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean pruefeLogin(String username, String password){
		try{
			ResultSet rs = Queries.rowQuery("*", "Mitglieder", "username = '"+username+"' AND pw = PASSWORD('"+password+"')");
			if(rs.next()){
				return true;
			}
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * Liefert alle Mitglieder eines Teams, die der Aufgabe noch nicht zugeordnet sind
	 * @param aufgabenID
	 * @return
	 */
	public static ArrayList<Mitglied> getListeVonAufgabeRest(long teamId, long aufgabenId){
		ArrayList<Mitglied> usersTeam = getListeVonTeam(teamId);
		ArrayList<Mitglied> usersTask = getListeVonAufgabe(aufgabenId);		
		
		for(Mitglied m : usersTeam){
			if(usersTask.contains(m)){
				usersTask.remove(m);
			}else{
				usersTask.add(m);
			}
		}
		
		return usersTask;
	}
	
	/**
	 * Liefert alle Benutzer, die dem Team noch nicht zugeordnet sind
	 * @param aufgabenID
	 * @return
	 */
	public static ArrayList<Mitglied> getListeVonTeamRest(long teamId){
		ArrayList<Mitglied> usersAll = getListe();
		ArrayList<Mitglied> usersTeam = getListeVonTeam(teamId);
		
		for(Mitglied m : usersTeam){
			
			if(usersAll.contains(m)){
				usersAll.remove(m);
			}
		}
		
		return usersAll;
	}

	private static Mitglied createMitgliedbyRow(ResultSet rs){
		try {
			if(!rs.isBeforeFirst() && !rs.isAfterLast()){
				Mitglied m = new Mitglied(rs.getLong("MitgliedID"), rs.getString("username"),
						rs.getString("PW"), rs.getString("email"),
						rs.getString("vorname"), rs.getString("nachname"),
						rs.getLong("regdatum"));
				return m;
			}else{
				return null;
			}
		} catch (SQLException e) {
			System.err.println("Mitgliederverwaltung.createMitgliedbyRow - SQL ERROR");
			return null;
		}
	}
}
