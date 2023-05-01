package com.humphrey.c196.Utility;

import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    public static boolean validateDateRange(String startString, String endString){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        boolean valid = false;
        try {
            Date start = sdf.parse(startString);
            Date end = sdf.parse(endString);

            if (start.before(end)|| start.equals(end)){
                valid = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valid;
    }
}
