package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;

import java.util.ArrayList;
import java.util.List;

public class TermDetail extends AppCompatActivity {
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    int id;
    String title;
    int startDate;
    int endDate;
    Term term;
    Repository repository;

    List<Course> associatedCourses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_detail);
        editTitle = findViewById(R.id.editTextTermTitle);
        editStartDate = findViewById(R.id.editTextTermStart);
        editEndDate = findViewById(R.id.editTextTermEnd);

        title = getIntent().getStringExtra("title");
        startDate = getIntent().getIntExtra("start",0);
        endDate = getIntent().getIntExtra("end",0);
        id = getIntent().getIntExtra("id",0);

        editTitle.setText(title);

        repository = new Repository(getApplication());
        if (startDate != 0){
            editStartDate.setText(Integer.toString(startDate));
        }
        if (endDate != 0){
            editEndDate.setText(Integer.toString(endDate));
        }

        for (Course c : repository.getAllCourses()){
            if (c.getTermID() == id) associatedCourses.add(c);
        }

        RecyclerView recyclerView = findViewById(R.id.coursesInsideTermDetails);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseAdapter.setCourseList(associatedCourses);



        Button saveButton = findViewById(R.id.termDetailSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 0){
                    term = new Term(
                            0,
                            editTitle.getText().toString(),
                            Integer.parseInt(editStartDate.getText().toString()),
                            Integer.parseInt(editEndDate.getText().toString())
                    );
                    repository.insertTerm(term);
                    goToTermScreen(v);

                }
                else{
                    term = new Term(
                            id,
                            editTitle.getText().toString(),
                            Integer.parseInt(editStartDate.getText().toString()),
                            Integer.parseInt(editEndDate.getText().toString())
                    );
                    repository.updateTerm(term);
                    goToTermScreen(v);
                }
            }
        });
    }
    public void goToTermScreen(View view){
        Intent intent = new Intent(TermDetail.this, TermScreen.class);
        startActivity(intent);

    }
}