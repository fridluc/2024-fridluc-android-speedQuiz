package com.fridluc.speedgame;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fridluc.speedgame.Models.SpeedGameSqLite;
import com.google.android.material.button.MaterialButton;

public class AjoutQuestionActivity extends AppCompatActivity {

    private TextView TV_ajoutQuestion;
    private TextView TV_ajoutReponse;
    private MaterialButton BT_AjoutQuestion;
    private MaterialButton BT_Terminer;
    private SpeedGameSqLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_question_activity);

        TV_ajoutQuestion = findViewById(R.id.ajout_question);
        TV_ajoutReponse = findViewById(R.id.ajout_reponse);
        BT_AjoutQuestion = findViewById(R.id.btn_ajoutQuestion);
        BT_Terminer = findViewById(R.id.btn_terminer);
        dbHelper = new SpeedGameSqLite(this);


        TV_ajoutReponse.setEnabled(false);

        BT_AjoutQuestion.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TV_ajoutQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TV_ajoutReponse.setEnabled(true);
            }
        });

        TV_ajoutReponse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                BT_AjoutQuestion.setEnabled(true);
            }
        });

        BT_AjoutQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterQuestion();
            }
        });

        BT_Terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private long insertQuestion(String question, int reponse) {
        ContentValues values = new ContentValues();
        values.put("question", question);
        values.put("reponse", reponse);
        return dbHelper.getWritableDatabase().insert("quiz", null, values);
    }

    private void ajouterQuestion() {
        String question = TV_ajoutQuestion.getText().toString().trim();
        String reponseString = TV_ajoutReponse.getText().toString().trim();
        int reponse = Integer.parseInt(reponseString);
        long result = insertQuestion(question, reponse);

        if (!question.isEmpty() && !reponseString.isEmpty()) {

        } else {
            // Gérer le cas où un champ est vide
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
