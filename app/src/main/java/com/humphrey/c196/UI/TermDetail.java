package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetail extends AppCompatActivity {
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();

    int id;
    String title;
    Term term;
    Repository repository;
    List<Course> associatedCourses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        repository = new Repository(getApplication());
        Button saveButton = findViewById(R.id.termDetailSaveButton);
        Button removeButton = findViewById(R.id.termRemoveButton);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTitle = findViewById(R.id.editTextTermTitle);
        editStartDate = findViewById(R.id.editTextTermStart);
        editEndDate = findViewById(R.id.editTextTermEnd);
        TextView label = findViewById(R.id.labelCurrentCourses);
        RecyclerView recyclerView = findViewById(R.id.coursesInsideTermDetails);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (id == 0){
            label.setText("Available Courses");

            removeButton.setEnabled(false);
        }
        else{
            label.setText("Assigned Courses");
            removeButton.setEnabled(true);
        }

        //set the EditText Views
        id = getIntent().getIntExtra("id",0);
        if (id == 0){
            editStartDate.setText(sdf.format(new Date()));
            editEndDate.setText(sdf.format(new Date()));
        }
        else{
            editStartDate.setText((getIntent().getStringExtra("start").toString()));
            editEndDate.setText((getIntent().getStringExtra("end").toString()));
            editTitle.setText(getIntent().getStringExtra("title").toString());
        }

        //get associated courses
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == id) associatedCourses.add(c);
        }
        courseAdapter.setCourseList(associatedCourses);

        // set up buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 0) {
                    repository.insertTerm(new Term(id, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString()));
                }
                else{
                    repository.updateTerm(new Term(id, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString()));
                }
                goToTermScreen(v);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(associatedCourses.size() == 0) {
                    deleteSelectedTerm(v);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't Remove Term until Associated Courses are Removed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editStartDate.getText().toString();
                try{
                    calendarStart.setTime(sdf.parse(info));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, startDate,
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
                updateDateLabel(editStartDate, calendarStart);
            }
        };

    }
    public void goToTermScreen(View view){
        Intent intent = new Intent(TermDetail.this, TermScreen.class);
        startActivity(intent);

    }
    public void deleteSelectedTerm(View view){
        id = getIntent().getIntExtra("id",0);
        repository.deleteTerm(new Term(id, null, null, null));
        goToTermScreen(view);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }
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
    private void updateDateLabel(EditText editText, Calendar calendar){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));

    }
}