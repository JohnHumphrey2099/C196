package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.R;

import java.util.List;

public class AssessmentScreen extends AppCompatActivity {
    private ImageView hamburger;
    private TextView toolbarText;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hamburger = toolbar.findViewById(R.id.menuIcon);
        toolbarText = toolbar.findViewById(R.id.toolbarText);
        toolbarText.setText("All Assessments");
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this,0);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());

        List<Assessment> allAssessments = repository.getAllAssessments();
        assessmentAdapter.setAssessmentList(allAssessments);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    public void goToTermScreen(View view) {
        Intent intent = new Intent(AssessmentScreen.this, TermScreen.class);
        Toast.makeText(getApplicationContext(), "Select Term and Course to add Assessment to. Tap + if none available.",
                Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    private void showPopupMenu(View view) {
        // Inflate the menu using the PopupMenu class
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_assessmentscreen);


        // Set a click listener on the menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            Intent intent;
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuAllTerms:
                        intent = new Intent(AssessmentScreen.this, TermScreen.class);
                        startActivity(intent);
                        return true;
                    case R.id.menuAllCourses:
                        intent = new Intent(AssessmentScreen.this, CourseScreen.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Show the menu
        popupMenu.show();
    }
}