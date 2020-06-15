package com.example.immunizationmanagement.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "Immunization.db";
    public static final int  DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemsTable.SQL_CREATE_B);
        db.execSQL(ItemsTable.SQL_CREATE_V);
        db.execSQL(ItemsTable.SQL_CREATE_BV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(ItemsTable.SQL_DELETE_BV);
        db.execSQL(ItemsTable.SQL_DELETE_B);
        db.execSQL(ItemsTable.SQL_DELETE_V);

        onCreate(db);
    }
}
