package com.example.android.shreygarg_spidertask2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {
    public static final String Database_Name = "Result.db";
    public static final String Table_Name = "Result_table";
    public static final String Col_1 = "S_No";
    public static final String Col_2 = "Mode";
    public static final String Col_3 = "Time";
    public Database(Context context) {
        super(context, Database_Name, null, 1);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_Name+ "(S_No INTEGER PRIMARY KEY AUTOINCREMENT, Mode TEXT,Time INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Table_Name);
        onCreate(db);
    }
    public void insertData(String Mode,int Time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,Mode);
        contentValues.put(Col_3,Time);
        long result = db.insert(Table_Name,null,contentValues);
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_Name,null);
        return res;
    }
}
