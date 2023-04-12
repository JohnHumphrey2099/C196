package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    public void goToTermScreen(View view){
        Intent intent = new Intent(MainActivity.this, TermScreen.class);
        startActivity(intent);

    }

}