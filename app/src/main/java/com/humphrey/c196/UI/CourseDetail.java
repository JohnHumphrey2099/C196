package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;

import java.text.ParseException;
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
    String termDetails = "com.humphrey.c196.UI.TermDetail";
    String courseScreen = "com.humphrey.c196.UI.CourseDetail";
    String assessmentDetails = "com.humphrey.c196.UI.assessmentDetail";
    Repository repository;
    List<Assessment> associatedAssessments;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
// Get the component that started the Intent
        ComponentName componentName = intent.getComponent();
// Get the class name of the component
        String className = componentName.getClassName();
        repository = new Repository(getApplication());
// Assign fields
        Button saveButton = findViewById(R.id.courseDetailSaveButton);
        editTextCourseTitle = findViewById(R.id.editTextCourseTitle);
        editTextCourseStart = findViewById(R.id.editTextCourseStart);
        editTextCourseEnd = findViewById(R.id.editTextCourseEnd);
        editNote = findViewById(R.id.editNote);
        profNameField = findViewById(R.id.profNameField);
        profPhoneField = findViewById(R.id.profPhoneField);
        profEmailField = findViewById(R.id.profEmailField);
        statusField = findViewById(R.id.statusField);

        RecyclerView recyclerView = findViewById(R.id.assessmentsRecycler);
        id = getIntent().getIntExtra("id", 0);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, id);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termID = getIntent().getIntExtra("termID", 0);
        if (id == 0) {
            editTextCourseStart.setText(sdf.format(new Date()));
            editTextCourseEnd.setText(sdf.format(new Date()));
        } else {
            editTextCourseStart.setText((getIntent().getStringExtra("start")));
            editTextCourseEnd.setText((getIntent().getStringExtra("end")));
            editTextCourseTitle.setText((getIntent().getStringExtra("title")));
            statusField.setText(getIntent().getStringExtra("status"));
            profNameField.setText(getIntent().getStringExtra("name"));
            profPhoneField.setText(getIntent().getStringExtra("phone"));
            profEmailField.setText(getIntent().getStringExtra("email"));
            editNote.setText(getIntent().getStringExtra("note"));
        }
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == id) {
                associatedAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessmentList(associatedAssessments);
        //set up buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.equals(termDetails)) {
                    if (id == 0) {
                        insertCourse();
                    } else {
                        updateCourse();
                    }
                    goToTermDetail(v);
                }
                if (className.equals(courseScreen)){
                    if (id == 0) {
                        insertCourse();
                    } else {
                        updateCourse();
                    }
                    goToCourseScreen(v);
                }
            }
        });
        editTextCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editTextCourseStart.getText().toString();
                try {
                    calendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, startDate,
                        calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel(editTextCourseStart, calendarStart);
            }
        };
        editTextCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editTextCourseEnd.getText().toString();
                try {
                    calendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, endDate,
                        calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel(editTextCourseEnd, calendarEnd);
            }
        };
    }
        private void updateDateLabel(EditText editText, Calendar calendar){
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            editText.setText(sdf.format(calendar.getTime()));
        }
        private void insertCourse(){
            repository.insertCourse(new Course(id,
                    editTextCourseTitle.getText().toString(),
                    editTextCourseStart.getText().toString(),
                    statusField.getText().toString(),
                    editTextCourseEnd.getText().toString(),
                    profNameField.getText().toString(),
                    profPhoneField.getText().toString(),
                    profEmailField.getText().toString(),
                    editNote.getText().toString(),
                    termID));
        }
        private void updateCourse(){
            repository.insertCourse(new Course(id,
                    editTextCourseTitle.getText().toString(),
                    editTextCourseStart.getText().toString(),
                    statusField.getText().toString(),
                    editTextCourseEnd.getText().toString(),
                    profNameField.getText().toString(),
                    profPhoneField.getText().toString(),
                    profEmailField.getText().toString(),
                    editNote.getText().toString(),
                    termID));
        }
    private void goToCourseScreen(View view){
        Intent intent = new Intent(CourseDetail.this, CourseScreen.class);
        startActivity(intent);
    }
    private void goToTermDetail(View view){
        Intent intent = new Intent(CourseDetail.this, TermDetail.class);
        startActivity(intent);
    }
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
//}