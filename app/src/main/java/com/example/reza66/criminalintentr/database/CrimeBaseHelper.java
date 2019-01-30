package com.example.reza66.criminalintentr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by www.NooR26.com on 1/18/2019.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    public CrimeBaseHelper(Context context ) {
        super(context, CrimeDBSchema.NAME, null, CrimeDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeDBSchema.CrimeTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                CrimeDBSchema.CrimeTable.Cols.UUID + ", " +
                CrimeDBSchema.CrimeTable.Cols.TITLE + ", " +
                CrimeDBSchema.CrimeTable.Cols.DATE + ", " +
                CrimeDBSchema.CrimeTable.Cols.SOLVED
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
