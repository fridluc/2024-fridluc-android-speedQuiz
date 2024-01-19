package com.fridluc.speedgame.Models;

import java.util.ArrayList;
import java.util.List;

public class SpeedQuizSQLiteOpenHelper {

    private final ArrayList<Question> questions = new ArrayList<>();

    public SpeedQuizSQLiteOpenHelper() {
        questions.add(new Question("La France est en Europe.", true));
        questions.add(new Question("La Terre est plate.", false));
        questions.add(new Question("L'eau bout à 100 degrés Celsius.", true));
        questions.add(new Question("Le Soleil tourne autour de la Terre.", false));
        questions.add(new Question("Le code PIN d'urgence pour les cartes de crédit est 1234.", true));
        questions.add(new Question("Le Python est un langage de programmation.", true));
        questions.add(new Question("La distance entre la Terre et la Lune est d'environ 10'000 kilomètres.", false));

        questions.add(new Question("La capitale de l'Australie est Sydney.", false));
        questions.add(new Question("Roméo et Juliette a été écrit par William Shakespeare.", true));
        questions.add(new Question("L'océan Atlantique est le plus grand océan du monde.", false));
        questions.add(new Question("La Joconde a été peinte par Vincent van Gogh.", false));
        questions.add(new Question("Le Sahara est le plus grand désert du monde.", false));
        questions.add(new Question("La monnaie officielle du Japon est le won. ", false));
        questions.add(new Question("Le symbole chimique de l'or est Gd.", false));
        questions.add(new Question("Le Nil est le plus long fleuve du monde.", true));
        questions.add(new Question("Il y a 7 continents dans le monde.", true));
        questions.add(new Question("Newton a découvert la gravité", true));
        questions.add(new Question("Léonard de vinci est un youtuber", false));
        questions.add(new Question("Messi est le joueur avec le plus de ballon d'or de l'histoire", true));

    }

    public List<Question> getQuestions() {
        return questions;
    }
}
