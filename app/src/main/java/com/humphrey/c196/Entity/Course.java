package com.humphrey.c196.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "Courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String title;
    private String startDate;
    private String status;
    private String endDate;
    private String note;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    @Ignore
    private ArrayList<Assessment> associatedAssessments;
    private int termID;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public ArrayList<Assessment> getAssociatedAssessments() {
        return associatedAssessments;
    }

    public void setAssociatedAssessments(ArrayList<Assessment> associatedAssessments) {
        ArrayList<Assessment> temp = new ArrayList<>(associatedAssessments);
        this.associatedAssessments = temp;
    }

    public Course(int courseID, String title, String startDate, String status, String endDate,
                  String instructorName, String instructorPhone, String instructorEmail, String note, int termID) {
        this.courseID = courseID;
        this.title = title;
        this.startDate = startDate;
        this.status = status;
        this.endDate = endDate;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.termID = termID;
    }
}
