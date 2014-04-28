package administration;

import java.sql.SQLException;

import entities.Aufgabe;
import entities.Datei;
import entities.Mitglied;
import database.Queries;

public class AufgabenDateien{

	public static boolean zuweisen (Datei d, Aufgabe a){
		try{
			return Queries.insertQuery("aufgabe_dateien", "AufgabeID, DateiID", a.getId()+", "+d.getId())>0;
		}catch(SQLException e){
			return false;
		}
	}
	
	public static boolean entfernen (Datei d, Aufgabe a){
		try{
			return Queries.deleteQuery("aufgabe_dateien", "DateiID = " + d.getId()+" AND AufgabeID = "+a.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
	}

}
