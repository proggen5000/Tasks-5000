package administration;

public class MitgliederVerwaltung {

	public boolean neu (String username, String pw, String email, String vorname,
			String nachname){}
	
	public boolean bearbeiten (int id, String username, String pw, String email,
			String vorname, String nachname){}
	
	public boolean loeschen (int id){}
	
	public boolean vorhanden (int id){}
	
	public boolean vorhanden (String name){}
	
	public ArrayList getListe(){}
	
	public ArrayList getListeVonAufgaben(int aufgID){}
	
	public ArrayList getListeVonTeam(int teamID){}
	
	public boolean istMitgliedInTeam(int mitgliedID, int teamID){}
	
	public boolean pruefeLogin(String username, String pw){}
}
