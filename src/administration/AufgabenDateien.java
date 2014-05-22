package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Aufgabe;
import entities.Datei;
import database.Queries;

public class AufgabenDateien{

	/**
	 * Weist einer Datei eine Aufgabe zu
	 * @param d Datei, welche die Aufgabe zugewiesen wird
	 * @param a Aufgabe, die der Datei zugewiesen werden soll
	 * @return Zuweisung erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean zuweisen (Datei d, Aufgabe a){
		try{
			return Queries.insertQuery("aufgaben_dateien", "aufgabeID, dateiID", a.getId()+", "+d.getId())>=0;
		}catch(SQLException e){
			return false;
		}
	}

	/**
	 * Entfernt die Zuweisung einer Aufgabe
	 * @param d Datei, bei dem die Zuweisung gelöscht werden soll
	 * @param a Aufgabe, bei dem die Zuweisung gelöscht werden soll
	 * @return Löschen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean entfernen (Datei d, Aufgabe a){
		try{
			return Queries.deleteQuery("aufgaben_dateien", "dateiID = " + d.getId()+" AND aufgabeID = "+a.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * Erstellt eine Liste von Aufgaben, die einer Datei zugewiesen sind
	 * @param dateiId ID einer Datei, für die die Liste zurückgegeben werden soll
	 * @return Aufgaben-Liste
	 */
	public static ArrayList<Aufgabe> getListVonDatei(long dateiId){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM aufgaben INNER JOIN aufgaben_dateien ON aufgaben.aufgabeID = aufgaben_dateien.aufgabeID WHERE dateiID = " + dateiId;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			while(rs.next()){
				//add every result in resultset to ArrayList
				al.add(AufgabenVerwaltung.createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	/**
	 * Entfernt alle Aufgabenzuordnungen der angegebenen Datei
	 * @param d Datei, zu dem die Zuweisungen gelöscht werden soll
	 * @return Löschen der Zuweisungen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean entfernenAlle(Datei d){
		try{
			return Queries.deleteQuery("aufgaben_dateien", "dateiID="+d.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
}
