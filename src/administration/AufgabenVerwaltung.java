package administration;

import java.util.ArrayList;
import java.util.Date;

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
	
	public ArrayList getListe(){
		return null;
	}
	
	public ArrayList getListeVonDatei(int dateiID){
		return null;
	}
	
	public ArrayList getListeVonGruppe(int grID){
		return null;
	}
	
}
