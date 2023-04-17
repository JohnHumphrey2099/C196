package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.humphrey.c196.DAO.AssessmentDAO;
import com.humphrey.c196.DAO.TermDAO;
import com.humphrey.c196.Database.DatabaseBuilder;
import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;
import com.humphrey.c196.Entity.Term;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static long numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository r = new Repository(getApplication());
        List<Term> deleteTerms;
        deleteTerms = r.getALlTerms();
        for (Term t : deleteTerms){
            r.deleteTerm(t);
        }
    }

    public void goToTermScreen(View view){
        Intent intent = new Intent(MainActivity.this, TermScreen.class);
        startActivity(intent);

    }
    public void goToAssessmentScreen(View view){
        Intent intent = new Intent(MainActivity.this, AssessmentScreen.class);
        startActivity(intent);
    }

    public void goToCourseScreen(View view){
        Intent intent = new Intent(MainActivity.this, CourseScreen.class);
        startActivity(intent);
    }

}