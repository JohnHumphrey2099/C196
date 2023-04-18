package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;

import java.util.List;

public class CourseScreen extends AppCompatActivity {
    int termID;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_screen);
        termID = getIntent().getIntExtra("termID", 0);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this,termID);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());

        List<Course> allCourses = repository.getAllCourses();
        courseAdapter.setCourseList(allCourses);



    }
    public void goToCourseDetailScreen(View view) {
        Intent intent = new Intent(CourseScreen.this, CourseDetail.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }
}