package administration;

import java.sql.SQLException;

import entities.Aufgabe;
import entities.Mitglied;
import database.Queries;

public class AufgabenMitglieder {

	/**
	 * Weist einem Mitglied eine Aufgabe zu
	 * @param m Mitglied, welchem die Aufgabe zugewiesen wird
	 * @param a Aufgabe, die dem Mitglied zugewiesen werden soll
	 * @return Zuweisung erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean zuweisen (Mitglied m, Aufgabe a){
		try{
			return Queries.insertQuery("aufgaben_mitglieder", "aufgabeID, mitgliedID", a.getId()+", "+m.getId())>=0;
		}catch(SQLException e){
			return false;
		}
	}
	/**
	 * Entfernt die Zuweisung einer Aufgabe
	 * @param m Mitglied, bei dem die Zuweisung gelöscht werden soll
	 * @param a Aufgabe, bei dem die Zuweisung gelöscht werden soll
	 * @return Löschen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean entfernen (Mitglied m, Aufgabe a){
		try{
			return Queries.deleteQuery("aufgaben_mitglieder", "aufgabeID = "+a.getId()+" AND mitgliedID = "+m.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
	}
	/**
	 * Entfernt alle Zuweisungen einer Aufgabe
	 * @param a Aufgabe, bei der alle Zuweisungen gelöscht werden sollen
	 * @return Löschen erfolgreich (true) / nicht erfolgreich (false)
	 */
	public static boolean entfernenAlle(Aufgabe a){
		try{
			return Queries.deleteQuery("aufgaben_mitglieder", "aufgabeID="+a.getId());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

}
