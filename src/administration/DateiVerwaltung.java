package administration;

import java.util.ArrayList;

import entities.Datei;

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
	
	public static Datei vorhanden (int id){
		return null;
	}
	
	public ArrayList<Datei> getListe(){
		return null;
	}
	
	public ArrayList<Datei> getListeVonAufgabe(int aufgabenID){
		return null;
	}
}
