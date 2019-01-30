package com.example.reza66.criminalintentr.models;

import com.example.reza66.criminalintentr.utils.RandomData;

import java.util.Date;
import java.util.UUID;

/**
 * Created by www.NooR26.com on 12/16/2018.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Crime() {
        this(UUID.randomUUID());
    }
    public Crime(UUID id){
        mId=id;
        mDate=RandomData.randomDate();
    }
}
