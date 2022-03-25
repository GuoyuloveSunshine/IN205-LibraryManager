package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet{
	private static final long serialVersionUID = 9L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_add.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService membreService = MembreService.getInstance();
		if (request.getParameter("nom") == null || request.getParameter("nom")=="") {
			throw new ServletException("Nom is empty");
		}
		try {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String adresse = request.getParameter("adresse");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			membreService.create(nom, prenom, adresse,email,telephone);
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		reponse.sendRedirect("/TP3Ensta/membre_list");
	}
}
