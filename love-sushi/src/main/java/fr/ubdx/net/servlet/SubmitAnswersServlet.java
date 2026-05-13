package fr.ubdx.net.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.ubdx.net.model.Answer;
import fr.ubdx.net.model.AnswerStorage;
import fr.ubdx.net.model.QuestionStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubmitAnswersServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String utilisateurId =
                request.getParameter(
                        "utilisateur_id"
                );

        QuestionStorage questionStorage =
                QuestionStorage.getInstance();

        List<String> reponses =
                new ArrayList<>();

        for (int i = 0;
             i < questionStorage.getQuestions().size();
             i++) {

            String reponse =
                    request.getParameter(
                            "q" + i
                    );

            reponses.add(reponse);
        }

        Answer answer = new Answer(
                utilisateurId,
                reponses
        );

        AnswerStorage.getInstance()
                     .addAnswer(answer);

        response.sendRedirect(
                "index.jsp"
        );
    }
}