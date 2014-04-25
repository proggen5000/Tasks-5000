package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import administration.AufgabenVerwaltung;
import administration.TeamVerwaltung;

@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Index() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean login = false;
		if(request.getSession().getAttribute("login") != null){
			login = (boolean) request.getSession().getAttribute("login");
		}
		
		String page = request.getParameter("page");
		
		RequestDispatcher view = request.getRequestDispatcher("/jsp/sites/index.jsp");
		
		if(page != null){
			view = request.getRequestDispatcher("/jsp/sites/" + page + ".jsp");
		}
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
		// TODO Auto-generated method stub
	}

}
