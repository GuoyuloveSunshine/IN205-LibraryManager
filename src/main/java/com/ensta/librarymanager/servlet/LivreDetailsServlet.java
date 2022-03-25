package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;

@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet{
	private static final long serialVersionUID = 7L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		LivreService livreService = LivreService.getInstance();
		EmpruntService empruntService = EmpruntService.getInstance();
		try {
			int idDuLivre = Integer.parseInt(request.getParameter("id"));
			Livre aLivre = livreService.getById(idDuLivre);
			request.setAttribute("idDuLivre",aLivre.getId());
			request.setAttribute("titreDuLivre",aLivre.getTitre());
			request.setAttribute("auteurDuLivre",aLivre.getAuteur());
			request.setAttribute("isbnDuLivre",aLivre.getIsbn());
			request.setAttribute("listStatActuel",empruntService.getListCurrentByLivre(idDuLivre));
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_details.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		LivreService livreService = LivreService.getInstance();
		if (request.getParameter("titre") == null || request.getParameter("titre")=="") {
			throw new ServletException("Title is empty");
		}
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Livre aLivre = livreService.getById(id);
			aLivre.setAuteur(request.getParameter("auteur"));
			aLivre.setIsbn(request.getParameter("isbn"));
			aLivre.setTitre(request.getParameter("titre"));
			livreService.update(aLivre);
			reponse.sendRedirect("/TP3Ensta/livre_details?id=" + id);
		} catch (NumberFormatException | ServiceException e) {
			e.printStackTrace();
		}
	}
}
