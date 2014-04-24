package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;
import entities.Mitglied;

@WebServlet("/user")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public User() {
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
			id = Long.parseLong(request.getParameter("teamId"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("error.jsp");
		}

		// Mitgliedsprofil ansehen
		else if(mode.equals("view")){
			if(id != -1){
				Mitglied user = MitgliederVerwaltung.getDummy(id); // TODO
				
				request.setAttribute("user", user);
				request.setAttribute("teams", TeamVerwaltung.getListeVonMitglied(user.getId()));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/user/userView.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Benutzer-ID!");
				view = request.getRequestDispatcher("error.jsp");
			}
		}
		
		// Profil bearbeiten (Formular)
		else if(mode.equals("edit")){
			Mitglied user = MitgliederVerwaltung.getDummy(currentUser); // TODO
			
			request.setAttribute("user", user);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("jsp/user/userEdit.jsp");
		}
		
		// Profil löschen
		else if(mode.equals("remove")){
			Mitglied user = MitgliederVerwaltung.getDummy(currentUser); // TODO
			
			request.setAttribute("user", user);			
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("jsp/user/userRemove.jsp");
		}
		
		// Team verlassen
		else if(mode.equals("leaveTeam")){
			if(MitgliederVerwaltung.istMitgliedInTeam(currentUser, teamId)){
				Mitglied user = MitgliederVerwaltung.getDummy(currentUser); // TODO
				request.setAttribute("user", user);
				request.setAttribute("team", TeamVerwaltung.get(teamId));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/user/userLeaveTeam.jsp");
			} else {
				if(TeamVerwaltung.get(teamId) == null){
					request.setAttribute("error", "Dieses Team existiert nicht!");
				} else {
					request.setAttribute("error", "Sie sind kein Mitglied des Teams " + TeamVerwaltung.get(teamId) + "!");
				}
				view = request.getRequestDispatcher("error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("error.jsp");
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
		
		long id = -1;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		long teamId = -1;
		try {
			id = Long.parseLong(request.getParameter("teamId"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("error.jsp");
		}

		// Profil bearbeiten (Aktion)
		if(mode.equals("edit")){
			Mitglied user = MitgliederVerwaltung.getDummy(currentUser); // TODO
			
			request.setAttribute("user", user);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("jsp/user/userEdit.jsp");
		}
		
		// Profil loeschen (Aktion)
		else if(mode.equals("remove")){
			Mitglied user = MitgliederVerwaltung.getDummy(currentUser); // TODO
			String sure = request.getParameter("sure");
			
			if(!sure.equals("true")){
				request.setAttribute("user", user);			
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/user/userRemove.jsp");
			} else if(sure.equals("true")){
				MitgliederVerwaltung.loeschen(user);
				// TODO hier lieber auf nettes "Auf Wiedersehen"-JSP weiterleiten?
				view = request.getRequestDispatcher("/login?mode=logout");
			}
		}
		
		// Team verlassen (Aktion)
		else if(mode.equals("leaveTeam")){
			Mitglied user = MitgliederVerwaltung.getDummy(currentUser); // TODO
			
			if(MitgliederVerwaltung.istMitgliedInTeam(currentUser, teamId)){
				request.setAttribute("user", user);
				request.setAttribute("team", TeamVerwaltung.get(teamId));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/user/userLeaveTeam.jsp");
			} else {
				if(TeamVerwaltung.get(id) == null){
					request.setAttribute("error", "Dieses Team existiert nicht!");
				} else {
					request.setAttribute("error", "Sie sind kein Mitglied des Teams " + TeamVerwaltung.get(teamId) + "!");
				}
				view = request.getRequestDispatcher("error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("error.jsp");
		}
		
		view.forward(request, response);
	}

}
