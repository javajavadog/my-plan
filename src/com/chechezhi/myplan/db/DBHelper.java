package com.chechezhi.myplan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_TABLE_NAME = "Plans";
    
    private static final String DB_NAME = "PlanDatabase";
    private static final int DB_VERSION = 1;
    
    public static final String DB_COLUMN_TITLE = "title";
    public static final String DB_COLUMN_CONTENT = "content";
    public static final String DB_COLUMN_CREATE_DATE = "create_date";
    public static final String DB_COLUMN_MODIFICATION_DATE = "modification_date";
    public static final String DB_COLUMN_FINISHED = "finished";
    public static final String DB_COLUMN_DES = "description";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE_NAME + " ("
                        + "_id INTEGER PRIMARY KEY, "
                        + DB_COLUMN_TITLE + " TEXT, "
                        + DB_COLUMN_CONTENT + " TEXT, "
                        + DB_COLUMN_CREATE_DATE + " INTEGER, "
                        + DB_COLUMN_MODIFICATION_DATE + " INTEGER, "
                        + DB_COLUMN_FINISHED + " INTEGER, "
                        + DB_COLUMN_DES + " TEXT"
                        + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

}
