package fr.ubdx.net.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/commandes")
public class PizzaRestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestionCommandes gestion = GestionCommandes.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        List<Commande> commandes = gestion.getListeCommande();
        
        out.print(toJson(commandes));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pizzaNom = request.getParameter("pizza");
        String quantiteStr = request.getParameter("quantite");

        if (pizzaNom != null && quantiteStr != null) {
            try {
                int quantite = Integer.parseInt(quantiteStr);
                Pizza pizza = new Pizza();
                pizza.setNom(pizzaNom);
                
                Commande cmd = new Commande();
                cmd.setPizza(pizza);
                cmd.setQuantite(quantite);
                
                gestion.addCommande(cmd);
                
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                response.getWriter().print("{\"status\":\"success\", \"id\":" + cmd.getId() + "}");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantité invalide");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants");
        }
    }

    private String toJson(List<Commande> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            Commande c = list.get(i);
            sb.append("{");
            sb.append("\"id\":").append(c.getId()).append(",");
            sb.append("\"pizza\":\"").append(c.getPizza().getNom()).append("\",");
            sb.append("\"quantite\":").append(c.getQuantite());
            sb.append("}");
            if (i < list.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}