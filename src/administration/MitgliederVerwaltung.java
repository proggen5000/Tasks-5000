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
			//Erstellen eines Mitglieds mit den uebernommenen Werten (mit ID)
			String sql= "SELECT * FROM mitglieder WHERE mitgliedid="+testID;
			try {
				ResultSet rs= Queries.rowQuery(sql);
				Mitglied testmitglied= new Mitglied(rs.getLong("mitgliedid"),
						rs.getString("username"), rs.getString("pw"), rs.getString("email"),
						rs.getString("vorname"), rs.getString("nachname"),
						rs.getLong("regdatum"));
				return testmitglied;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
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
				//erstellen eines Mitglieds mit aktualisierten Daten
				String sql= "SELECT * FROM mitglieder WHERE mitgliedid="+mitglied.getId();
				try {
					ResultSet rs= Queries.rowQuery(sql);
					Mitglied testmitglied= new Mitglied(rs.getLong("mitgliedid"),
							rs.getString("username"), rs.getString("pw"), rs.getString("email"),
							rs.getString("vorname"), rs.getString("nachname"),
							rs.getLong("regdatum"));
					return testmitglied;
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
	 * Loescht ein Mitglied komplett aus der Datenbank
	 * @param mitglied
	 * @return boolean
	 */
	public static boolean loeschen (long id){
		
		//Mitglied anhand der ID loeschen
		String table= "mitglieder";
		String where= "mitgliedid="+id;
		String sql= "SELECT teamid FROM teams WHERE erstellerid= "+id;
				
		
		try {
			ResultSet rs= Queries.rowQuery(sql); 
			if (rs!= null){
				while (rs.next()){
					TeamVerwaltung.loeschen(rs.getLong("teamid"));
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
	 * Sucht ein Mitglied anhand der ID in der Datenbank
	 * liefert ein Mitglied mit den gefundenen Werten zurueck
	 * @param id
	 * @return testmitglied
	 */
	public static Mitglied get(long id){
		try{
			ResultSet rs = Queries.rowQuery("*", "Mitglieder", "MitgliedId = "+id);
			rs.next();
			return createMitgliedbyRow(rs);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sucht ein Mitglied anhand des Usernamens in der Datenbank
	 * liefert ein Mitglied mit den gefundenen Werten zurueck
	 * @param username
	 * @return testmitglied
	 */
	public static Mitglied get(String username){
		
		//Suchen des Mitglieds anhand des usernamens
		try{
			long id = (Long) Queries.scalarQuery("Mitglieder", "MitgliedID", "username = '"+username+"'");
			
			return get(id);
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
			ResultSet rs = Queries.rowQuery("*", "Mitglieder", "true ORDER BY username DSC");
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
		String sql = "SELECT * FROM mitglieder JOIN aufgaben_mitglieder "
					+"ON mitglieder.mitgliedid= aufgaben_mitglieder.mitgliedid "
					+"JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_mitglieder.aufgabeid "
					+"WHERE aufgaben.aufgabeid= " + aufgID +" ORDER BY username DSC";
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
					+"WHERE teams.teamid= " + teamID + " ORDER BY username DSC";
		
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
		
		String sql= "SELECT Mitglied FROM mitglieder JOIN mitglieder_teams "
					+"ON "+mitgliedID+"= mitglieder_teams.mitgliedid "
					+"JOIN teams ON teams.teamid= mitglieder_teams.teamid "
					+"WHERE teams.teamid= " + teamID;
		long testID;
		
		try {
			testID= (Long) Queries.scalarQuery(sql);
			if (testID!= -1){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
	public static ArrayList<Mitglied> getListeVonAufgabeRest(long aufgabenID){
		ArrayList<Mitglied> usersTask = getListeVonAufgabe(aufgabenID);
		ArrayList<Mitglied> usersRest = new ArrayList<Mitglied>();
		
		for(Mitglied m : usersRest){
			if(!usersTask.contains(m)){
				usersRest.add(m);
			}
		}
		
		return usersRest;
	}

	private static Mitglied createMitgliedbyRow(ResultSet rs){
		try {
			Mitglied m = new Mitglied(rs.getLong("MitgliedID"), rs.getString("username"),
					rs.getString("PW"), rs.getString("email"),
					rs.getString("vorname"), rs.getString("nachname"),
					rs.getLong("regdatum"));
			return m;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
