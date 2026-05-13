package fr.ubdx.net.model;

public class Question {

    private String question;
    private String choix1;
    private String choix2;

    public Question(String question,
                    String choix1,
                    String choix2) {

        this.question = question;
        this.choix1 = choix1;
        this.choix2 = choix2;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoix1() {
        return choix1;
    }

    public String getChoix2() {
        return choix2;
    }
}