package com.example.reza66.criminalintentr.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reza66.criminalintentr.database.CrimeBaseHelper;
import com.example.reza66.criminalintentr.database.CrimeCursorWrapper;
import com.example.reza66.criminalintentr.database.CrimeDBSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by www.NooR26.com on 12/17/2018.
 */

public class CrimeLab {
    private static CrimeLab instance;
    SQLiteDatabase mDatabase;
    private Context mContext;

    private CrimeLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public static CrimeLab getInstance(Context  context) {
        if (instance==null)
            instance=new CrimeLab(context);
        return instance;
    }
    public List<Crime> getCrimes() {
        List<Crime> crimes=new ArrayList<>();
        CrimeCursorWrapper crimeCursorWrapper = queryCrime(null, null);
        try {
            if(crimeCursorWrapper.getCount()==0)
                return crimes;
            crimeCursorWrapper.moveToFirst();
            while (!crimeCursorWrapper.isAfterLast()) {
                crimes.add(crimeCursorWrapper.getCrime());
                crimeCursorWrapper.moveToNext();
            }

        } finally {
            crimeCursorWrapper.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){
        String whereClause=CrimeDBSchema.CrimeTable.Cols.UUID + " = ? ";
        String[] whereArgs =new String[]{id.toString()};
        CrimeCursorWrapper crimeCursorWrapper = queryCrime(whereClause, whereArgs);
        try {
            if(crimeCursorWrapper.getCount()==0)
                return null;
            crimeCursorWrapper.moveToFirst();
            return crimeCursorWrapper.getCrime();
        } finally {
            crimeCursorWrapper.close();
        }

    }

    private CrimeCursorWrapper queryCrime(String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(CrimeDBSchema.CrimeTable.NAME,null,
                    whereClause,whereArgs,null,null,null);
        return new CrimeCursorWrapper(cursor);
    }

    public void addCrime(Crime crime){
        ContentValues values=getContentValues(crime);
        mDatabase.insert(CrimeDBSchema.CrimeTable.NAME,null,values);
    }
    public void removeItem(UUID id) {
        mDatabase.delete(CrimeDBSchema.CrimeTable.NAME,
                CrimeDBSchema.CrimeTable.Cols.UUID + " = ?", new String[]{id.toString()});
    }
    public ContentValues getContentValues(Crime crime){
        ContentValues values=new ContentValues();
        values.put(CrimeDBSchema.CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeDBSchema.CrimeTable.Cols.TITLE ,crime.getTitle());
        values.put(CrimeDBSchema.CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeDBSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }
    public void update(Crime crime){
        // String whereClause = CrimeDBSchema.CrimeTable.Cols.UUID + " = " + crime.getId().toString();

//        String whereClause1=CrimeDBSchema.CrimeTable.Cols.UUID + " = ? AND _id > ?";
//        mDatabase.update(CrimeDBSchema.CrimeTable.NAME,
//                getContentValues(crime),
//                whereClause1,new String[]{crime.getId().toString(),"8"});
        String whereClause = CrimeDBSchema.CrimeTable.Cols.UUID + " = ?";
        mDatabase.update(CrimeDBSchema.CrimeTable.NAME,
                getContentValues(crime),
                whereClause, new String[]{crime.getId().toString()});
    }
}
