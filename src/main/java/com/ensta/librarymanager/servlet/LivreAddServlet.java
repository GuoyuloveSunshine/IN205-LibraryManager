package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.LivreService;

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet{
	private static final long serialVersionUID = 5L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
//		MembreService aMembreService = MembreService.getIntance();
//		LivreService aLivreService = LivreService.getIntance();
//		try {
//			request.setAttribute("listeLivreDisponible", aLivreService.getListDispo());
//			request.setAttribute("listeMembreDisponible", aMembreService.getListMembreEmpruntPossible());
//		}
//		catch(ServiceException e) {
//			e.printStackTrace();
//		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_add.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		LivreService livreService = LivreService.getInstance();
		if (request.getParameter("titre") == null || request.getParameter("titre")=="") {
			throw new ServletException("Title is empty");
		}
		try {
			String titre = request.getParameter("titre");
			String auteur = request.getParameter("auteur");
			String isbn = request.getParameter("isbn");
			livreService.create(titre, auteur, isbn);
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		reponse.sendRedirect("/TP3Ensta/livre_list");
	}
}
