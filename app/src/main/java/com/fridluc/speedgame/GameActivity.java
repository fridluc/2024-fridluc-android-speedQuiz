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

    // Initialisation des variables globales
    private MaterialButton BT_Redémarrer;
    private MaterialButton BT_Menu;
    private MaterialButton BT_Joueur1;
    private MaterialButton BT_Joueur2;

    private TextView TV_Joueur1;
    private TextView TV_Joueur2;
    private TextView questionJoueur1;
    private TextView questionJoueur2;
    private TextView scoreJoueur1;
    private TextView scoreJoueur2;

    private View relativeLayout;
    private View ligneSeparation;
    private ArrayList<Question> questions;
    private int scoreJoueur1Value = 0;
    private int scoreJoueur2Value = 0;
    private final int JOUEUR1 = 1;
    private final int JOUEUR2 = 2;

    Handler handler;
    Runnable questionRunnable = null;
    QuestionManager qManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // Affecte les ID aux variables
        BT_Redémarrer = findViewById(R.id.btn_restart);
        BT_Menu = findViewById(R.id.btn_menu);
        BT_Joueur1 = findViewById(R.id.hitButtonPlayer1);
        BT_Joueur2 = findViewById(R.id.hitButtonPlayer2);

        TV_Joueur2 = findViewById(R.id.nom_joueur2);
        TV_Joueur1 = findViewById(R.id.nom_joueur1);
        scoreJoueur1 = findViewById(R.id.TV_Nbre1);
        scoreJoueur2 = findViewById(R.id.TV_Nbre2);

        questionJoueur1 = findViewById(R.id.questionJoueur1);
        questionJoueur2 = findViewById(R.id.questionJoueur2);

        relativeLayout = findViewById(R.id.relativeLayout);
        ligneSeparation = findViewById(R.id.ligne_de_separation);

        relativeLayout.setVisibility(View.INVISIBLE);

        // Récupère les noms des joueurs
        Intent activityGame = getIntent();
        String joueur1 = activityGame.getStringExtra("Joueur1");
        TV_Joueur1.setText(joueur1);
        String joueur2 = activityGame.getStringExtra("Joueur2");
        TV_Joueur2.setText(joueur2);

        // Récupère les questions, les mélange et affiche la première
        questions = QuestionManager.getInstance(this).getQuestions();

        qManager = new QuestionManager(this);

        Collections.shuffle(questions);
    }

    @Override
    protected void onStart() {
        super.onStart();

        startQuestionIterative();

        BT_Joueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestionIterative();
                int reponseActuelle = qManager.getReponseQuestionEnCours();
                incrementerScoreJoueur(JOUEUR1, reponseActuelle);
            }
        });

        BT_Joueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestionIterative();
                int reponseActuelle = qManager.getReponseQuestionEnCours();
                incrementerScoreJoueur(JOUEUR2, reponseActuelle);
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

                BT_Joueur1.setEnabled(true);
                BT_Joueur2.setEnabled(true);

                qManager.setQuestionEnCoursIndex(0);

                Collections.shuffle(questions);

                startQuestionIterative();
            }
        });
    }

    private void incrementerScoreJoueur(int joueur, int reponse) {
        if (joueur == JOUEUR1) {
            if (reponse == 1) {
                // Réponse vraie et le joueur appuie, il gagne un point
                scoreJoueur1Value++;
            } else {
                // Réponse fausse et le joueur appuie, il perd un point
                scoreJoueur1Value = Math.max(0, scoreJoueur1Value - 1);
            }
            scoreJoueur1.setText(String.valueOf(scoreJoueur1Value));
        } else if (joueur == JOUEUR2) {
            if (reponse == 1) {
                // Réponse vraie et le joueur appuie, il gagne un point
                scoreJoueur2Value++;
            } else {
                // Réponse fausse et le joueur appuie, il perd un point
                scoreJoueur2Value = Math.max(0, scoreJoueur2Value - 1);
            }
            scoreJoueur2.setText(String.valueOf(scoreJoueur2Value));
        }
    }

    private void resultatFinPartie() {
        BT_Joueur1.setEnabled(false);
        BT_Joueur2.setEnabled(false);

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
        handler = new Handler();

        questionRunnable = new Runnable() {
            @Override
            public void run() {
                if(qManager.endOfList()){
                    relativeLayout.setVisibility(View.VISIBLE);
                    ligneSeparation.setVisibility(View.INVISIBLE);

                    resultatFinPartie();
                    handler.removeCallbacks(this);
                } else {
                    String questionEnCours = qManager.recevoirQuestion();
                    questionJoueur1.setText(questionEnCours);
                    questionJoueur2.setText(questionEnCours);

                    int reponseActuelle = qManager.getReponseQuestionEnCours();

                    if (reponseActuelle == 0) {
                        // Réponse fausse, les joueurs gagnent 1 point chacun
                        scoreJoueur1Value++;
                        scoreJoueur2Value++;
                    } else {
                        // Réponse vraie, les joueurs perdent 1 point chacun
                        scoreJoueur1Value = Math.max(0, scoreJoueur1Value - 1);
                        scoreJoueur2Value = Math.max(0, scoreJoueur2Value - 1);
                    }

                    scoreJoueur1.setText(String.valueOf(scoreJoueur1Value));
                    scoreJoueur2.setText(String.valueOf(scoreJoueur2Value));

                    handler.postDelayed(this, 2000);
                }
            }
        };
        handler.postDelayed(questionRunnable, 1000);
    }

    private void retournerAMainActivity() {
        finish();
    }
}
