package administration;

import java.sql.SQLException;

import entities.Aufgabe;
import entities.Mitglied;
import database.Queries;

public class AufgabenMitglieder {

	public static boolean zuweisen (Mitglied m, Aufgabe a){
		try{
			return Queries.insertQuery("aufgaben_mitglieder", "aufgabeID, mitgliedID", a.getId()+", "+m.getId())>=0;
		}catch(SQLException e){
			return false;
		}
	}
	
	public static boolean entfernen (Mitglied m, Aufgabe a){
		try{
			return Queries.deleteQuery("aufgaben_mitglieder", "aufgabeID = "+a.getId()+" AND mitgliedID = "+m.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
	}
	
	public static boolean entfernenAlle(Aufgabe a){
		try{
			return Queries.deleteQuery("aufgaben_mitglieder", "aufgabeID="+a.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

}
