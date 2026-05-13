package fr.ubdx.net.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.ubdx.net.model.QuestionStorage;

import java.io.IOException;

public class QuestionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        QuestionStorage storage =
                QuestionStorage.getInstance();

        request.setAttribute(
                "questions",
                storage.getQuestions()
        );

        request.getRequestDispatcher(
                "/front/questions.jsp"
        ).forward(request, response);
    }
}