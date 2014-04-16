package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.Queries;
import entities.Aufgabe;
import entities.Mitglied;



public class AufgabenVerwaltung {
	public static Aufgabe neu (Aufgabe aufgabe){
	// returns null if error else returns inserted obj with ID			
		 
		String values = aufgabe.getTeam().getId() + ", " + aufgabe.getGruppe().getId() + ", " + aufgabe.getErsteller().getId() + ", " + aufgabe.getTitel() + ", " + aufgabe.getBeschreibung() + ", " + aufgabe.getStatus() + ", " + aufgabe.getDeadline();
		int id = Queries.insertQuery("Aufgabe", "TeamID, AufgabenGruppeID, ErstellerID, Titel, Beschreibung, Status, Deadline", values);
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
	
	public static Aufgabe bearbeiten (Aufgabe aufgabe){
		//Aktualisieren des Mitglieds
		String table= "Aufgabe";
		String updateString= "TeamID = " + aufgabe.getTeam().getId() + ", AufgabenGruppeID = " + aufgabe.getGruppe().getId()
				+ ", ErstellerID = " + aufgabe.getErsteller().getId() + ", Titel = " + aufgabe.getTitel()
				+ ", Beschreibung = " + aufgabe.getBeschreibung() + ", Status = " + aufgabe.getStatus()
				+ ", Deadline = " + aufgabe.getDeadline();
		String where = "AufgabeID = " + aufgabe.getId();
		Aufgabe aufgabe_neu = null;
		
		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				String sql= "SELECT * FROM Aufgabe WHERE AufgabeID=" + aufgabe.getId();
				aufgabe_neu= (Aufgabe) Queries.scalarQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return aufgabe_neu;
	}
	
	public static boolean loeschen (Aufgabe aufgabe){
		String table= "Aufgabe";
		String where = "AufgabeID = " + aufgabe.getId();
		return Queries.deleteQuery(table, where);
	}
	
	public static Aufgabe vorhanden (int id){
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
		ArrayList<Aufgabe> al = null;
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.vorhanden(rs.getInt("TeamID")),
						AufgabengruppenVerwaltung.vorhanden(rs.getInt("AufgabenGruppeID")),
						MitgliederVerwaltung.vorhanden(rs.getInt("ErstellerID")), rs.getString("Titel"),
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
	
	public static ArrayList<Aufgabe> getListeVonDatei(int dateiID){
		// returnd eine ArrayListe aller Aufgabe die zu einer bestimmten datei gehöhren
		String sql = "SELECT * FROM aufgaben JOIN aufgaben_dateien ON aufgaben.AufgabeID = aufgaben_dateien.Aufgaben_AufgabeID JOIN dateien ON dateien.DateiID = aufgaben_dateien.Dateien_DateiID WHERE DateiID = " + dateiID;
		ArrayList<Aufgabe> al = null;
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.vorhanden(rs.getInt("TeamID")),
						AufgabengruppenVerwaltung.vorhanden(rs.getInt("AufgabenGruppeID")),
						MitgliederVerwaltung.vorhanden(rs.getInt("ErstellerID")), rs.getString("Titel"),
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
	
	public static ArrayList<Aufgabe> getListeVonGruppe(int grID){
		// returnd eine ArrayListe aller Aufgabe
				String sql = "SELECT * FROM Aufgabe WHERE AufgabenGruppeID = " + grID;
				ArrayList<Aufgabe> al = null;
				try {
					ResultSet rs = Queries.rowQuery(sql);
					
					while(rs.next()){
						//add every result in resultset to ArrayList
						Aufgabe a = new Aufgabe(rs.getLong("AufgabeID"), TeamVerwaltung.vorhanden(rs.getInt("TeamID")),
								AufgabengruppenVerwaltung.vorhanden(rs.getInt("AufgabenGruppeID")),
								MitgliederVerwaltung.vorhanden(rs.getInt("ErstellerID")), rs.getString("Titel"),
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
	
}
