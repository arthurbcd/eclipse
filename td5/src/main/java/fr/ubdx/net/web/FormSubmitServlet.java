package fr.ubdx.net.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormSubmitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        FormData formData = new FormData(name, email, message);
        System.out.println("=> [FormSubmitServlet] Dados recebidos:");
        System.out.println("   nome=" + formData.getName());
        System.out.println("   email=" + formData.getEmail());
        System.out.println("   mensagem=" + formData.getMessage());

        request.setAttribute("formData", formData);
        request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("form.html");
    }
}
