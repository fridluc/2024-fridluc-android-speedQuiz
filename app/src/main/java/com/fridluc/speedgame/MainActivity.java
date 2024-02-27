package com.fridluc.speedgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton BT_NouveauJoueur;
    private EditText ET_SaisieJoueur1;
    private EditText ET_SaisieJoueur2;
    private MaterialButton BT_NouvellePartie;

    private View MENUUUUUUUU;

    private Menu mainMenu = null;
    String joueur1 = "";
    String joueur2 = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_question) {
            // Ouvrir AjoutQuestionActivity lorsque l'élément de menu "question" est sélectionné
            Log.d("MainActivity", "action_question selected");

            Intent intent = new Intent(this, AjoutQuestionActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolBar = findViewById(R.id.MainToolbar);
        setSupportActionBar(mainToolBar);

        BT_NouveauJoueur = findViewById(R.id.btn_startaddPlayer);
        ET_SaisieJoueur1 = findViewById(R.id.startPlayer1EditText);
        ET_SaisieJoueur2 = findViewById(R.id.startPlayer2EditText);
        BT_NouvellePartie = findViewById(R.id.btn_startNewGame);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Ajoutez un écouteur de clic au bouton
        BT_NouveauJoueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_SaisieJoueur1.setVisibility(View.VISIBLE);
            }
        });

        ET_SaisieJoueur1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ET_SaisieJoueur2.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                joueur1 = ET_SaisieJoueur1.getText().toString();
            }
        });

        ET_SaisieJoueur2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Affiche le bouton de nouvelle partie
                BT_NouvellePartie.setVisibility(View.VISIBLE);

                //vérifie si les 2 champs textes sont remplis
                if (!ET_SaisieJoueur1.getText().toString().equals("") && !ET_SaisieJoueur2.getText().toString().equals(""))
                    //Grise le bouton
                    BT_NouveauJoueur.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                joueur2 = ET_SaisieJoueur2.getText().toString();
            }
        });




        BT_NouvellePartie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityGame = new Intent(MainActivity.this, GameActivity.class);
                activityGame.putExtra("Joueur1", joueur1);
                activityGame.putExtra("Joueur2", joueur2);
                startActivity(activityGame);
            }
        });

    }
}
