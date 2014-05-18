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
		request.setAttribute("teams_menu", TeamVerwaltung.getListeVonMitglied(currentUser)); // TODO Workaround
		
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
		
		RequestDispatcher view = request.getRequestDispatcher(request.getContextPath()+"/error.jsp");
		
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
			request.setAttribute("files", DateiVerwaltung.getListeVonAufgabe(id));
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/task/taskEdit.jsp");
		}
		
		// Aufgabe löschen
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
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
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
			response.sendRedirect(request.getContextPath()+"/error.jsp");
		}

		// Aufgabe erstellen (Aktion)
		else if(mode.equals("new")){
			Aufgabe task = new Aufgabe();
			
			String name = request.getParameter("name");
			if(name != null && name.length() > 0){
				task.setName(name);
			} else {
				session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Aufgabennamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			task.setErsteller(MitgliederVerwaltung.get(currentUser));
			
			task.setBeschreibung(request.getParameter("description"));
			task.setGruppe(AufgabengruppenVerwaltung.get(Long.parseLong(request.getParameter("group"))));
			
			String status = request.getParameter("status");
			if(status != null && status.length() > 0){
				int statusInt = 0;
				try {
					statusInt = Integer.parseInt(status);
				} catch (NumberFormatException e){
					session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
				if(statusInt >= 0 && statusInt <= 100){
					task.setStatus(statusInt);				
				} else {
					session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}
			
			task.setErstellungsdatum(new Date().getTime());
			
			String deadlineString = request.getParameter("deadline");
			if(deadlineString != null && deadlineString.length() > 0){
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Date deadline = new Date();
				try {
					deadline = f.parse(deadlineString);
					task.setDeadline(deadline.getTime());
				} catch (ParseException e) {
					session.setAttribute("alert", "Bitte geben Sie ein g&uuml;ltiges Datum als Deadline an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}
			
			Aufgabe taskNew = AufgabenVerwaltung.neu(task);
			
			String[] userIDs = request.getParameterValues("users");
			if(userIDs != null && userIDs.length > 0){
				for(String userID : userIDs){
					Mitglied user = MitgliederVerwaltung.get(Long.parseLong(userID));
					AufgabenMitglieder.zuweisen(user, taskNew);
				}
			}
			
			session.setAttribute("alert", "Aufgabe erfolgreich erstellt!");
			response.sendRedirect(request.getContextPath()+"/task?mode=view&id="+taskNew.getId());
			return;
		}
		
		// Aufgabe bearbeiten (Aktion)
		else if(mode.equals("edit")){
			Aufgabe task = AufgabenVerwaltung.get(id);
			
			String name = request.getParameter("name");			
			if(name != null && name.length() > 0){
				task.setName(name);
			} else {
				session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Aufgabennamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			task.setGruppe(AufgabengruppenVerwaltung.get(Long.parseLong(request.getParameter("group"))));
			task.setBeschreibung(request.getParameter("description"));
			
			String status = request.getParameter("status");
			if(status != null && status.length() > 0){
				int statusInt = 0;
				try {
					statusInt = Integer.parseInt(status);
				} catch (NumberFormatException e){
					session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
				if(statusInt >= 0 && statusInt <= 100){
					task.setStatus(statusInt);	
				} else {
					session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}
			
			String deadlineString = request.getParameter("deadline");
			if(deadlineString != null && deadlineString.length() > 0){
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Date deadline = new Date();
				try {
					deadline = f.parse(deadlineString);
					task.setDeadline(deadline.getTime());
				} catch (ParseException e) {
					session.setAttribute("alert", "Bitte geben Sie ein g&uuml;ltiges Datum als Deadline an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}
			
			AufgabenMitglieder.entfernenAlle(task);
			String[] userIDs = request.getParameterValues("users");
			if(userIDs != null && userIDs.length > 0){
				for(String userID : userIDs){
					Mitglied user = MitgliederVerwaltung.get(Long.parseLong(userID));
					AufgabenMitglieder.zuweisen(user, task);
				}
			}
			
			// TODO entsprechende Dateien löschen!
			// String[] fileIDs = request.getParameterValues("deleteFiles");

			Aufgabe taskUpdated = AufgabenVerwaltung.bearbeiten(task);
			if(taskUpdated != null){
				session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
				response.sendRedirect(request.getContextPath()+"/task?mode=view&id="+taskUpdated.getId());
				return;
			} else {
				session.setAttribute("alert", "Fehler bei der Speicherung der &Auml;nderungen!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}	
		}
		
		// Aufgabe löschen (Aktion)
		else if(mode.equals("remove")){			
			if (AufgabenVerwaltung.vorhanden(id)){
				long teamId = AufgabenVerwaltung.get(id).getGruppe().getTeam().getId();
				if(AufgabenVerwaltung.loeschen(AufgabenVerwaltung.get(id))){
					session.setAttribute("alert", "Aufgabe erfolgreich gel&ouml;scht!");
					response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+teamId);
					return;
				} else {
					session.setAttribute("alert", "Aufgabe konnte nicht gel&ouml;scht werden!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+teamId);
					return;
				}
			} else {
				session.setAttribute("error", "Aufgabe nicht gefunden!");
				response.sendRedirect(request.getContextPath()+"/error.jsp");
				return;
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect(request.getContextPath()+"/error.jsp");
			return;
		}
	}
}
