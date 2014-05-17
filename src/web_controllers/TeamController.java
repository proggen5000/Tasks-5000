package web_controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Mitglied;
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
			ArrayList<Mitglied> users = new ArrayList<Mitglied>();
			users.add(MitgliederVerwaltung.get(currentUser));
			request.setAttribute("users", users);
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
			entities.Team team = new entities.Team();
			
			String name = request.getParameter("name");
			if(name.length() > 0){
				if(!TeamVerwaltung.vorhanden(name)){
					team.setName(name);
				} else {
					session.setAttribute("alert", "Dieser Teamname ist bereits vergeben! Bitte verwenden Sie einen anderen.");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			} else {
				session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Teamnamen an.");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			team.setGruppenfuehrer(MitgliederVerwaltung.get(currentUser));
			team.setBeschreibung(request.getParameter("description"));
			
			String[] userIDs = request.getParameterValues("users");
			if(userIDs == null || userIDs.length == 0){
				session.setAttribute("alert", "Sie m&uuml;ssen Mitglieder zu Ihrem Team hinzuf&uuml;gen!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			entities.Team teamNew = TeamVerwaltung.neu(team);
			
			if(teamNew != null){
				for(String userID : userIDs){
					long userIDLong = Long.parseLong(userID);
					if(userIDLong != currentUser){
						MitgliederTeams.beitreten(userIDLong, teamNew.getId(), "Mitglied");
					}
				}
				MitgliederTeams.beitreten(currentUser, teamNew.getId(), "Manager");
				
				session.setAttribute("alert", "Sie haben das Team erfolgreich erstellt!");
				response.sendRedirect("/team?mode=view&id=" + teamNew.getId());
				return;
			} else {
				session.setAttribute("error", "Fehler beim Speichern des Teams!");
				response.sendRedirect("/error.jsp");
				return;
			}		
		}
		
		// Team bearbeiten (Aktion)
		else if(mode.equals("edit")){			
			entities.Team team = TeamVerwaltung.get(id);
			
			if(currentUser == team.getGruppenfuehrer().getId()){				
				
				// Name (falls neu)
				String currentName = team.getName();
				String newName = request.getParameter("name");
				if(!newName.equals(currentName)){
					if(newName.length() > 0){
						if(!TeamVerwaltung.vorhanden(newName)){
							team.setName(newName);
						} else {
							session.setAttribute("alert", "Dieser Teamname ist bereits vergeben! Bitte verwenden Sie einen anderen.");
							session.setAttribute("alert_mode", "danger");
							response.sendRedirect(request.getHeader("Referer"));
							return;
						}
					} else {
						session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Teamnamen an.");
						session.setAttribute("alert_mode", "danger");
						response.sendRedirect(request.getHeader("Referer"));
						return;
					}
				}
				
				team.setGruppenfuehrer(MitgliederVerwaltung.get(Long.parseLong(request.getParameter("manager"))));
				
				team.setBeschreibung(request.getParameter("description"));
				
				String[] userIDs = request.getParameterValues("users");
				if(userIDs == null || userIDs.length == 0){
					session.setAttribute("alert", "Sie m&uuml;ssen Mitglieder zu Ihrem Team hinzuf&uuml;gen!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
				
				entities.Team teamUpdated = TeamVerwaltung.bearbeiten(team);
				
				if(teamUpdated != null){
					for(String userID : userIDs){
						long userIDLong = Long.parseLong(userID);
						if(userIDLong != currentUser){
							MitgliederTeams.beitreten(userIDLong, teamUpdated.getId(), "Mitglied");
						}
					}
					MitgliederTeams.beitreten(currentUser, teamUpdated.getId(), "Manager");
					
					session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
					response.sendRedirect("/team?mode=view&id=" + teamUpdated.getId());
				} else {
					session.setAttribute("alert", "Team konnte nicht bearbeitet werden!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
				}
			} else {
				session.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Team löschen (Aktion)
		else if(mode.equals("remove")){
			entities.Team team = TeamVerwaltung.get(id);
			String name = team.getName();
			
			if(currentUser == team.getGruppenfuehrer().getId()){
				if(TeamVerwaltung.loeschen(team.getId())){
					session.setAttribute("title", "Team gel&ouml;scht");
					session.setAttribute("message", "Sie haben das Team <b>" + name + "</b> erfolgreich gel&ouml;scht!");
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
