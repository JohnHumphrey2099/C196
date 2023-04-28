package com.humphrey.c196.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;
import com.humphrey.c196.Utility.Util;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    EditText editNote;
    EditText profNameField;
    EditText profPhoneField;
    EditText profEmailField;
    EditText editTextCourseStart;
    EditText editTextCourseTitle;
    EditText editTextCourseEnd;
    Spinner typeSpinner;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    int id;
    int termID;
    int position;
    String selectedStatus;
    Repository repository;
    AssessmentAdapter assessmentAdapter;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
    private ImageView hamburger;
    private TextView toolbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        repository = new Repository(getApplication());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hamburger = toolbar.findViewById(R.id.menuIcon);
        toolbarText = toolbar.findViewById(R.id.toolbarText);
        //set title of action bar
        if(getIntent().getStringExtra("title") == null){
            toolbarText.setText("New Course");
        }
        else{
            toolbarText.setText(getIntent().getStringExtra("title"));
        }

// Assign fields
        Button saveButton = findViewById(R.id.courseSaveButton);
        Button addAssessment = findViewById(R.id.addAssessment);
        editTextCourseTitle = findViewById(R.id.editTextCourseTitle);
        editTextCourseStart = findViewById(R.id.editTextCourseStart);
        editTextCourseEnd = findViewById(R.id.editTextAssessmentEnd);
        editNote = findViewById(R.id.editNote);
        profNameField = findViewById(R.id.profNameField);
        profPhoneField = findViewById(R.id.profPhoneField);
        profEmailField = findViewById(R.id.profEmailField);

//create Spinner
        typeSpinner = findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
