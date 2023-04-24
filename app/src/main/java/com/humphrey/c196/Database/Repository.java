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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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

    public void updateTerm(Term term){
        databaseExecutor.execute(()->{
            termDAO.update(term);
        });
    }
    public long insertTerm(Term term){
        AtomicLong insertResult = new AtomicLong();
        databaseExecutor.execute(()->{
            insertResult.set(termDAO.insert(term));
        });
        Util.waitAsec();
        return insertResult.get();
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
    public void updateAssessment(Assessment assessment){
        databaseExecutor.execute(()-> {
            assessmentDAO.update(assessment);
        });
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

    public void updateCourse(Course course){
        databaseExecutor.execute(()->{
            courseDAO.update(course);
        });
    }
    public long insertCourse(Course course){
        AtomicLong insertResult = new AtomicLong();
        databaseExecutor.execute(()->{
            insertResult.set(courseDAO.insert(course));
        });
        Util.waitAsec();
        return insertResult.get();
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
