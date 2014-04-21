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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean login = false;
		try {
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		} catch (NullPointerException e){
			request.setAttribute("error", e);
		}
		
		int id = -1;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		int groupId = -1;
		try {
			groupId = Integer.parseInt(request.getParameter("group"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");
		RequestDispatcher view = request.getRequestDispatcher("error.jsp");;
		
		
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
			
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("task.jsp");
		}
		
		// Aufgabe erstellen (Formular)
		else if(mode.equals("new") && groupId != -1){
			// TODO Team setzen
			// String team = AufgabengruppenVerwaltung.get(groupId).getTeam();
			// request.setAttribute("team", team);
			
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("task.jsp");
		}
		
		// Aufgabe bearbeiten (Formular)
		else if(mode.equals("edit") && id != -1){
			Aufgabe task = AufgabenVerwaltung.getDummy(id); // TODO
			request.setAttribute("task", task);
			request.setAttribute("date", new java.util.Date(task.getDate()));
			request.setAttribute("deadline", new java.util.Date(task.getDeadline()));
			
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("task.jsp");
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
				view = request.getRequestDispatcher("task.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Kein g&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("error.jsp");
		}
		
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		boolean login = false;
		try {
			login = Boolean.parseBoolean(request.getSession().getAttribute("login").toString());
		} catch (NullPointerException e){
			request.setAttribute("error", e);
		}
		
		int loginId = -1;
		try {
			loginId = Integer.parseInt(request.getSession().getAttribute("loginId").toString());
		} catch (NullPointerException e){
			request.setAttribute("error", e);
		}
		
		int id = -1;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e){
			request.setAttribute("error", e);
		}
		
		String mode = request.getParameter("mode");		
		RequestDispatcher view = request.getRequestDispatcher("error.jsp");;
		
		
		// Fehler - kein Login
		if(!login){
			request.setAttribute("error", "Sie sind nicht eingeloggt!");
			view = request.getRequestDispatcher("error.jsp");
		}

		// Aufgabe erstellen (Aktion)
		else if(mode.equals("new")){
			Aufgabe task = new Aufgabe();
			task.setErsteller(MitgliederVerwaltung.get(loginId));
			task.setTitel(request.getParameter("name"));
			// task.setTeam(team); // hoffentlich bald unnötig
			task.setGruppe(AufgabengruppenVerwaltung.get(Integer.parseInt(request.getParameter("group"))));
			task.setBeschreibung(request.getParameter("description"));			
			// task.setDate(date);
			// task.setDeadline(deadline);
			task.setStatus(Integer.parseInt(request.getParameter("status")));

			AufgabenVerwaltung.neu(task);
			
			//request.setAttribute("team", task.getTeam().getName());
			request.setAttribute("mode", "view");
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("team.jsp");
		}
		
		// Aufgabe bearbeiten (Formular)
		else if(mode.equals("edit") && id != -1){
			Aufgabe task = AufgabenVerwaltung.getDummy(id); // TODO
			request.setAttribute("task", task);
			request.setAttribute("date", new java.util.Date(task.getDate()));
			request.setAttribute("deadline", new java.util.Date(task.getDeadline()));
			
			request.setAttribute("mode", mode);
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("task.jsp");
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
				view = request.getRequestDispatcher("task.jsp");
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			request.setAttribute("error", "Kein g&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("error.jsp");
		}
		
		// view.forward(request, response);
	}

}