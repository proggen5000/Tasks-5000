package administration;


import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Datei;

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
		String columns= "dateiid, titel, beschreibung, pfad, version, teamid, erstellerid";
		String values= "NULL, "+datei.getTitel()+", "+datei.getBeschreibung()+", "
				+datei.getPfad()+", "+datei.getVersion()+", "+datei.getTeam().getId()
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
			Datei testdatei= new Datei();
			String sql= "SELECT * FROM dateien WHERE dateiid="+testID;
			try {
				testdatei= (Datei)Queries.scalarQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return testdatei;
		}
	} //als Parameter noch Pfad oder Dateiobjekt �bergeben?
	
	public static Datei bearbeiten (Datei datei){
		
		//Aktualisieren der Dateibeschreibung
		String table= "dateien";
		String updateString= "titel="+datei.getTitel()+", beschreibung="
				+datei.getBeschreibung()+", pfad="+datei.getPfad()+", version="
				+datei.getVersion()+", teamid="+datei.getTeam().getId();
		String where= "dateiid="+datei.getId();
		Datei testdatei= new Datei();
		
		try {
			if (Queries.updateQuery(table, updateString, where)==true) {
				//erstellen einer Datei mit aktualisierten Daten
				String sql= "SELECT * FROM dateien WHERE dateiid="+datei.getId();
				try {
					testdatei= (Datei)Queries.scalarQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return testdatei;
	}
	
	public static boolean loeschen (Datei datei){
		
		//Datei anhand der ID l�schen
		String table= "dateien";
		String where= "dateiid="+datei.getId();
		try {
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static Datei vorhanden (long id){

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
	
	public ArrayList<Datei> getListe(){
		return null;
	}
	
	public static ArrayList<Datei> getListeVonAufgabe(long aufgabenID){
		ArrayList<Datei> al = new ArrayList<Datei>();
		
		// TODO bitte erg�nzen
		
		return al;
	}
	
	public static ArrayList<Datei> getListeVonTeam(long teamID){
		ArrayList<Datei> al = new ArrayList<Datei>();
		
		// TODO bitte erg�nzen
		
		return al;
	}
}
