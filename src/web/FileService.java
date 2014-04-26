package web;

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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import administration.AufgabenVerwaltung;
import administration.AufgabengruppenVerwaltung;
import administration.DateiVerwaltung;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;
import entities.Aufgabe;
import entities.Datei;

@WebServlet("/file")
@MultipartConfig
public class FileService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

    public FileService() {
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
			id = Long.parseLong(request.getParameter("teamId"));
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
				Datei file = DateiVerwaltung.vorhanden(id); // TODO zu .get aendern
				request.setAttribute("file", file);
				// request.setAttribute("group", ); // TODO
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
				request.setAttribute("team", TeamVerwaltung.getTeamWithId(teamId));
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
			request.setAttribute("file", DateiVerwaltung.vorhanden(id)); // TODO zu .get aendern
			request.setAttribute("team", TeamVerwaltung.getTeamWithId(teamId));
			request.setAttribute("tasks", AufgabenVerwaltung.getListeVonTeam(teamId));
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/file/fileEdit.jsp");
		}
		
		// Datei loeschen (Rueckfrage)
		else if(mode.equals("remove")){
			if(DateiVerwaltung.vorhanden(id)){ // TODO funktioniert erst mit richtiger vorhanden-Methode
				request.setAttribute("file", DateiVerwaltung.vorhanden(id)); // TODO zu .get aendern
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
		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			response.sendRedirect("/error.jsp");
		}
		
		// Datei herunterladen (Aktion)
		else if(mode.equals("download")){
			if(DateiVerwaltung.vorhanden(id)){
				Datei file = DateiVerwaltung.get(id);
				response.sendRedirect("/"+file.getPfad()); // TODO funktioniert der Download so?
			} else {
				request.setAttribute("error", "Datei nicht gefunden!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Datei hochladen TEST (Aktion)
		else if(mode.equals("newTest")){
			Datei file = new Datei();
			file.setName(request.getParameter("name"));
			file.setBeschreibung(request.getParameter("description"));
			
			// Check that we have a file upload request // TODO vermutlich unnoetig
	        if(!ServletFileUpload.isMultipartContent(request)){
	        	request.setAttribute("error", "Fehler bei der Speicherung!");
				response.sendRedirect("/error.jsp");
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
		}

		// Datei hochladen (Aktion)
		else if(mode.equals("new")){
			Datei file = new Datei();
			file.setErsteller(MitgliederVerwaltung.getMitgliedWithId(currentUser));
			file.setName(request.getParameter("name"));
			file.setBeschreibung(request.getParameter("description"));
			file.setTeam(TeamVerwaltung.getTeamWithId(Long.parseLong(request.getParameter("team"))));
			
			// Check that we have a file upload request // TODO vermutlich unnoetig
	        if(!ServletFileUpload.isMultipartContent(request)){
	        	request.setAttribute("error", "Fehler bei der Speicherung!");
				response.sendRedirect("/error.jsp");
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
			
			// TODO Aufgabenzuordnung via request.getParameter("task")

			Datei fileNew = DateiVerwaltung.neu(file);
			if(fileNew != null){
				response.sendRedirect("/file?mode=view&id="+fileNew.getId());
			} else {
				request.setAttribute("error", "Fehler bei der Speicherung!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Datei bearbeiten (Aktion)
		else if(mode.equals("edit")){
			Datei file = DateiVerwaltung.get(id);
			file.setName(request.getParameter("name"));
			file.setBeschreibung(request.getParameter("description"));
			// file.setPfad(pfad); // TODO Datei hochladen bzw. aendern (+alte Datei loeschen)!
			// TODO Aufgabenzuordnung via request.getParameter("task")

			Datei fileUpdated = DateiVerwaltung.bearbeiten(file);
			response.sendRedirect("/file?mode=view&id="+fileUpdated.getId());
		}
		
		// Datei loeschen (Aktion)
		else if(mode.equals("remove")){ // TODO
			Datei file = DateiVerwaltung.get(id);
			if(DateiVerwaltung.vorhanden(id)){
				long teamId = file.getTeam().getId();
				if(DateiVerwaltung.loeschen(file)){
					request.setAttribute("valid_request", true);
					response.sendRedirect("/team?mode=view&id="+teamId);
				} else {
					request.setAttribute("error", "Datei konnte nicht gel&ouml;scht werden!");
					response.sendRedirect("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Datei nicht gefunden!");
				response.sendRedirect("/error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect("/error.jsp");
		}
	}
}
