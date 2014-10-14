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

import persistence.UserManager;
import entities.User;

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
		
		RequestDispatcher view = request.getRequestDispatcher(request.getContextPath()+"/error.jsp");
		
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
				
				// Cookie entfernen
				Cookie cookie = new Cookie("currentUser", "");
				cookie.setMaxAge(0);
				
				session.setAttribute("alert", "Sie haben sich erfolgreich ausgeloggt! Auf Wiedersehen. :)");
				response.sendRedirect(request.getContextPath()+"/index");
				return;
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
				if(UserManager.checkLogin(username, password)){
					User user =  UserManager.get(username);
					
					session.setAttribute("login", true);
					session.setAttribute("currentUser", user.getId());
					
					// Cookie setzen
					if(request.getParameter("cookie") != null){
						Cookie cookie = new Cookie("currentUser", String.valueOf(user.getId()));
						cookie.setMaxAge(30 * 24 * 60 * 60); // 30 Tage
						response.addCookie(cookie);
					}
					
					// Weiterleitung ohne Cookie
					boolean cookie_forward = false;
					if(request.getAttribute("cookie_forward") != null){
						cookie_forward = (Boolean) request.getAttribute("cookie_forward");
					}
					
					if(!cookie_forward){
						session.setAttribute("alert", "Sie haben sich erfolgreich eingeloggt! Herzlich willkommen, " + user.getName() + ".");
						response.sendRedirect(request.getContextPath()+"/index");
					} else {
						// direkte Weiterleitung zur Startseite, falls Cookie gefunden
						response.sendRedirect(request.getContextPath()+"/index");
					}
				} else {
					session.setAttribute("error", "Benutzername und Password stimmen nicht &uuml;berein!<br />Bitte versuchen Sie es erneut oder <a href=\"/?page=register\">registrieren</a> Sie sich, falls Sie noch kein Benutzerprofil angelegt haben.");
					response.sendRedirect(request.getContextPath()+"/error.jsp");
				}
			} else {
				session.setAttribute("error", "Sie sind bereits eingeloggt!");
				response.sendRedirect(request.getContextPath()+"/error.jsp");
			}
			
		// Registrierung (Aktion)
		} else if(mode.equals("register")){
			if(!login){
				User user = new User();
				
				if(username.length() > 0 && !UserManager.exists(username)){
					user.setName(username);
				} else {
					session.setAttribute("alert", "Der gew&auml;hlte Benutzername ist ung&uuml;ltig oder bereits vergeben! Bitte w&auml;hlen Sie einen anderen.");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()+"/?page=register");
					return;
				}
				
				user.setFirstName(request.getParameter("vorname"));
				user.setSecondName(request.getParameter("nachname"));
				
				String email = request.getParameter("email");
				if(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"	+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
					user.setEmail(email);						
				} else {
					session.setAttribute("alert", "Ihre E-Mail-Adresse ist ung&uuml;ltig! Bitte &uuml;berpr&uuml;fen Sie diese und versuchen Sie es erneut.");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()+"/?page=register");
					return;
				}
				
				String passwordRepeat = request.getParameter("passwordRepeat");
				if(password.length() > 0 && password.equals(passwordRepeat)){	
					user.setPassword(password);
				} else {
					session.setAttribute("alert", "Ihre Passw&ouml;rter stimmen nicht &uuml;berein oder sind leer!");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()+"/?page=register");
					return;
				}
				
				User userNew = UserManager.add(user);
				if(userNew != null){
					session.setAttribute("title", "Erfolgreich registriert");
					session.setAttribute("message", "Sie haben sich hiermit erfolgreich als \"<b>" + userNew.getName() + "</b>\" registriert und k&ouml;nnen sich ab sofort mit Ihrem Passwort einloggen.<br />Herzlich willkommen! :)");
					response.sendRedirect(request.getContextPath()+"/success.jsp");
					return;
				} else {
					session.setAttribute("alert", "Fehler bei der Erstellung des Profils! Bitte versuchen Sie es erneut.");
					session.setAttribute("alert_mode", "danger");
					response.sendRedirect(request.getContextPath()+"/?page=register");
					return;
				}
			} else {
				session.setAttribute("error", "Sie sind bereits registriert und eingeloggt!");
				response.sendRedirect(request.getContextPath()+"/error.jsp");
				return;
			}
		}
		
		// Fehler - kein mode angegeben
		else {
			session.setAttribute("error", "Ung&uuml;ltiger Modus!");
			response.sendRedirect(request.getContextPath()+"/error.jsp");
		}
	}
}