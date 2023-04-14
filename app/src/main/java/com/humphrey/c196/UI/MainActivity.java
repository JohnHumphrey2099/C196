package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.humphrey.c196.DAO.AssessmentDAO;
import com.humphrey.c196.DAO.TermDAO;
import com.humphrey.c196.Database.DatabaseBuilder;
import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;
import com.humphrey.c196.Entity.Term;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repository = new Repository(getApplication());

        repository.insertCourse(new Course("aoij", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("dfdf", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("sjthedrg", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("ikhjgfhd4", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("aoij", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("dfdf", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("sjthedrg", 3, "oijo", 4, "John",
                "494949","dkjkj"));
        repository.insertCourse(new Course("ikhjgfhd4", 3, "oijo", 4, "John",
                "494949","dkjkj"));
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