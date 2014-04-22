package administration;

import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Mitglied;

public class MitgliederVerwaltung {

	public static Mitglied neu (Mitglied mitglied){
		
		//Einfügen der Werte (ohne ID)
		String table= "mitglieder";
		String columns= "mitgliedid, username, pw-hash, email, vorname, nachname, "
				+"registrierungsdatum";
		String values= "NULL, "+mitglied.getUsername()+", "+mitglied.getPassword()+", "
				+mitglied.getEmail()+", "+mitglied.getVorname()+", "+mitglied.getNachname()
				+", "+mitglied.getReg_datum();
		int testID= Queries.insertQuery(table, columns, values);
		
		if (testID== -1){
			return null;
		}
		else{
			//Erstellen eines Mitglieds mit den übernommenen Werten (mit ID)
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
	
	public static Mitglied bearbeiten (Mitglied mitglied){
		
		//Aktualisieren des Mitglieds
		String table= "mitglieder";
		String updateString= "username="+mitglied.getUsername()+", pw-hash="
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
	
	public static boolean loeschen (Mitglied mitglied){
		
		//Mitglied anhand der ID löschen
		String table= "mitglieder";
		String where= "mitgliedid="+mitglied.getId();
		return Queries.deleteQuery(table, where);
	}
	
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
	
	public static Mitglied vorhanden (String name){
		
		//Suchen des Mitglieds anhand der ID
		String sql= "SELECT * FROM mitglieder WHERE username="+name;
		Mitglied testmitglied= new Mitglied();
		try {
			testmitglied=(Mitglied)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testmitglied;
	}
	
	public static ArrayList<Mitglied> getListe(){
		return null;
	}
	
	public static ArrayList<Mitglied> getListeVonAufgaben(int aufgID){
		return null;
	}
	
	public static ArrayList<Mitglied> getListeVonTeam(int teamID){
		return null;
	}
	
	public static boolean istMitgliedInTeam(int mitgliedID, int teamID){
		return false;
	}
	
	public static boolean pruefeLogin(String username, String pw){
		if(username.equals("admin") && pw.equals("123")){
			return true;
		}
		return false;
	}

	public static Mitglied get(int int1) {
		// TODO Auto-generated method stub
		return null;
	}
}
