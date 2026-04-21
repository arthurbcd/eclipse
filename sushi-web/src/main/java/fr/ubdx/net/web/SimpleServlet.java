package fr.ubdx.net.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Exibe o texto na console padrão do Eclipse
        System.out.println("=> [SimpleServlet] Requisição recebida! Imprimindo na saída padrão.");

        // Responde ao navegador também
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Veja a console do Eclipse! A servlet imprimiu um texto la.");
    }
}
