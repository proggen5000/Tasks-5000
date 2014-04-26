package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Aufgabe;
import entities.Aufgabengruppe;
import entities.Mitglied;
import entities.Team;


public class AufgabengruppenVerwaltung {

	/**
	 * Ersellt eine neue AufgabenGruppe in der Datenbank
	 * @param aufgabengruppe die Aufgabengruppe die gespeichert werden soll
	 * @return Aufgabengruppe, so wie sie in der Datenbank gespeichert wurde
	 */
	public static Aufgabengruppe neu (Aufgabengruppe aufgabengruppe){		
		
		String values = aufgabengruppe.getName() + ", " + aufgabengruppe.getBeschreibung() + ", " + aufgabengruppe.getTeam().getId();
		long id;
		try {
			id = Queries.insertQuery("AufgabenGruppen", "Name, Beschreibung, Team", values);
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

	/**
	 * Die Daten werden auf die des übergebenen Objekts geupdated.
	 * @param aufgabengruppe die aktualisiert werden soll. 
	 * @return Aufgabengruppe so wie sie in der Datenbank steht
	 */
	public static Aufgabengruppe bearbeiten (Aufgabengruppe aufgabengruppe){
		//Aktualisieren des Aufgabengruppe
		String table = "AufgabenGruppen";
		String updateString = "Name = " + aufgabengruppe.getName() + ", Beschreibung = " + aufgabengruppe.getBeschreibung()  + ", Name = " + aufgabengruppe.getName();
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

	/**
	 * Löscht die übergebene Aufgabengruppe aus der Datenbank
	 * @param aufgabengruppe die gelöscht werden soll
	 * @return boolean ob gelöscht oder nicht
	 */
	public static boolean loeschen (Aufgabengruppe aufgabengruppe){
		// erst m�ssen alle untergeordneten Aufgaben gel�scht werden
		ArrayList<Aufgabe> a = AufgabenVerwaltung.getListeVonGruppe(aufgabengruppe.getId());
		int l = a.size();
		for(int i = 0; i < l; i++){
			AufgabenVerwaltung.loeschen(a.get(i));
		}
		
		String table = "AufgabenGruppen";
		String where = "AufgabenGruppeID = " + aufgabengruppe.getId();
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Überprüft, ob eine AufgabenGruppen-ID in der Datenbank vorhanden ist
	 * @param id die auf vorhandensein geprüft werden soll
	 * @return boolean ob sie vorhanden ist oder nicht
	 */
	public static boolean vorhanden (long id){
		return AufgabengruppenVerwaltung.get(id) != null;
	}	

	/**
	 * Gibt die AufgabenGruppen mit der angegebenen AufgabenGruppen-ID aus der Datenbank zurück
	 * @param id der Aufgabengruppe nach der gesucht werden soll 
	 * @return Aufgabengruppe, nach der gesucht wurde
	 */
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

	/**
	 * Dummy
	 * @param id der Aufgabengruppe nach der gesucht werden soll
	 * @return Aufgabengruppe, nach der gesucht wurde
	 */
	public static Aufgabengruppe getDummy (long id){
		return new Aufgabengruppe(id, "Dummy-Gruppe", "Dummy-Gruppenbeschreibung", new Team(1l, "toll", 0l, "Testgruppe", new Mitglied(1, "testi", "bla", "test@test.de", "tester", "Typ", 0)));
	}

	/**
	 * Überprüft, ob ein AufgabenGruppen-Name in der Datenbank vorhanden ist
	 * @param name der auf vorhandensein geprüft werden soll
	 * @return boolean ob er vorhanden ist oder nicht
	 */
	public static boolean vorhanden (String name){
		return AufgabengruppenVerwaltung.get(name) != null;
	}

	/**
	 * Gibt die AufgabenGruppen mit dem angegebenen AufgabenGruppen-Name aus der Datenbank zurück
	 * @param name der Aufgabengruppe nach der gesucht werden soll
	 * @return Aufgabengruppe, nach der gesucht wurde
	 */
	public static Aufgabengruppe get(String name){
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

	/**
	 * Gibt alle AufgabeGruppen die in der Datenbank existieren zurück
	 * @return Alle AufgabeGruppen die in der Datenbank existieren
	 */
	public static ArrayList<Aufgabengruppe> getListe(){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgabe";
		ArrayList<Aufgabengruppe> al = new ArrayList<Aufgabengruppe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabengruppe a = new Aufgabengruppe(rs.getLong("AufgabenGruppeID"), rs.getString("Name"), 
						rs.getString("Beschreibung"), TeamVerwaltung.get(rs.getLong("Team")));
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
	 * Gibt alle Aufgabengruppen eines Teams zurück
	 * @param teamID nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgabengruppe für die gesuchte ID
	 */
	public static ArrayList<Aufgabengruppe> getListeVonTeam(long teamID){
		String sql = "SELECT * FROM `aufgaben` WHERE `TeamID` = "+ teamID +" group by `AufgabenGruppeID`";
		ArrayList<Aufgabengruppe> al = new ArrayList<Aufgabengruppe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				Aufgabengruppe a = AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID"));
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
