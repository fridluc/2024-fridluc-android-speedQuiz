package com.fridluc.speedgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class ParametrePartieActivity extends AppCompatActivity {

    private Slider sliderDelay;
    private MaterialButton BT_Valider;
    private MaterialButton BT_Annuler;

    private EditText ET_NombreQuestion;

    private int nombreQuestion;

    private float delayPartie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre_partie_activity);

        sliderDelay = findViewById(R.id.delay_partie);
        BT_Valider = findViewById(R.id.btn_valider);
        BT_Annuler = findViewById(R.id.btn_annuler);
        ET_NombreQuestion = findViewById(R.id.nombre_question);
    }

    @Override
    protected void onStart() {
        super.onStart();

        sliderDelay.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                delayPartie = sliderDelay.getValue();
            }
        });

        ET_NombreQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nombreQuestion = Integer.parseInt(ET_NombreQuestion.getText().toString());
            }
        });


        BT_Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parametreActivity = new Intent(ParametrePartieActivity.this, MainActivity.class);

                //Récupère le délai entré pour la partie
                parametreActivity.putExtra("delayPartie", delayPartie);
                parametreActivity.putExtra("nombreQuestion", nombreQuestion);
                startActivity(parametreActivity);
            }
        });

        BT_Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
