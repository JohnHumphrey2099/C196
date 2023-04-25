package com.humphrey.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
            int assessmentID;

    private String title;
    private String type;
    private String startDate;
    private String endDate;
    private int courseID;

    public Assessment(int assessmentID, String title, String type, String startDate, String endDate, int courseID) {
        this.assessmentID = assessmentID;
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
