package com.fridluc.speedgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fridluc.speedgame.Controllers.GameManager;
import com.fridluc.speedgame.Models.Question;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    //Initialisation des variables globales
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

    private int questionEnCoursIndex = 0;
    private int scoreJoueur1Value = 0;
    private int scoreJoueur2Value = 0;
    private final int JOUEUR1 = 1;
    private final int JOUEUR2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Affecte les ID au variables
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

        //Récupère les noms des joueurs
        Intent activityGame = getIntent();
        String joueur1 = activityGame.getStringExtra("Joueur1");
        TV_Joueur1.setText(joueur1);
        String joueur2 = activityGame.getStringExtra("Joueur2");
        TV_Joueur2.setText(joueur2);

        //Récupère les questions les mélanges et affiche la première
        questions = GameManager.getInstance().getQuestions();
        melangerQuestions();
        afficherQuestionSuivante();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Incrémente le score du joueur 1 et affiche la question d'après
        BT_Joueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficherQuestionSuivante();
                incrementerScoreJoueur(JOUEUR1);
            }
        });

        //Incrémente le score du joueur 2 et affiche la question d'après
        BT_Joueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficherQuestionSuivante();
                incrementerScoreJoueur(JOUEUR2);
            }
        });

        //Arrête le jeu et retourne au démarrage de l'application
        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retournerAMainActivity();
            }
        });

        //Recommence le jeu
        BT_Redémarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rend invisible le relativeLayout et fait apparaitre
                // une barre de séparation entre les questions
                relativeLayout.setVisibility(View.INVISIBLE);
                ligneSeparation.setVisibility(View.VISIBLE);

                // Réinitialise les scores des joueurs
                scoreJoueur1.setText(String.valueOf(scoreJoueur1Value = 0));
                scoreJoueur2.setText(String.valueOf(scoreJoueur2Value = 0));

                //Réactive les boutons pour recommencer le jeu
                BT_Joueur1.setEnabled(true);
                BT_Joueur2.setEnabled(true);

                questionEnCoursIndex = 0;

                melangerQuestions();

                afficherQuestionSuivante();

            }
        });

    }

    // Fonction qui permet de retourner a la page d'accueil
    private void retournerAMainActivity() {
        finish();
    }

    // Fonction qui permet d'augmenter le score des joueurs
    private void incrementerScoreJoueur(int joueur) {
        if (joueur == 1) {
            scoreJoueur1Value++;
            scoreJoueur1.setText(String.valueOf(scoreJoueur1Value));
        } else if (joueur == 2) {
            scoreJoueur2Value++;
            scoreJoueur2.setText(String.valueOf(scoreJoueur2Value));
        }
    }

    // Fonction qui permet d'afficher les questions au clique d'un bouton
    private void afficherQuestionSuivante() {
        if (questionEnCoursIndex < questions.size()) {
            String questionEnCours = questions.get(questionEnCoursIndex).getIntitule();
            questionJoueur1.setText(questionEnCours);
            questionJoueur2.setText(questionEnCours);

            // Incrémente l'index de la question pour la suivante
            questionEnCoursIndex++;
        } else {
            //Demande aux joueurs si ils veulent recommencer
            //et affiche le relativeLayout
            questionJoueur1.setText(R.string.restart_game);
            questionJoueur2.setText(R.string.restart_game);
            relativeLayout.setVisibility(View.VISIBLE);

            //Grise les boutons des joueurs
            //et rend invisible la ligne de séparation
            BT_Joueur1.setEnabled(false);
            BT_Joueur2.setEnabled(false);
            ligneSeparation.setVisibility(View.INVISIBLE);

        }
    }

    // Fonction qui permet de mélanger les questions
    private void melangerQuestions() {
        GameManager.getInstance().melangerQuestions();
    }
}

