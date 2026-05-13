package fr.ubdx.net.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionStorage {

    private static QuestionStorage instance;

    private List<Question> questions;

    private QuestionStorage() {

        questions = new ArrayList<>();
    }

    public static QuestionStorage getInstance() {

        if (instance == null) {
            instance = new QuestionStorage();
        }

        return instance;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void clearQuestions() {
        questions.clear();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }
}