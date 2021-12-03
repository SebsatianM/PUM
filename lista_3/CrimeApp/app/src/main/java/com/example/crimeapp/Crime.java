package com.example.crimeapp;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public void setUUID(UUID ID) {this.mId =ID;    }

    public void setTitle(String newTitle) {
        this.mTitle=newTitle;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public Boolean getSolved(){
        return mSolved;
    }

}