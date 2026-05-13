package fr.ubdx.net.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Affiche_data
 */
@WebServlet("/Affiche_data")
public class Affiche_data extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    /**
     * Default constructor. 
     */
    public Affiche_data() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String nom = request.getParameter("nom");
		if ( (nom == null) || ("".equals(nom)) ) {
			nom = "vide";
		}
		PrintWriter out = response.getWriter();
		out.println("Bonjour Monsieur "+nom);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
