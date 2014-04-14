package administration;

import java.util.ArrayList;

public class DateiVerwaltung {

	public boolean neu (String titel, String beschr, String version, int teamID,
			int mitgliedID){
		return false;
	} //als Parameter noch Pfad oder Dateiobjekt übergeben?
	
	public boolean bearbeiten (int id, String titel, String beschr, String version,
			int teamID, int mitgliedID){
		return false;
	}
	
	public boolean loeschen (int id){
		return false;
	}
	
	public boolean vorhanden (int id){
		return false;
	}
	
	public ArrayList getListe(){
		return null;
	}
	
	public ArrayList getListeVonAufgabe(int aufgabenID){
		return null;
	}
}