//Create RecyclerView
        RecyclerView recyclerView = findViewById(R.id.assessmentsRecycler);
        id = getIntent().getIntExtra("id", 0);
        termID = getIntent().getIntExtra("termID", 0);
        position = getIntent().getIntExtra("position", 9999);
        assessmentAdapter = new AssessmentAdapter(this, id);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//Populate Fields.
        if (id == 0) { //if a unsaved course
            if (position == 9999){ // if brand new, not coming from list click
                editTextCourseStart.setText(sdf.format(new Date())); //set date to today
                editTextCourseEnd.setText(sdf.format(new Date())); // set date to today
                Util.cacheAssessments.clear(); // clear any outdated assessments
            }
            else{ // unsaved but coming from the list
                if(getIntent().getStringExtra("start") == null){//if no date set, set date to today
                    editTextCourseStart.setText(sdf.format(new Date()));
                    editTextCourseEnd.setText(sdf.format(new Date()));
                }
                else{//get the date from the saved course
                    editTextCourseStart.setText((getIntent().getStringExtra("start")));
                    editTextCourseEnd.setText((getIntent().getStringExtra("end")));
                }
                Util.cacheAssessments.clear(); // clear out any outdated assessments
                ArrayList<Assessment> temp = new ArrayList<>((Util.cacheCourses.get(position)).getAssociatedAssessments()); // make a copy of associated assessments from the object to not disturb original
                Util.cacheAssessments.addAll(temp);
            }

        }
        else {//course already saved
            editTextCourseStart.setText((getIntent().getStringExtra("start")));
            editTextCourseEnd.setText((getIntent().getStringExtra("end")));
            //populate cacheAssessments with matching Assessments from db
            for(Assessment a : repository.getAllAssessments()){
                if(a.getCourseID() == id){
                    Util.cacheAssessments.add(a);
                }
            }
        }
        editTextCourseTitle.setText((getIntent().getStringExtra("title")));
        //get status from intent
        if(getIntent().getStringExtra("status")!= null){
            selectedStatus = getIntent().getStringExtra("status");
            int index = 0;
            String[] items = getResources().getStringArray(R.array.type_options);
            for (int i = 0; i < items.length; i++) {
                if (items[i].equals(selectedStatus)) {
                    index = i;
                    break;
                }
            }
// set the selected item of the spinner
            typeSpinner.setSelection(index);
        }
        profNameField.setText(getIntent().getStringExtra("name"));
        profPhoneField.setText(getIntent().getStringExtra("phone"));
        profEmailField.setText(getIntent().getStringExtra("email"));
        editNote.setText(getIntent().getStringExtra("note"));
        assessmentAdapter.setAssessmentList(Util.cacheAssessments);

        //set up buttons
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termID ==0){
                    if(position == 9999){
                        ArrayList<Assessment> temp = new ArrayList<>(Util.cacheAssessments);
                        Course course = new Course(id,
                                editTextCourseTitle.getText().toString(),
                                editTextCourseStart.getText().toString(),
                                typeSpinner.getSelectedItem().toString(),
                                editTextCourseEnd.getText().toString(),
                                profNameField.getText().toString(),
                                profPhoneField.getText().toString(),
                                profEmailField.getText().toString(),
                                editNote.getText().toString(),
                                termID);
                        course.setAssociatedAssessments(Util.cacheAssessments);
                        Util.cacheCourses.add(course);
                        Util.cacheAssessments.clear();
                    }
                    else{
                        Course course = Util.cacheCourses.get(position);
                        course.setTitle(editTextCourseTitle.getText().toString());
                        course.setStartDate(editTextCourseStart.getText().toString());
                        course.setStatus(typeSpinner.getSelectedItem().toString());
                        course.setEndDate(editTextCourseEnd.getText().toString());
                        course.setInstructorName(profNameField.getText().toString());
                        course.setInstructorPhone(profPhoneField.getText().toString());
                        course.setNote(editNote.getText().toString());
                        course.setAssociatedAssessments(Util.cacheAssessments);
                        Util.cacheAssessments.clear();
                    }
                }
                else{//if we do have a term ID
                    if (id ==0){//if we have term id but course is unsaved
                        //insert the course and return the primaryKey.
                       int courseID = (int)repository.insertCourse(new Course(id,
                                editTextCourseTitle.getText().toString(),
                                editTextCourseStart.getText().toString(),
                                typeSpinner.getSelectedItem().toString(),
                                editTextCourseEnd.getText().toString(),
                                profNameField.getText().toString(),
                                profPhoneField.getText().toString(),
                                profEmailField.getText().toString(),
                                editNote.getText().toString(),
                                termID));
                       for (Assessment a : Util.cacheAssessments){
                           a.setCourseID(courseID);
                           repository.insertAssessment(a);
                       } //save all cached assessments including courseID
                    }
                    else{ //if we have a term id AND the course is already saved
                        // update the course in the db.
                        //don't worry about assessments, those would have already been inserted when saving on assessment screen
                        repository.updateCourse(new Course(id,
                                editTextCourseTitle.getText().toString(),
                                editTextCourseStart.getText().toString(),
                                typeSpinner.getSelectedItem().toString(),
                                editTextCourseEnd.getText().toString(),
                                profNameField.getText().toString(),
                                profPhoneField.getText().toString(),
                                profEmailField.getText().toString(),
                                editNote.getText().toString(),
                                termID));
                    }
                }
                finish();
            }
        });
        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
                intent.putExtra("courseID", id);
                startActivity(intent);

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
    @Override
    protected void onResume() {
        super.onResume();
        if(id!=0){ //if the course is saved, then the assessment should have saved to db upon saving
            Util.cacheAssessments.clear();
            for(Assessment a : repository.getAllAssessments()){
                if(a.getCourseID() == id){
                    Util.cacheAssessments.add(a);
                }
            }
        }
        //if course isn't saved, assessment was added to cached assessments. adapter already pointed to cached assessments
        assessmentAdapter.notifyDataSetChanged();
    }
    private void showPopupMenu(View view) {
        // Inflate the menu using the PopupMenu class
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_coursedetails);

        // Set a click listener on the menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.courseStartAlert:

                        return true;
                    case R.id.courseEndAlert:

                        return true;
                    case R.id.shareNote:

                        return true;
                    case R.id.removeCourse:
                        if (id == 0) { // unsaved course
                            if (Util.cacheAssessments.size() == 0) {
                                if (position != 9999) {
                                    Util.cacheCourses.remove(position);
                                }
                                Util.cacheAssessments.clear();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Cannot delete until all assigned assessments are deleted.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        else {//course is in db
                            ArrayList<Assessment> temp = new ArrayList<>();
                            for (Assessment a : repository.getAllAssessments()) {
                                if (a.getCourseID() == id) {
                                    temp.add(a);
                                }
                            }
                            if (temp.size() != 0 || Util.cacheAssessments.size() != 0) {//there are associated assessments
                                Toast.makeText(getApplicationContext(), "Cannot delete until all assigned assessments are deleted.",
                                        Toast.LENGTH_LONG).show();
                            } else {//there are not any associated assessments
                                repository.deleteCourse(new Course(id, null, null,
                                        null,null, null,
                                        null, null, null, 0));
                                finish();
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Show the menu
        popupMenu.show();
    }

    private void updateDateLabel(EditText editText, Calendar calendar){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }


}
