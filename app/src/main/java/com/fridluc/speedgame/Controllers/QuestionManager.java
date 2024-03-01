package com.fridluc.speedgame.Controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fridluc.speedgame.Models.Question;
import com.fridluc.speedgame.Models.SpeedGameSqLite;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionManager {

    //Pour qu'il n'y est qu'une seule instance de GameManager peut exister.
    private static QuestionManager instance;

    private int questionEnCoursIndex = 0;
    private ArrayList<Question> questionList;

    Context context;

    /**
     * Constructeur de la classe QuestionManager.
     * Initialise la liste des questions à partir du contexte donné.
     * @param context Le contexte de l'application.
     */
    public QuestionManager(Context context, float nombreQuestion){
        questionList = initQuestionList(context, nombreQuestion);
        this.context = context;
    }

    /**
     * Charge une liste de question depuis la DB.
     * @param context Le contexte de l'application pour passer la query
     * @return Une arraylist charger de Question
     */
    private ArrayList<Question> initQuestionList(Context context, float nombreQuestion){
        ArrayList<Question> listQuestion = new ArrayList<>();
        SpeedGameSqLite helper = new SpeedGameSqLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true,"quiz",new String[]{"idQuiz","question","reponse"},null,null,null,null,"idquiz", String.valueOf(nombreQuestion));

        while(cursor.moveToNext()){
            listQuestion.add(new Question(cursor));
        }
        cursor.close();
        db.close();

        return listQuestion;
    }

    /**
     * Reçois la question de la fonction précédente
     * @return L'initialisation de la liste de question
     */
    public ArrayList<Question> getQuestions(float nombreQuestion) {
        return initQuestionList(context, nombreQuestion);
    }

    /**
     * Mélange la liste de question
     * @param questionList Liste de question à mélanger
     */
    public void melangerQuestion(ArrayList<Question> questionList) {
        Collections.shuffle(questionList);
    }

    /**
     * Permet de recevoir la réponse de la question en cours
     * @return La réponse de la question affichée
     */
    public int getReponseQuestionEnCours(ArrayList<Question> questionList) {
        return questionList.get(questionEnCoursIndex-1).getReponses();
    }

    /**
     * Met en place l'index de la question
     * @param index Index de la question
     */
    public void setQuestionEnCoursIndex(int index) {
        this.questionEnCoursIndex = index;
    }

    /**
     * Reçois l'index de la question en cours
     * @return L'index de la question en cours
     */
    public int getIndex() {
        return questionEnCoursIndex;
    }

    /**
     * Méthode pour recevoir la question qui doit être afficé
     * @param questionList La liste des questions à afficher
     * @return L'intitulé de la question
     */
    public String recevoirQuestion(ArrayList<Question> questionList) {
        questionEnCoursIndex++;
        return questionList.get(questionEnCoursIndex-1).getIntitule();
    }

    /**
     * Méthode pour vérifier si c'est la fin de la liste de questions.
     * @return true si c'est la fin de la liste, sinon false
     */
    public boolean endOfList() {
        return questionEnCoursIndex >= questionList.size();
    }

}
