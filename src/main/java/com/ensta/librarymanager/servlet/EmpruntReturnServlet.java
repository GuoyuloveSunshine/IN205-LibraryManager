package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet{
	private static final long serialVersionUID = 4L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		EmpruntService empruntService = EmpruntService.getInstance();
		try {
			request.setAttribute("currEmprunt", empruntService.getListCurrent());
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp")
		.forward(request, reponse);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		EmpruntService empruntService = EmpruntService.getInstance();
		try {
			if (request.getParameter("id") == null) {
				throw new ServletException("Pas de Emprunt.");
			}
			else {
				empruntService.returnBook(Integer.parseInt(request.getParameter("id")));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			request.setAttribute("currEmprunt", empruntService.getListCurrent());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		reponse.sendRedirect("/TP3Ensta/emprunt_list");
	}
}
