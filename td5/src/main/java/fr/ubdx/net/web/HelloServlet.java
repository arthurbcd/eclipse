package fr.ubdx.net.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Exibe o texto na saída padrão do Eclipse
        System.out.println("=> [HelloServlet] Requisição recebida! Texto impresso na saída padrão.");

        // Exibe o texto também no navegador
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("Olá do projeto td5! A servlet imprimiu um texto na saída padrão e no navegador.");
        }
    }
}
