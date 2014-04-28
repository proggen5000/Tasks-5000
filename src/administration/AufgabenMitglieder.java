package administration;

import java.sql.SQLException;

import entities.Aufgabe;
import entities.Mitglied;
import database.Queries;

public class AufgabenMitglieder {
	Mitglied mitglied;
	Aufgabe aufgabe;
	public static boolean zuweisen (Mitglied m, Aufgabe a){
		try{
			return Queries.insertQuery("aufgabe_mitglieder", "AufgabeID, MitgliedID", a.getId()+", "+m.getId())>0;
		}catch(SQLException e){
			return false;
		}
	}
	public static boolean entfernen (Mitglied m, Aufgabe a){
		try{
			return Queries.deleteQuery("aufgabe_mitglieder", "AufgabeID = "+a.getId()+" AND MitgliedID = "+m.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
	}

}
