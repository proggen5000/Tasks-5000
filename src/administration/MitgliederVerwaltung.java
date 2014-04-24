package administration;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Mitglied;

public class MitgliederVerwaltung {

	/**
	 * F�gt Werte eines Mitglieds in die Datenbank ein,
	 * liefert ein Mitglied mit den eben eingef�gten Werten zur�ck (inkl. ID)
	 * @param mitglied
	 * @return testmitglied
	 */
	public static Mitglied neu (Mitglied mitglied){
		
		//Einf�gen der Werte (ohne ID)
		String table= "mitglieder";
		String columns= "mitgliedid, username, password, email, vorname, nachname, "
				+"regdatum";
		String values= "NULL, "+mitglied.getUsername()+", "+mitglied.getPassword()+", "
				+mitglied.getEmail()+", "+mitglied.getVorname()+", "+mitglied.getNachname()
				+", "+mitglied.getRegdatum();
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
			//Erstellen eines Mitglieds mit den �bernommenen Werten (mit ID)
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
	 * liefert ein Mitglied mit allen Werten zur�ck
	 * ID und Registrierungsdatum k�nnen nicht ge�ndert werden
	 * @param mitglied
	 * @return testmitglied
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
	 * L�scht ein Mitglied komplett aus der Datenbank
	 * @param mitglied
	 * @return boolean
	 */
	public static boolean loeschen (Mitglied mitglied){
		
		//Mitglied anhand der ID l�schen
		String table= "mitglieder";
		String where= "mitgliedid="+mitglied.getId();
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
	 * liefert ein Mitglied mit den gefundenen Werten zur�ck
	 * @param id
	 * @return testmitglied
	 */
	public static Mitglied vorhanden (long id){
		
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
	 * Sucht ein Mitglied anhand des usernamens in der Datenbank
	 * liefert ein Mitglied mit den gefundenen Werten zur�ck
	 * @param username
	 * @return testmitglied
	 */
	public static Mitglied vorhanden (String username){
		
		//Suchen des Mitglieds anhand der ID
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
	 * liefert eine ArrayList aller Mitglieder
	 * @return al
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
	 * @param aufgID
	 * @return al
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

	public static Mitglied get(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Mitglied getDummy(long id) {
		Mitglied dummyMitglied = new Mitglied(4, "Dummy-Mitglied", "qwertz", "test@mail.de", "Max", "Mustermann", 1398100350);
		return dummyMitglied;
	}
	
	/**
	 * Liefert das Mitglied mit dem angegebenen Username 
	 * @return Objekt des Mitglieds mit dem angegebenen Username
	 */
	public static Mitglied getAnhandUsername(String username){
		return null;
	}
	
	public static Mitglied getAnhandUsernameDummy(String username){
		Mitglied dummyMitglied = new Mitglied(4, "Dummy-Mitglied", "qwertz", "test@mail.de", "Max", "Mustermann", 1398100350);
		return dummyMitglied;
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
