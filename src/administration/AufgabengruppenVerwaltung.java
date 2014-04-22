package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Aufgabengruppe;


public class AufgabengruppenVerwaltung {

	public static Aufgabengruppe neu (Aufgabengruppe aufgabengruppe){
		// returns null if error else returns inserted obj with ID			
			 /*
			   AufgabenGruppeID	int(11) 
			   Name	varchar(45)
			   Beschreibung varchar(45)	
			   */
			String values = aufgabengruppe.getName() + ", " + aufgabengruppe.getBeschreibung();
			int id = Queries.insertQuery("AufgabenGruppen", "Name, Beschreibung", values);
			if (id == -1){
				return null;
			}
			else{
				Aufgabengruppe aufgabengruppe_neu = null;
				String sql = "SELECT * FROM AufgabenGruppen WHERE AufgabenGruppeID = " + id;
				try {
					aufgabengruppe_neu = (Aufgabengruppe)Queries.scalarQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return aufgabengruppe_neu;
			}
		}
	
	public static Aufgabengruppe bearbeiten (Aufgabengruppe aufgabengruppe){
		//Aktualisieren des Aufgabengruppe
		String table = "AufgabenGruppen";
		String updateString = "Name = " + aufgabengruppe.getName() + ", Beschreibung = " + aufgabengruppe.getBeschreibung();
		String where = "AufgabenGruppeID = " + aufgabengruppe.getId();
		
		Aufgabengruppe aufgabengruppe_neu = null;
		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				String sql= "SELECT * FROM AufgabenGruppen WHERE AufgabenGruppeID = " + aufgabengruppe.getId();
				aufgabengruppe_neu= (Aufgabengruppe) Queries.scalarQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return aufgabengruppe_neu;
	}
	
	public static boolean loeschen (Aufgabengruppe aufgabengruppe){
		String table = "AufgabenGruppen";
		String where = "AufgabenGruppeID = " + aufgabengruppe.getId();
		return Queries.deleteQuery(table, where);
	}
	
	public static boolean vorhanden (int id){
		return AufgabengruppenVerwaltung.get(id) != null;
	}	
	
	public static Aufgabengruppe get (int id){
		//Suchen der Aufgabe anhand der ID
		String sql = "SELECT * FROM AufgabenGruppen WHERE AufgabenGruppeID = " + id;
		Aufgabengruppe aufgabengruppe_neu = null;
		try {
			aufgabengruppe_neu = (Aufgabengruppe) Queries.scalarQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aufgabengruppe_neu;
	}
	
	public static Aufgabengruppe getDummy (int id){
		return new Aufgabengruppe(id, "Dummy-Gruppe", "Dummy-Gruppenbeschreibung");
	}
	
	public static Aufgabengruppe vorhanden (String name){
		//Suchen der Aufgabe anhand der ID
		String sql = "SELECT * FROM AufgabenGruppen WHERE Name = " + name;
		Aufgabengruppe aufgabengruppe_neu = null;
		try {
			aufgabengruppe_neu = (Aufgabengruppe) Queries.scalarQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aufgabengruppe_neu;
	}
	
	public ArrayList<Aufgabengruppe> getListe(){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgabe";
		ArrayList<Aufgabengruppe> al = new ArrayList<Aufgabengruppe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabengruppe a = new Aufgabengruppe(rs.getLong("AufgabenGruppeID"), rs.getString("Name"), 
						rs.getString("Beschreibung"));
				al.add(a);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public ArrayList<Aufgabengruppe> getListeVonTeam(int teamID){
		String sql = "SELECT * FROM `aufgaben` WHERE `TeamID` = 1 group by `AufgabenGruppeID`";
		ArrayList<Aufgabengruppe> al = new ArrayList<Aufgabengruppe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			
			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabengruppe a = AufgabengruppenVerwaltung.get(rs.getInt("AufgabenGruppeID"));
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
