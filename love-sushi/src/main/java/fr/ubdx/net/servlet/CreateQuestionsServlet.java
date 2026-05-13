package fr.ubdx.net.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.ubdx.net.model.Question;
import fr.ubdx.net.model.QuestionStorage;

import java.io.IOException;

public class CreateQuestionsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String[] questions =
                request.getParameterValues("question");

        String[] choix1 =
                request.getParameterValues("choix1");

        String[] choix2 =
                request.getParameterValues("choix2");

        QuestionStorage storage =
                QuestionStorage.getInstance();

        storage.clearQuestions();

        for (int i = 0; i < questions.length; i++) {

            Question q = new Question(
                    questions[i],
                    choix1[i],
                    choix2[i]
            );

            storage.addQuestion(q);
        }

        response.sendRedirect(
                "AdminAnswersServlet"
        );
    }
}