package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;



@WebServlet("/dashboard")
public class HomeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService aMembreService = MembreService.getInstance();
		LivreService aLivreService = LivreService.getInstance();
		EmpruntService aEmpruntService = EmpruntService.getInstance();
		try {
			request.setAttribute("nMembre", aMembreService.count());
			request.setAttribute("nLivre", aLivreService.count());
			request.setAttribute("nEmprunt", aEmpruntService.count());
			request.setAttribute("currEmprunt", aEmpruntService.getListCurrent());
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/dashboard.jsp")
		.forward(request, reponse);
	}
}
