package administration;

import java.util.ArrayList;

public class MitgliederVerwaltung {

	public boolean neu (String username, String pw, String email, String vorname,
			String nachname){
		return false;
	}
	
	public boolean bearbeiten (int id, String username, String pw, String email,
			String vorname, String nachname){
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
	
	public ArrayList getListeVonAufgaben(int aufgID){
		return null;
	}
	
	public ArrayList getListeVonTeam(int teamID){
		return null;
	}
	
	public boolean istMitgliedInTeam(int mitgliedID, int teamID){
		return false;
	}
	
	public boolean pruefeLogin(String username, String pw){
		return false;
	}
}
