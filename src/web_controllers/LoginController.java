package web_controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import administration.MitgliederVerwaltung;
import entities.Mitglied;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = (boolean) request.getSession().getAttribute("login");
		}
		
		String mode = request.getParameter("mode");
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/error.jsp");
		
		if(mode == null){
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("/error.jsp");
		}
		
		// Logout
		else if(mode.equals("logout")){
			if(login){
				HttpSession session = request.getSession(true);
				session.removeAttribute("login");
				session.removeAttribute("currentUser");
				
				request.setAttribute("title", "Logout erfolgreich");
				request.setAttribute("message", "Sie haben sich erfolgreich ausgeloggt!<br />Auf Wiedersehen. :)");
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/success.jsp");
			} else {
				request.setAttribute("error", "Sie sind bereits ausgeloggt!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		} else {
			request.setAttribute("error", "Ung&uuml;ltiger Modus!");
			view = request.getRequestDispatcher("/error.jsp");
		}
			
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = (boolean) request.getSession().getAttribute("login");
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String mode = request.getParameter("mode");
		if(request.getAttribute("mode") != null){
			mode = (String) request.getAttribute("mode");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/error.jsp");
		
		// Login (Aktion)
		if(mode.equals("login")){
			if(!login){
				if(MitgliederVerwaltung.pruefeLogin(username, password)){
					Mitglied user =  MitgliederVerwaltung.get(username);
					// TODO Debugging:
					System.out.println("Benutzer: " + user.getId() + " -> " + user.getUsername());
					System.out.println("PW: " + user.getPw());
					
					HttpSession session = request.getSession(true);
					session.setAttribute("login", true);
					session.setAttribute("currentUser", user.getId());
					
					request.setAttribute("title", "Login erfolgreich");
					request.setAttribute("message", "Sie haben sich erfolgreich eingeloggt!<br />Hallo, " + user.getUsername() + ". :)");
					request.setAttribute("link_url", "/");
					request.setAttribute("link_text", "Weiter zur pers&ouml;nlichen Startseite");
					request.setAttribute("valid_request", true);
					view = request.getRequestDispatcher("/success.jsp");
				} else {
					request.setAttribute("error", "Benutzername und Password stimmen nicht &uuml;berein!<br />Bitte versuchen Sie es erneut oder <a href=\"/?page=register\">registrieren</a> Sie sich.");
					view = request.getRequestDispatcher("/error.jsp");
				}
			} else {
				request.setAttribute("error", "Sie sind bereits eingeloggt!");
				view = request.getRequestDispatcher("/error.jsp");
			}
			
		// Logout
		} else if(mode.equals("logout")){
			if(login){
				HttpSession session = request.getSession(true);
				session.removeAttribute("login");
				session.removeAttribute("currentUser");
				
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/jsp/login/logout.jsp");
			} else {
				request.setAttribute("error", "Sie sind bereits ausgeloggt!");
				view = request.getRequestDispatcher("/error.jsp");
			}
		
		// Registrierung (Aktion)
		} else if(mode.equals("register")){
			if(!login){
				Mitglied user = new Mitglied();
				// user.setId(id); // TODO noetig?
				user.setUsername(request.getParameter("username"));
				user.setVorname(request.getParameter("vorname"));
				user.setNachname(request.getParameter("nachname"));
				user.setEmail(request.getParameter("email"));
				user.setPw(request.getParameter("password"));
				// user.setRegdatum(new Date().getTime()); // TODO noetig?
				
				Mitglied userNew = MitgliederVerwaltung.neu(user);
				request.setAttribute("valid_request", true);
				request.setAttribute("title", "Erfolgreich registriert");
				request.setAttribute("message", "Sie haben sich hiermit erfolgreich als \"<b>" + userNew.getUsername() + "</b>\" registriert und k&ouml;nnen sich ab sofort mit Ihrem Passwort einloggen.<br />Herzlich willkommen! :)");
				request.setAttribute("valid_request", true);
				view = request.getRequestDispatcher("/success.jsp");
			} else {
				request.setAttribute("error", "Sie sind bereits registriert und eingeloggt!");
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
}