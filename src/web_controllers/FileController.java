package web_controllers;

import java.io.IOException;
import java.io.File;
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

import administration.AufgabenDateien;
import administration.AufgabenMitglieder;
import administration.AufgabenVerwaltung;
import administration.DateiVerwaltung;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;
import entities.Aufgabe;
import entities.Datei;
import entities.Mitglied;

@WebServlet("/file")
@MultipartConfig
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

    public FileController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		}
		
		long id = -1; // Datei-ID
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

		// Datei ansehen
		else if(mode.equals("view")){
			if(id != -1){
				Datei file = DateiVerwaltung.get(id);
				request.setAttribute("file", file);
				// request.setAttribute("task", ); // TODO
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/file/fileView.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Datei-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Datei hochladen (Formular)
		else if(mode.equals("new")){
			if(TeamVerwaltung.vorhanden(teamId)){
				request.setAttribute("team", TeamVerwaltung.get(teamId));
				request.setAttribute("tasks", AufgabenVerwaltung.getListeVonTeam(teamId));
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/file/fileEdit.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Team-ID!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		}
		
		// Datei bearbeiten (Formular)
		else if(mode.equals("edit")){
			Datei file = DateiVerwaltung.get(id);
			request.setAttribute("file", file);
			request.setAttribute("team", file.getTeam());
			request.setAttribute("tasks", AufgabenVerwaltung.getListeVonTeam(file.getTeam().getId()));
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/file/fileEdit.jsp");
		}
		
		// Datei loeschen (Rueckfrage)
		else if(mode.equals("remove")){
			if(DateiVerwaltung.vorhanden(id)){
				request.setAttribute("file", DateiVerwaltung.get(id));
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
		
		long id = -1; // Datei-ID
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
			return;
		}
		
		// Datei herunterladen (Aktion)
		else if(mode.equals("download")){
			if(DateiVerwaltung.vorhanden(id)){
				Datei file = DateiVerwaltung.get(id);
				response.sendRedirect("/"+file.getPfad()); // TODO funktioniert der Download so?
			} else {
				session.setAttribute("error", "Datei nicht gefunden!");
				response.sendRedirect("/error.jsp");
				return;
			}
		}

		// Datei hochladen (Aktion)
		else if(mode.equals("new")){
			Datei file = new Datei();
			file.setErsteller(MitgliederVerwaltung.get(currentUser));
			
			String name = request.getParameter("name");
			if(name != null && name.length() > 0){
				file.setName(name);
			} else {
				session.setAttribute("alert", "Bitte geben Sie einen g&uuml;ltigen Dateinamen an!");
				session.setAttribute("alert_mode", "danger");
				response.sendRedirect(request.getHeader("Referer"));
				return;
			}
			
			file.setBeschreibung(request.getParameter("description"));
			file.setTeam(TeamVerwaltung.get(Long.parseLong(request.getParameter("team"))));
			
			// Check that we have a file upload request // TODO vermutlich unnoetig
	        if(!ServletFileUpload.isMultipartContent(request)){
	        	session.setAttribute("error", "Fehler bei der Speicherung!");
				response.sendRedirect("/error.jsp");
				return;
	        }
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(MAX_MEMORY_SIZE);
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	        String uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY; // TODO hier noch Team-Ordnerstruktur realisieren
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setSizeMax(MAX_REQUEST_SIZE);
			
	        try {
	            List items = upload.parseRequest(request);
	            Iterator iter = items.iterator();
	            while (iter.hasNext()) {
	                FileItem item = (FileItem) iter.next();
	                if(!item.isFormField()) {
	                    String fileName = new File(item.getName()).getName();
	                    String filePath = uploadFolder + File.separator + fileName;
	                    File uploadedFile = new File(filePath);
	                    System.out.println(filePath);
	                    file.setPfad(filePath);
	                    item.write(uploadedFile);
	                }
	            }	
	        }
	        catch (FileUploadException ex) { throw new ServletException(ex); }
	        catch (Exception ex) { throw new ServletException(ex); }			

			Datei fileNew = DateiVerwaltung.neu(file);
			if(fileNew != null){
				// Aufgabenzuordnung
		        String[] taskIDs = request.getParameterValues("tasks");
				if(taskIDs != null && taskIDs.length > 0){
					for(String taskID : taskIDs){
						Aufgabe task = AufgabenVerwaltung.get(Long.parseLong(taskID));
						AufgabenDateien.zuweisen(fileNew, task);
					}
				}
				session.setAttribute("alert", "Datei erfolgreich hochgeladen!");
				response.sendRedirect("/file?mode=view&id="+fileNew.getId());
				return;
			} else {
				session.setAttribute("error", "Datei konnte nicht erstellt werden!");
				response.sendRedirect("/error.jsp");
				return;
			}
		}
		
		// Datei bearbeiten (Aktion)
		else if(mode.equals("edit")){
			Datei file = DateiVerwaltung.get(id);
			file.setName(request.getParameter("name"));
			file.setBeschreibung(request.getParameter("description"));
			// file.setPfad(pfad);
			// TODO Datei hochladen bzw. aendern (+alte Datei loeschen)
			// TODO Aufgabenzuordnung via request.getParameter("tasks")

			Datei fileUpdated = DateiVerwaltung.bearbeiten(file);
			session.setAttribute("alert", "&Auml;nderungen erfolgreich gespeichert!"); // TODO wird das angezeigt?
			response.sendRedirect("/file?mode=view&id="+fileUpdated.getId());
		}
		
		// Datei löschen (Aktion)
		else if(mode.equals("remove")){
			if(DateiVerwaltung.vorhanden(id)){
				Datei file = DateiVerwaltung.get(id);
				long teamId = file.getTeam().getId();
				if(DateiVerwaltung.loeschen(file.getId())){
					// TODO auch phys. Datei loeschen! 
					session.setAttribute("alert", "Datei erfolgreich gel&ouml;scht!");
					response.sendRedirect("/team?mode=view&id="+teamId);
					return;
				} else {
					session.setAttribute("error", "Datei konnte nicht gel&ouml;scht werden!");
					response.sendRedirect("/error.jsp");
					return;
				}
			} else {
				session.setAttribute("error", "Datei nicht gefunden!");
				response.sendRedirect("/error.jsp");
				return;
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
