package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Datei;
import entities.Mitglied;
import entities.Team;

public class DateiVerwaltung {

	/**
	 * Schreibt eine Datei in die DB
	 * liefert eine Datei mit den gespeicherten Daten zurueck
	 * @param datei mit einzuschreibenden Daten
	 * @return testdatei mit gespeicherten Daten
	 */
	public static Datei neu (Datei datei){
		
		//Einfuegen der Werte (ohne ID)
		String table= "dateien";
		String columns= "dateiid, name, beschreibung, pfad, teamid, erstellerid";
		String values= "NULL, "+datei.getName()+", "+datei.getBeschreibung()+", "
				+datei.getPfad()+", "+datei.getTeam().getId()
				+", "+datei.getErsteller().getId();
		int testID;
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
			//Erstellen einer Datei mit den �bernommenen Werten (mit ID)
			String sql= "SELECT * FROM dateien WHERE dateiid="+testID;
			try {
				ResultSet rs= Queries.rowQuery(sql);
				Team team= TeamVerwaltung.get(rs.getLong("teamid"));
				Mitglied ersteller= MitgliederVerwaltung.get(rs.getLong("erstellerid"));
				Datei testdatei= new Datei(rs.getLong("dateiid"), rs.getString("name"),
						rs.getString("beschreibung"), rs.getString("pfad"), team,
						ersteller);
				return testdatei;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public static Datei bearbeiten (Datei datei){
		
		//Aktualisieren der Dateibeschreibung
		String table= "dateien";
		String updateString= "name="+datei.getName()+", beschreibung="
				+datei.getBeschreibung()+", pfad="+datei.getPfad()
				+", teamid="+datei.getTeam().getId();
		String where= "dateiid="+datei.getId();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				//erstellen einer Datei mit aktualisierten Daten
				String sql= "SELECT * FROM dateien WHERE dateiid="+datei.getId();
				try {
					ResultSet rs= Queries.rowQuery(sql);
					Team team= TeamVerwaltung.get(rs.getLong("teamid"));
					Mitglied ersteller= MitgliederVerwaltung.get(rs.getLong("erstellerid"));
					Datei testdatei= new Datei(rs.getLong("dateiid"), rs.getString("name"),
							rs.getString("beschreibung"), rs.getString("pfad"), team,
							ersteller);
					return testdatei;
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean loeschen (long dateiid){
		
		//Datei anhand der ID l�schen
		String table= "dateien";
		String where= "dateiid="+dateiid;
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static Datei get(long id){

		//Suchen der Datei anhand der ID
		String sql= "SELECT * FROM dateien WHERE dateiid="+id;
		Datei testdatei= new Datei();
		try {
			testdatei=(Datei)Queries.scalarQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testdatei;
	}
	
	public static boolean vorhanden (long id){
		return get(id) != null;
	}
	
	public static ArrayList<Datei> getListeVonAufgabe(long aufgabenID){
		
		String sql = "SELECT * FROM dateien JOIN aufgaben_dateien "
					+"ON dateien.dateiid= aufgaben_dateien.dateiid "
					+"JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_dateien.aufgabeid "
					+"WHERE aufgaben.aufgabeid= " + aufgabenID;
		ArrayList<Datei> al = new ArrayList<Datei>();
	
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Team team= TeamVerwaltung.get(rs.getLong("teamid"));
				Mitglied ersteller= MitgliederVerwaltung.get(rs.getLong("erstellerid"));
				Datei d= new Datei(rs.getLong("dateiid"), rs.getString("name"),
						rs.getString("beschreibung"), rs.getString("pfad"), team,
						ersteller);
				al.add(d);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	public static ArrayList<Datei> getListeVonTeam(long teamID){

		String sql = "SELECT * FROM dateien WHERE teamid= "+teamID;
	ArrayList<Datei> al = new ArrayList<Datei>();

	try {
		ResultSet rs = Queries.rowQuery(sql);	
		while(rs.next()){
			Team team= TeamVerwaltung.get(rs.getLong("teamid"));
			Mitglied ersteller= MitgliederVerwaltung.get(rs.getLong("erstellerid"));
			Datei d= new Datei(rs.getLong("dateiid"), rs.getString("name"),
					rs.getString("beschreibung"), rs.getString("pfad"), team,
					ersteller);
			al.add(d);
		}
	} catch (SQLException e) {
		// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
		e.printStackTrace();
		al = null;
	}
	return al;
	}
}
