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
import java.util.Collections;

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
    private final int JOUEUR1 = 1;
    private final int JOUEUR2 = 2;

    private ArrayList<Question> questionList;

    private boolean appuyer = false;

    //private boolean quelquUnARepondu = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        BT_Redémarrer = findViewById(R.id.btn_restart);
        BT_Menu = findViewById(R.id.btn_menu);
        BT_Joueur1 = findViewById(R.id.hitButtonPlayer1);
        BT_Joueur2 = findViewById(R.id.hitButtonPlayer2);

        BT_Joueur1.setEnabled(false);
        BT_Joueur2.setEnabled(false);

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

        questionList = qManager.getQuestions();

        mélangerQuestions();
    }

    @Override
    protected void onStart() {
        super.onStart();

        startQuestionIterative();
        BT_Joueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementerScoreAvecBouton(JOUEUR1);
                questionSuivante();
                appuyer = true;
            }
        });

        BT_Joueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementerScoreAvecBouton(JOUEUR2);
                questionSuivante();
                appuyer = true;
            }
        });

        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retournerAMainActivity();
            }
        });

        BT_Redémarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.INVISIBLE);
                ligneSeparation.setVisibility(View.VISIBLE);

                scoreJoueur1.setText(String.valueOf(scoreJoueur1Value = 0));
                scoreJoueur2.setText(String.valueOf(scoreJoueur2Value = 0));

                qManager.setQuestionEnCoursIndex(0);
                handler = null;

                BT_Joueur1.setEnabled(true);
                BT_Joueur2.setEnabled(true);
                appuyer = false;

                mélangerQuestions();

                startQuestionIterative();
                questionSuivante();
            }
        });
    }

    private void incrementerScoreAvecBouton(int joueur) {
        if (qManager.getReponseQuestionEnCours() == 1) {
            if (joueur == JOUEUR1) {
                scoreJoueur1Value++;
            } else if (joueur == JOUEUR2) {
                scoreJoueur2Value++;
            }
        } else {
            if (joueur == JOUEUR1) {
                scoreJoueur1Value = Math.max(0, scoreJoueur1Value - 1);
            } else if (joueur == JOUEUR2) {
                scoreJoueur2Value = Math.max(0, scoreJoueur2Value - 1);
            }
        }
        scoreJoueur1.setText(String.valueOf(scoreJoueur1Value));
        scoreJoueur2.setText(String.valueOf(scoreJoueur2Value));
    }

    private void incrementerScoreSansBouton() {
        if (qManager.getIndex() > 0 && !appuyer) {
            if (qManager.getReponseQuestionEnCours() == 0) {
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

    private void startQuestionIterative(){
        if (handler == null)
            handler = new Handler();

        questionRunnable = new Runnable() {
            @Override
            public void run() {
                if (qManager.endOfList()) {
                    resultatFinPartie();
                    handler.removeCallbacks(this);
                } else {
                    // Réactiver les boutons après avoir affiché la question
                    BT_Joueur1.setEnabled(true);
                    BT_Joueur2.setEnabled(true);

                    incrementerScoreSansBouton();
                    appuyer = false;

                    String questionEnCours = qManager.recevoirQuestion(questionList);
                    questionJoueur1.setText(questionEnCours);
                    questionJoueur2.setText(questionEnCours);

                    handler.postDelayed(this, 5000);
                }
            }
        };
        handler.postDelayed(questionRunnable, 5000);
    }

    private void questionSuivante() {
        appuyer = false;
        if (qManager.endOfList()) {
            resultatFinPartie();
        } else {
            String questionEnCours = qManager.recevoirQuestion(questionList);
            questionJoueur1.setText(questionEnCours);
            questionJoueur2.setText(questionEnCours);
        }
        long delay = 5000;
        handler.removeCallbacks(questionRunnable);
        handler.postDelayed(questionRunnable, delay);
    }

    private void mélangerQuestions() {
        Collections.shuffle(qManager.getQuestions());
    }

    private void retournerAMainActivity() {
        finish();
    }
}
