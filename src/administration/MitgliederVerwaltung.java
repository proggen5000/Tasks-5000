package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.Queries;
import entities.Mitglied;

public class MitgliederVerwaltung {

	/**
	 * Fuegtgt Werte eines Mitglieds in die Datenbank ein,
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
		String columns= "mitgliedid, username, password, email, vorname, nachname, "
				+"regdatum";
		String values= "NULL, "+mitglied.getUsername()+", "+mitglied.getPassword()+", "
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
			Mitglied testmitglied= new Mitglied();
			String sql= "SELECT * FROM mitglieder WHERE mitgliedid="+testID;
			try {
				testmitglied= (Mitglied)Queries.scalarQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		String updateString= "username="+mitglied.getUsername()+", password="
				+mitglied.getPassword()+", email="+mitglied.getEmail()+", vorname="
				+mitglied.getVorname()+", nachname="+mitglied.getNachname();
		String where= "mitgliedid="+mitglied.getId();
		Mitglied testmitglied= new Mitglied();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				//erstellen eines Mitglieds mit aktualisierten Daten
				String sql= "SELECT * FROM mitglieder WHERE mitgliedid="+mitglied.getId();
				try {
					testmitglied= (Mitglied)Queries.scalarQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return testmitglied;
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
		try {
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
	public static Mitglied getMitgliedWithId(long id){
		
		//Suchen des Mitglieds anhand der ID
		String sql= "SELECT * FROM mitglieder WHERE mitgliedid="+id;
		Mitglied testmitglied= new Mitglied();
		try {
			testmitglied=(Mitglied)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testmitglied;
	}
	
	/**
	 * Sucht ein Mitglied anhand des Usernamens in der Datenbank
	 * liefert ein Mitglied mit den gefundenen Werten zurueck
	 * @param username
	 * @return testmitglied
	 */
	public static Mitglied getMitgliedWithName(String username){
		
		//Suchen des Mitglieds anhand des usernamens
		String sql= "SELECT * FROM mitglieder WHERE username="+username;
		Mitglied testmitglied= new Mitglied();
		try {
			testmitglied=(Mitglied)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testmitglied;
	}
	
	/**
	 * Sucht ein Mitglied anhand einer id
	 * liefert true bei Fund, false bei Nichtexistenz zurueck
	 * @param id ID des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean vorhanden(long id){
		
		String sql= "SELECT * FROM mitglieder WHERE mitgliedid= "+id;
		Mitglied testmitglied= new Mitglied();
		try{
			testmitglied=(Mitglied)Queries.scalarQuery(sql);
			if (testmitglied.getId()!= -1){
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
	 * @param username Username des gesuchten Mitglieds
	 * @return boolean
	 */
	public static boolean vorhanden(String username){
		
		String sql= "SELECT * FROM mitglieder WHERE username= "+username;
		Mitglied testmitglied= new Mitglied();
		try{
			testmitglied=(Mitglied)Queries.scalarQuery(sql);
			if (testmitglied.getId()!= -1){
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
	 * liefert eine ArrayList aller Mitglieder
	 * @return al ArrayList mit Mitgliedern
	 */
	public static ArrayList<Mitglied> getListe(){

		String sql = "SELECT * FROM mitglieder";
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
		
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				Mitglied a= new Mitglied(rs.getLong("MitgliedID"), rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("vorname"), rs.getString("nachname"),
						rs.getLong("regdatum"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	/**
	 * liefert eine ArrayList aller Mitglieder, die einer bestimmten Aufgabe zugeordnet sind
	 * @param aufgID ID der Aufgabe
	 * @return al ArrayList mit Mitgliedern
	 */
	public static ArrayList<Mitglied> getListeVonAufgaben(long aufgID){
		
		String sql = "SELECT * FROM mitglieder JOIN aufgaben_mitglieder "
					+"ON mitglieder.mitgliedid= aufgaben_mitglieder.mitglieder_mitgliedid "
					+"JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_mitglieder.aufgaben_aufgabeid "
					+"WHERE aufgaben.aufgabeid= " + aufgID;
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
		
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Mitglied a= new Mitglied(rs.getLong("MitgliedID"), rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("vorname"), rs.getString("nachname"),
						rs.getLong("regdatum"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	/**
	 * liefert eine ArrayList aller Mitglieder eines Teams
	 * @param teamID
	 * @return al
	 */
	public static ArrayList<Mitglied> getListeVonTeam(long teamID){
		
		String sql = "SELECT * FROM mitglieder JOIN mitglieder_teams "
					+"ON mitglieder.mitgliedid= mitglieder_teams.mitglieder_mitgliedid "
					+"JOIN teams ON teams.teamid = mitglieder_teams.teams_teamid "
					+"WHERE teams.teamid= " + teamID;
		ArrayList<Mitglied> al = new ArrayList<Mitglied>();
		
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Mitglied a= new Mitglied(rs.getLong("MitgliedID"), rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("vorname"), rs.getString("nachname"),
						rs.getLong("regdatum"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	/**
	 * Pr�ft ob ein bestimmtes Mitglied einem bestimmten Team zugeordnet ist
	 * @param mitgliedID
	 * @param teamID
	 * @return boolean
	 */
	public static boolean istMitgliedInTeam(long mitgliedID, long teamID){
		
		String sql= "SELECT * FROM mitglieder JOIN mitglieder_teams "
					+"ON "+mitgliedID+"= mitglieder_teams.mitglieder_mitgliedid "
					+"JOIN teams ON teams.teamid= mitglieder_teams.teams_teamid "
					+"WHERE teams.teamid= " + teamID;
		long testID;
		
		try {
			ResultSet rs= Queries.rowQuery(sql);
			testID=rs.getLong("MitgliedID");
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
	 * Pr�ft ob Username und Password �bereinstimmen
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean pruefeLogin(String username, String password){
		
		String sql= "SELECT * FROM mitglieder WHERE username="+username;
		
		try {
			Mitglied testmitglied = (Mitglied)Queries.scalarQuery(sql);
			if (testmitglied.getPassword()==password){
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
	
	public static boolean pruefeLoginDummy(String username, String password){ // TODO
		if(username.equals("Bastart") && password.equals("kaesekuchen")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Entfernt das Mitglied aus dem angegebenen Team.
	 * @param teamID
	 * @return true bei Misserfolg, false bei Misserfolg
	 */
	public static boolean verlasseTeam(long teamID){
		return false;
	}
}
