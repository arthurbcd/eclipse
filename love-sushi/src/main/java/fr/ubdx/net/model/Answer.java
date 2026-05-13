package fr.ubdx.net.model;

import java.util.List;

public class Answer {

    private String utilisateurId;

    private List<String> reponses;

    public Answer(String utilisateurId,
                  List<String> reponses) {

        this.utilisateurId = utilisateurId;
        this.reponses = reponses;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public List<String> getReponses() {
        return reponses;
    }
}