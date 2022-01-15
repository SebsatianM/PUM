package com.example.pomodoroapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Pomodoro.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table if not exists userSettings(id TEXT primary key, session_length INTEGER, break_length INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                db.execSQL("create Table if not exists userSessions(id TEXT primary key, user_id TEXT, session_date TEXT, session_length TEXT,break_length TEXT )");
            }
        }

    public Boolean insertSettings(String ID, Integer sessionLength, Integer breakLength) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.rawQuery("Select * from userSettings where id = ?", new String[]{ID});
        contentValues.put("id", ID);
        contentValues.put("session_length", sessionLength);
        contentValues.put("break_length", breakLength);
        if (cursor.getCount() < 1) {
            long result = db.insert("userSettings", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean updateSettings(String ID, Integer sessionLength, Integer breakLength) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.rawQuery("Select * from userSettings where id = ?", new String[]{ID});
        contentValues.put("session_length", sessionLength);
        contentValues.put("break_length", breakLength);
        if (cursor.getCount() > 0) {
            long result = db.update("userSettings", contentValues, "id=?", new String[]{ID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public Boolean insertSession(String User_id,String Date, String sessionLength, String breakLength) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM userSessions",null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(0));
        }
        Integer ID = Integer.parseInt(buffer.substring(0,buffer.length()));

        contentValues.put("id", ID);
        contentValues.put("user_id",User_id);
        contentValues.put("session_date",Date);
        contentValues.put("session_length", sessionLength);
        contentValues.put("break_length", breakLength);
        if (cursor.getCount() < 2) {
            long result = db.insert("userSessions", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getSettings(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select session_length,break_length from userSettings where id = ?", new String[]{ID});
        return cursor;
    }



}
