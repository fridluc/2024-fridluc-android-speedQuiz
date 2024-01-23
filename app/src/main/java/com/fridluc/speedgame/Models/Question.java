package com.fridluc.speedgame.Models;

import android.database.Cursor;

public class Question {

    private String intitule;
    private int reponses;

    public Question(String intitule, int reponses) {
        this.intitule = intitule;
        this.reponses = reponses;
    }

    public Question(Cursor cursor) {
        intitule = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        reponses = cursor.getInt(cursor.getColumnIndexOrThrow("reponse"));
    }

    public String getIntitule() {
        return intitule;
    }

    public int getReponses() {
        return reponses;
    }
}
