package com.fridluc.speedgame.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SpeedGameSqLite extends SQLiteOpenHelper {

    static String DB_NAME = "SpeedGame.db";
    static int DB_VERSION = 1;


    public SpeedGameSqLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDataTableQuiz = "CREATE TABLE quiz(idQuiz INTEGER PRIMARY KEY, question TEXT, reponse INTEGER);";
        db.execSQL(sqlCreateDataTableQuiz);

        db.execSQL("INSERT INTO quiz VALUES (0, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La Terre est ronde.\", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Le panda est un membre de la famille des félins\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"L'eau bout à 100 degrés Celsius.\", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"William Shakespeare était un dramaturge français\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Le plus grand océan de la Terre est l'Océan Atlantique.\", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Michael schumacher a 8 titres de champion du monde\",0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La Tour Eiffel a été construite au 19e siècle\", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Lionel Messi a 15 ballon d'or\",0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La Terre tourne autour du Soleil\", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La Mona Lisa a été peinte par Vincent van Gogh\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La Grande Muraille de Chine est invisible depuis la Lune\", 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
