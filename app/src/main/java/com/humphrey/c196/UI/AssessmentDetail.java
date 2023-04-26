package com.humphrey.c196.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.R;
import com.humphrey.c196.Utility.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {
    private int id;
    private int courseID;
    private int position;
    String myFormat = "MM/dd/yy";
    EditText title;
    EditText startDate;
    EditText endDate;
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    DatePickerDialog.OnDateSetListener startPicker;
    DatePickerDialog.OnDateSetListener endPicker;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    RadioGroup radioGroup;
    RadioButton performanceButton;
    RadioButton objectiveButton;
    String type;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        //set title of action bar
        if(getIntent().getStringExtra("title") == null){
            actionBar.setTitle("New Assessment");
        }
        else{
            actionBar.setTitle(getIntent().getStringExtra("title"));
        }
        setContentView(R.layout.activity_assessment_detail);
        repository = new Repository(getApplication());
        id = getIntent().getIntExtra("id", 0);
        courseID = getIntent().getIntExtra("courseID", 0);
        position = getIntent().getIntExtra("position", 9999);
        title = findViewById(R.id.editTextAssessmentTitle);
        startDate = findViewById(R.id.editTextAssessmentStart);
        endDate = findViewById(R.id.editTextAssessmentEnd);
        Button saveButton = findViewById(R.id.saveAssessment);
        radioGroup = findViewById(R.id.radio_group);
        performanceButton = findViewById(R.id.radio_performance);
        objectiveButton = findViewById(R.id.radio_objective);
        //set performance button to true by default
        performanceButton.setChecked(true);
        type = "Performance";
//set fields////////////////////////////////////////////////////////////
        title.setText(getIntent().getStringExtra("title"));
        //set the radio button from saved type, if type is not null
        if(getIntent().getStringExtra("type") != null){
            String temp = getIntent().getStringExtra("type");
            if (temp.equals("Performance")) {
                performanceButton.setChecked(true);
                type = "Performance";
            } else if (temp.equals("Objective")) {
                objectiveButton.setChecked(true);
                type = "Objective";
            }
        }

        // date field logic
        if(id == 0) {//unsaved assessment
            if(position == 9999) {//brand new assessment
                startDate.setText(sdf.format(new Date()));
                endDate.setText(sdf.format(new Date()));
                }
            else {//coming from list
                if (getIntent().getStringExtra("start") == null){
                    startDate.setText(sdf.format(new Date()));
                    endDate.setText(sdf.format(new Date()));
                }
                else{
                    startDate.setText((getIntent().getStringExtra("start")));
                    endDate.setText((getIntent().getStringExtra("end")));
                }
            }
        }
        else{
            startDate.setText(getIntent().getStringExtra("start"));
            endDate.setText(getIntent().getStringExtra("end"));
        }
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = startDate.getText().toString();
                try {
                    calendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startPicker,
                        calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel(startDate, calendarStart);
            }
        };
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = endDate.getText().toString();
                try {
                    calendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endPicker,
                        calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel(endDate, calendarEnd);
            }
        };
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(courseID == 0){
                    if(position == 9999){
                        Assessment assessment = new Assessment(id,
                                title.getText().toString(),
                                type,
                                startDate.getText().toString(),
                                endDate.getText().toString(),courseID);
                        Util.cacheAssessments.add(assessment);
                    }
                    else{
                        Assessment assessment = Util.cacheAssessments.get(position);
                        assessment.setTitle(title.getText().toString());
                        assessment.setType(type);
                        assessment.setStartDate(startDate.getText().toString());
                        assessment.setEndDate(endDate.getText().toString());
                    }
                }
                else{
                    if(id == 0){
                        repository.insertAssessment(new Assessment(id,
                                title.getText().toString(), type,
                                startDate.getText().toString(),
                                endDate.getText().toString(), courseID));
                    }
                    else{
                        repository.updateAssessment(new Assessment(id,
                                title.getText().toString(), type,
                                startDate.getText().toString(),
                                endDate.getText().toString(), courseID));
                    }
                }
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton selectedRadio = findViewById(checkedId);
                type = selectedRadio.getText().toString();
            }
        });
    }
    private void updateDateLabel(EditText editText, Calendar calendar){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.removeAssessment:
                if(id !=0){
                    repository.deleteAssessment(new Assessment(id, null, null, null, null, courseID));
                    finish();
                }
                else{
                    if(position == 0){
                        Util.cacheAssessments.remove(position);
                    }
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}