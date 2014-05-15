package web_controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import administration.AufgabengruppenVerwaltung;
import administration.DateiVerwaltung;
import administration.MitgliederTeams;
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
		try {
			currentUser = Long.parseLong(request.getSession().getAttribute("currentUser").toString());
		} catch (NullPointerException e){
			request.setAttribute("error", e);
		}
		
		long id = -1; // TeamID
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}		
		
		String mode = request.getParameter("mode");
		
		RequestDispatcher view = request.getRequestDispatcher("/error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}

		// Team ansehen
		else if(mode.equals("view")){
			if(TeamVerwaltung.vorhanden(id)){
				if(MitgliederVerwaltung.istMitgliedInTeam(currentUser, id)){
					request.setAttribute("team", TeamVerwaltung.get(id));
					request.setAttribute("taskGroups", AufgabengruppenVerwaltung.getListeVonTeam(id));				
					request.setAttribute("files", DateiVerwaltung.getListeVonTeam(id));
					request.setAttribute("users", MitgliederVerwaltung.getListeVonTeam(id));
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/jsp/team/teamView.jsp");
				} else {
					request.setAttribute("error", "Sie sind kein Mitglied dieses Teams!");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Team nicht gefunden!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Team erstellen (Formular)
		else if(mode.equals("new")){
			// TODO Manager sollte automatisch Mitglied sein:
			//ArrayList<Mitglied> users = new ArrayList<Mitglied>();
			//users.add(MitgliederVerwaltung.get(currentUser));
			//request.setAttribute("users", users);
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
				request.setAttribute("usersRest", MitgliederVerwaltung.getListeVonTeamRest(id));
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/team/teamEdit.jsp");
			} else {
				request.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Team löschen (Formular)
		else if(mode.equals("remove")){
			entities.Team team = TeamVerwaltung.get(id);
			if(currentUser == team.getGruppenfuehrer().getId()){
				if(TeamVerwaltung.vorhanden(team.getId())){
					request.setAttribute("team", team);
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/jsp/team/teamRemove.jsp");
				} else {
					request.setAttribute("error", "Team nicht gefunden!");
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
		
		HttpSession session = request.getSession(true);
		
		// Fehler - kein Login
		if(!login){
			session.setAttribute("error", "Sie sind nicht eingeloggt!");
			response.sendRedirect("/error.jsp");
		}
	
		// Team erstellen (Aktion)
		else if(mode.equals("new")){
			String name = request.getParameter("name");
			
			if(name.length() > 0){
				entities.Team team = new entities.Team();
				team.setGruppenfuehrer(MitgliederVerwaltung.get(currentUser));
				team.setName(name);
				team.setBeschreibung(request.getParameter("description"));
				
				entities.Team teamNew = TeamVerwaltung.neu(team);
				
				if(teamNew != null){
					String[] userIDs = request.getParameterValues("users");
					for(String userID : userIDs){
						long userIDLong = Long.parseLong(userID);
						MitgliederTeams.beitreten(userIDLong, teamNew.getId(), "Mitglied");
					}
					session.setAttribute("alert", "Sie haben das Team erfolgreich erstellt!");
					session.setAttribute("alert_mode", "success");
					response.sendRedirect("/team?mode=view&id=" + teamNew.getId());
				} else {
					session.setAttribute("error", "Fehler beim Speichern des Teams!");
					response.sendRedirect("/error.jsp");
				}
			} else {
				session.setAttribute("alert", "Bitte geben Sie alle Daten an, die mit einem Sternchen (*) gekennzeichnet sind.");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
			}
		}
		
		// Team bearbeiten (Aktion)
		else if(mode.equals("edit")){			
			entities.Team team = TeamVerwaltung.get(id);
			
			if(currentUser == team.getGruppenfuehrer().getId()){
				team.setGruppenfuehrer(MitgliederVerwaltung.get(Long.parseLong(request.getParameter("manager"))));
				team.setName(request.getParameter("name"));
				team.setBeschreibung(request.getParameter("description"));
				entities.Team teamUpdated = TeamVerwaltung.bearbeiten(team);
				
				if(teamUpdated != null){
					session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
					response.sendRedirect("/team?mode=view&id=" + teamUpdated.getId());
				} else {
					session.setAttribute("error", "Team konnte nicht bearbeitet werden!");
					response.sendRedirect("/team?mode=view&id=" + id);
				}
			} else {
				session.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Team löschen (Aktion)
		else if(mode.equals("remove")){
			entities.Team team = TeamVerwaltung.get(id);
			
			if(currentUser == team.getGruppenfuehrer().getId()){
				if(TeamVerwaltung.loeschen(team.getId())){
					session.setAttribute("title", "Team gel&ouml;scht");
					session.setAttribute("message", "Sie haben das Team erfolgreich gel&ouml;scht!");
					response.sendRedirect("/success.jsp");
				} else {
					session.setAttribute("error", "Fehler beim L&ouml;schen!");
					response.sendRedirect("/error.jsp");
				}
			} else {
				session.setAttribute("error", "Nur Teammanager d&uuml;rfen das Team l&ouml;schen!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect("/error.jsp");
		}
	}

}
