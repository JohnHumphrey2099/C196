package com.humphrey.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;

import java.util.ArrayList;
import java.util.List;

public class TermScreen extends AppCompatActivity {
    private Repository repository;
    private ImageView hamburger;
    private ImageView home;
    private TextView toolbarText;
    private TermAdapter termAdapter;
    List<Term> allTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(getApplication());
        setContentView(R.layout.activity_term_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hamburger = toolbar.findViewById(R.id.menuIcon);
        home = toolbar.findViewById(R.id.homeIcon);
        toolbarText = toolbar.findViewById(R.id.toolbarText);
        toolbarText.setText("All Terms");
        //recycler
        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTerms = repository.getALlTerms();
        termAdapter.setTermList(allTerms);

        //set buttons
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

    }
    private void showPopupMenu(View view) {
        // Inflate the menu using the PopupMenu class
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_termscreen);

        // Set a click listener on the menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuAllCourses:
                        goToCourseScreen(view);
                        return true;
                    case R.id.menuAllAssessments:
                        goToAssessmentScreen(view);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Show the menu
        popupMenu.show();
    }
    public void goToAssessmentScreen(View view){
        Intent intent = new Intent(TermScreen.this, AssessmentScreen.class);
        startActivity(intent);
    }

    public void goToCourseScreen(View view){
        Intent intent = new Intent(TermScreen.this, CourseScreen.class);
        startActivity(intent);
    }
    public void goToTermDetailScreen(View view) {
        Intent intent = new Intent(TermScreen.this, TermDetail.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        allTerms = repository.getALlTerms();
        termAdapter.setTermList(allTerms);
        termAdapter.notifyDataSetChanged();
        if (allTerms.size() != 0){
            TextView label = findViewById(R.id.termsScreenEmptyLabel);
            label.setVisibility(View.GONE);
        }
        else{
            TextView label = findViewById(R.id.termsScreenEmptyLabel);
            label.setVisibility(View.VISIBLE);
        }
    }
    private void goHome(View view) {
        Intent intent = new Intent(TermScreen.this, MainActivity.class);
        startActivity(intent);
    }
}