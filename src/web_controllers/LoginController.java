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
			login = (Boolean) request.getSession().getAttribute("login");
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
			login = (Boolean) request.getSession().getAttribute("login");
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String mode = request.getParameter("mode");
		
		HttpSession session = request.getSession(true);
		// RequestDispatcher view = request.getRequestDispatcher("/error.jsp");
		
		// Login (Aktion)
		if(mode.equals("login")){
			if(!login){
				if(MitgliederVerwaltung.pruefeLogin(username, password)){
					Mitglied user =  MitgliederVerwaltung.get(username);
					
					session.setAttribute("login", true);
					session.setAttribute("currentUser", user.getId());
					
					// Cookie setzen
					if(request.getParameter("cookie") != null){
						Cookie cookie = new Cookie("currentUser", String.valueOf(user.getId()));
						cookie.setMaxAge(30 * 24 * 60 * 60); // 30 Tage
						response.addCookie(cookie);
						// TODO Debug:
						System.out.println("Cookie gesetzt! Cookie: " + cookie.getName() + " = " + cookie.getValue());
					}
					
					// Weiterleitung ohne Cookie
					boolean cookie_forward = false;
					if(request.getAttribute("cookie_forward") != null){
						cookie_forward = (Boolean) request.getAttribute("cookie_forward");
					}
					
					if(!cookie_forward){
						session.setAttribute("alert", "Sie haben sich erfolgreich eingeloggt! Herzlich willkommen, " + user.getUsername() + ".");
						response.sendRedirect("/index");
					} else {
						// direkte Weiterleitung zur Startseite, falls Cookie gefunden
						response.sendRedirect("/index");
					}
				} else {
					session.setAttribute("error", "Benutzername und Password stimmen nicht &uuml;berein!<br />Bitte versuchen Sie es erneut oder <a href=\"/?page=register\">registrieren</a> Sie sich, falls Sie noch kein Benutzerprofil angelegt haben.");
					response.sendRedirect("/error.jsp");
				}
			} else {
				session.setAttribute("error", "Sie sind bereits eingeloggt!");
				response.sendRedirect("/error.jsp");
			}
			
		// Logout (Aktion)
		} else if(mode.equals("logout")){
			if(login){
				session.removeAttribute("login");
				session.removeAttribute("currentUser");
				
				// Cookie entfernen
				Cookie cookie = new Cookie("currentUser", "");
				cookie.setMaxAge(0);
				
				session.setAttribute("alert", "Sie haben sich erfolgreich ausgeloggt! Haben Sie noch einen sch&ouml;nen Tag.");
				response.sendRedirect("/index");
			} else {
				session.setAttribute("error", "Sie sind bereits ausgeloggt!");
				response.sendRedirect("/error.jsp");
			}
		
		// Registrierung (Aktion)
		} else if(mode.equals("register")){
			if(!login){
				String passwordRepeat = request.getParameter("passwordRepeat");
				if(password.equals(passwordRepeat)){
					Mitglied user = new Mitglied();
					user.setUsername(request.getParameter("username"));
					user.setVorname(request.getParameter("vorname"));
					user.setNachname(request.getParameter("nachname"));
					user.setEmail(request.getParameter("email"));
					user.setPw(password);
					
					Mitglied userNew = MitgliederVerwaltung.neu(user);
					// if(userNew != null)
					session.setAttribute("title", "Erfolgreich registriert");
					session.setAttribute("message", "Sie haben sich hiermit erfolgreich als \"<b>" + userNew.getUsername() + "</b>\" registriert und k&ouml;nnen sich ab sofort mit Ihrem Passwort einloggen.<br />Herzlich willkommen! :)");
					response.sendRedirect("/success.jsp");
					return;
				} else {
					session.setAttribute("alert", "Ihre Passw&ouml;rter stimmen nicht &uuml;berein!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect("/?page=register");
					return;
				}
			} else {
				session.setAttribute("error", "Sie sind bereits registriert und eingeloggt!");
				response.sendRedirect("/error.jsp");
				return;
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect("/error.jsp");
		}
	}
}