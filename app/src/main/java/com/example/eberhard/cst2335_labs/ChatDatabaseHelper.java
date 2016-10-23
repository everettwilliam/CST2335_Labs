package com.example.eberhard.cst2335_labs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by eberhard on 05-Oct-16.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public ChatDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        Log.i(ACTIVITY_NAME, "Calling onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(ACTIVITY_NAME, "Calling onUpgrade(), oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i(ACTIVITY_NAME, "Calling onDownGrade(), oldVersion=" + oldVersion + " newVersion=" + newVersion);

    }

    public static final String DATABASE_NAME = "Chats.db";
    public static final int DATABASE_VERSION = 100;
    public static final String TABLE_NAME = "CHAT_TABLE";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";
    public static final String[] FIELDS = new String[]{KEY_ID, KEY_MESSAGE};
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_MESSAGE + " TEXT"
            + ");";

    SQLiteDatabase database;
    protected static final String ACTIVITY_NAME = "Database Helper";

}
