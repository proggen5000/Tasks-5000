package web_controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import administration.MitgliederTeams;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;
import entities.Mitglied;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserController() {
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
		
		long id = -1;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		long teamId = -1;
		try {
			teamId = Long.parseLong(request.getParameter("teamId"));
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

		// Profil ansehen
		else if(mode.equals("view")){
			if(MitgliederVerwaltung.vorhanden(id)){
				request.setAttribute("user", MitgliederVerwaltung.get(id));
				request.setAttribute("teams", TeamVerwaltung.getListeVonMitglied(id));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/user/userView.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Benutzer-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Profil bearbeiten (Formular)
		else if(mode.equals("edit")){		
			request.setAttribute("user", MitgliederVerwaltung.get(currentUser));
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/user/userEdit.jsp");
		}
		
		// Profil loeschen
		else if(mode.equals("remove")){
			request.setAttribute("user", MitgliederVerwaltung.get(currentUser));
			request.setAttribute("teams", TeamVerwaltung.getListeVonMitglied(currentUser)); // TODO eigentlich nur Teams, wo Benutzer MANAGER ist
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/user/userRemove.jsp");
		}
		
		// Team verlassen
		else if(mode.equals("leaveTeam")){
			Mitglied user = MitgliederVerwaltung.get(currentUser);
			entities.Team team = TeamVerwaltung.get(teamId);
			
			if(MitgliederVerwaltung.istMitgliedInTeam(currentUser, teamId)){
				if(currentUser != team.getGruppenfuehrer().getId()){
					request.setAttribute("user", user);
					request.setAttribute("team", team);
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/jsp/user/userLeaveTeam.jsp");
				} else {
					request.setAttribute("error", "Sie k&ouml;nnen das Team nicht verlassen, weil Sie noch dessen <b>Teammanager</b> sind!<br />Bitte <a href=\"/team?mode=edit&id=" + teamId + "\">bearbeiten Sie das Team</a> und stellen Sie einen anderen Teammanager ein.");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				if(TeamVerwaltung.get(teamId) == null){
					request.setAttribute("error", "Dieses Team existiert nicht!");
				} else {
					request.setAttribute("error", "Sie sind kein Mitglied des Teams " + TeamVerwaltung.get(teamId).getName() + "!");
				}
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
		
		// keine (separate) Benutzer-ID noetig, da sich alle Aktionen auf currentUser beziehen
		
		long teamId = -1;
		try {
			teamId = Long.parseLong(request.getParameter("teamId"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");
		
		HttpSession session = request.getSession(true);
		
		// Fehler - kein Login
		if(!login){
			session.setAttribute("error", "Sie sind nicht eingeloggt!");
			response.sendRedirect("/error.jsp");
			return;
		}

		// Profil bearbeiten (Aktion)
		if(mode.equals("edit")){
			Mitglied user = MitgliederVerwaltung.get(currentUser);
			
			String currentUsername = user.getUsername();
			String username = request.getParameter("username");
			// neuer Username
			if(!username.equals(currentUsername)){
				if(!MitgliederVerwaltung.vorhanden(username)){
					user.setUsername(username);
				} else {
					// TODO Debug:
					System.out.println("Name " + username +  " schon vergeben!");
					session.setAttribute("alert", "Dieser Benutzername ist schon vergeben! Bitte versuchen Sie es erneut mit einem anderen Benutzernamen.");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect("/user?mode=edit");
					return;
				}
			}

			user.setVorname(request.getParameter("vorname"));
			user.setNachname(request.getParameter("nachname"));
			user.setEmail(request.getParameter("email"));

			String password = request.getParameter("password");
			
			// neues Passwort
			if(password != null && password.length() > 0){
				if(password.equals(request.getParameter("passwordRepeat"))){
					user.setPw(password);
				} else {
					session.setAttribute("alert", "Sie haben zwei verschiedene Passw&ouml;rter eingegeben! Bitte versuchen Sie es erneut.");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect("/user?mode=edit");
					return;
				}
			}
			
			Mitglied userUpdated = MitgliederVerwaltung.bearbeiten(user);
			
			if(userUpdated != null){
				session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
				response.sendRedirect("/user?mode=edit");
			} else {
				session.setAttribute("error", "Fehler beim Speichern der Profildaten!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Profil loeschen (Aktion)
		else if(mode.equals("remove")){
			// TODO Check, ob Benutzer noch Mitglied/Ersteller von Elementen ist?
			// Mitglied user = MitgliederVerwaltung.getMitgliedWithId(currentUser);
			if(MitgliederVerwaltung.loeschen(currentUser)){
				session.removeAttribute("login");
				session.removeAttribute("currentUser");
				
				session.setAttribute("title", "Profil gel&ouml;scht");
				session.setAttribute("message", "Sie haben Ihr Profil endg&uuml;ltig gel&ouml;scht!<br />Auf Wiedersehen. :'(");
				response.sendRedirect("/success.jsp");
				return;
			} else {
				session.setAttribute("error", "Ihr Profil konnte nicht gel&ouml;scht werden!");
				response.sendRedirect("/error.jsp");
				return;
			}
			
		}
		
		// Team verlassen (Aktion)
		else if(mode.equals("leaveTeam")){
			Mitglied user = MitgliederVerwaltung.get(currentUser);
			entities.Team team = TeamVerwaltung.get(teamId);
			
			if(MitgliederVerwaltung.istMitgliedInTeam(user.getId(), team.getId())){
				if(MitgliederTeams.austreten(user.getId(), team.getId())){
					session.setAttribute("title", "Team verlassen");
					session.setAttribute("message", "Sie haben das Team \"<b>" + team.getName() + "</b>\" verlassen.");
					response.sendRedirect("/success.jsp");
				} else {
					session.setAttribute("error", "Sie konnten nicht aus dem Team entfernt werden! :(");
					response.sendRedirect("/error.jsp");
				}
			} else {
				session.setAttribute("error", "Dieses Team existiert nicht oder Sie sind kein Mitglied des Teams \"<b>" + team.getName() + "</b>\"!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect("/error.jsp");
			return;
		}
	}
}
