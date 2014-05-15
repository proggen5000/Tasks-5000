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
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;
import entities.Aufgabengruppe;
import entities.Team;

@WebServlet("/taskGroup")
public class TaskGroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TaskGroupController() {
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
		
		long id = -1; // Aufgabengruppen-ID
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
		
		// Aufgabengruppe erstellen (Formular)
		else if(mode.equals("new")){
			if(MitgliederVerwaltung.istMitgliedInTeam(currentUser, teamId)){
				Team team = TeamVerwaltung.get(teamId);
				request.setAttribute("team", team);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/taskGroup/taskGroupEdit.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Team-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Aufgabengruppe bearbeiten (Formular)
		else if(mode.equals("edit")){
			Aufgabengruppe taskGroup = AufgabengruppenVerwaltung.get(id);
			request.setAttribute("taskGroup", taskGroup);
			request.setAttribute("team", taskGroup.getTeam());
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/taskGroup/taskGroupEdit.jsp");
		}
		
		// Aufgabengruppe loeschen (Weiterleitung an Nachfrage)
		else if(mode.equals("remove")){
			if(AufgabengruppenVerwaltung.vorhanden(id) && MitgliederVerwaltung.istMitgliedInTeam(currentUser, AufgabengruppenVerwaltung.get(id).getTeam().getId())){
				Aufgabengruppe taskGroup = AufgabengruppenVerwaltung.get(id);
				request.setAttribute("taskGroup", taskGroup);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/taskGroup/taskGroupRemove.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Aufgabengruppen-ID!");
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
			login = Boolean.parseBoolean((String) request.getSession().getAttribute("login"));
		}
		
		long id = -1; // Aufgabengruppen-ID
		if(request.getSession().getAttribute("id") != null){
			try {
				id = Long.parseLong(request.getSession().getAttribute("id").toString());
			} catch (NullPointerException e){
				request.setAttribute("error", e);
			} 
		}
		
		String mode = request.getParameter("mode");
		
		HttpSession session = request.getSession(true);
		
		// Fehler - kein Login
		if(!login){
			session.setAttribute("error", "Sie sind nicht eingeloggt!");
			response.sendRedirect("/error.jsp");
		}

		// Aufgabengruppe erstellen (Aktion)
		else if(mode.equals("new")){
			String name = request.getParameter("name");
			if(name != null && name.length() > 0){
				Aufgabengruppe taskGroup = new Aufgabengruppe();
				Team team = TeamVerwaltung.get(Integer.parseInt(request.getParameter("teamId")));
				taskGroup.setName(name);
				taskGroup.setBeschreibung(request.getParameter("description"));			
				taskGroup.setTeam(team);

				Aufgabengruppe taskGroupNew = AufgabengruppenVerwaltung.neu(taskGroup);
				if(taskGroupNew != null){
					session.setAttribute("alert", "Neue Aufgabengruppe erstellt!");
					response.sendRedirect("/team?mode=view&id="+team.getId());
				} else {
					session.setAttribute("error", "Fehler bei der Speicherung!");
					response.sendRedirect("/error.jsp");
				}
			} else {
				session.setAttribute("alert", "Bitte geben Sie alle Daten an, die mit einem Sternchen (*) gekennzeichnet sind.");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
			}
		}
		
		// Aufgabengruppe bearbeiten (Aktion)
		else if(mode.equals("edit")){
			Aufgabengruppe taskGroup = AufgabengruppenVerwaltung.get(id);
			taskGroup.setName(request.getParameter("name"));
			taskGroup.setBeschreibung(request.getParameter("description"));

			Aufgabengruppe taskGroupUpdated = AufgabengruppenVerwaltung.bearbeiten(taskGroup);
			if(taskGroupUpdated != null){
				session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
				response.sendRedirect("/team?mode=view&id="+taskGroupUpdated.getTeam().getId()); // TODO ändern zu normaler Weiterleitung?
			} else {
				session.setAttribute("error", "Fehler bei der Speicherung!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Aufgabengruppe loeschen (Aktion)
		else if(mode.equals("remove")){
			String sure = request.getParameter("sure");
			if (AufgabengruppenVerwaltung.vorhanden(id) && sure.equals("true")){
				long teamId = AufgabengruppenVerwaltung.get(id).getTeam().getId();
				if(AufgabengruppenVerwaltung.loeschen(AufgabengruppenVerwaltung.get(id))){
					response.sendRedirect("/team?mode=view&id="+teamId);
				} else {
					session.setAttribute("error", "Aufgabengruppe konnte nicht gel&ouml;scht werden!");
					response.sendRedirect("/error.jsp");
				}
			} else {
				session.setAttribute("error", "Aufgabengruppe nicht gefunden!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else if (!mode.equals("new") && !mode.equals("edit") && !mode.equals("remove")) {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect("/error.jsp");
		}
	}
}
