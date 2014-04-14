package administration;

import java.util.ArrayList;
import java.util.Date;

import entities.Aufgabe;



public class AufgabenVerwaltung {

	public boolean neu (int teamID, int aufgGrID, int erstellerID, String titel,
			String beschr, int status, Date deadline){
		return false;
	}
	
	public boolean bearbeiten (int id, int aufgGrID, int erstellerID, String titel,
			String beschr, int status, Date deadline){
		return false;
	}
	
	public boolean loeschen (int id){
		return false;
	}
	
	public boolean vorhanden (int id){
		return false;
	}
	
	public ArrayList<Aufgabe> getListe(){
		return null;
	}
	
	public ArrayList<Aufgabe> getListeVonDatei(int dateiID){
		return null;
	}
	
	public ArrayList<Aufgabe> getListeVonGruppe(int grID){
		return null;
	}
	
}
