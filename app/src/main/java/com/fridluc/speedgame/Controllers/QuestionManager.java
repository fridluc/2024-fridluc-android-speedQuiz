package com.fridluc.speedgame.Controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fridluc.speedgame.Models.Question;
import com.fridluc.speedgame.Models.SpeedGameSqLite;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionManager {

    //Pour qu'il n'y est qu'une seule instance de GameManager peut exister.
    private static QuestionManager instance;

    private int questionEnCoursIndex = 0;
    private ArrayList<Question> questionList;

    public QuestionManager(Context context){
        questionList = initQuestionList(context);
        Collections.shuffle(questionList);
    }

    /**
     * Méthode pour obtenir l'instance unique de GameManager.
     * @return L'instance
     */
    public static QuestionManager getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionManager(context);
        }
        return instance;
    }

    /**
     * Méthode pour vérifier si c'est la fin de la liste de questions.
     * @return true si c'est la fin de la liste, sinon false
     */
    public boolean endOfList() {
        return questionEnCoursIndex >= questionList.size();
    }

    /**
     * Charge une liste de question depuis la DB.
     * @param context Le contexte de l'application pour passer la query
     * @return Une arraylist charger de Question
     */
    private ArrayList<Question> initQuestionList(Context context){
        ArrayList<Question> listQuestion = new ArrayList<>();
        SpeedGameSqLite helper = new SpeedGameSqLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true,"quiz",new String[]{"idQuiz","question","reponse"},null,null,null,null,"idquiz",null);

        // Ajoutez des logs de débogage ici
        Log.d("QuestionManager", "Number of rows in cursor: " + cursor.getCount());

        while(cursor.moveToNext()){
            listQuestion.add(new Question(cursor));
        }
        cursor.close();
        db.close();

        return listQuestion;
    }

    public int getReponseQuestionEnCours() {
        return questionList.get(questionEnCoursIndex -1).getReponses();
    }

    public ArrayList<Question> getQuestions() {
        return questionList;
    }

    public void setQuestionEnCoursIndex(int index) {
        this.questionEnCoursIndex = index;
    }

    public String recevoirQuestion() {
        questionEnCoursIndex++;
        return getQuestions().get(questionEnCoursIndex-1).getIntitule();
    }


}