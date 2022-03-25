package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet{
	private static final long serialVersionUID = 2L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService membreService = MembreService.getInstance();
		LivreService livreService = LivreService.getInstance();
		try {
			request.setAttribute("listeLivreDisponible", livreService.getListDispo());
			request.setAttribute("listeMembreDisponible", membreService.getListMembreEmpruntPossible());
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		EmpruntService aEmpruntService = EmpruntService.getInstance();
		try {
			int idLivre = Integer.parseInt(request.getParameter("idLivre"));
			int idMembre = Integer.parseInt(request.getParameter("idMembre"));
			aEmpruntService.create(idMembre, idLivre, LocalDate.now());
//			for (Emprunt aEmprunt: aEmpruntService.getListCurrent()){
//				System.out.println(aEmprunt.toString());
//			}
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		reponse.sendRedirect("/TP3Ensta/emprunt_list");
	}
}
