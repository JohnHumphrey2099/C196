package com.humphrey.c196.Database;

import static java.util.concurrent.Executors.newFixedThreadPool;

import android.app.Application;

import com.humphrey.c196.DAO.AssessmentDAO;
import com.humphrey.c196.DAO.CourseDAO;
import com.humphrey.c196.DAO.TermDAO;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.Utility.Util;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class Repository {

    private TermDAO termDAO;
    private AssessmentDAO assessmentDAO;
    private CourseDAO courseDAO;


    private List<Term> allTerms;
    private List<Assessment> allAssessments;

    private List<Course> allCourses;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);

        termDAO = db.termDAO();
        assessmentDAO = db.assessmentDAO();
        courseDAO = db.courseDAO();
    }
    public void insertTerm(Term term){
        databaseExecutor.execute(()->{
           termDAO.insert(term);
        });
       Util.waitAsec();
    }
    public void deleteTerm(Term term){
        databaseExecutor.execute(()->{
            termDAO.delete(term);
        });
        Util.waitAsec();
    }
    public List<Term> getALlTerms(){
        databaseExecutor.execute(()->{
            allTerms = termDAO.getAllTerms();
        });
        Util.waitAsec();
        return allTerms;
    }
    public void insertAssessment(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.insert(assessment);
        });
        Util.waitAsec();
    }
    public void deleteAssessment(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.delete(assessment);
        });
        Util.waitAsec();
    }
    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            allAssessments = assessmentDAO.getAllAssessments();
        });
        Util.waitAsec();
        return allAssessments;
    }
    public void insertCourse(Course course){
        databaseExecutor.execute(()->{
            courseDAO.insert(course);
        });
        Util.waitAsec();
    }
    public void deleteCourse(Course course){
        databaseExecutor.execute(()-> {
            courseDAO.delete(course);
        });
        Util.waitAsec();
    }
    public List<Course> getAllCourses(){

        databaseExecutor.execute(()->{
            allCourses = courseDAO.getAllCourses();
        });
        Util.waitAsec();
        return allCourses;
    }
}
