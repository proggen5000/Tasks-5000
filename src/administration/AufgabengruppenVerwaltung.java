package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Aufgabengruppe;


public class AufgabengruppenVerwaltung {

	/**
	 * Ersellt eine neue AufgabenGruppe in der Datenbank
	 * @param aufgabengruppe die Aufgabengruppe die gespeichert werden soll
	 * @return Aufgabengruppe, so wie sie in der Datenbank gespeichert wurde
	 */
	public static Aufgabengruppe neu (Aufgabengruppe aufgabengruppe){
		// returns null if error else returns inserted obj with ID			
			 /*
			   AufgabenGruppeID	int(11) 
			   Name	varchar(45)
			   Beschreibung varchar(45)	
			   */
			String values = aufgabengruppe.getName() + ", " + aufgabengruppe.getBeschreibung();
			long id;
			try {
				id = Queries.insertQuery("AufgabenGruppen", "Name, Beschreibung", values);
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}
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
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean vorhanden (long id){
		return AufgabengruppenVerwaltung.get(id) != null;
	}	
	
	public static Aufgabengruppe get (long id){
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
	
	// voll unnoetig, oder? :P
	public static ArrayList<Aufgabengruppe> getListe(){
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
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public static ArrayList<Aufgabengruppe> getListeVonTeam(long teamID){
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
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
}
