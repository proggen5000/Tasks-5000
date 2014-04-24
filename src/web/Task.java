package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Aufgabe;
import entities.Aufgabengruppe;
import administration.AufgabenVerwaltung;
import administration.AufgabengruppenVerwaltung;
import administration.MitgliederVerwaltung;

@WebServlet("/task")
@MultipartConfig
public class Task extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		}
		
		long id = -1;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		long groupId = -1;
		try {
			id = Long.parseLong(request.getParameter("groupId"));
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

		// Aufgabe ansehen
		else if(mode.equals("view") && id != -1){
			Aufgabe task = AufgabenVerwaltung.getDummy(id); // TODO
			
			request.setAttribute("task", task);
			request.setAttribute("date", new java.util.Date(task.getDate()));
			request.setAttribute("deadline", new java.util.Date(task.getDeadline()));
			
			request.setAttribute("valid_request", true);
			request.setAttribute("mode", mode);
			view = request.getRequestDispatcher("jsp/task/taskView.jsp");
		}
		
		// Aufgabe erstellen (Formular)
		else if(mode.equals("new")){
			if(groupId != -1){
				// TODO Team setzen
				// String team = AufgabengruppenVerwaltung.get(groupId).getTeam();
				// request.setAttribute("team", team);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/task/taskEdit.jsp");
			} else {
				request.setAttribute("error", "Ung&uuml;ltige Gruppen-ID!");
				view = request.getRequestDispatcher("error.jsp");
			}
		}
		
		// Aufgabe bearbeiten (Formular)
		else if(mode.equals("edit")){
			Aufgabe task = AufgabenVerwaltung.getDummy(id); // TODO
			request.setAttribute("task", task);
			request.setAttribute("date", new java.util.Date(task.getDate()));
			request.setAttribute("deadline", new java.util.Date(task.getDeadline()));
			
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("jsp/task/taskEdit.jsp");
		}
		
		// Aufgabe löschen
		else if(mode.equals("remove") && id != -1){
			String sure = request.getParameter("sure");
			if(AufgabenVerwaltung.vorhanden(id) && !sure.equals("true")){
				Aufgabe task = AufgabenVerwaltung.getDummy(id); // TODO
				request.setAttribute("task", task);
				request.setAttribute("mode", mode);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("task.jsp");
			} else if (AufgabenVerwaltung.vorhanden(id) && sure.equals("true")){
				AufgabenVerwaltung.loeschen(AufgabenVerwaltung.getDummy(id)); // TODO
				request.setAttribute("mode", mode);
				request.setAttribute("sure", true);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("jsp/task/taskRemove.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Kein g&uuml;ltiger Modus!");
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
		if(request.getSession().getAttribute("id") != null){
			try {
				currentUser = Long.parseLong(request.getSession().getAttribute("id").toString());
			} catch (NullPointerException e){
				request.setAttribute("error", e);
			} 
		}
		
		String mode = request.getParameter("mode");
		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			response.sendRedirect("error.jsp");
		}

		// Aufgabe erstellen (Aktion)
		else if(mode.equals("new")){
			Aufgabe task = new Aufgabe();
			task.setId(4);
			task.setErsteller(MitgliederVerwaltung.get(currentUser));
			task.setTitel(request.getParameter("name"));
			// task.setTeam(team); // hoffentlich bald unnötig
			task.setGruppe(AufgabengruppenVerwaltung.get(Integer.parseInt(request.getParameter("group"))));
			task.setBeschreibung(request.getParameter("description"));			
			// task.setDate(date);
			// task.setDeadline(deadline);
			task.setStatus(Integer.parseInt(request.getParameter("status")));

			Aufgabe taskNew = AufgabenVerwaltung.neu(task);
			response.sendRedirect("task?mode=view&id="+taskNew.getId());
		}
		
		// Aufgabe bearbeiten (Aktion)
		else if(mode.equals("edit")){
			Aufgabe task = new Aufgabe();
			task.setId(id);
			task.setErsteller(MitgliederVerwaltung.get(currentUser));
			task.setTitel(request.getParameter("name"));
			// task.setTeam(team); // hoffentlich bald unnötig
			task.setGruppe(AufgabengruppenVerwaltung.getDummy(Integer.parseInt(request.getParameter("group")))); // TODO dummy weg
			task.setBeschreibung(request.getParameter("description"));			
			// task.setDate(date);
			// task.setDeadline(deadline);
			task.setStatus(Integer.parseInt(request.getParameter("status")));

			Aufgabe taskUpdated = AufgabenVerwaltung.neuDummy(task); // TODO zu .bearbeiten(task) ändern
			response.sendRedirect("task?mode=view&id="+taskUpdated.getId());
		}
		
		// Aufgabe löschen (Aktion)
		else if(mode.equals("remove")){
			String sure = request.getParameter("sure");
			if(AufgabenVerwaltung.vorhanden(id) && !sure.equals("true")){
				Aufgabe task = AufgabenVerwaltung.getDummy(id); // TODO
				request.setAttribute("valid_request", true);
				response.sendRedirect("task&mode=remove&id="+task.getId());
				
			} else if (AufgabenVerwaltung.vorhanden(id) && sure.equals("true")){
				long teamId = AufgabenVerwaltung.get(id).getTeam().getId();
				AufgabenVerwaltung.loeschen(AufgabenVerwaltung.getDummy(id)); // TODO
				request.setAttribute("valid_request", true);
				response.sendRedirect("team&mode=view&id="+teamId);
			} else {
				request.setAttribute("error", "Keine g&uuml;ltige Aufgaben-ID!");
				response.sendRedirect("error.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else if (!mode.equals("new") && !mode.equals("edit") && !mode.equals("remove")) {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			//view = request.getRequestDispatcher("error.jsp");
		}
	}

}