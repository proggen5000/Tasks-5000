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
		String values= "NULL, '"+datei.getName()+"', '"+datei.getBeschreibung()+"', '"
				+datei.getPfad()+"', "+datei.getTeam().getId()
				+", "+datei.getErsteller().getId();
		int testID;
		
		try {
			testID = Queries.insertQuery(table, columns, values);
		} catch (SQLException e1) {
			e1.printStackTrace();
			testID= -1;
		}
		
		if (testID== -1){
			return null;
		}
		else{
			Datei testdatei= get(testID);
			return testdatei;
		}
	}
	
	/**
	 * aktualisiert Werte einer Datei in der DB
	 * @param datei mit zu aktualisierenden Werten
	 * @return testdatei aus der DB mit den aktualisierten Werten
	 */
	public static Datei bearbeiten (Datei datei){
		
		//Aktualisieren der Dateibeschreibung
		String table= "dateien";
		String updateString= "name='"+datei.getName()+"', beschreibung='"
				+datei.getBeschreibung()+"', pfad='"+datei.getPfad()
				+"', teamid="+datei.getTeam().getId();
		String where= "dateiid="+datei.getId();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				Datei testdatei= get(datei.getId());
				return testdatei;
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * löscht Datei anhand der ID
	 * löscht außerdem alle Verbindungen zu Aufgaben
	 * @param dateiid der zu löschenden Datei
	 * @return boolean
	 */
	public static boolean loeschen (long dateiid){
		
		//Datei anhand der ID löschen
		String table= "dateien";
		String where= "dateiid="+dateiid;
		String aufgabensql= "SELECT aufgabeid FROM aufgaben_dateien WHERE dateiid= "+dateiid;
		
		try {
			//löschen aller Verbindungen zu Aufgaben
			ResultSet rs= Queries.rowQuery(aufgabensql);
			if (rs!= null){
				while (rs.next()){
					AufgabenDateien.entfernen(get(dateiid), AufgabenVerwaltung.get(rs.getLong("aufgabeid")));
				}
			}
			
			//löschen der Datei
			return Queries.deleteQuery(table, where);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * liefert eine Datei aus der DB anhand einer Dateiid
	 * @param id der zurückzugebenden Datei
	 * @return testdatei mit Werten der Datei
	 */
	public static Datei get(long id){

		//Suchen der Datei anhand der ID
		String sql= "SELECT * FROM dateien WHERE dateiid="+id;
		
		try {
			ResultSet rs= Queries.rowQuery(sql);
			rs.next();
			Datei testdatei= createDateibyRow(rs);
			return testdatei;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * prüft ob eine Datei in der DB vorhanden ist
	 * @param id der zu suchenden Datei
	 * @return boolean
	 */
	public static boolean vorhanden (long id){
		return get(id) != null;
	}
	
	public static ArrayList<Datei> getListeVonAufgabe(long aufgabenID){
		
		String sql = "SELECT dateien.dateiid FROM dateien JOIN aufgaben_dateien "
					+"ON dateien.dateiid= aufgaben_dateien.dateiid "
					+"JOIN aufgaben ON aufgaben.aufgabeid = aufgaben_dateien.aufgabeid "
					+"WHERE aufgaben.aufgabeid= " + aufgabenID;
		ArrayList<Datei> al = new ArrayList<Datei>();
	
		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Datei d= get(rs.getLong("dateiid"));
				al.add(d);
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	
	/**
	 * liefert eine ArrayList mit allen Dateien eines Teams
	 * @param teamID des Teams
	 * @return al ArrayList mit Dateien
	 */
	public static ArrayList<Datei> getListeVonTeam(long teamID){

		String sql = "SELECT dateiid FROM dateien WHERE teamid= "+teamID;
		ArrayList<Datei> al = new ArrayList<Datei>();

		try {
			ResultSet rs = Queries.rowQuery(sql);	
			while(rs.next()){
				Datei d= get(rs.getLong("dateiid"));
				al.add(d);
			}
			return al;
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurueckgegeben werden
			e.printStackTrace();
			return null;
		}
	}
	
	private static Datei createDateibyRow(ResultSet rs){
		try {
			Team team= TeamVerwaltung.get(rs.getLong("teamid"));
			Mitglied ersteller= MitgliederVerwaltung.get(rs.getLong("erstellerid"));
			Datei d= new Datei(rs.getLong("dateiid"), rs.getString("name"),
					rs.getString("beschreibung"), rs.getString("pfad"), team,
					ersteller);
			return d;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
