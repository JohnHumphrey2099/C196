package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.humphrey.c196.DAO.TermDAO;
import com.humphrey.c196.Database.DatabaseBuilder;
import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.R;
import com.humphrey.c196.Entity.Term;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository r = new Repository(getApplication());
        r.getALlTerms();
    }
}