package fr.ubdx.net.model;

import java.util.ArrayList;
import java.util.List;

public class AnswerStorage {

    private static AnswerStorage instance;

    private List<Answer> answers;

    private AnswerStorage() {

        answers = new ArrayList<>();
    }

    public static AnswerStorage getInstance() {

        if (instance == null) {
            instance = new AnswerStorage();
        }

        return instance;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}