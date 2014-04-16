package administration;

import java.util.ArrayList;

import entities.Aufgabengruppe;
public class AufgabengruppenVerwaltung {

	public boolean neu (String name){
		return false;
	}// für manu
	
	public boolean bearbeiten (int id, String name){
		return false;
	}
	
	public boolean loeschen (int id){
		return false;
	}
	
	public static Aufgabengruppe vorhanden (int id){
		return null;
	}
	
	public static Aufgabengruppe vorhanden (String name){
		return null;
	}
	
	public ArrayList<Aufgabengruppe> getListe(){
		return null;
	}
	
	public ArrayList<Aufgabengruppe> getListeVonTeam(int teamID){
		return null;
	}
}
