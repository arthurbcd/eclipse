package fr.ubdx.net.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.ubdx.net.model.AnswerStorage;
import fr.ubdx.net.model.QuestionStorage;

import java.io.IOException;

public class AdminAnswersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "questions",
                QuestionStorage.getInstance()
                               .getQuestions()
        );

        request.setAttribute(
                "answers",
                AnswerStorage.getInstance()
                             .getAnswers()
        );

        request.getRequestDispatcher(
                "/backoffice/adminAnswers.jsp"
        ).forward(request, response);
    }
}