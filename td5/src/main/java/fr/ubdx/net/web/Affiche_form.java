package fr.ubdx.net.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ubdx.net.web.Commande;
import fr.ubdx.net.web.Pizza;
import fr.ubdx.net.web.GestionCommandes;

/**
 * Servlet implementation class Affiche_form
 */
@WebServlet("/Affiche_form")
public class Affiche_form extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
 	
   private GestionCommandes gestComm;
   
   
    @Override
public void init() throws ServletException {
	// TODO Auto-generated method stub
	super.init();
	gestComm = GestionCommandes.getInstance();
}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Affiche_form() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("type").equals("list")) {
			request.setAttribute("liste", gestComm.getListeCommande());
			this.getServletContext().getRequestDispatcher("/WEB-INF/Affiche_liste.jsp" ).include(request, response);
		}
		else if (request.getParameter("type").equals("del")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Commande c = gestComm.getCommande(id+"");
			gestComm.delCommande(c);
			request.setAttribute("liste", gestComm.getListeCommande());
			this.getServletContext().getRequestDispatcher("/WEB-INF/Affiche_liste.jsp" ).include(request, response);
		}
		else {
			PrintWriter out = response.getWriter();
			out.println("bad type");
		}
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Pizza p = new Pizza();
		p.setNom(request.getParameter("nom"));
		Commande c = new Commande();
		c.setPizza(p);
		String quantite = request.getParameter("quantite");
		c.setQuantite(Integer.parseInt(quantite));
		gestComm.addCommande(c);

		request.setAttribute("commande", c);
		this.getServletContext().getRequestDispatcher("/WEB-INF/Affiche_pizza.jsp" ).include(request, response);
	}

}
