package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.humphrey.c196.R;

public class AssessmentDetail extends AppCompatActivity {
    private int id;
    private int courseID;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        id = getIntent().getIntExtra("id", 0);
        courseID = getIntent().getIntExtra("termID", 0);
        position = getIntent().getIntExtra("position", 0);
    }
}