package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    EditText editNote;
    EditText profNameField;
    EditText profPhoneField;
    EditText profEmailField;
    EditText statusField;
    EditText editTextCourseStart;
    EditText editTextCourseTitle;
    EditText editTextCourseEnd;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    int id;
    int termID;
    Repository repository;
    List<Assessment> associatedAssessments;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        repository = new Repository(getApplication());
        Button saveButton = findViewById(R.id.courseDetailSaveButton);
        editTextCourseTitle = findViewById(R.id.editTextCourseTitle);
        editTextCourseStart = findViewById(R.id.editTextCourseStart);
        editTextCourseEnd = findViewById(R.id.editTextCourseEnd);
        RecyclerView recyclerView = findViewById(R.id.assessmentsInsideCourseDetails);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        id = getIntent().getIntExtra("id", 0);
        termID = getIntent().getIntExtra("termID", 0);
        if (id == 0){
            editTextCourseStart.setText(sdf.format(new Date()));
            editTextCourseEnd.setText(sdf.format(new Date()));
        }
        else{
            editTextCourseStart.setText((getIntent().getStringExtra("start")));
            editTextCourseEnd.setText((getIntent().getStringExtra("end")));
            editTextCourseTitle.setText((getIntent().getStringExtra("title")));
            statusField.setText(getIntent().getStringExtra("status"));
            profNameField.setText(getIntent().getStringExtra("name"));
            profPhoneField.setText(getIntent().getStringExtra("phone"));
            profEmailField.setText(getIntent().getStringExtra("email"));
            editNote.setText(getIntent().getStringExtra("note"));
        }
        for (Assessment a : repository.getAllAssessments()){
            if (a.getCourseID() == id){
                associatedAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessmentList(associatedAssessments);
        //set up buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 0){
                    //TODO finish this insert statement. Also need to figure out to get the termID and pass in Intent
                    repository.insertCourse(new Course(id,
                            editTextCourseTitle.getText().toString(),
                            editTextCourseStart.getText().toString(),
                            statusField.getText().toString(),
                            editTextCourseEnd.getText().toString(),
                            profNameField.getText().toString(),
                            profPhoneField.getText().toString(),
                            profEmailField.getText().toString(),
                            editNote.getText().toString(),
                            ));
                }
            }
        });
    }
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case android.R.id.home:
//                this.finish();
//                return true;
//            case R.id.share:
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
//                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
//                sendIntent.setType("text/plain");
//                Intent shareIntent = Intent.createChooser(sendIntent,null);
//                startActivity(shareIntent);
//                return true;
//            case R.id.notifyStart:
//                String dateFromScreen = editStartDate.getText().toString();
//                String myFormat = "MM/dd/yy";
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                Date myDate = null;
//
//        }
//    }
}