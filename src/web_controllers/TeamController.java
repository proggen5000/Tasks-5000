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

import persistence.UploadManager;
import persistence.UsersTeams;
import persistence.UserManager;
import persistence.TaskGroupManager;
import persistence.TeamManager;
import entities.User;

@WebServlet("/team")
public class TeamController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final short descriptionLimit = 5000;
       
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
		request.setAttribute("teams_menu", TeamManager.getListOfUser(currentUser)); // TODO Workaround
		
		long id = -1; // TeamID
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}		
		
		String mode = request.getParameter("mode");
		
		RequestDispatcher view = request.getRequestDispatcher(request.getContextPath()+"/error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}

		// Team ansehen
		else if(mode.equals("view")){
			if(TeamManager.exists(id)){
				if(UserManager.isMemberInTeam(currentUser, id)){
					request.setAttribute("team", TeamManager.get(id));
					request.setAttribute("taskGroups", TaskGroupManager.getList(id));				
					request.setAttribute("files", UploadManager.getListOfTeam(id));
					request.setAttribute("users", UserManager.getListOfTeam(id));
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
			ArrayList<User> users = new ArrayList<User>();
			users.add(UserManager.get(currentUser));
			request.setAttribute("users", users);
			request.setAttribute("usersRest", UserManager.getList());
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/team/teamEdit.jsp");
		}
		
		// Team bearbeiten (Formular)
		else if(mode.equals("edit")){			
			entities.Team team = TeamManager.get(id);
			
			if(currentUser == team.getManager().getId()){
				request.setAttribute("team", team);
				request.setAttribute("users", UserManager.getListOfTeam(id));
				request.setAttribute("usersRest", UserManager.getListOfTeamRest(id));
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
			entities.Team team = TeamManager.get(id);
			if(currentUser == team.getManager().getId()){
				if(TeamManager.exists(team.getId())){
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
			response.sendRedirect(request.getContextPath()+"/error.jsp");
		}
	
		// Team erstellen (Aktion)
		else if(mode.equals("new")){
			entities.Team team = new entities.Team();
			
			String name = request.getParameter("name");
			if(name.length() > 0){
				if(!TeamManager.exists(name)){
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
			
			team.setManager(UserManager.get(currentUser));
			
			String description = request.getParameter("description");
			if(description.length() <= descriptionLimit){
				team.setDescription(description);
			} else {
				session.setAttribute("alert", "Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: " + descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			String[] userIDs = request.getParameterValues("users");
			if(userIDs == null || userIDs.length == 0){
				session.setAttribute("alert", "Sie m&uuml;ssen Mitglieder zu Ihrem Team hinzuf&uuml;gen!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			entities.Team teamNew = TeamManager.add(team);
			
			if(teamNew != null){
				for(String userID : userIDs){
					long userIDLong = Long.parseLong(userID);
					if(userIDLong != currentUser){
						UsersTeams.link(userIDLong, teamNew.getId(), "Mitglied");
					}
				}
				UsersTeams.link(currentUser, teamNew.getId(), "Manager");
				
				session.setAttribute("alert", "Sie haben das Team erfolgreich erstellt!");
				response.sendRedirect(request.getContextPath()+"/team?mode=view&id=" + teamNew.getId());
				return;
			} else {
				session.setAttribute("error", "Fehler beim Speichern des Teams!");
				response.sendRedirect(request.getContextPath()+"/error.jsp");
				return;
			}		
		}
		
		// Team bearbeiten (Aktion)
		else if(mode.equals("edit")){			
			entities.Team team = TeamManager.get(id);
			
			if(currentUser == team.getManager().getId()){				
				
				// Name (falls neu)
				String currentName = team.getName();
				String newName = request.getParameter("name");
				if(!newName.equals(currentName)){
					if(newName.length() > 0){
						if(!TeamManager.exists(newName)){
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
				
				team.setManager(UserManager.get(Long.parseLong(request.getParameter("manager"))));
				
				team.setDescription(request.getParameter("description"));
				
				String[] userIDs = request.getParameterValues("users");
				if(userIDs == null || userIDs.length == 0){
					session.setAttribute("alert", "Sie m&uuml;ssen Mitglieder zu Ihrem Team hinzuf&uuml;gen!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
				
				entities.Team teamUpdated = TeamManager.bearbeiten(team);
				
				if(teamUpdated != null){
					UsersTeams.unlinkAll(teamUpdated);
					for(String userID : userIDs){
						long userIDLong = Long.parseLong(userID);
						if(userIDLong != currentUser){
							UsersTeams.link(userIDLong, teamUpdated.getId(), "Mitglied");
						}
					}
					UsersTeams.link(currentUser, teamUpdated.getId(), "Manager");
					
					session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
					response.sendRedirect(request.getContextPath()+"/team?mode=view&id=" + teamUpdated.getId());
				} else {
					session.setAttribute("alert", "Team konnte nicht bearbeitet werden!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
				}
			} else {
				session.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				response.sendRedirect(request.getContextPath()+"/error.jsp");
			}
		}
		
		// Team löschen (Aktion)
		else if(mode.equals("remove")){
			entities.Team team = TeamManager.get(id);
			String name = team.getName();
			
			if(currentUser == team.getManager().getId()){
				if(TeamManager.loeschen(team.getId())){
					session.setAttribute("title", "Team gel&ouml;scht");
					session.setAttribute("message", "Sie haben das Team <b>" + name + "</b> erfolgreich gel&ouml;scht!");
					response.sendRedirect(request.getContextPath()+"/success.jsp");
				} else {
					session.setAttribute("error", "Fehler beim L&ouml;schen!");
					response.sendRedirect(request.getContextPath()+"/error.jsp");
				}
			} else {
				session.setAttribute("error", "Nur Teammanager d&uuml;rfen das Team l&ouml;schen!");
				response.sendRedirect(request.getContextPath()+"/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect(request.getContextPath()+"/error.jsp");
		}
	}

}
