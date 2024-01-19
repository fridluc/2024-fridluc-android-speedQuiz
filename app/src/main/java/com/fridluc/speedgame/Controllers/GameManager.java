package com.fridluc.speedgame.Controllers;

import com.fridluc.speedgame.Models.Question;
import com.fridluc.speedgame.Models.SpeedQuizSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    //Pour qu'il n'y est qu'une seule instance de GameManager peut exister.
    private static GameManager instance;
    private final SpeedQuizSQLiteOpenHelper dbHelper;

    /**
     * Constructeur privé pour garantir qu'une seule instance de GameManager est créée.
     */
    private GameManager() {
        dbHelper = new SpeedQuizSQLiteOpenHelper();
    }

    /**
     * Méthode pour obtenir l'instance unique de GameManager.
     * @return L'instance
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Méthode pour récupérer la liste des questions depuis la base de données.
     * @return La liste de question
     */
    public ArrayList<Question> getQuestions() {
        return (ArrayList<Question>) dbHelper.getQuestions();
    }

    /**
     * Méthode pour mélanger aléatoirement les questions.
     */
    public void melangerQuestions() {
        // Utilisation de la méthode shuffle de la classe Collections pour mélanger la liste des questions.
        Collections.shuffle(getQuestions());
    }
}