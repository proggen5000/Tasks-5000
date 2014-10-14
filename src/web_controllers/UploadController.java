package web_controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import persistence.TaskManager;
import persistence.UploadManager;
import persistence.UserManager;
import persistence.TasksUploads;
import persistence.TeamManager;
import entities.Task;
import entities.Upload;

@WebServlet("/file")
@MultipartConfig
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_DIRECTORY = "data";
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	public UploadController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean login = false;
		if (request.getSession().getAttribute("login") != null) {
			login = Boolean.parseBoolean(request.getSession()
					.getAttribute("login").toString());
		}

		// TODO currentUser eigentlich nicht benötigt
		long currentUser = -1;
		try {
			currentUser = Long.parseLong(request.getSession()
					.getAttribute("currentUser").toString());
		} catch (NullPointerException e) {
			request.setAttribute("error", e);
		}
		request.setAttribute("teams_menu",
				TeamManager.getListOfUser(currentUser)); // TODO
																	// Workaround

		long id = -1; // Datei-ID
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			request.setAttribute("error", e);
		}

		long teamId = -1;
		try {
			teamId = Long.parseLong(request.getParameter("teamId"));
		} catch (NumberFormatException e) {
			request.setAttribute("error", e);
		}

		long taskId = -1;
		try {
			taskId = Long.parseLong(request.getParameter("taskId"));
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

		// Datei ansehen
		else if (mode.equals("view")) {
			if (id != -1) {
				Upload file = UploadManager.get(id);
				request.setAttribute("file", file);
				request.setAttribute("tasks",
						TasksUploads.getList(id));
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/file/fileView.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Datei-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}

		// Datei hochladen/erstellen (Formular)
		else if (mode.equals("new")) {
			if (TeamManager.exists(teamId)) {
				request.setAttribute("team", TeamManager.get(teamId));
				if (taskId > -1) {
					ArrayList<Task> tasks = new ArrayList<Task>();
					tasks.add(TaskManager.get(taskId));
					request.setAttribute("tasks", tasks);
					request.setAttribute("taskLink", taskId);
				}
				request.setAttribute("tasksRest",
						TaskManager.getListeVonTeam(teamId));
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/file/fileEdit.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Team-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}

		// Datei bearbeiten (Formular)
		else if (mode.equals("edit")) {
			Upload file = UploadManager.get(id);
			request.setAttribute("file", file);
			request.setAttribute("team", file.getTeam());
			request.setAttribute("tasks",
					TaskManager.getListeVonDatei(id));
			request.setAttribute("tasksRest", TaskManager
					.getListeVonDateiRest(file.getTeam().getId(), id));
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/file/fileEdit.jsp");
		}

		// Datei löschen (Rückfrage)
		else if (mode.equals("remove")) {
			if (UploadManager.exists(id)) {
				request.setAttribute("file", UploadManager.get(id));
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/file/fileRemove.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Datei-ID!");
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
			return;
		}

		// Datei herunterladen (Aktion)
		else if (mode.equals("download")) {
			if (UploadManager.exists(id)) {
				Upload file = UploadManager.get(id);
				response.sendRedirect(request.getContextPath() + "/"
						+ file.getPath()); // TODO funktioniert der Download so?
			} else {
				session.setAttribute("error", "Datei nicht gefunden!");
				response.sendRedirect(request.getContextPath() + "/error.jsp");
				return;
			}
		}

		// Datei hochladen (Aktion)
		else if (mode.equals("new")) {
			Upload file = new Upload();
			file.setAuthor(UserManager.get(currentUser));

			String name = request.getParameter("name");
			if (name != null && name.length() > 0) {
				file.setName(name);
			} else {
				session.setAttribute("alert",
						"Bitte geben Sie einen g&uuml;ltigen Dateinamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			final short descriptionLimit = 5000;
			String description = request.getParameter("description");
			if (description.length() <= descriptionLimit) {
				file.setDescription(description);
			} else {
				session.setAttribute(
						"alert",
						"Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: "
								+ descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			file.setTeam(TeamManager.get(Long.parseLong(request
					.getParameter("team"))));

			// Check that we have a file upload request // TODO vermutlich
			// unnötig
			if (!ServletFileUpload.isMultipartContent(request)) {
				session.setAttribute("error", "Fehler bei der Speicherung!");
				response.sendRedirect(request.getContextPath() + "/error.jsp");
				return;
			}
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MAX_MEMORY_SIZE);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			String uploadFolder = getServletContext().getRealPath("")
					+ File.separator + DATA_DIRECTORY; // TODO hier noch
														// Team-Ordnerstruktur
														// realisieren
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(MAX_REQUEST_SIZE);

			try {
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadFolder + File.separator
								+ fileName;
						File uploadedFile = new File(filePath);
						// TODO Debug:
						System.out.println(filePath);
						file.setPath(filePath);
						item.write(uploadedFile);
					}
				}
			} catch (FileUploadException ex) {
				throw new ServletException(ex);
			} catch (Exception ex) {
				throw new ServletException(ex);
			}

			Upload fileNew = UploadManager.add(file);
			if (fileNew != null) {
				// Aufgabenzuordnung
				String[] taskIDs = request.getParameterValues("tasks");
				if (taskIDs != null && taskIDs.length > 0) {
					for (String taskID : taskIDs) {
						Task task = TaskManager.get(Long
								.parseLong(taskID));
						TasksUploads.link(fileNew, task);
					}
				}
				session.setAttribute("alert", "Datei erfolgreich hochgeladen!");
				response.sendRedirect(request.getContextPath()
						+ "/file?mode=view&id=" + fileNew.getId());
				return;
			} else {
				session.setAttribute("error",
						"Datei konnte nicht erstellt werden!");
				response.sendRedirect(request.getContextPath() + "/error.jsp");
				return;
			}
		}

		// Datei bearbeiten (Aktion)
		else if (mode.equals("edit")) {
			Upload file = UploadManager.get(id);

			String name = request.getParameter("name");
			if (name != null && name.length() > 0) {
				file.setName(name);
			} else {
				session.setAttribute("alert",
						"Bitte geben Sie einen g&uuml;ltigen Dateinamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			final short descriptionLimit = 5000;
			String description = request.getParameter("description");
			if (description.length() <= descriptionLimit) {
				file.setDescription(description);
			} else {
				session.setAttribute(
						"alert",
						"Bitte geben Sie eine k&uuml;rzere Beschreibung an! (Zeichenbeschr&auml;nkung: "
								+ descriptionLimit + ")");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}

			// file.setPfad(pfad);
			// TODO Datei hochladen bzw. aendern (+alte Datei loeschen)

			Upload fileUpdated = UploadManager.edit(file);

			// Aufgabenzuordnung
			// TODO AufgabenDateien.entfernenAlle(file);
			String[] taskIDs = request.getParameterValues("tasks");
			if (taskIDs != null && taskIDs.length > 0) {
				for (String taskID : taskIDs) {
					Task task = TaskManager.get(Long.parseLong(taskID));
					TasksUploads.link(fileUpdated, task);
				}
			}

			session.setAttribute("alert",
					"&Auml;nderungen erfolgreich gespeichert!");
			response.sendRedirect(request.getContextPath()
					+ "/file?mode=view&id=" + fileUpdated.getId());
			return;
		}

		// Datei löschen (Aktion)
		else if (mode.equals("remove")) {
			if (UploadManager.exists(id)) {
				Upload file = UploadManager.get(id);
				long teamId = file.getTeam().getId();
				if (UploadManager.remove(file.getId())) {
					// TODO auch phys. Datei loeschen!
					session.setAttribute("alert",
							"Datei erfolgreich gel&ouml;scht!");
					response.sendRedirect(request.getContextPath()
							+ "/team?mode=view&id=" + teamId);
					return;
				} else {
					session.setAttribute("error",
							"Datei konnte nicht gel&ouml;scht werden!");
					response.sendRedirect(request.getContextPath()
							+ "/error.jsp");
					return;
				}
			} else {
				session.setAttribute("error", "Datei nicht gefunden!");
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