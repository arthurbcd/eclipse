package tds;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tds.metier.Personne;

/**
 * Servlet implementation class RecuperationParametre
 */
@WebServlet("/RecuperationParametre")
public class RecuperationParametre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperationParametre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = ""+request.getParameter("nom");
		String prenom = ""+request.getParameter("prenom");
		if ("console".equals(request.getParameter("TypeAffichage"))) {
			System.out.println(nom);
			System.out.println(prenom);
		}

		Personne p = new Personne();
		p.setNom(nom);
		p.setPrenom(prenom); 
		
		request.setAttribute("personne", p);
		RequestDispatcher resDis = request.getRequestDispatcher("Affichage.jsp");
		resDis.forward(request, response);
				
	}

}
