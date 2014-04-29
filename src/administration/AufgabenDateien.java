package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Aufgabe;
import entities.Datei;
import entities.Mitglied;
import database.Queries;

public class AufgabenDateien{

	public static boolean zuweisen (Datei d, Aufgabe a){
		try{
			return Queries.insertQuery("aufgaben_dateien", "AufgabeID, DateiID", a.getId()+", "+d.getId())>=0;
		}catch(SQLException e){
			return false;
		}
	}

	public static boolean entfernen (Datei d, Aufgabe a){
		try{
			return Queries.deleteQuery("aufgaben_dateien", "DateiID = " + d.getId()+" AND AufgabeID = "+a.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
	}
	public static ArrayList<Aufgabe> getListAufgabenVonDatei(long dateiId){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM aufgaben_dateien WHERE DateiID = " + dateiId + " GROUP BY DateiID";
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
}
