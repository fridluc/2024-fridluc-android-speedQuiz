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

        db.execSQL("INSERT INTO quiz VALUES (1, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La Terre est plate.\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"L'eau bout à 100 degrés Celsius.\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Le Soleil tourne autour de la Terre.\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La capitale de l'australie est sidney\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null, \"La capitale de l'australie est sidney\", 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
