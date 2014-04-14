package administration;

public class AufgabenVerwaltung {

	public boolean neu (int teamID, int aufgGrID, int erstellerID, String titel,
			String beschr, int status, Date deadline){}
	
	public boolean bearbeiten (int id, int aufgGrID, int erstellerID, String titel,
			String beschr, int status, Date deadline){}
	
	public boolean loeschen (int id){}
	
	public boolean vorhanden (int id){}
	
	public ArrayList getListe(){}
	
	public ArrayList getListeVonDatei(int dateiID){}
	
	public ArrayList getListeVonGruppe(int grID){}
	
}
