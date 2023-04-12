package com.humphrey.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termID;

    private String title;
    private int startDate;
    private int endDate;

    public Term(String title, int startDate, int endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTermID() {
        return termID;
    }

    public String getTitle() {
        return title;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
