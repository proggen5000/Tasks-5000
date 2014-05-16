package web_controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Aufgabe;
import entities.Mitglied;
import administration.AufgabenMitglieder;
import administration.AufgabenVerwaltung;
import administration.AufgabengruppenVerwaltung;
import administration.DateiVerwaltung;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;

@WebServlet("/task")
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		
		long teamID = -1;
		try {
			teamID = Long.parseLong(request.getParameter("teamId"));
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

		// Aufgabe ansehen
		else if(mode.equals("view")){
			if(id != -1){
				Aufgabe task = AufgabenVerwaltung.get(id);
				request.setAttribute("task", task);
				request.setAttribute("files", DateiVerwaltung.getListeVonAufgabe(id));
				request.setAttribute("users", MitgliederVerwaltung.getListeVonAufgabe(id));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/task/taskView.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Aufgaben-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Aufgabe erstellen (Formular)
		else if(mode.equals("new")){
			if(TeamVerwaltung.vorhanden(teamID)){
				entities.Team team = TeamVerwaltung.get(teamID);
				if(MitgliederVerwaltung.istMitgliedInTeam(currentUser, teamID)){
					request.setAttribute("team", TeamVerwaltung.get(teamID));
					request.setAttribute("taskGroups", AufgabengruppenVerwaltung.getListeVonTeam(team.getId()));
					request.setAttribute("users", MitgliederVerwaltung.getListeVonTeam(teamID));
					request.setAttribute("today", new Date());
					request.setAttribute("mode", mode);
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/jsp/task/taskEdit.jsp");
				} else {
					request.setAttribute("error", "Sie sind nicht Mitglied dieses Teams!");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Team-ID! Bitte erstellen Sie Aufgaben innerhalb von Teams.");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Aufgabe bearbeiten (Formular)
		else if(mode.equals("edit")){
			Aufgabe task = AufgabenVerwaltung.get(id);
			request.setAttribute("task", task);
			request.setAttribute("taskGroups", AufgabengruppenVerwaltung.getListeVonTeam(task.getGruppe().getTeam().getId()));
			// Ausgewählte Mitglieder:
			request.setAttribute("usersSelected", MitgliederVerwaltung.getListeVonAufgabe(id));
			// Restliche Mitglieder:
			request.setAttribute("users", MitgliederVerwaltung.getListeVonAufgabeRest(task.getGruppe().getTeam().getId(), id));
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/task/taskEdit.jsp");
		}
		
		// Aufgabe loeschen
		else if(mode.equals("remove")){
			if(AufgabenVerwaltung.vorhanden(id)){
				Aufgabe task = AufgabenVerwaltung.get(id);
				request.setAttribute("task", task);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/task/taskRemove.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Aufgaben-ID! Ist die Aufgabe schon gel&ouml;scht?");
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

		long id = -1; // Aufgaben-ID
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

		// Aufgabe erstellen (Aktion)
		else if(mode.equals("new")){
			Aufgabe task = new Aufgabe();
			task.setErsteller(MitgliederVerwaltung.get(currentUser));
			task.setName(request.getParameter("name"));
			task.setBeschreibung(request.getParameter("description"));
			task.setGruppe(AufgabengruppenVerwaltung.get(Long.parseLong(request.getParameter("group"))));
			task.setStatus(Integer.parseInt(request.getParameter("status")));
			task.setErstellungsdatum(new Date().getTime());
			
			String deadlineString = request.getParameter("deadline");
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date deadline = new Date();
			try {
				deadline = f.parse(deadlineString);
				task.setDeadline(deadline.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Aufgabe taskNew = AufgabenVerwaltung.neu(task);
			
			String[] userIDs = request.getParameterValues("users");
			for(String userID : userIDs){
				Mitglied user = MitgliederVerwaltung.get(Long.parseLong(userID));
				AufgabenMitglieder.zuweisen(user, task);
			}
			
			session.setAttribute("alert", "Aufgabe erfolgreich erstellt!");
			response.sendRedirect("/task?mode=view&id="+taskNew.getId());
		}
		
		// Aufgabe bearbeiten (Aktion)
		else if(mode.equals("edit")){
			Aufgabe task = AufgabenVerwaltung.get(id);
			task.setName(request.getParameter("name"));
			task.setGruppe(AufgabengruppenVerwaltung.get(Long.parseLong(request.getParameter("group"))));
			task.setBeschreibung(request.getParameter("description"));
			task.setStatus(Integer.parseInt(request.getParameter("status")));
			
			String deadlineString = request.getParameter("deadline");
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date deadline = new Date();
			try {
				deadline = f.parse(deadlineString);
				task.setDeadline(deadline.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			AufgabenMitglieder.entfernenAlle(task);
			String[] userIDs = request.getParameterValues("users");
			for(String userID : userIDs){
				Mitglied user = MitgliederVerwaltung.get(Long.parseLong(userID));
				AufgabenMitglieder.zuweisen(user, task);
			}

			Aufgabe taskUpdated = AufgabenVerwaltung.bearbeiten(task);
			if(taskUpdated != null){
				session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
				response.sendRedirect("/task?mode=view&id="+taskUpdated.getId());
			} else {
				session.setAttribute("error", "Fehler bei der Speicherung der &Auml;nderungen!");
				response.sendRedirect("/error.jsp");
			}	
		}
		
		// Aufgabe loeschen (Aktion)
		else if(mode.equals("remove")){
			if (AufgabenVerwaltung.vorhanden(id)){
				long teamId = AufgabenVerwaltung.get(id).getGruppe().getTeam().getId();
				if(AufgabenVerwaltung.loeschen(AufgabenVerwaltung.get(id))){
					response.sendRedirect("/team?mode=view&id="+teamId);
				} else {
					session.setAttribute("error", "Aufgabe konnte nicht gel&ouml;scht werden!");
					response.sendRedirect("/error.jsp");
				}
				
			} else {
				session.setAttribute("error", "Aufgabe nicht gefunden!");
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
