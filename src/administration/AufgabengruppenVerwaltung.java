package administration;

import java.util.ArrayList;

import entities.Aufgabengruppe;
public class AufgabengruppenVerwaltung {

	public boolean neu (String name){
		return false;
	}// fŸr manu
	
	public boolean bearbeiten (int id, String name){
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
	
	public ArrayList<Aufgabengruppe> getListe(){
		return null;
	}
	
	public ArrayList<Aufgabengruppe> getListeVonTeam(int teamID){
		return null;
	}
}
