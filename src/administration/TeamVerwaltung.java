package administration;

public class TeamVerwaltung {

	public boolean neu (String name, String slogan, int gruppenfuehrerID){}
	
	public boolean bearbeiten (int id, String name, String slogan, int gruppenfuehrerID){}
	
	public boolean loeschen (int id){}
	
	public boolean vorhanden (int id){}
	
	public boolean vorhanden (String name){}
	
	public ArrayList getListe(){}
	
	public ArrayList getListeVonMitglied(int mitgliedID){}
	
	public boolean neuesTeammitglied(int teamID, int mitgliedID){}
	
	public boolean loescheTeammitglied(int teamID, int mitgliedID){}
}
