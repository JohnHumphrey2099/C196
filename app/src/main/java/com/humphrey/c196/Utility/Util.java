package com.humphrey.c196.Utility;

import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;

import java.util.ArrayList;

public class Util {
    public static ArrayList<Course> cacheCourses = new ArrayList<>();
    public static ArrayList<Assessment> cacheAssessments = new ArrayList<>();
    public static void waitAsec(){
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
