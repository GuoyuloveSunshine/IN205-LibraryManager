package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet{
	private static final long serialVersionUID = 11L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService membreService = MembreService.getInstance();
		EmpruntService empruntService = EmpruntService.getInstance();
		try {
			int idDuMembre = Integer.parseInt(request.getParameter("id"));
			Membre aMembre = membreService.getById(idDuMembre);
			request.setAttribute("idDuMembre",aMembre.getId());
			request.setAttribute("nomDuMembre",aMembre.getNom());
			request.setAttribute("prenomDuMembre",aMembre.getPrenom());
			request.setAttribute("adresseDuMembre",aMembre.getAdresse());
			request.setAttribute("emailDuMembre",aMembre.getEmail());
			request.setAttribute("telephoneDuMembre",aMembre.getTelephone());
			request.setAttribute("abonnement",aMembre.getAbonnement().toString());
			request.setAttribute("listStatActuel",empruntService.getListCurrentByMembre(idDuMembre));
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		MembreService membreService = MembreService.getInstance();
		if (request.getParameter("nom") == null || request.getParameter("nom")=="") {
			throw new ServletException("Nom is empty");
		}
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Membre aMembre = membreService.getById(id);
			aMembre.setNom(request.getParameter("nom"));
			aMembre.setPrenom(request.getParameter("prenom"));
			aMembre.setAdresse(request.getParameter("adresse"));
			aMembre.setEmail(request.getParameter("email"));
			aMembre.setTelephone(request.getParameter("telephone"));
			String ab = request.getParameter("abonnement");
			aMembre.setAbonnement(Abonnement.valueof(ab));
			membreService.update(aMembre);
			reponse.sendRedirect("/TP3Ensta/membre_details?id=" + id);
		} catch (NumberFormatException | ServiceException e) {
			e.printStackTrace();
		}
	}
}
