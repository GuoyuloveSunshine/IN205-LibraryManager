package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 10L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService membreService = MembreService.getInstance();
		try {
			int idDuMembre = Integer.parseInt(request.getParameter("id"));
			Membre aMembre = membreService.getById(idDuMembre);
			request.setAttribute("idDuMembre",aMembre.getId());
			request.setAttribute("nomDuMembre",aMembre.getNom());
			request.setAttribute("prenomDuMembre",aMembre.getPrenom());
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_delete.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService membreService = MembreService.getInstance();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			membreService.delete(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		reponse.sendRedirect("/TP3Ensta/membre_list");
	}
}


