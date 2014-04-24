package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Aufgabe;
import entities.Aufgabengruppe;
import entities.Mitglied;
import entities.Team;



public class AufgabenVerwaltung {
	public static Aufgabe neu (Aufgabe aufgabe){
	// returns null if error else returns inserted obj with ID			
		 
		String values = aufgabe.getTeam().getId() + ", " + aufgabe.getGruppe().getId() + ", " + aufgabe.getErsteller().getId() + ", " + aufgabe.getTitel() + ", " + aufgabe.getBeschreibung() + ", " + aufgabe.getStatus() + ", " + aufgabe.getDeadline();
		long id = -1;
		try {
			id = Queries.insertQuery("Aufgabe", "TeamID, AufgabenGruppeID, ErstellerID, Titel, Beschreibung, Status, Deadline", values);
		} catch (SQLException e1) {
			// TODO Hier sollte irgend etwas passieren!
			e1.printStackTrace();
		}
		if (id == -1){
			return null; 
		}
		else{
			Aufgabe aufgabe_neu = null;
			String sql = "SELECT * FROM Aufgabe WHERE AufgabeID = " + id;
			try {
				aufgabe_neu = (Aufgabe)Queries.scalarQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return aufgabe_neu;
		}
	}
	
	public static Aufgabe neuDummy (Aufgabe aufgabe){
		return aufgabe;
	}
	
	public static Aufgabe bearbeiten (Aufgabe aufgabe){
		//Aktualisieren der Aufgabe
		String table = "Aufgabe";
		String updateString = "TeamID = " + aufgabe.getTeam().getId() + ", AufgabenGruppeID = " + aufgabe.getGruppe().getId()
				+ ", ErstellerID = " + aufgabe.getErsteller().getId() + ", Titel = " + aufgabe.getTitel()
				+ ", Beschreibung = " + aufgabe.getBeschreibung() + ", Status = " + aufgabe.getStatus()
				+ ", Deadline = " + aufgabe.getDeadline();
		String where = "AufgabeID = " + aufgabe.getId();
		Aufgabe aufgabe_neu = null;
		
		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				String sql = "SELECT * FROM Aufgabe WHERE AufgabeID = " + aufgabe.getId();
				aufgabe_neu = (Aufgabe) Queries.scalarQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return aufgabe_neu;
	}
	
	public static boolean loeschen (Aufgabe aufgabe){
		String table = "Aufgabe";
		String where = "AufgabeID = " + aufgabe.getId();
		return Queries.deleteQuery(table, where);
	}
	
	public static boolean vorhanden (long id){
		return AufgabenVerwaltung.get(id) != null;
	}	
	
	public static Aufgabe get (long id){
		//Suchen der Aufgabe anhand der ID
		String sql = "SELECT * FROM Aufgabe WHERE AufgabeID = "+id;
		Aufgabe aufgabe_neu = null;
		try {
			aufgabe_neu = (Aufgabe) Queries.scalarQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aufgabe_neu;
	}
	
	public static Aufgabe getDummy (long id){
		Mitglied ersteller = new Mitglied(1337, "superadmin", "admin@email.de", "pw456", "Super", "Admin", 123);
		Mitglied erstellerAufgabe = new Mitglied(2, "herrherrmann", "basti@email.de", "123", "Sebastian", "Herrmann", 123);
		Aufgabe dummy = new Aufgabe(id, new Team(13, "Dummy-Team", 1231231, "Dummy-Slogan", ersteller), new Aufgabengruppe(3, "Dummy-Gruppe", "Dummy-Gruppenbeschreibung"), erstellerAufgabe, "Dummy-Aufgabe", "Dummy-Beschreibung", 45, 1398100350, 1398500350);
		return dummy;
	}
	
	public static ArrayList<Aufgabe> getListe(){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgabe";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.get(rs.getInt("TeamID")),
						AufgabengruppenVerwaltung.get(rs.getInt("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getInt("ErstellerID")), rs.getString("Titel"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Titel"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public static ArrayList<Aufgabe> getListeVonDatei(long dateiID){
		// returnd eine ArrayListe aller Aufgabe die zu einer bestimmten datei gehöhren
		String sql = "SELECT * FROM aufgaben JOIN aufgaben_dateien ON aufgaben.AufgabeID = aufgaben_dateien.Aufgaben_AufgabeID JOIN dateien ON dateien.DateiID = aufgaben_dateien.Dateien_DateiID WHERE DateiID = " + dateiID;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.get(rs.getInt("TeamID")),
						AufgabengruppenVerwaltung.get(rs.getInt("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getInt("ErstellerID")), rs.getString("Titel"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Titel"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public static ArrayList<Aufgabe> getListeVonGruppe(long grID){
		// returnd eine ArrayListe aller Aufgabe
				String sql = "SELECT * FROM Aufgabe WHERE AufgabenGruppeID = " + grID;
				ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
				try {
					ResultSet rs = Queries.rowQuery(sql);
					
					while(rs.next()){
						//add every result in resultset to ArrayList
						Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.get(rs.getInt("TeamID")),
								AufgabengruppenVerwaltung.get(rs.getInt("AufgabenGruppeID")),
								MitgliederVerwaltung.get(rs.getInt("ErstellerID")), rs.getString("Titel"),
								rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Titel"));
						al.add(a);
					}
				} catch (SQLException e) {
					// Falls ein Fehler auftritt soll eine lehere Liste zurückgegeben werden
					e.printStackTrace();
					al = null;
				}
				return al;
	}
	
	/**
	 * Findet alle Aufgaben eines bestimmten Mitglieds (sowohl erstellt, als auch zugewiesen)
	 * @param mitgliedID ID des Mitglieds
	 * @return Liste mit Aufgaben des angegebenen Mitglieds
	 */
	public static ArrayList<Aufgabe> getListeVonMitglied(long mitgliedID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		
		// TODO bitte erg‰nzen
		
		return al;
	}
	
	public static ArrayList<Aufgabe> getListeVonMitgliedDummy(long mitgliedID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		Mitglied dummyMitglied = new Mitglied(13, "Dummy-Mitglied", "qwertz", "test@mail.de", "Dummy", "User", 23453467);
		Team dummyTeam = new Team(1337, "Dummy-Team", 214325, "Wir rulen.", dummyMitglied);
		Aufgabengruppe dummyGruppe = new Aufgabengruppe(13, "Dummy-Gruppe", "Aufgabenbeschreibung hier.");
		Aufgabe dummyAufgabe1 = new Aufgabe(1337, dummyTeam, dummyGruppe, dummyMitglied, "Dummy-Aufgabe 1", "Hier steht was.", 45, 98723423, 99235456);
		Aufgabe dummyAufgabe2 = new Aufgabe(1338, dummyTeam, dummyGruppe, dummyMitglied, "Dummy-Aufgabe II", "Hier steht auch was.", 98, 942443423, 943235456);
		Aufgabe dummyAufgabe3 = new Aufgabe(1339, dummyTeam, dummyGruppe, dummyMitglied, "Dummy-Aufgabe 5000", "Hier steht so richtig viel, sodass es gek¸rzt werden muss, denn es werden mehr als 50 Zeichen verwendet!", 12, 934253423, 983235456);
		al.add(dummyAufgabe1);
		al.add(dummyAufgabe2);
		al.add(dummyAufgabe3);
		return al;
	}
	
	/**
	 * Findet alle Aufgaben eines bestimmten Teams
	 * @param teamID ID des Teams
	 * @return Liste mit Aufgaben des angegebenen Teams
	 */
	public static ArrayList<Aufgabe> getListeVonTeam(long teamID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		
		// TODO bitte erg‰nzen
		
		return al;
	}
	
	/**
	 * Findet alle Aufgaben unter Angabe eines Mitglieds (sowohl erstellt, als auch zugewiesen) und eines Teams
	 * @param mitgliedID
	 * @param teamID
	 * @return Liste mit Aufgaben des Mitglieds und Teams
	 */
	public static ArrayList<Aufgabe> getListeVonMitgliedVonTeam(long mitgliedID, long teamID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		
		// TODO bitte erg‰nzen
		
		return al;
	}
	
}
