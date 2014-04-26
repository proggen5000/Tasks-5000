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
	/**
	 * Ersellt eine neue Aufgaben in der Datenbank
	 * @param aufgabe die gespeichert werden soll
	 * @return Aufgabe, so wie sie in der Datenbank gespeichert wurde
	 */
	public static Aufgabe neu (Aufgabe aufgabe){
		// returns null if error else returns inserted obj with ID			

		String values = aufgabe.getGruppe().getId() + ", " 
				+ aufgabe.getErsteller().getId() + ", "
				+ aufgabe.getName() + ", " 
				+ aufgabe.getBeschreibung() + ", " 
				+ aufgabe.getStatus() + ", "
				+ aufgabe.getErstellungsdatum() + ", "
				+ aufgabe.getDeadline();
		long id = -1;
		try {
			id = Queries.insertQuery("Aufgabe", "AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline", values);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (id == -1){
			return null; 
		}
		else{
			Aufgabe aufgabe_neu = null; // TODO
			String sql = "SELECT * FROM Aufgabe WHERE AufgabeID = " + id;
			try {
				aufgabe_neu = (Aufgabe)Queries.scalarQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return aufgabe_neu;
		}
	}

	/**
	 * Dummy
	 * @param aufgabe die zurückgegeben wird
	 * @return Aufgabe
	 */
	public static Aufgabe neuDummy (Aufgabe aufgabe){
		return aufgabe;
	}

	/**
	 * Die Daten werden auf die des übergebenen Objekts geupdated.
	 * @param aufgabe die aktualisiert werden soll. 
	 * @return Aufgabe so wie sie in der Datenbank steht
	 */
	public static Aufgabe bearbeiten (Aufgabe aufgabe){
		//Aktualisieren der Aufgabe
		String table = "Aufgabe";
		// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline
		String updateString = "AufgabenGruppeID = " + aufgabe.getGruppe().getId()
				+ ", ErstellerID = " + aufgabe.getErsteller().getId() + ", Name = " + aufgabe.getName()
				+ ", Beschreibung = " + aufgabe.getBeschreibung() + ", Status = " + aufgabe.getStatus()
				+ ", Erstellungsdatum = " + aufgabe.getDeadline() + ", Deadline = " + aufgabe.getDeadline();
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

	/**
	 * Löscht die übergebene Aufgabe aus der Datenbank
	 * @param aufgabe die gelöscht werden soll
	 * @return boolean ob gelöscht oder nicht
	 */
	public static boolean loeschen (Aufgabe aufgabe){
		String table = "Aufgabe";
		String where = "AufgabeID = " + aufgabe.getId();
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Überprüft, ob eine Aufgabe-ID in der Datenbank vorhanden ist
	 * @param id die auf vorhandensein geprüft werden soll
	 * @return boolean ob sie vorhanden ist oder nicht
	 */
	public static boolean vorhanden (long id){
		return AufgabenVerwaltung.get(id) != null;
	}	

	/**
	 * Gibt die Aufgabe mit der angegebenen Aufgabe-ID aus der Datenbank zurück
	 * @param id der Aufgabe nach der gesucht werden soll 
	 * @return Aufgabe, nach der gesucht wurde
	 */
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
	
	public static ArrayList<Aufgabe> getListe(){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgabe";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"),
						AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Gibt alle Aufgaben einer Datei zurück
	 * @param dateiID nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgaben für die gesuchte ID
	 */
	public static ArrayList<Aufgabe> getListeVonDatei(long dateiID){
		// returnd eine ArrayListe aller Aufgabe die zu einer bestimmten datei geh�hren
		String sql = "SELECT * FROM aufgaben JOIN aufgaben_dateien ON aufgaben.AufgabeID = aufgaben_dateien.Aufgaben_AufgabeID JOIN dateien ON dateien.DateiID = aufgaben_dateien.Dateien_DateiID WHERE DateiID = " + dateiID;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"),
						AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Gibt alle Aufgaben einer Gruppe zurück
	 * @param gruppenID nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgaben für die gesuchte ID
	 */
	public static ArrayList<Aufgabe> getListeVonGruppe(long gruppenID){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgabe WHERE AufgabenGruppeID = " + gruppenID;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"),
						AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
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
		String sql = "SELECT * FROM  `aufgaben_mitglieder` INNER JOIN  `aufgaben` USING ( AufgabeID ) WHERE MitgliedID = "+ mitgliedID +" OR ErstellerID = "+ mitgliedID +" GROUP BY AufgabeID";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"),
						AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/** Dummy
	 * @param mitgliedID Dummy
	 * @return DUmmy
	 */
	public static ArrayList<Aufgabe> getListeVonMitgliedDummy(long mitgliedID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		Mitglied dummyMitglied = new Mitglied(13, "Dummy-Mitglied", "qwertz", "test@mail.de", "Dummy", "User", 23453467);
		Team dummyTeam = new Team(1337, "Dummy-Team", 214325, "Wir rulen.", dummyMitglied);
		Aufgabengruppe dummyGruppe = new Aufgabengruppe(13, "Dummy-Gruppe", "Aufgabenbeschreibung hier.", dummyTeam);
		Aufgabe dummyAufgabe1 = new Aufgabe(1337, dummyGruppe, dummyMitglied, "Dummy-Aufgabe 1", "Hier steht was.", 45, 98723423, 99235456);
		Aufgabe dummyAufgabe2 = new Aufgabe(1338, dummyGruppe, dummyMitglied, "Dummy-Aufgabe II", "Hier steht auch was.", 98, 942443423, 943235456);
		Aufgabe dummyAufgabe3 = new Aufgabe(1339, dummyGruppe, dummyMitglied, "Dummy-Aufgabe 5000", "Hier steht so richtig viel, sodass es gek¸rzt werden muss, denn es werden mehr als 50 Zeichen verwendet!", 12, 934253423, 983235456);
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
		String sql = "SELECT * FROM aufgaben INNER JOIN aufgabengruppen USING(AufgabenGruppeID) WHERE TEAM = " + teamID + " GROUP BY AufgabeID";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"),
						AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Findet alle Aufgaben unter Angabe eines Mitglieds (sowohl erstellt, als auch zugewiesen) und eines Teams
	 * @param mitgliedID
	 * @param teamID
	 * @return Liste mit Aufgaben des Mitglieds und Teams
	 */
	public static ArrayList<Aufgabe> getListeVonMitgliedVonTeam(long mitgliedID, long teamID){
		// returnd eine ArrayListe aller Aufgabe
				String sql = "SELECT * FROM  `aufgaben_mitglieder` INNER JOIN  `aufgaben` USING ( AufgabeID ) INNER JOIN aufgabengruppen USING(AufgabenGruppeID) WHERE (MitgliedID = " + mitgliedID + " OR ErstellerID = " + mitgliedID + ") AND TEAM = " + teamID + " GROUP BY AufgabeID";
				ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
				try {
					ResultSet rs = Queries.rowQuery(sql);

					while(rs.next()){
						//add every result in resultset to ArrayList
						Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"),
								AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
								MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
								rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
						al.add(a);
					}
				} catch (SQLException e) {
					// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
					e.printStackTrace();
					al = null;
				}
				return al;
	}

}
