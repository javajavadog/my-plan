package com.chechezhi.myplan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private static DBManager sInstance;

    private DBHelper mHelper;

    private DBManager(Context c) {
        mHelper = new DBHelper(c);
    };

    public synchronized static DBManager getInstance(Context c) {
        if (sInstance == null) {
            sInstance = new DBManager(c);
        }
        return sInstance;
    }

    public static class Record {
        public String title;
        public String content;
        public long createDate;
        public long modificationDate;
        public int finished;
        public String description;
    }

    public long add(Record r) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.DB_COLUMN_TITLE, r.title);
        cv.put(DBHelper.DB_COLUMN_CONTENT, r.content);
        cv.put(DBHelper.DB_COLUMN_CREATE_DATE, r.createDate);
        cv.put(DBHelper.DB_COLUMN_MODIFICATION_DATE, r.modificationDate);
        cv.put(DBHelper.DB_COLUMN_FINISHED, r.finished);
        cv.put(DBHelper.DB_COLUMN_DES, r.description);

        SQLiteDatabase db = mHelper.getWritableDatabase();
        long id = db.insert(DBHelper.DB_TABLE_NAME, null, cv);
        db.close();
        return id;
    }
    
    public int update(long id, Record r){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.DB_COLUMN_TITLE, r.title);
        cv.put(DBHelper.DB_COLUMN_CONTENT, r.content);
        cv.put(DBHelper.DB_COLUMN_CREATE_DATE, r.createDate);
        cv.put(DBHelper.DB_COLUMN_MODIFICATION_DATE, r.modificationDate);
        cv.put(DBHelper.DB_COLUMN_FINISHED, r.finished);
        cv.put(DBHelper.DB_COLUMN_DES, r.description);

        SQLiteDatabase db = mHelper.getWritableDatabase();
        int rows = db.update(DBHelper.DB_TABLE_NAME, cv, "_id = " + id, null);
        return rows;
    }
    
    public int delete(long id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db.delete(DBHelper.DB_TABLE_NAME, "_id = " + id, null);
    }

    public Cursor query() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor c = db.query(DBHelper.DB_TABLE_NAME, new String[] { "_id", DBHelper.DB_COLUMN_TITLE,
                        DBHelper.DB_COLUMN_CONTENT, DBHelper.DB_COLUMN_CREATE_DATE,
                        DBHelper.DB_COLUMN_MODIFICATION_DATE, DBHelper.DB_COLUMN_FINISHED, DBHelper.DB_COLUMN_DES },
                        null, null, null, null, null);
        return c;
    }
    
    public Cursor query(int id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor c = db.query(DBHelper.DB_TABLE_NAME, new String[] { "_id", DBHelper.DB_COLUMN_TITLE,
                        DBHelper.DB_COLUMN_CONTENT, DBHelper.DB_COLUMN_CREATE_DATE,
                        DBHelper.DB_COLUMN_MODIFICATION_DATE, DBHelper.DB_COLUMN_FINISHED, DBHelper.DB_COLUMN_DES },
                        "_id = " + id, null, null, null, null);
        return c;
    }
    
    public Cursor query(String where){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor c = db.query(DBHelper.DB_TABLE_NAME, new String[] { "_id", DBHelper.DB_COLUMN_TITLE,
                        DBHelper.DB_COLUMN_CONTENT, DBHelper.DB_COLUMN_CREATE_DATE,
                        DBHelper.DB_COLUMN_MODIFICATION_DATE, DBHelper.DB_COLUMN_FINISHED, DBHelper.DB_COLUMN_DES },
                        where, null, null, null, null);
        return c;
        
    }
}
