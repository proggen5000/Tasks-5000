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
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Date"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public static ArrayList<Aufgabe> getListeVonDatei(long dateiID){
		// returnd eine ArrayListe aller Aufgabe die zu einer bestimmten datei geh�hren
		String sql = "SELECT * FROM aufgaben JOIN aufgaben_dateien ON aufgaben.AufgabeID = aufgaben_dateien.Aufgaben_AufgabeID JOIN dateien ON dateien.DateiID = aufgaben_dateien.Dateien_DateiID WHERE DateiID = " + dateiID;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.get(rs.getInt("TeamID")),
						AufgabengruppenVerwaltung.get(rs.getInt("AufgabenGruppeID")),
						MitgliederVerwaltung.get(rs.getInt("ErstellerID")), rs.getString("Titel"),
						rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Date"), rs.getLong("Deadline"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
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
								rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Date"), rs.getLong("Deadline"));
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
	 * Findet alle Aufgaben eines bestimmten Mitglieds (sowohl erstellt, als auch zugewiesen)
	 * @param mitgliedID ID des Mitglieds
	 * @return Liste mit Aufgaben des angegebenen Mitglieds
	 */
	public static ArrayList<Aufgabe> getListeVonMitglied(long mitgliedID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		
		// TODO bitte erg�nzen
		
		return al;
	}

	
	/**
	 * Findet alle Aufgaben eines bestimmten Teams
	 * @param teamID ID des Teams
	 * @return Liste mit Aufgaben des angegebenen Teams
	 */
	public static ArrayList<Aufgabe> getListeVonTeam(long teamID){
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		
		// TODO bitte erg�nzen
		
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
		
		// TODO bitte erg�nzen
		
		return al;
	}
	
}
