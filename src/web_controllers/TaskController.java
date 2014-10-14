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

import persistence.UploadManager;
import persistence.UserManager;
import persistence.TaskGroupManager;
import persistence.TaskManager;
import persistence.TasksMembers;
import persistence.TasksUploads;
import persistence.TeamManager;
import entities.Task;
import entities.Upload;
import entities.User;

@WebServlet("/task")
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final short descriptionLimit = 5000;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean login = false;
		if (request.getSession().getAttribute("login") != null) {
			login = Boolean.parseBoolean(request.getSession()
					.getAttribute("login").toString());
		}

		long currentUser = -1;
		if (request.getSession().getAttribute("currentUser") != null) {
			try {
				currentUser = Long.parseLong(request.getSession()
						.getAttribute("currentUser").toString());
			} catch (NullPointerException e) {
				request.setAttribute("error", e);
			}
		}
		request.setAttribute("teams_menu",
				TeamManager.getListOfUser(currentUser)); // TODO
																	// Workaround

		long id = -1;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			request.setAttribute("error", e);
		}

		long teamID = -1;
		try {
			teamID = Long.parseLong(request.getParameter("teamId"));
		} catch (NumberFormatException e) {
			request.setAttribute("error", e);
		}

		String mode = request.getParameter("mode");
		if (request.getAttribute("mode") != null) {
			mode = (String) request.getAttribute("mode");
		}

		RequestDispatcher view = request.getRequestDispatcher(request
				.getContextPath() + "/error.jsp");

		// Fehler - kein Login
		if (!login) {
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("/error.jsp");
		}

		// Aufgabe ansehen
		else if (mode.equals("view")) {
			if (TaskManager.vorhanden(id)) {
				long tempTeamID = TaskManager.get(id).getTaskGroup().getTeam()
						.getId();
				if (UserManager.isMemberInTeam(currentUser,
						tempTeamID)) {
					Task task = TaskManager.get(id);
					request.setAttribute("task", task);
					request.setAttribute("files",
							UploadManager.getListOfTask(id));
					request.setAttribute("users",
							UserManager.getListOfTask(id));
					request.setAttribute("valid_request", true);
					view = request
							.getRequestDispatcher("/jsp/task/taskView.jsp");
				} else {
					request.setAttribute("error",
							"Sie sind nicht Mitglied dieses Teams!");
					view = request.getRequestDispatcher("/error.jsp");
				}

			} else {
				request.setAttribute("error", "Ung&uuml;ltige Aufgaben-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}

		}

		// Aufgabe erstellen (Formular)
		else if (mode.equals("new")) {
			if (TeamManager.exists(teamID)) {
				entities.Team team = TeamManager.get(teamID);
				if (UserManager.isMemberInTeam(currentUser, teamID)) {
					request.setAttribute("team", TeamManager.get(teamID));
					request.setAttribute("taskGroups",
							TaskGroupManager.getList(team.getId()));
					request.setAttribute("users",
							UserManager.getListOfTeam(teamID));
					request.setAttribute("today", new Date());
					request.setAttribute("mode", mode);
					request.setAttribute("valid_request", true);
					view = request
							.getRequestDispatcher("/jsp/task/taskEdit.jsp");
				} else {
					request.setAttribute("error",
							"Sie sind nicht Mitglied dieses Teams!");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error",
						"Ung&uuml;ltige Team-ID! Bitte erstellen Sie Aufgaben innerhalb von Teams.");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}

		// Aufgabe bearbeiten (Formular)
		else if (mode.equals("edit")) {
			Task task = TaskManager.get(id);
			request.setAttribute("task", task);
			request.setAttribute(
					"taskGroups",
					TaskGroupManager.getList(task.getTaskGroup().getTeam()
							.getId()));
			// Ausgewählte Mitglieder:
			request.setAttribute("usersSelected",
					UserManager.getListOfTask(id));
			// Restliche Mitglieder:
			request.setAttribute(
					"users",
					UserManager.getListOfTaskRest(task
							.getTaskGroup().getTeam().getId(), id));
			request.setAttribute("files",
					UploadManager.getListOfTask(id));
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/task/taskEdit.jsp");
		}

		// Aufgabe löschen
		else if (mode.equals("remove")) {
			if (TaskManager.vorhanden(id)) {
				Task task = TaskManager.get(id);
				request.setAttribute("task", task);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/task/taskRemove.jsp");
			} else {
				request.setAttribute("error",
						"Ung&uuml;ltige Aufgaben-ID! Ist die Aufgabe schon gel&ouml;scht?");
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

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean login = false;
		if (request.getSession().getAttribute("login") != null) {
			login = Boolean.parseBoolean(request.getSession()
					.getAttribute("login").toString());
		}

		long currentUser = -1;
		if (request.getSession().getAttribute("currentUser") != null) {
			try {
				currentUser = Long.parseLong(request.getSession()
						.getAttribute("currentUser").toString());
			} catch (NullPointerException e) {
				request.setAttribute("error", e);
			}
		}

		long id = -1; // Aufgaben-ID
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			request.setAttribute("error", e);
		}
		if (request.getSession().getAttribute("id") != null) {
			try {
				id = Long.parseLong(request.getSession().getAttribute("id")
						.toString());
			} catch (NullPointerException e) {
				request.setAttribute("error", e);
			}
		}

		String mode = request.getParameter("mode");

		HttpSession session = request.getSession(true);

		// Fehler - kein Login
		if (!login) {
			session.setAttribute("error", "Sie sind nicht eingeloggt!");
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}

		// Aufgabe erstellen (Aktion)
		else if (mode.equals("new")) {
			Task task = new Task();

			String name = request.getParameter("name");
			if (name != null && name.length() > 0) {
				task.setName(name);
			} else {
				session.setAttribute("alert",
						"Bitte geben Sie einen g&uuml;ltigen Aufgabennamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			task.setAuthor(UserManager.get(currentUser));

			String description = request.getParameter("description");
			if (description.length() <= descriptionLimit) {
				task.setDescription(description);
			} else {
				session.setAttribute(
						"alert",
						"Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: "
								+ descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			task.setTaskGroup(TaskGroupManager.get(Long.parseLong(request
					.getParameter("group"))));

			String status = request.getParameter("status");
			if (status != null && status.length() > 0) {
				int statusInt = 0;
				try {
					statusInt = Integer.parseInt(status);
				} catch (NumberFormatException e) {
					session.setAttribute("alert",
							"Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
				if (statusInt >= 0 && statusInt <= 100) {
					task.setStatus(statusInt);
				} else {
					session.setAttribute("alert",
							"Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}

			task.setDate(new Date().getTime());

			String deadlineString = request.getParameter("deadline");
			if (deadlineString != null && deadlineString.length() > 0) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Date deadline = new Date();
				try {
					deadline = f.parse(deadlineString);
					task.setDeadline(deadline.getTime());
				} catch (ParseException e) {
					session.setAttribute("alert",
							"Bitte geben Sie ein g&uuml;ltiges Datum als Deadline an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}

			Task taskNew = TaskManager.add(task);

			String[] userIDs = request.getParameterValues("users");
			if (userIDs != null && userIDs.length > 0) {
				for (String userID : userIDs) {
					User user = UserManager
							.get(Long.parseLong(userID));
					TasksMembers.link(user, taskNew);
				}
			}

			session.setAttribute("alert", "Aufgabe erfolgreich erstellt!");
			response.sendRedirect(request.getContextPath()
					+ "/task?mode=view&id=" + taskNew.getId());
			return;
		}

		// Aufgabe bearbeiten (Aktion)
		else if (mode.equals("edit")) {
			Task task = TaskManager.get(id);

			String name = request.getParameter("name");
			if (name != null && name.length() > 0) {
				task.setName(name);
			} else {
				session.setAttribute("alert",
						"Bitte geben Sie einen g&uuml;ltigen Aufgabennamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			task.setTaskGroup(TaskGroupManager.get(Long.parseLong(request
					.getParameter("group"))));

			String description = request.getParameter("description");
			if (description.length() <= descriptionLimit) {
				task.setDescription(description);
			} else {
				session.setAttribute(
						"alert",
						"Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: "
								+ descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			String status = request.getParameter("status");
			if (status != null && status.length() > 0) {
				int statusInt = 0;
				try {
					statusInt = Integer.parseInt(status);
				} catch (NumberFormatException e) {
					session.setAttribute("alert",
							"Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
				if (statusInt >= 0 && statusInt <= 100) {
					task.setStatus(statusInt);
				} else {
					session.setAttribute("alert",
							"Bitte geben Sie einen g&uuml;ltigen Status (0 bis 100) an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}

			String deadlineString = request.getParameter("deadline");
			if (deadlineString != null && deadlineString.length() > 0) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				Date deadline = new Date();
				try {
					deadline = f.parse(deadlineString);
					task.setDeadline(deadline.getTime());
				} catch (ParseException e) {
					session.setAttribute("alert",
							"Bitte geben Sie ein g&uuml;ltiges Datum als Deadline an!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getHeader("Referer"));
					return;
				}
			}

			TasksMembers.unlinkAll(task);
			String[] userIDs = request.getParameterValues("users");
			if (userIDs != null && userIDs.length > 0) {
				for (String userID : userIDs) {
					User user = UserManager
							.get(Long.parseLong(userID));
					TasksMembers.link(user, task);
				}
			}

			// Dateiverknüpfungen löschen
			String[] fileIDs = request.getParameterValues("deleteFiles");
			if (fileIDs != null && fileIDs.length > 0) {
				for (String fileID : fileIDs) {
					Upload file = UploadManager.get(Long.parseLong(fileID));
					TasksUploads.unlink(file, task);
				}
			}

			Task taskUpdated = TaskManager.bearbeiten(task);
			if (taskUpdated != null) {
				session.setAttribute("alert",
						"&Auml;nderungen erfolgreich gespeichert!");
				response.sendRedirect(request.getContextPath()
						+ "/task?mode=view&id=" + taskUpdated.getId());
				return;
			} else {
				session.setAttribute("alert",
						"Fehler bei der Speicherung der &Auml;nderungen!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
		}

		// Aufgabe löschen (Aktion)
		else if (mode.equals("remove")) {
			if (TaskManager.vorhanden(id)) {
				long teamId = TaskManager.get(id).getTaskGroup().getTeam()
						.getId();
				if (TaskManager.loeschen(TaskManager.get(id))) {
					session.setAttribute("alert",
							"Aufgabe erfolgreich gel&ouml;scht!");
					response.sendRedirect(request.getContextPath()
							+ "/team?mode=view&id=" + teamId);
					return;
				} else {
					session.setAttribute("alert",
							"Aufgabe konnte nicht gel&ouml;scht werden!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()
							+ "/team?mode=view&id=" + teamId);
					return;
				}
			} else {
				session.setAttribute("error", "Aufgabe nicht gefunden!");
				response.sendRedirect(request.getContextPath() + "/error.jsp");
				return;
			}
		}

		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			return;
		}
	}
}
