package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.AufgabenVerwaltung;
import administration.AufgabengruppenVerwaltung;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;

@WebServlet("/team")
public class Team extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Team() {
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
		
		RequestDispatcher view = request.getRequestDispatcher("error.jsp");

		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("error.jsp");
		}

		// Team ansehen
		else if(mode.equals("view")){
			if(id != -1){
				request.setAttribute("team", TeamVerwaltung.get(id)); // TODO
				request.setAttribute("members", MitgliederVerwaltung.getListeVonTeam(id));
				request.setAttribute("groups", AufgabengruppenVerwaltung.getListeVonTeam(id)); // TODO
				// request.setAttribute("files", DateiVerwaltung.getListeVonTeam(id)); // TODO
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/team/teamView.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Team-ID!");
				view = request.getRequestDispatcher("error.jsp");
			}
		}
		
		// Team erstellen (Formular)
		else if(mode.equals("new")){
			request.setAttribute("usersAll", MitgliederVerwaltung.getListe());
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("jsp/team/teamEdit.jsp");
		}
		
		// Team bearbeiten (Formular)
		else if(mode.equals("edit")){			
			entities.Team team = TeamVerwaltung.get(id);
			
			if(team.getGruppenfuehrer().getId() == currentUser){
				request.setAttribute("team", team);
				request.setAttribute("users", MitgliederVerwaltung.getListeVonTeam(id));
				request.setAttribute("usersAll", MitgliederVerwaltung.getListe());
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/team/teamEdit.jsp");
			} else {
				request.setAttribute("error", "Nur Teammanager d&uuml;rfen die Teamdetails bearbeiten!");
				view = request.getRequestDispatcher("error.jsp");
			}
		}
		
		// Team loeschen
		else if(mode.equals("remove") && id != -1){
			String sure = request.getParameter("sure");
			if(TeamVerwaltung.vorhanden(id) && !sure.equals("true")){
				entities.Team team = TeamVerwaltung.get(id);
				request.setAttribute("team", team);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/team/teamRemove.jsp");
			} else if (AufgabenVerwaltung.vorhanden(id) && sure.equals("true")){
				AufgabenVerwaltung.loeschen(AufgabenVerwaltung.get(id)); // TODO
				request.setAttribute("mode", mode);
				request.setAttribute("sure", true);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/team/teamRemove.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Team-ID!");
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
		
	}

}
