package fr.ubdx.net.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String target = request.getParameter("target");
        boolean browser = "browser".equalsIgnoreCase(target);
        boolean console = "console".equalsIgnoreCase(target);

        if (console) {
            System.out.println("=> [HelloServlet]");
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("mode console");
            }
        } else if (browser) {
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("mode navigator");
            }
        } else {
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("td5. Use ?target=console ou ?target=browser in the url");
            }
        }
    }
}
