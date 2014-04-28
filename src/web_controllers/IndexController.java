package web_controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Mitglied;
import administration.AufgabenVerwaltung;
import administration.MitgliederVerwaltung;
import administration.TeamVerwaltung;

@WebServlet("/index")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IndexController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = (boolean) request.getSession().getAttribute("login");
		}
		
		// Cookie auslesen
		Cookie[] cookies = request.getCookies();
		String currentUserCookie = null;
		if(cookies != null){
			for(Cookie cookie : cookies){
			    if("currentUser".equals(cookie.getName())){
			    	currentUserCookie = cookie.getValue();
			    }
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/jsp/sites/index.jsp");
		
		// ?=seitenname führt zur Weiterleitung nach /jsp/sites/seitenname.jsp
		String page = request.getParameter("page");
		if(page != null){
			view = request.getRequestDispatcher("/jsp/sites/" + page + ".jsp");
		}
		
		// ausgeloggt, aber Cookie vorhanden
		else if(!login && currentUserCookie != null){
			if(MitgliederVerwaltung.vorhanden(currentUserCookie)){
				Mitglied user = MitgliederVerwaltung.get(currentUserCookie);
				request.setAttribute("username", user.getUsername());
				request.setAttribute("password", user.getPw());
				request.setAttribute("cookie_forward", true);
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/login?mode=login");
			} else {
				// TODO Benutzer nicht vorhanden! (Fehlermeldung)
			}
			
		}
		
		// eingeloggt
		else if(login){
			HttpSession session = request.getSession(true);
			long currentUser = (long) session.getAttribute("currentUser");
			request.setAttribute("teams", TeamVerwaltung.getListeVonMitglied(currentUser));
			request.setAttribute("tasks", AufgabenVerwaltung.getListeVonMitglied(currentUser));
			request.setAttribute("valid_request", true);
			view = request.getRequestDispatcher("/jsp/sites/indexUser.jsp");
		}
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/index");
	}

}
