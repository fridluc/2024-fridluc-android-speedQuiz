package com.fridluc.speedgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fridluc.speedgame.Controllers.QuestionManager;
import com.fridluc.speedgame.Models.Question;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    Handler handler;
    Runnable questionRunnable = null;
    QuestionManager qManager;

    private MaterialButton BT_Redémarrer;
    private MaterialButton BT_Menu;
    private MaterialButton BT_Joueur1;
    private MaterialButton BT_Joueur2;

    private TextView questionJoueur1;
    private TextView questionJoueur2;
    private TextView scoreJoueur1;
    private TextView scoreJoueur2;

    private View relativeLayout;
    private View ligneSeparation;
    private int scoreJoueur1Value = 0;
    private int scoreJoueur2Value = 0;

    private ArrayList<Question> questionList;

    private boolean appuyer = false;
    private boolean firstClick = true;

    private final int DELAY = 5;
    private float delayPartie;
    private int nombreQuestion;
    private final int NOMBRE_QUESTION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        BT_Redémarrer = findViewById(R.id.btn_restart);
        BT_Menu = findViewById(R.id.btn_menu);
        BT_Joueur1 = findViewById(R.id.hitButtonPlayer1);
        BT_Joueur2 = findViewById(R.id.hitButtonPlayer2);

        TextView TV_Joueur2 = findViewById(R.id.nom_joueur2);
        TextView TV_Joueur1 = findViewById(R.id.nom_joueur1);
        scoreJoueur1 = findViewById(R.id.TV_Nbre1);
        scoreJoueur2 = findViewById(R.id.TV_Nbre2);

        questionJoueur1 = findViewById(R.id.questionJoueur1);
        questionJoueur2 = findViewById(R.id.questionJoueur2);

        relativeLayout = findViewById(R.id.relativeLayout);
        ligneSeparation = findViewById(R.id.ligne_de_separation);

        relativeLayout.setVisibility(View.INVISIBLE);

        qManager = new QuestionManager(this);

        Intent activityGame = getIntent();
        String joueur1 = activityGame.getStringExtra("Joueur1");
        TV_Joueur1.setText(joueur1);
        String joueur2 = activityGame.getStringExtra("Joueur2");
        TV_Joueur2.setText(joueur2);

        Intent activityMain = getIntent();
        delayPartie = activityMain.getFloatExtra("delayPartie", DELAY);
        nombreQuestion = activityMain.getIntExtra("nombreQuestion", NOMBRE_QUESTION);
        questionList = qManager.getQuestions();
    }

    @Override
    protected void onStart() {
        super.onStart();

        qManager.melangerQuestion(questionList);

        startQuestionIterative();

        // Actions lorsqu'un joueur appuie sur son bouton durant le jeu
        BT_Joueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstClick) {
                    scoreJoueur1Value = ajouterPointJoueur(scoreJoueur1Value, scoreJoueur1);
                    appuyer = true;
                }
                questionSuivante();
                firstClick = false;
            }
        });

        BT_Joueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstClick) {
                    scoreJoueur2Value = ajouterPointJoueur(scoreJoueur2Value, scoreJoueur2);
                    appuyer = true;
                }
                questionSuivante();
                firstClick = false;
            }
        });

        //Permet de retourner à l'activité principale
        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retournerAMainActivity();
            }
        });

        // Bouton qui permet de redémarrer la partie
        // avec les question dans un autre ordre
        BT_Redémarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.INVISIBLE);
                ligneSeparation.setVisibility(View.VISIBLE);

                scoreJoueur1.setText(String.valueOf(scoreJoueur1Value = 0));
                scoreJoueur2.setText(String.valueOf(scoreJoueur2Value = 0));

                qManager.setQuestionEnCoursIndex(0);

                BT_Joueur1.setEnabled(true);
                BT_Joueur2.setEnabled(true);
                appuyer = false;

                qManager.melangerQuestion(questionList);

                questionSuivante();
            }
        });
    }

    /**
     * Méthode pour ajouter un point au joueur selon la réponse.
     * @param playerPoint Le score actuel du joueur.
     * @param TV_player   Le TextView affichant le score du joueur.
     * @return Le nouveau score du joueur.
     */
    private int ajouterPointJoueur(int playerPoint, TextView TV_player) {
        if (qManager.getReponseQuestionEnCours(questionList) == 1) {
            playerPoint++;
        } else {
            playerPoint = Math.max(0, scoreJoueur1Value - 1);
        }
        TV_player.setText(String.valueOf(playerPoint));
        return playerPoint;
    }

    /**
     *Méthode pour ajouter les points en fonction des réponses.
     */
    private void ajouterPoint() {
        if (qManager.getIndex() > 0 && !appuyer &&
            qManager.getIndex() <= questionList.size() && !qManager.endOfList()) {
            if (qManager.getReponseQuestionEnCours(questionList) == 0) {
                scoreJoueur1Value++;
                scoreJoueur2Value++;
            } else {
                scoreJoueur1Value = Math.max(0, scoreJoueur1Value - 1);
                scoreJoueur2Value = Math.max(0, scoreJoueur2Value - 1);
            }
        }
        scoreJoueur1.setText(String.valueOf(scoreJoueur1Value));
        scoreJoueur2.setText(String.valueOf(scoreJoueur2Value));
    }

    /**
     * Méthode pour afficher le résultat final de la partie.
     */
    private void resultatFinPartie() {
        BT_Joueur1.setEnabled(false);
        BT_Joueur2.setEnabled(false);

        relativeLayout.setVisibility(View.VISIBLE);
        ligneSeparation.setVisibility(View.INVISIBLE);

        if (scoreJoueur1Value < scoreJoueur2Value) {
            questionJoueur1.setText(R.string.loose);
            questionJoueur2.setText(R.string.win);
        } else if (scoreJoueur1Value > scoreJoueur2Value) {
            questionJoueur1.setText(R.string.win);
            questionJoueur2.setText(R.string.loose);
        } else {
            questionJoueur1.setText(R.string.equals);
            questionJoueur2.setText(R.string.equals);
        }
    }

    /**
     * Méthode pour démarrer l'itération des questions.
     */
    private void startQuestionIterative(){
        if (handler == null)
            handler = new Handler();

        questionRunnable = new Runnable() {
            @Override
            public void run() {
                if (qManager.endOfList()) {
                    ajouterPoint();
                    resultatFinPartie();
                    handler.removeCallbacks(this);
                } else {
                    ajouterPoint();
                    appuyer = false;

                    String questionEnCours = qManager.recevoirQuestion(questionList);
                    questionJoueur1.setText(questionEnCours);
                    questionJoueur2.setText(questionEnCours);
                    firstClick = false;
                    handler.postDelayed(this, (long) (delayPartie * 1000));
                }
            }
        };
        handler.postDelayed(questionRunnable, (long) (delayPartie * 1000));
    }

    /**
     * Méthode pour passer à la question suivante.
     */
    private void questionSuivante() {
        if (qManager.endOfList()) {
            resultatFinPartie();
        } else {
            String questionEnCours = qManager.recevoirQuestion(questionList);
            questionJoueur1.setText(questionEnCours);
            questionJoueur2.setText(questionEnCours);
            appuyer = false;
        }
        handler.removeCallbacks(questionRunnable);
        handler.postDelayed(questionRunnable, DELAY);
    }

    /**
     * Méthode pour retourner à l'activité principale.
     */
    private void retournerAMainActivity() {
        finish();
    }
}
