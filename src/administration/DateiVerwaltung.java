package administration;

import java.util.ArrayList;

import entities.Datei;

public class DateiVerwaltung {

	public static boolean neu (String titel, String beschr, String version, long teamID,
			long mitgliedID){
		return false;
	} //als Parameter noch Pfad oder Dateiobjekt übergeben?
	
	public static boolean bearbeiten (long id, String titel, String beschr, String version,
			long teamID, long mitgliedID){
		return false;
	}
	
	public static boolean loeschen (long id){
		return false;
	}
	
	public static Datei vorhanden (long id){
		return null;
	}
	
	public static ArrayList<Datei> getListe(){
		return null;
	}
	
	public static ArrayList<Datei> getListeVonAufgabe(long aufgabenID){
		ArrayList<Datei> al = new ArrayList<Datei>();
		
		// TODO bitte ergänzen
		
		return al;
	}
}