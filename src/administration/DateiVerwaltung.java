package administration;

public class DateiVerwaltung {

	public boolean neu (String titel, String beschr, String version, int teamID,
			int mitgliedID){} //als Parameter noch Pfad oder Dateiobjekt übergeben?
	
	public boolean bearbeiten (int id, String titel, String beschr, String version,
			int teamID, int mitgliedID){}
	
	public boolean loeschen (int id){}
	
	public boolean vorhanden (int id){}
	
	public ArrayList getListe(){}
	
	public ArrayList getListeVonAufgabe(int aufgabenID){}
}
