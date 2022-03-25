package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.LivreService;

@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 8L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		LivreService livreService = LivreService.getInstance();
		try {
			int idDuLivre = Integer.parseInt(request.getParameter("id"));
			Livre aLivre = livreService.getById(idDuLivre);
			request.setAttribute("idDuLivre",aLivre.getId());
			request.setAttribute("titreDuLivre",aLivre.getTitre());
			request.setAttribute("auteurDuLivre",aLivre.getAuteur());
			request.setAttribute("isbnDuLivre",aLivre.getIsbn());
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_delete.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		LivreService livreService = LivreService.getInstance();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			livreService.delete(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		reponse.sendRedirect("/TP3Ensta/livre_list");
	}
}


