package com.fridluc.speedgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class ParametrePartieActivity extends AppCompatActivity {

    private Slider sliderDelay;
    private MaterialButton BT_Valider;

    private float delayPartie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre_partie_activity);

        sliderDelay = findViewById(R.id.delay_partie);
        BT_Valider = findViewById(R.id.btn_valider);
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

        BT_Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parametreActivity = new Intent(ParametrePartieActivity.this, MainActivity.class);

                //Récupère le délai entré pour la partie
                parametreActivity.putExtra("delayPartie", delayPartie);
                startActivity(parametreActivity);
            }
        });


    }
}
