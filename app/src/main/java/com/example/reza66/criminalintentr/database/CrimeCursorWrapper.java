package com.example.reza66.criminalintentr.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.reza66.criminalintentr.models.Crime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by www.NooR26.com on 1/19/2019.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){

        UUID uuid=UUID.fromString(getString(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.UUID)));
        String title = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.TITLE));
        Date date=new Date(getLong(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.DATE)));
        boolean isSolved=getInt(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.SOLVED))!=0;

        Crime crime=new Crime(uuid);
        crime.setTitle(title);
        crime.setDate(date);
        crime.setSolved(isSolved);

        return crime;
    }
}
