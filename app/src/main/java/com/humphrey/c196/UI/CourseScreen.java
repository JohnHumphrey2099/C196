package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;

import java.util.List;

public class CourseScreen extends AppCompatActivity {
    private Repository repository;
    private ImageView hamburger;
    private TextView toolbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hamburger = toolbar.findViewById(R.id.menuIcon);
        toolbarText = toolbar.findViewById(R.id.toolbarText);
        toolbarText.setText("All Courses");
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this,0);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());

        List<Course> allCourses = repository.getAllCourses();
        if(allCourses.size() != 0) {
            TextView label = findViewById(R.id.noCoursesLabel);
            courseAdapter.setCourseList(allCourses);
            label.setVisibility(View.GONE);
        }
        else{
            TextView label = findViewById(R.id.noCoursesLabel);
            label.setVisibility(View.VISIBLE);
        }

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

    }
    private void showPopupMenu(View view) {
        // Inflate the menu using the PopupMenu class
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_coursescreen);

        // Set a click listener on the menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuAllTerms:
                        Intent intent = new Intent(CourseScreen.this, TermScreen.class);
                        startActivity(intent);
                        return true;
                    case R.id.menuAllAssessments:
                        goToAssessmentScreen(view);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Show the menu
        popupMenu.show();
    }
    public void goToAssessmentScreen(View view){
        Intent intent = new Intent(CourseScreen.this, AssessmentScreen.class);
        startActivity(intent);
    }
    public void goToTermScreen(View view) {
        Intent intent = new Intent(CourseScreen.this, TermScreen.class);
        Toast.makeText(getApplicationContext(), "Select Term to Add Courses. Tap + if none available.",
                Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}