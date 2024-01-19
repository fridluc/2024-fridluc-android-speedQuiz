package com.fridluc.speedgame.Models;

public class Question {

    private String intitule;
    private boolean reponses;

    public Question(String intitule, boolean reponses) {
        this.intitule = intitule;
        this.reponses = reponses;
    }

    public String getIntitule() {
        return intitule;
    }

    public boolean getReponses() {
        return reponses;
    }
}
