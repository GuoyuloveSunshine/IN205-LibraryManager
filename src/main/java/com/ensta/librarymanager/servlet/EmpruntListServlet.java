package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;

@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet{
	private static final long serialVersionUID = 3L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException{
		EmpruntService empruntService = EmpruntService.getInstance();
		try {
			String showAll = "";
			if (request.getParameter("show") != null) {
				showAll = request.getParameter("show");
			}
			if(showAll.equals("all")) {
				request.setAttribute("totalEmprunt", empruntService.getList());
			}
			else {
				request.setAttribute("totalEmprunt", empruntService.getListCurrent());
			}
			
		}
		catch(ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp")
		.forward(request, reponse);
	}
}
