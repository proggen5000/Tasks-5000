package web_controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.AufgabengruppenVerwaltung;
import administration.DateiVerwaltung;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;

@WebServlet("/team")
public class TeamController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TeamController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		}
		
		long currentUser = -1;
		if(request.getSession().getAttribute("currentUser") != null){
			try {
				currentUser = Long.parseLong(request.getSession().getAttribute("currentUser").toString());
			} catch (NullPointerException e){
				request.setAttribute("error", e);
			}
		}
		
		long id = -1; // TeamID
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}

		// Team ansehen
		else if(mode.equals("view")){
			if(TeamVerwaltung.vorhanden(id)){
				request.setAttribute("team", TeamVerwaltung.get(id));
				request.setAttribute("groups", AufgabengruppenVerwaltung.getListeVonTeam(id));
				request.setAttribute("files", DateiVerwaltung.getListeVonTeam(id));
				request.setAttribute("users", MitgliederVerwaltung.getListeVonTeam(id));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/team/teamView.jsp");
			} else {
				request.setAttribute("error", "Team nicht gefunden!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Team erstellen (Formular)
		else if(mode.equals("new")){
			request.setAttribute("usersRest", MitgliederVerwaltung.getListe());
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/team/teamEdit.jsp");
		}
		
		// Team bearbeiten (Formular)
		else if(mode.equals("edit")){			
			entities.Team team = TeamVerwaltung.get(id);
			
			if(currentUser == team.getGruppenfuehrer().getId()){
				request.setAttribute("team", team);
				request.setAttribute("users", MitgliederVerwaltung.getListeVonTeam(id));
				request.setAttribute("usersRest", MitgliederVerwaltung.getListeVonAufgabeRest(id));
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/team/teamEdit.jsp");
			} else {
				request.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Team loeschen
		else if(mode.equals("remove")){
			entities.Team team = TeamVerwaltung.get(id);
			if(TeamVerwaltung.vorhanden(team.getId())){
				request.setAttribute("team", team);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/team/teamRemove.jsp");
			} else {
				request.setAttribute("error", "Team nicht gefunden!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("/error.jsp");
		}
		
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		}
		
		long currentUser = -1;
		if(request.getSession().getAttribute("currentUser") != null){
			try {
				currentUser = Long.parseLong(request.getSession().getAttribute("currentUser").toString());
			} catch (NullPointerException e){
				request.setAttribute("error", e);
			}
		}
		
		long id = -1; // Team-ID
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}
	
		// Team erstellen (Aktion)
		else if(mode.equals("new")){
			entities.Team team = new entities.Team();
			team.setGruppenfuehrer(MitgliederVerwaltung.get(currentUser));
			team.setGruendungsdatum(new Date().getTime()); // TODO ggf. unnoetig?
			team.setName(request.getParameter("name"));
			team.setBeschreibung(request.getParameter("description"));
			entities.Team teamNew = TeamVerwaltung.neu(team);
			
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/team?mode=view&id="+teamNew.getId());
		}
		
		// Team bearbeiten (Aktion)
		else if(mode.equals("edit")){			
			entities.Team team = TeamVerwaltung.get(id);
			
			if(currentUser == team.getGruppenfuehrer().getId()){
				team.setGruppenfuehrer(MitgliederVerwaltung.get(Long.parseLong(request.getParameter("manager"))));
				team.setName(request.getParameter("name"));
				team.setBeschreibung(request.getParameter("description"));
				entities.Team teamUpdated = TeamVerwaltung.bearbeiten(team);
				
				request.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/team?mode=view&id="+teamUpdated.getId());
			} else {
				request.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Team loeschen (Aktion)
		else if(mode.equals("remove")){
			entities.Team team = TeamVerwaltung.get(id);
			
			if(currentUser == team.getGruppenfuehrer().getId()){
				if(TeamVerwaltung.loeschen(team.getId())){
					request.setAttribute("title", "Team gel&ouml;scht");
					request.setAttribute("message", "Sie haben das Team erfolgreich gel&ouml;scht!");
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/success.jsp");
				} else {
					request.setAttribute("error", "Fehler beim L&ouml;schen!");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Nur Teammanager d&uuml;rfen das Team l&ouml;schen!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("/error.jsp");
		}
		
		view.forward(request, response);
	}

}
