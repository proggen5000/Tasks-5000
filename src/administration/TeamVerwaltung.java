package administration;

import java.util.ArrayList;

public class TeamVerwaltung {

	public boolean neu (String name, String slogan, int gruppenfuehrerID){
		return false;
	}
	
	public boolean bearbeiten (int id, String name, String slogan, int gruppenfuehrerID){
		return false;
	}
	
	public boolean loeschen (int id){
		return false;
	}
	
	public boolean vorhanden (int id){
		return false;
	}
	
	public boolean vorhanden (String name){
		return false;
	}
	
	public ArrayList getListe(){
		return null;
	}
	
	public ArrayList getListeVonMitglied(int mitgliedID){
		return null;
	}
	
	public boolean neuesTeammitglied(int teamID, int mitgliedID){
		return false;
	}
	
	public boolean loescheTeammitglied(int teamID, int mitgliedID){
		return false;
	}
}
