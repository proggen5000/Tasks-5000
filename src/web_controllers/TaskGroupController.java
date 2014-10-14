package web_controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistence.UserManager;
import persistence.TaskGroupManager;
import persistence.TeamManager;
import entities.TaskGroup;
import entities.Team;

@WebServlet("/taskGroup")
public class TaskGroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final short descriptionLimit = 5000;

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
		request.setAttribute("teams_menu", TeamManager.getListOfUser(currentUser)); // TODO Workaround
		
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
		
		RequestDispatcher view = request.getRequestDispatcher(request.getContextPath()+"/error.jsp");
		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}
		
		// Aufgabengruppe erstellen (Formular)
		else if(mode.equals("new")){
			if(UserManager.isMemberInTeam(currentUser, teamId)){
				Team team = TeamManager.get(teamId);
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
			TaskGroup taskGroup = TaskGroupManager.get(id);
			request.setAttribute("taskGroup", taskGroup);
			request.setAttribute("team", taskGroup.getTeam());
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/taskGroup/taskGroupEdit.jsp");
		}
		
		// Aufgabengruppe loeschen (Weiterleitung an Nachfrage)
		else if(mode.equals("remove")){
			if(TaskGroupManager.exists(id) && UserManager.isMemberInTeam(currentUser, TaskGroupManager.get(id).getTeam().getId())){
				TaskGroup taskGroup = TaskGroupManager.get(id);
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
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		}
		
		long id = -1; // Aufgabengruppen-ID
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

		// Aufgabengruppe erstellen (Aktion)
		else if(mode.equals("new")){
			TaskGroup taskGroup = new TaskGroup();
			
			String name = request.getParameter("name");
			if(name != null && name.length() > 0){
				taskGroup.setName(name);
			} else {
				session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Aufgabengruppennamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			String description = request.getParameter("description");
			if(description.length() <= descriptionLimit){
				taskGroup.setBeschreibung(description);
			} else {
				session.setAttribute("alert", "Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: " + descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			Team team = TeamManager.get(Integer.parseInt(request.getParameter("teamId")));
			taskGroup.setTeam(team);

			TaskGroup taskGroupNew = TaskGroupManager.add(taskGroup);
			if(taskGroupNew != null){
				session.setAttribute("alert", "Neue Aufgabengruppe erstellt!");
				response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+team.getId());
			} else {
				session.setAttribute("alert", "Fehler bei der Speicherung der Aufgabengruppe!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+team.getId());
			}
		}
		
		// Aufgabengruppe bearbeiten (Aktion)
		else if(mode.equals("edit")){
			TaskGroup taskGroup = TaskGroupManager.get(id);
			
			String name = request.getParameter("name");
			if(name != null && name.length() > 0){
				taskGroup.setName(name);
			} else {
				session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Aufgabengruppennamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			String description = request.getParameter("description");
			if(description.length() <= descriptionLimit){
				taskGroup.setBeschreibung(description);
			} else {
				session.setAttribute("alert", "Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: " + descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			TaskGroup taskGroupUpdated = TaskGroupManager.edit(taskGroup);
			if(taskGroupUpdated != null){
				session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!");
				response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+taskGroupUpdated.getTeam().getId());
				return;
			} else {
				session.setAttribute("alert", "Fehler bei der Speicherung der Aufgabengruppe!");
				session.setAttribute("alert_mode", "danger");
				Team team = TeamManager.get(Integer.parseInt(request.getParameter("teamId")));
				response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+team.getId());
			}
		}
		
		// Aufgabengruppe löschen (Aktion)
		else if(mode.equals("remove")){
			if (TaskGroupManager.exists(id)){
				long teamId = TaskGroupManager.get(id).getTeam().getId();
				if(TaskGroupManager.remove(TaskGroupManager.get(id))){
					session.setAttribute("alert", "Aufgabengruppe erfolgreich gel&ouml;scht!");
					response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+teamId);
					return;
				} else {
					session.setAttribute("alert", "Aufgabengruppe konnte nicht gel&ouml;scht werden!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()+"/team?mode=view&id="+teamId);
					return;
				}
			} else {
				session.setAttribute("error", "Aufgabengruppe nicht gefunden!");
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
