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
			request.setAttribute("teams", TeamVerwaltung.getListeVonMitglied(currentUser));
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
					request.setAttribute("error", "Sie sind kein Mitglied des Teams " + TeamVerwaltung.get(teamId) + "!");
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
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/error.jsp");
		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}

		// Profil bearbeiten (Aktion)
		if(mode.equals("edit")){
			String password = request.getParameter("password");
			String username = request.getParameter("username");
			if(password.equals(request.getParameter("passwordRepeat"))){
				if(!MitgliederVerwaltung.vorhanden(username)){
					Mitglied user = MitgliederVerwaltung.get(currentUser);
					user.setUsername(username);
					if(password != null){
						user.setPw(password);
					}
					user.setVorname(request.getParameter("vorname"));
					user.setNachname(request.getParameter("nachname"));
					user.setEmail(request.getParameter("email"));

					Mitglied userUpdated = MitgliederVerwaltung.bearbeiten(user);
					request.setAttribute("user", userUpdated);
					request.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
					// request.setAttribute("alter_mode", "success");
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/jsp/user/userEdit.jsp");
				} else {
					request.setAttribute("error", "Dieser Benutzername ist schon vergeben! Bitte <a href=\"/user?mode=edit\">versuchen Sie es erneut</a> mit einem anderen Benutzernamen.");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Sie haben zwei verschiedene Passw&ouml;rter eingegeben! Bitte <a href=\"/user?mode=edit\">versuchen Sie es erneut</a>.");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Profil loeschen (Aktion)
		else if(mode.equals("remove")){
			// TODO Check, ob Benutzer noch Mitglied/Ersteller von Elementen ist?
			// Mitglied user = MitgliederVerwaltung.getMitgliedWithId(currentUser);
			MitgliederVerwaltung.loeschen(currentUser);
			HttpSession session = request.getSession(true);
			session.removeAttribute("login");
			session.removeAttribute("currentUser");
			
			request.setAttribute("title", "Profil gel&ouml;scht");
			request.setAttribute("message", "Sie haben Ihr Profil endg&uuml;ltig gel&ouml;scht!<br />Auf Wiedersehen. :'(");
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/success.jsp");
		}
		
		// Team verlassen (Aktion)
		else if(mode.equals("leaveTeam")){
			Mitglied user = MitgliederVerwaltung.get(currentUser);
			entities.Team team = TeamVerwaltung.get(teamId);
			
			if(MitgliederVerwaltung.istMitgliedInTeam(user.getId(), team.getId())){
				if(MitgliederTeams.austreten(user.getId(), team.getId())){
					request.setAttribute("user", user);
					request.setAttribute("team", TeamVerwaltung.get(teamId));
					
					request.setAttribute("title", "Team verlassen");
					request.setAttribute("message", "Sie haben das Team \"<b>" + team.getName() + "</b>\" verlassen.");
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/success.jsp");
				} else {
					request.setAttribute("error", "Sie konnten nicht aus dem Team entfernt werden! :(");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Dieses Team existiert nicht oder Sie sind kein Mitglied des Teams \"<b>" + team.getName() + "</b>\"!");
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
