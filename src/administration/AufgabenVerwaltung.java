package administration;

import java.util.ArrayList;
import java.util.Date;

import database.Queries;
import entities.Aufgabe;



public class AufgabenVerwaltung {
	public Aufgabe neu (Aufgabe aufgabe){
				
		
		String sql = "INSERT INTO Aufgabe" + this.toInsertSQL(aufgabe);// 
		String values = aufgabe.getTeam().getId() + ", " + aufgabe.getGruppe().getId() + ", " + aufgabe.getErsteller().getId() + ", " + aufgabe.getTitel() + ", " + aufgabe.getBeschreibung() + ", " + aufgabe.getStatus() + ", " + aufgabe.getDeadline();
		Queries.insertQuery("Aufgabe", "TeamID, AufgabenGruppeID, ErstellerID, Titel, Beschreibung, Status, Deadline", values);
		return null;
	}
	
	private String toInsertSQL(Aufgabe aufgabe) {
		// TODO Auto-generated method stub
		return null;
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
